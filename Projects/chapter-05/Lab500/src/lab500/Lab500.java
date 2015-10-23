package lab500;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * File Name: Lab500.java
 *   Created: Sep 18, 2014
 *    Author: 
 */


public class Lab500 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  private JButton move;
  private boolean pressed = false;
  
  // Constructor
  public Lab500(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    move = new JButton("Move");
    super.add(move);
    move.addActionListener(this);
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    
    g.setColor(Color.RED);
    g.drawOval(0, 0, 300, 300);
    g.setColor(Color.BLUE);
    g.drawOval(100, 100, 300, 300);
    
    if (pressed){
      g.setColor(Color.BLACK);
      Random r1 = new Random();
      int x = r1.nextInt(360);
      int y = r1.nextInt(360);
      distance(g, x, y);
      g.fillOval(x, y, 40, 40);
    }
  }
  
  public void distance(Graphics g, int x, int y){
    double distance1 = Math.sqrt((Math.pow((x+20)-150,2))+(Math.pow((y+20)-150,2)));
    double distance2 = Math.sqrt((Math.pow((x+20)-250,2))+(Math.pow((y+20)-250,2)));
    if (distance1<=130)
      g.setColor(Color.RED);
    if (distance2<=130)
      g.setColor(Color.BLUE);
    if (distance1<=130 && distance2<=130)
      g.setColor(Color.MAGENTA);
  }
  
  // Process GUI input in this method
  @Override  
  public void actionPerformed(ActionEvent e)
  {
    pressed = true;
    
    super.repaint();
  }
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab500");
    fr.setContentPane(new Lab500(400, 400, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
