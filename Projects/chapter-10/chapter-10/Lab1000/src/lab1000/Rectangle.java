package lab1000;

import java.awt.Color;
import java.awt.Graphics;

/*
 * File Name: Rectangle.java
 *   Created: Mar 10, 2015
 *    Author: 
 */

public class Rectangle extends Shape
{
    private int width;
    private int height;
    
    public Rectangle(int x, int y, int w, int h, Color c){
        super(x,y,c);
        width = w;
        height = h;
    }
    
    @Override
    public String getName(){
        return "Rectangle";
    }

    @Override
    public double getArea()
    {
        return width*height;
    }
    
    public void drawOnto(Graphics g){
        super.drawOnto(g);
        g.fillRect(getX(), getY(), width, height);
    }
  
}
