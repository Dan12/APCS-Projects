package lab304;

import java.awt.Image;
import java.awt.Toolkit;

/*
 * File Name: Card.java
 *   Created: Sep 26, 2013
 *    Author: 
 */

public class Card extends Object 
{
  // Declare instance variables here...
  private Image i;
  private int n;
  private int xVal;
  private int yVal;
  private String name;

  // Constructor
  public Card(String fn, int v){
    xVal = 0;
    yVal = 0;
    i = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource(fn));
    name = fn.replace(".png", "");
    n = v;
  }
  
  public int getX(){
    return xVal;
  }
  
  public int getY(){
    return yVal;
  }
  
  public int getValue(){
    return n;
  }
  
  public Image getImage(){
    return i;
  }
  
  public void setXY(int x, int y){
    xVal = x;
    yVal = y;
  }
  
  public boolean contains(int x, int y){
    if(x <= xVal+79 && x >= xVal && y <= yVal+123 && y >= yVal)
      return true;
    return false;
  }
  
  public int differenceBetween(Card c){
    if(this.getValue() == 0 || c.getValue() == 0 || (this.getValue() == 1 && c.getValue() == 13) || (c.getValue() == 1 && this.getValue() == 13))
      return 1;
    return Math.abs(this.getValue()-c.getValue());
  }
  
  public String toString(){
    return name+" ("+xVal+", "+yVal+") value = "+this.getValue();
  }
}
