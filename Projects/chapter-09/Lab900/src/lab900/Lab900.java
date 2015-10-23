package lab900;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.ArrayList;

/*
 * File Name: Lab900.java
 *   Created: Mar 18, 2013
 *    Author:
 */


public class Lab900 extends JPanel
{
// Place instance variables here...
  private ArrayList<Drawable> list;


  // Constructor
  public Lab900(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(Color.WHITE);

    Message m1 = new Message("Hello", 100, 200, 36);
    m1.append(" World!");

    Message m2 = new Message("APCS", 500, 350, 10);

    FourSquare fs1 = new FourSquare(300, 150, 60, Color.RED);
    fs1.toggleQuadrant(2);
    FourSquare fs2 = new FourSquare(700, 500, 100, Color.BLUE);

    Annulus a1 = new Annulus(800, 400, 100);
    a1.changeXY(700, 300);
    Annulus a2 = new Annulus(100, 600, 50);
    

    list = new ArrayList<Drawable>();
    list.add(m1);
    list.add(m2);
    list.add(fs1);
    list.add(fs2);
    list.add(a1);
    list.add(a2);
  }

  // Perform any custom painting (when necessary) in this method
  @Override
  public void paintComponent(Graphics g)
  {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, super.getWidth(), super.getHeight());

    for (Drawable d : list)
    {
      //d.drawOnto(g);        //paint the Drawable onto the applet
      d.drawOnto(g, Color.GREEN); //paint the Drawable in GREEN
      //d.drawOnto(g, 2.5);   //paint the Drawable with a scale factor of 2.5
    }

  }


  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab900");
    fr.setContentPane(new Lab900(1024, 768, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);
  }
  //</editor-fold>
}
