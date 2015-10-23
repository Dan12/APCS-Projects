package lab800;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * File Name: Lab800.java
 *   Created: Jan 20, 2014
 *    Author: 
 */


public class Lab800 extends JPanel
{
  // Declare instance variables here...
  
  // Constructor
  public Lab800(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(255, 255, 255));
    
    JTextArea out = new JTextArea();
    JScrollPane jsp = new JScrollPane(out, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    super.setLayout(null);
    jsp.setBounds(0, 0, w, h);
    out.setEditable(false);
    out.setFont(new Font("Consolas", Font.BOLD, 20));
    out.setBackground(Color.BLACK);
    out.setForeground(Color.CYAN);
    super.add(jsp);
    
    out.append(PostNet.encode("60026"));
    
    
    Lab800Test.run(out);
    
  }
  
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab800");
    fr.setContentPane(new Lab800(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true); 
      //System.out.println(PostNet.encode("60026"));
      //System.out.println(PostNet.decode("|.||..||...||.....|.|.||....||.|"));
  }
  //</editor-fold>  

}
