package lab801;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * File Name: MSConclude.java
 *   Created: Jan 4, 2014
 *    Author: 
 */

public class MSConclude extends JDialog
{
  private ImageIcon fail, win;
  private JLabel content;
  private JButton b;
  
  public MSConclude(JFrame parent, boolean modal)
  {
    super(parent, modal);
    super.setTitle("Game Over!");
    super.setLayout(null);
    super.setPreferredSize(new Dimension(410,420));
    super.setMinimumSize(new Dimension(410,420));
    super.setResizable(false);
    super.setIconImage(MSResources.BOMB.getImage());
    fail = new ImageIcon(Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("resources/fail.jpg")).getScaledInstance(super.getWidth(), super.getHeight(), Image.SCALE_SMOOTH));
    win = new ImageIcon(Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("resources/win.jpg")).getScaledInstance(super.getWidth(), super.getHeight(), Image.SCALE_SMOOTH));
    content = new JLabel();
    super.add(content);
    content.setBounds(0,0,super.getWidth(), super.getHeight());
    b = new JButton("OK");
    super.add(b);
    b.setFont(new Font("Verdana", Font.PLAIN, 12));
    b.setBounds((super.getWidth() - 60) / 2, super.getHeight() - 65, 60, 30); 
    b.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        MSConclude.super.setVisible(false);
      }
    });
  }
  
  public void showDialog(boolean gameWon)
  {
    if (gameWon) content.setIcon(win);
    else content.setIcon(fail);
    super.setVisible(true);
  }
  
}
