package lab301;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/*
 * File Name: Roomba.java
 *   Created: Sep 26, 2013
 *    Author:
 */

public class Roomba extends Object
{
  // Declare instance variables here...
  private Color col;
  private int xPos;
  private int yPos;
  private String name;
  private int direction = 0;
  public int steps = 0;
  private int inc = 2;
  private int maxSteps = 300;
  public boolean move = false;
  boolean sleeping = false;
  
  private Image canvas;

  // Constructor
  public Roomba(Color c, int x, int y, String n){
    col = c;
    xPos = x;
    yPos = y;
    name = n;
    canvas = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);
  }
  
  public Image getImage(){
    Graphics2D g = (Graphics2D) canvas.getGraphics();
    g.rotate(Math.toRadians(direction), 50, 50);
    g.setColor(col);
    g.fillOval(0, 0, 100, 100);
    g.setColor(Color.BLACK);
    g.fillRect(75, 25, 10, 50);
    g.setFont(new Font("TimesRoman", Font.BOLD, 14));
    if(sleeping)
      g.drawString("Sleeping", 7, 57);
    return canvas;
  }
  
  public int getX(){
    return xPos;
  }
  
  public int getY(){
    return yPos;
  }
  
  public String toString(){
    return name+" ("+xPos+","+yPos+")";
  }
  
  public void turnLeft(){
    if(!sleeping){
      direction -= 45;
      if(direction<0)
        direction = 315;
    }
  }
  
  public void turnRight(){
    if(!sleeping){
      direction += 45;
      if(direction>315)
        direction = 0;
    }
  }
  
  public void move(){
    if(!sleeping && steps < maxSteps && move){
      switch (direction){
        case 0:
          xPos+=inc;
          break;
        case 45:
          xPos+=inc;
          yPos+=inc;
          break;
        case 90:
          yPos+=inc;
          break;
        case 135:
          xPos-=inc;
          yPos+=inc;
          break;
        case 180:
          xPos-=inc;
          break;
        case 225:
          xPos-=inc;
          yPos-=inc;
          break;
        case 270:
          yPos-=inc;
          break;
        case 315:
          xPos+=inc;
          yPos-=inc;
          break;
      }
      steps+=inc;
    }
    if(steps>=maxSteps){
      move = false;
    }
  }
  
  public void hibernate(){
    sleeping = true;
  }
  
  public void activate(){
    sleeping = false;
  }
  
}
