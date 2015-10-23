package lab303;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;


/*
 * File Name: Lab303.java
 *   Created: Oct 27, 2014
 *    Author:
 */


public class Lab303 extends JPanel implements ActionListener
{
  // Declare instance variables here...



  // Constructor
  public Lab303(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    super.setLayout(null);
    JTextArea out = new JTextArea();
    out.setFont(new Font("Consolas", Font.BOLD, 16));
    out.setLineWrap(false);
    out.setEditable(false);
    JScrollPane jsp = new JScrollPane(out, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jsp.setBounds(5, 5, w - 10, h - 10);
    super.add(jsp);

    out.append("Test your Complex class methods by adding output to this window....\n\n");
    Complex c1 = new Complex(3.0, -4.0);
    out.append("c1 = "+c1+"\n");
    Complex c2 = new Complex(5, 12);
    out.append("c2 = "+ c2+"\n\n");
    out.append("c2.magnitude() = "+c2.magnitude()+"\n");



    CTest.run(out);
    out.setCaretPosition(out.getDocument().getLength());
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
    JFrame fr = new JFrame("Application: Lab303");
    fr.setContentPane(new Lab303(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);
  }
  //</editor-fold>

}
