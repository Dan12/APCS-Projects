package lab1300;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

/*
 * File Name: Lab1300.java
 *   Created: Dec 12, 2013
 *    Author: 
 */


public class Lab1300 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  
  
  // Constructor
  public Lab1300(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.add("Exercise A", new Lab1300A(w,h));
    super.add(tabbedPane);
    
    tabbedPane.add("Exercise B", new Lab1300B(w,h));
    super.add(tabbedPane);
    
    tabbedPane.add("Exercise C", new Lab1300C(w,h));
    super.add(tabbedPane);
    
    tabbedPane.add("Exercise D", new Lab1300D(w,h));
    super.add(tabbedPane);
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
    JFrame fr = new JFrame("Application: Lab1300");
    fr.setContentPane(new Lab1300(729, 759, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
