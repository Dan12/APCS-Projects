package lab801;


import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;


/*
 * File Name: MSCounter.java
 *   Created: Jan 24, 2013
 *    Author: 
 */

public class MSCounter extends JLabel
{
  
    private int mineNum;
    private int initMinNum;
    
  public MSCounter(int x, int y, int w, int h, int mr)
  {
    super.setBounds(x, y, w, h);
    super.setIcon(MSResources.BOMB32X32);
    super.setOpaque(true);
    super.setBackground(new Color(192, 192, 192));
    super.setForeground(new Color(0, 128, 0));
    super.setFont(MSResources.DIGITAL_NUMBERS);
    super.setBorder(BorderFactory.createLoweredBevelBorder());
    
    mineNum = mr;
    initMinNum = mr;
    super.setText(mineNum+"");
  }
  
  public void increase()
  {
      mineNum++;
      if(mineNum > initMinNum)
        super.setText(initMinNum+"");
      else if(mineNum > 0)
        super.setText(mineNum+"");
  }
  
  public void decrease()
  {
      mineNum--;
      if(mineNum < 0)
        super.setText("0");
      else if(mineNum < initMinNum)
        super.setText(mineNum+"");
  }
  
  public void reset()
  {
      mineNum = initMinNum;
      super.setText(mineNum+"");
  }     
}
