package lab501;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/*
 * File Name: Lab501.java
 *   Created: Sep 22, 2014
 *    Author: 
 */


public class Lab501 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  SortForm p1,p2,p3,p4,p5,p6;
  
  // Constructor
  public Lab501(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    p1 = new SortForm();
    super.add(p1);
    p2 = new SortForm();
    super.add(p2);
    p3 = new SortForm();
    super.add(p3);
    p4 = new SortForm();
    super.add(p4);
    p5 = new SortForm();
    super.add(p5);
    p6 = new SortForm();
    super.add(p6);
    int n = JOptionPane.showConfirmDialog(
      null,
      "Sort Letters?",
      "Question",
      JOptionPane.YES_NO_OPTION);

      if(true){
        p1.numSort = false;
        p2.numSort = false;
        p3.numSort = false;
        p4.numSort = false;
        p5.numSort = false;
        p6.numSort = false;
      }
      else {
        p1.numSort = true;
        p2.numSort = true;
        p3.numSort = true;
        p4.numSort = true;
        p5.numSort = true;
        p6.numSort = true;
      }
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
    JFrame fr = new JFrame("Application: Lab501");
    fr.setContentPane(new Lab501(800, 620, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
