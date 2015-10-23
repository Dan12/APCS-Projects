package lab801;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.Timer;


/*
 * File Name: MSTimer.java
 *   Created: Dec 30, 2013
 *    Author: 
 */

public class MSTimer extends JLabel implements ActionListener
{
    
    private Timer t;
    private int c;
  
  public MSTimer(int x, int y, int w, int h)
  {
    super.setBounds(x, y, w, h);
    super.setIcon(MSResources.HOURGLASS);
    super.setOpaque(true);
    super.setBackground(new Color(192, 192, 192));
    super.setForeground(new Color(0, 128, 0));
    super.setFont(MSResources.DIGITAL_NUMBERS);
    super.setText("00:00");
    super.setBorder(BorderFactory.createLoweredBevelBorder());
    
    t = new Timer(1000,this);
    
    c = 0;
  }
  
  public void start()
  {
      t.start();
  }
  
  public void stop()
  {
      t.stop();
  }
  
  public void reset()
  {
      c = 0;
  }
  
  @Override
  public void actionPerformed(ActionEvent e)
  {
      c++;
      int h = c / 3600;
      int m = (c - (h*3600))/60;
      int s = c - (h*3600+m*60);
      super.setText(String.format("%02d:%02d:%02d", h,m,s));
  }
  
}
