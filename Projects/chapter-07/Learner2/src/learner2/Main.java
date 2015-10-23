package learner2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Main extends JPanel {
    
    // Declare instance variables here...

    Learner2 l1;
    Learner2 l2;
    Random rand = new Random();
    JSlider delay;
    int winWidth = 500;
    int winHeight = 500;
    
    int stepsPerGen = 100000;
    int currentStep = 0;
    int currentGen = 0;

    // Constructor
    public Main(JFrame f){
        super.setOpaque(true);
        super.setPreferredSize(new Dimension(winWidth, winHeight));
        super.setBackground(new Color(225, 225, 225));
        super.setLayout(null);
        l1 = new Learner2(100,100,Color.GREEN, winWidth, winHeight, "L1");
        l2 = new Learner2(500,500,Color.RED, winWidth, winHeight, "L2");
        delay = new JSlider(JSlider.HORIZONTAL, 0, 20, 5);
        delay.setBounds(340, winHeight-20, 80, 20);
        super.add(delay);
    }
    
    @Override  
    public void paintComponent(Graphics g){
        
        long startTime = System.nanoTime();
        
        super.paintComponent(g);
        
        l1.update(l2);
        l2.update(l1);
        l1.drawLearner(g);
        l2.drawLearner(g);
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("L1 Got Hit: "+l1.gHit+"  ,  L1 Hit: "+l1.hit+"  ,  L2 Got Hit: "+l2.gHit+"  ,  L2 Hit: "+l2.hit, 10, winHeight-10);
        g.drawString("FPS: "+((double)1000/delay.getValue()), 430, winHeight-10);
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        //System.out.println(duration);
        
        try {
            if(duration < delay.getValue())
              Thread.sleep(delay.getValue()-duration);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        super.repaint();
    }
    
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame fr = new JFrame("Application: Main");
        fr.setContentPane(new Main(fr));
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(10, 10);
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);  
    }
    
    public void stepTracker(){
        currentStep++;
        
        if(currentStep >= stepsPerGen){
            currentGen++;
            currentStep = 0;
            l1.mutate();
            l2.mutate();
        }
    }
    
}
