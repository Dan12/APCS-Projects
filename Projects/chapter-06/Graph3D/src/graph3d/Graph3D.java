package graph3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * File Name: Graph3D.java
 *   Created: Oct 23, 2014
 *    Author: 
 */


public class Graph3D extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  private static int width = 700;
  private static int height = 700;
  private int xMax = 2000;
  private int yMax = 2000;
  private int increment = 1;
  private double half = 8.3;
  private int speed = 30;
  //private ArrayList<Integer> zPoints = new ArrayList<Integer>();
  
  // Constructor
  public Graph3D(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    
  }
  
  public double zFunction(double x, double y){
    return Math.sin(Math.abs(x)+Math.abs(y));
    //return ((5/(4+Math.sin(x*x*y*y))));
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    drawPoints(g);
    setMove();
  }
  
  public void drawPoints(Graphics g){
    double max = -100000;
    double min = 100000;
    double xTicks = (xMax)/((double)(width));
    double yTicks = (yMax)/((double)(height));
    for(int i = -(height/2); i <= height/2; i += increment){
      for(int ii = -(width/2); ii<= width/2; ii += increment){
        double x = Math.toRadians(i*xTicks);
        double y = Math.toRadians(ii*yTicks);
        double z = zFunction(x, y);
        if(z>max)
          max = z;
        if(z<min)
          min = z;
      }
    }
    double scale = (255/(max-min));
    for(int i = -(height/2); i <= height/2; i += increment){
      for(int ii = -(width/2); ii<= width/2; ii += increment){
        double x = Math.toRadians(i*xTicks);
        double y = Math.toRadians(ii*yTicks);
        double z = zFunction(x, y);
        g.setColor(new Color(0,(int)((z-min)*scale),0));
        g.fillRect(i+(width/2), ii+(height/2), increment, increment);
      }
    }
  }
  
  public void setMove(){
    if(xMax>50)
      xMax-=speed;
    if(yMax>50)
      yMax-=speed;
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
    JFrame fr = new JFrame("Application: Graph3D");
    fr.setContentPane(new Graph3D(width, height, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
    while(true){
      try {
				Thread.sleep(10);
			} catch (InterruptedException interruptedexception) {
				System.out.println(interruptedexception.getMessage());
			}
      fr.repaint();
    }
  }
  //</editor-fold>  

}
