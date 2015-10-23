package lab401;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * File Name: Lab401.java
 *   Created: Sep 12, 2014
 *    Author: 
 */


public class Lab401 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  CalendarForm p1, p2, p3, p4, p5, p6;
  
  // Constructor
  public Lab401(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    
    p1 = new CalendarForm();
    super.add(p1);
    p2 = new CalendarForm();
    super.add(p2);
    p3 = new CalendarForm();
    super.add(p3);
    p4 = new CalendarForm();
    super.add(p4);
    p5 = new CalendarForm();
    super.add(p5);
    p6 = new CalendarForm();
    super.add(p6);
    
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    
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
    JFrame fr = new JFrame("Application: Lab401");
    fr.setContentPane(new Lab401(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
