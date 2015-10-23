package lab300;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import javax.swing.JLabel;

/*
 * File Name: Lab300.java
 *   Created: Sep 26, 2013
 *    Author: 
 */


public class Lab300 extends JPanel implements MouseListener, MouseMotionListener
{
  // Declare instance variables here...
  private JLabel mouseL, clickL, valueL, distL;
  private Card c0, c1, c2, c3, c4, last;
  private Random rng;
  
  // Constructor
  public Lab300(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225)); 
    super.addMouseListener(this);
    super.addMouseMotionListener(this);
    
    Font f1 = new Font("Consolas", Font.BOLD, 16);
    super.setLayout(null);
    
    mouseL = new JLabel("Mouse at ( , )");
    mouseL.setFont(f1);
    mouseL.setBounds(0, 0, 200, 25);
    super.add(mouseL);
    
    clickL = new JLabel("Click at ( , )");
    clickL.setFont(f1);
    clickL.setBounds(200, 0, 200, 25);
    super.add(clickL);
    
    valueL = new JLabel("Last Card Value: ");
    valueL.setFont(f1);
    valueL.setBounds(400, 0, 200, 25);
    super.add(valueL);
    
    distL = new JLabel("Distance between: ");
    distL.setFont(f1);
    distL.setBounds(600, 0, 200, 25);
    super.add(distL);
 
    rng = new Random();
    
    //c0 = new Card("s1.png", 1);
    //c0.setXY(250, 50);
    //System.out.println(c0);
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    //g.drawImage(c0.getImage(), c0.getX(), c0.getY(), this);

  }
  
  // Process GUI input in this method
  @Override  
  public void mouseMoved(MouseEvent e)
  {
    mouseL.setText("Mouse at ("+e.getX()+", "+e.getY()+")");
  }

  @Override 
  public void mousePressed(MouseEvent e)
  {
    int mx = e.getX(), my = e.getY();
    if (e.getButton() == MouseEvent.BUTTON1) clickL.setText("Click at ("+mx+", "+my+")");
    
    
    super.repaint();
  }
  
  @Override public void mouseDragged(MouseEvent e){/* not implemented */}
  @Override public void mouseEntered(MouseEvent e){/* not implemented */}
  @Override public void mouseExited(MouseEvent e){/* not implemented */}
  @Override public void mouseClicked(MouseEvent e){/* not implemented */}
  @Override public void mouseReleased(MouseEvent e){/* not implemented */}
  
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab300");
    fr.setContentPane(new Lab300(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  

}
