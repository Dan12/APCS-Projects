package lab302;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;


/*
 * File Name: Lab302.java
 *   Created: Sep 30, 2013
 *    Author:
 */

public class Lab302 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  private Timer swingTimer;
  private JButton go;
  private Block b1, b2, b3;
  private JLabel info1, info2, info3;

  // Constructor
  public Lab302(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(240, 240, 240));
    /*go = new JButton("Play ");
    go.setFont(new Font("Consolas", Font.BOLD, 18));
    super.add(go);
    go.addActionListener(this);
    swingTimer = null;
    b1 = new Block(500, 0, 70, w);
    b2 = new Block(250, 300, 0, w);
    b3 = new Block(100, 600, -15, w);
    info1 = new JLabel("(1) "+b1.toString());
    info2 = new JLabel("(2) "+b2.toString());
    info3 = new JLabel("(3) "+b3.toString());
    info1.setFont(new Font("Consolas", Font.BOLD, 12) );
    info2.setFont(new Font("Consolas", Font.BOLD, 12) );
    info3.setFont(new Font("Consolas", Font.BOLD, 12) );
    super.add(info1);
    super.add(info2);
    super.add(info3);*/

    super.add(new BlockForm(w));
    super.add(new BlockForm(w));
    super.add(new BlockForm(3*w/4));
    super.add(new BlockForm(3*w/5));
  }

  // Perform any custom painting (if necessary) in this method
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    //int bw = (int) b1.getBlockWidth();
    //g.drawRect((int)b1.getX(), 100, bw, bw);
    //g.drawRect((int)b2.getX(), 100, bw, bw);
    //g.drawRect((int)b3.getX(), 100, bw, bw);
  }

  // Process GUI input in this method
  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == go)
    {
      if ("Play ".equals(go.getText())) go.setText("Pause");
      else go.setText("Play ");
      if (swingTimer == null)
      {
        swingTimer = new Timer(5, this);
        swingTimer.start();
      }
      else
      {
        swingTimer.stop();
        swingTimer = null;
      }
    }
    if (e.getSource() == swingTimer)
    {
      //b1.move(0.1);
      //b2.move(0.1);
      //b3.move(0.1);

      //b1.checkCollision(b2);
      //b1.checkCollision(b3);
      //b2.checkCollision(b3);

      //info1.setText("(1) "+b1.toString());
      //info2.setText("(2) "+b2.toString());
      //info3.setText("(3) "+b3.toString());
    }

    super.repaint();
  }

  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab302");
    fr.setContentPane(new Lab302(1000, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);
  }
  //</editor-fold>

}
