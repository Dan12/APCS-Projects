package lab1300;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Lab1300D extends JPanel
{

  //////////////////////////////////////////////////////////////////////////////
  //
  //  Use recursion to produce the image shown in your lab sheet.  As a hint, 
  //  fill the entire middle 1/9th (in terms of area) with a yellow square.
  //  Then recursively call this method in the eight adjoining smaller
  //  subsections.  The base case occurs when the width is zero.
  //
  //////////////////////////////////////////////////////////////////////////////
  private void gasket(Graphics g, int x, int y, int width)
  {
    //g.setColor(new Color(width/3,width/3,width/3));
    if(width>0){
      g.fillRect(x+width/3, y+width/3, width/3, width/3);
      gasket(g, x, y, width/3);
      gasket(g, x+width/3, y, width/3);
      gasket(g, x+(width*2)/3, y, width/3);
      gasket(g, x, y+width/3, width/3);
      gasket(g, x+(width*2)/3, y+width/3, width/3);
      gasket(g, x, y+(width*2)/3, width/3);
      gasket(g, x+width/3, y+(width*2)/3, width/3);
      gasket(g, x+(width*2)/3, y+(width*2)/3, width/3);
    }
    else{
      g.fillRect(x, y, width/3, width/3);
    }
      
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // You  do not need to modify this section
  //////////////////////////////////////////////////////////////////////////////
  
  public Lab1300D(int w, int h)
  {
    super.setLayout(null);
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h-20));
  }
  
  public void paintComponent(Graphics g)
  {
    g.setColor(Color.BLUE);
    g.fillRect(0,0,729,729);
    g.setColor(Color.WHITE);
    this.gasket(g, 0, 0, 729);
  }
}
