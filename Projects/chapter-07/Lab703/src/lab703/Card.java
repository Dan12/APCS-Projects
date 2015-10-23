package lab703;

import java.awt.Image;
import java.awt.Toolkit;

public class Card extends Object 
{
  String filename;
  String suit;
  int val;
  Image cImage;
  float xPos;
  float yPos;
  float xSpeed;
  float ySpeed;
  boolean startAnim = false;
  int frame = 0;
  
  public Card(String f, String s, int v, int x, int y){
    filename = f;
    suit = s;
    val = v;
    cImage = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource(filename));
    xPos = x;
    yPos = y;
  }
  
  @Override
  public String toString(){
    return val+"of"+suit;
  }
  
  public void setCooridinates(int x, int y){
    xPos = x;
    yPos = y;
  }
  
  public void startAnimate(int tx, int ty){
    xSpeed = (tx-xPos)/(float)20;
    ySpeed = (ty-yPos)/(float)20;
    startAnim = true;
  }
  
  public void animate(){
    frame++;
    xPos+=xSpeed;
    yPos+=ySpeed;
    if(frame>=20){
        startAnim = false;
        frame = 0;
    }
  }
}
