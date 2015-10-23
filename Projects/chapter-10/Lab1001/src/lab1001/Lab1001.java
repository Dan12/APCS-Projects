package lab1001;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.ArrayList;

/*
 * File Name: Lab1001.java
 *   Created: Feb 20, 2014
 *    Author: David Rogers
 */


public class Lab1001 extends JPanel
{
  // Declare instance variables here...

  //private ArrayList<Shape> shapeList;


  // Constructor
  public Lab1001(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(Color.WHITE);
    //    shapeList = new ArrayList<Shape>();
    //    shapeList.add(new Circle(25, 80, 20, Color.BLUE));                   //x, y, r
    //    shapeList.add(new Rectangle(400, 350, 70, 140, Color.RED));          //x, y, w, h
    //    shapeList.add(new Square(300, 100, 200, Color.GREEN));               //x, y, w
    //    shapeList.add(new RightTriangle(50, 200, 180, 85, Color.ORANGE));    //x, y, w, h
    //    shapeList.add(new Trapezoid(25, 400, 100, 200, 150, Color.MAGENTA)); //x, y, b1, b2, h
    //    shapeList.add(new Kite(550, 200, 150, 50, 100, Color.PINK));         //x, y, w1, w2, h
    //    shapeList.add(new Circle(650, 450, 75, Color.CYAN));                 //x, y, r
  }

  @Override
  public void paintComponent(Graphics g)
  {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, super.getWidth(), super.getHeight());
    double total = 0;
    //for (Shape s : shapeList)
    {
      //s.drawOnto(g);
      //total += s.getArea();
    }
    g.setFont(new Font("Arial", Font.BOLD, 36));
    g.setColor(Color.BLACK);
    g.drawString("Total Area = "+total, 0, 40);
  }

  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab1001");
    fr.setContentPane(new Lab1001(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold>  
}
