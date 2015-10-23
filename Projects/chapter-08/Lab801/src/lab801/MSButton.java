package lab801;

import java.awt.Color;
import javax.swing.JButton;



/*
 * File Name: Lab801.java
 *   Created: Jan 24, 2013
 *    Author: 
 */

public class MSButton extends JButton
{
  
    private int row;
    private int col;
    private int bSize = MSPanel.BUTTON_SIZE;
    
  public MSButton(int r, int c)
  {
    row = r;
    col = c;
    super.setIcon(null);
    super.setBounds(c*bSize,r*bSize,bSize,bSize);
    //
    super.setBackground(new Color(100,100,100,100));
  }
  
  public boolean flagDisplayed()
  {
      if(super.getIcon() == MSResources.FLAG)
        return true;
      return false;
  }
  
  public boolean questionDisplayed()
  {
    if(super.getIcon() == MSResources.QUESTION)
        return true;
      return false;
  }
  
  public void reset()
  {
      super.setIcon(null);
      super.setVisible(true);
  }
  
  public int getRow()
  {
    return row;
  }
  
  public int getColumn()
  {
    return col;
  }
  
  public void cycleIcon()
  {
      if(super.getIcon() == null)
          super.setIcon(MSResources.FLAG);
      else if(super.getIcon() == MSResources.FLAG)
          super.setIcon(MSResources.QUESTION);
      else
          super.setIcon(null);
  }
  
  public void clearButton(){
      super.setVisible(false);
  }
  
  @Override
  public String toString()
  {
    return "row = "+row+"  col = "+col+"  visible = "+super.isVisible()+"  icon = "+super.getIcon();
  }
}
