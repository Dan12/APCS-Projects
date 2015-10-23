package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * File Name: Main.java
 *   Created: Jan 29, 2015
 *    Author: 
 */


public class Main extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  Learner l1;
  Learner l2;
  
  // Constructor
  public Main(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    l1 = new Learner(100,100);
    l2 = new Learner(500,500);
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    l1.update(l2);
    l2.update(l1);
    g.setColor(Color.RED);
    drawLearner(g,l1);
    g.setColor(Color.GREEN);
    drawLearner(g,l2);
    g.setColor(Color.BLACK);
    g.drawString("L1 Score: "+l1.score+"      L2 Score: "+l2.score, 10, 590);
//    try {
//      Thread.sleep(1);
//    } catch (InterruptedException interruptedexception) {
//      System.out.println(interruptedexception.getMessage());
//    }
    super.repaint();
  }
  
  public void drawLearner(Graphics g, Learner l){
    g.fillOval((int)l.xL-(int)l.radiusL, (int)l.yL-(int)l.radiusL, (int)l.radiusL*2, (int)l.radiusL*2);
    g.setColor(Color.BLACK);
    g.drawLine((int)l.xL, (int)l.yL, (int) (l.xL+Math.cos(Math.toRadians(l.directionL))*l.radiusL*2), (int) (l.yL+Math.sin(Math.toRadians(l.directionL))*l.radiusL*2));
    for(int i = 0; i < l.bullets.size(); i++){
      g.fillOval((int) l.bullets.get(i).xB - (int) l.bullets.get(i).radiusB, (int) l.bullets.get(i).yB - (int) l.bullets.get(i).radiusB, (int) l.bullets.get(i).radiusB*2, (int) l.bullets.get(i).radiusB*2);
    }
  }
  
  // Process GUI input in this method
  @Override  
  public void actionPerformed(ActionEvent e)
  {
    
    
    super.repaint();
  }
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Main");
    fr.setContentPane(new Main(600, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
