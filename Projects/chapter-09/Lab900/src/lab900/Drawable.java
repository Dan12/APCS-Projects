package lab900;

import java.awt.Color;
import java.awt.Graphics;

interface Drawable
{
  public void drawOnto(Graphics g);
  public void drawOnto(Graphics g, Color c);
  public void drawOnto(Graphics g, double d);
}
