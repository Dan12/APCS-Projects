package lab201;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * File Name: Lab201.java
 *   Created: Sep 4, 2014
 *    Author: 
 */


public class Lab201 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  
  
  // Constructor
  public Lab201(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    int winHeight = super.getHeight();
    int winWidth = super.getWidth();
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.BLACK);
    int bWidth = winWidth-100;
    int bHeight = winHeight-100;
    Rectangle border = new Rectangle(50,50,bWidth,bHeight);
    g2.draw(border);
    Random random1 = new Random();
    
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    
    if (((int) (0.25*bWidth)) > 0 && ((int) (0.25*bHeight)) > 0){
      width = (int) (random1.nextInt((int) (0.5*bWidth))+(0.25*bWidth));
      height = (int) (random1.nextInt((int) (0.5*bHeight))+(0.25*bHeight));
      x = random1.nextInt((bWidth-width))+50;
      y = random1.nextInt(bHeight-height)+50;
    }
    
    Rectangle r1 = new Rectangle(x,y,width,height);
    g2.setColor(Color.RED);
    g2.fill(r1);
    
    if (((int) (0.25*bWidth)) > 0 && ((int) (0.25*bHeight)) > 0){
      width = (int) (random1.nextInt((int) (0.5*bWidth))+(0.25*bWidth));
      height = (int) (random1.nextInt((int) (0.5*bHeight))+(0.25*bHeight));
      x = random1.nextInt((bWidth-width))+50;
      y = random1.nextInt(bHeight-height)+50;
    }
    
    Rectangle r2 = new Rectangle(x,y,width,height);
    g2.setColor(Color.YELLOW);
    g2.fill(r2);
    
    Rectangle r3 = r1.intersection(r2);
    g2.setColor(Color.ORANGE);
    g2.fill(r3);
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
    JFrame fr = new JFrame("Application: Lab201");
    fr.setContentPane(new Lab201(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(true);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
