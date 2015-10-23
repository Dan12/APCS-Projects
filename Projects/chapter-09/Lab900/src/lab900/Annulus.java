package lab900;

import java.awt.Color;
import java.awt.Graphics;

public class Annulus extends Object implements Drawable
{
  private int xPos,yPos,width;
  
  public Annulus(int x, int y, int w){
      xPos = x;
      yPos = y;
      width = w;
  }
  
  public void changeXY(int nx, int ny){
      xPos = nx;
      yPos = ny;
  }

    @Override
    public void drawOnto(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillOval(xPos, yPos, width, width);
        g.setColor(Color.BLUE.darker());
        g.fillOval(xPos+width/4, yPos+width/4, width/2, width/2);
    }

    @Override
    public void drawOnto(Graphics g, Color c)
    {
        g.setColor(c);
        g.fillOval(xPos, yPos, width, width);
        g.setColor(c.darker());
        g.fillOval(xPos+width/4, yPos+width/4, width/2, width/2);
    }

    @Override
    public void drawOnto(Graphics g, double d)
    {
        g.setColor(Color.BLUE);
        g.fillOval(xPos, yPos, (int) (width*d), (int) (width*d));
        g.setColor(Color.BLUE.darker());
        g.fillOval((int) (xPos+(width/4)*d), (int) (yPos+(width/4)*d), (int) ((width/2)*d), (int) ((width/2)*d));
    }
}
