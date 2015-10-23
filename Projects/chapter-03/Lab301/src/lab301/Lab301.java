package lab301;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * File Name: Lab301.java
 *   Created: Sep 26, 2013
 *    Author:
 */


public class Lab301 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  private JComboBox ddMenu;
  private JButton move, left, right, sleep, wake;
  private Image bg;

  // Constructor
  public Lab301(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    ddMenu = new JComboBox();
    super.add(ddMenu);
    ddMenu.addItem(new Roomba(Color.BLUE, 100, 200, "Barry"));
    ddMenu.addItem(new Roomba(Color.GREEN, 400, 150, "Gary"));
    ddMenu.addItem(new Roomba(Color.MAGENTA, 500, 300, "Mary"));
    ddMenu.addItem(new Roomba(Color.PINK, 500, 100, "Peri"));
    move = new JButton("Move");
    move.addActionListener(this);
    super.add(move);
    left = new JButton("Turn Left");
    left.addActionListener(this);
    super.add(left);
    right = new JButton("Turn Right");
    right.addActionListener(this);
    super.add(right);
    sleep = new JButton("Hibernate");
    sleep.addActionListener(this);
    super.add(sleep);
    wake = new JButton("Wake Up");
    wake.addActionListener(this);
    super.add(wake);
    bg = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("bg.png"));
  }

  // Perform any custom painting (if necessary) in this method
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.drawImage(bg, 0, 0, super.getWidth(), super.getHeight(), this);
    for (int k = 0; k < ddMenu.getItemCount(); k++)
    {
      Roomba r = (Roomba) ddMenu.getItemAt(k);
      g.drawImage(r.getImage(), r.getX(), r.getY(), this);
      r.move();
    }
  }

  // Process GUI input in this method
  @Override
  public void actionPerformed(ActionEvent e)
  {
    Roomba r = (Roomba) ddMenu.getSelectedItem();
    if (e.getSource() == move){ r.steps = 0; r.move = true;}
    if (e.getSource() == right) r.turnRight();
    if (e.getSource() == left) r.turnLeft();
    if (e.getSource() == sleep) r.hibernate();
    if (e.getSource() == wake) r.activate();

    super.repaint();
  }

  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab301");
    fr.setContentPane(new Lab301(800, 600, fr));
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
