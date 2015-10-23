package lab304;

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
 * File Name: Lab304.java
 *   Created: Nov 03, 2014
 *    Author: 
 */


public class Lab304 extends JPanel implements MouseListener, MouseMotionListener
{
  // Declare instance variables here...
  private JLabel mouseL, clickL, valueL, distL;
  private Card c0, c1, c2, c3, c4, lastCard;
  private Random rng;
  
  // Constructor
  public Lab304(int w, int h, JFrame f)
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
    
    distL = new JLabel("Difference between: ");
    distL.setFont(f1);
    distL.setBounds(600, 0, 200, 25);
    super.add(distL);
 
    rng = new Random();
    
    c0 = new Card("s1.png", 1);
    setRandom(c0, 0);
    System.out.println(c0);
    
    c1 = new Card("d3.png", 3);
    setRandom(c1, 1);
    System.out.println(c1);
    
    c2 = new Card("c7.png", 7);
    setRandom(c2, 2);
    System.out.println(c2);
    
    c3 = new Card("h13.png", 13);
    setRandom(c3, 3);
    System.out.println(c3);
    
    c4 = new Card("joker.png", 0);
    setRandom(c4, 4);
    System.out.println(c4);
  }
  
  public void setRandom(Card c, int i){
    Random r = new Random();
    int x = 160*i+r.nextInt(160-79);
    int y = r.nextInt(600-123);
    c.setXY(x, y);
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.drawImage(c0.getImage(), c0.getX(), c0.getY(), this);
    g.drawImage(c1.getImage(), c1.getX(), c1.getY(), this);
    g.drawImage(c2.getImage(), c2.getX(), c2.getY(), this);
    g.drawImage(c3.getImage(), c3.getX(), c3.getY(), this);
    g.drawImage(c4.getImage(), c4.getX(), c4.getY(), this);

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

    if(c0.contains(e.getX(), e.getY())){
      if(lastCard!=null){
        distL.setText("Difference Between: "+c0.differenceBetween(lastCard));
      }
      valueL.setText("Last Card Value: "+c0.getValue()+"");
      lastCard = c0;
    }
    if(c1.contains(e.getX(), e.getY())){
      if(lastCard!=null){
        distL.setText("Difference Between: "+c1.differenceBetween(lastCard));
      }
      valueL.setText("Last Card Value: "+c1.getValue()+"");
      lastCard = c1;
    }
    if(c2.contains(e.getX(), e.getY())){
      if(lastCard!=null){
        distL.setText("Difference Between: "+c2.differenceBetween(lastCard));
      }
      valueL.setText("Last Card Value: "+c2.getValue()+"");
      lastCard = c2;
    }
    if(c3.contains(e.getX(), e.getY())){
      if(lastCard!=null){
        distL.setText("Difference Between: "+c3.differenceBetween(lastCard));
      }
      valueL.setText("Last Card Value: "+c3.getValue()+"");
      lastCard = c3;
    }
    if(c4.contains(e.getX(), e.getY())){
      if(lastCard!=null){
        distL.setText("Difference Between: "+c4.differenceBetween(lastCard));
      }
      valueL.setText("Last Card Value: "+c4.getValue());
      lastCard = c4;
    }
    
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
    JFrame fr = new JFrame("Application: Lab304");
    fr.setContentPane(new Lab304(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.setVisible(true);
    fr.pack();  
  }
  //</editor-fold>  

}
