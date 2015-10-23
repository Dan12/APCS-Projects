package lab801;


import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


/*
 * File Name: MSLabel.java
 *   Created: Jan 24, 2013
 *    Author: 
 */

public class MSLabel extends JLabel
{
  private static final Color[] colors = {new Color(200, 200, 200), new Color(0,0,255), new Color(0,130,0), new Color(255,0,0),
                                         new Color(0,0,132), new Color(132,0,0), new Color(0,130,132),
                                         new Color(0,0,0), new Color(132,130,132), Color.BLACK};
  
  private int rowNum;
  private int colNum;
  private int numBombs;
  
  public MSLabel(int r, int c, int v)
  {
    super.setVerticalAlignment(JLabel.CENTER);
    super.setHorizontalAlignment(JLabel.CENTER);
    super.setOpaque(true);
    super.setBackground(colors[0]);
    super.setBorder(new LineBorder(Color.DARK_GRAY, 1));    

    rowNum = r;
    colNum = c;
    numBombs = v;
    
    super.setBounds(c*MSPanel.BUTTON_SIZE, r*MSPanel.BUTTON_SIZE, MSPanel.BUTTON_SIZE, MSPanel.BUTTON_SIZE);
    if(numBombs < 9){
        super.setFont(new Font("Consolas", Font.BOLD, (int)(MSPanel.BUTTON_SIZE*0.8)));
        super.setText(""+numBombs+"");
        //System.out.println("NumBombs:"+numBombs);
        super.setForeground(colors[numBombs]);
    }
    else
        super.setIcon(MSResources.BOMB);
    super.setVisible(true);
  }
  
  public int getValue()
  {
    return numBombs;
  }
  
  public boolean isBomb()
  {
      if(numBombs > 8)
        return true;
      return false;
  }
  
  public void setIcon(ImageIcon x)
  {
      super.setIcon(x);
      super.setText("");
  } 
  
  public int getRow()
  {
    return rowNum;
  }
  
  public int getColumn()
  {
    return colNum;
  }
  
  public void reset()
  {
    if(numBombs < 9)
        super.setText(""+numBombs);
  }
}
