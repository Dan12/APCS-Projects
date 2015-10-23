package lab100;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Font;
import javax.swing.JTextArea;

/*
 * File Name: Lab100.java
 *   Created: Aug 28, 2014
 *    Author: 
 */


public class Lab100 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  private JTextArea out;
  
  // Constructor
  public Lab100(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    out = new JTextArea(24,80);
    out.setLineWrap(true);
    out.setFont(new Font("Monospaced", Font.BOLD, 14));
    super.add(out);
    
    out.append("            +---+\n            |   |\n        +---+---+\n        |   |   |\n    +---+---+---+\n    |   |   |   |\n+---+---+---+---+\n|   |   |   |   |\n+---+---+---+---+");
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
    JFrame fr = new JFrame("Application: Lab100");
    fr.setContentPane(new Lab100(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
