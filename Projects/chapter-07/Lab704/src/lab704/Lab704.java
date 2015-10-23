package lab704;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * File Name: Lab704.java
 *   Created: Dec 5, 2013
 *    Author: 
 */

public class Lab704 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  private JButton go;
  private JTextField input;
  private JTextArea out;
  private JLabel text;
  private JScrollPane jsp;
  
  // Constructor
  public Lab704(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    
    super.setLayout(null);
    Font ff = new Font("Monospaced", Font.BOLD, 20);
    text = new JLabel("Enter an odd whole number:");
    text.setBounds(10,10,350,25);
    text.setFont(ff);
    super.add(text);
    
    input = new JTextField();
    input.setBounds(340, 10, 60, 25);
    input.setFont(ff);
    super.add(input);
    
    go = new JButton("Calculate");
    go.setBounds(410, 10, 150, 25);
    go.setFont(ff);
    super.add(go);
    go.addActionListener(this);
    
    out = new JTextArea();
    out.setFont(ff);
    out.setEditable(false);
    out.setLineWrap(false);
    jsp = new JScrollPane(out);
    jsp.setBounds(10, 50, w-20, h-60);
    super.add(jsp);
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
    int[][] x = new int[1][1];
    try
    {
      int n = Integer.parseInt(input.getText());
      x = MagicSquare.makeMagicSquare(n);
    }
    catch (NumberFormatException nfe)
    {
      //x = new int[][]{{1}};
      //x = new int[][]{{2}};
      //x = new int[][]{{5,5},{5,5}};
      //x = new int[][]{{8,3,6},{7,4,1},{2,5,9}};
      //x = new int[][]{{10,3,8},{5,7,9},{6,11,4}};
      //x = new int[][]{{8,1,6},{3,5,7},{4,9,2}};
      x = new int[][]{{1,15,14,4},{12,6,7,9},{8,10,11,5},{13,3,2,16}};
    }
    
    String output = MagicSquare.toString(x);    
    output += "\n\n           row sums: ";
    for(int i = 0; i < x.length; i++){
      output += "  "+MagicSquare.rowSum(x, i);
    }
    output += "\n\n         colum sums: ";
    for(int i = 0; i < x[0].length; i++){
      output += "  "+MagicSquare.rowSum(x, i);
    }
    output += "\n\nmajor diagonal sums: ";
    output += "  "+MagicSquare.majorDiagonalSum(x);
    output += "\n\nminor diagonal sums: ";
    output += "  "+MagicSquare.minorDiagonalSum(x);
    output += "\n\n       Magic Square: ";
    output += "  "+MagicSquare.isMagic(x);

    out.setText(output);   
    
    super.repaint();
  }
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab704");
    fr.setContentPane(new Lab704(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
