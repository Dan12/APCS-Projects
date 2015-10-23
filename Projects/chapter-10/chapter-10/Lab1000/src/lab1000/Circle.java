package lab1000;

import java.awt.Color;
import java.awt.Graphics;

/*
 * File Name: Circle.java
 *   Created: Mar 10, 2015
 *    Author: 
 */

public class Circle extends Shape
{

    private int radius;
    
    public Circle(int x, int y, int r, Color c){
        super(x,y,c);
        radius = r;
    }
    
    @Override
    public String getName(){
        return "Circle";
    }

    @Override
    public double getArea()
    {
        return 3.14*radius*radius;
    }
    
    public void drawOnto(Graphics g){
        super.drawOnto(g);
        g.fillOval(getX(), getY(), radius*2, radius*2);
    }
  
}
