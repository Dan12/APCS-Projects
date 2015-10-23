package lab1000;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/*
 * File Name: Shape.java
 *   Created: Mar 10, 2015
 *    Author: 
 */

public abstract class Shape extends Object{
    private Color color;
    private int xPos;
    private int yPos;
    
    public Shape(int x, int y, Color c){
        color = c;
        xPos = x;
        yPos = y;
    }
    
    public int getX(){
        return xPos;
    }
    
    public int getY(){
        return yPos;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void drawOnto(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.BOLD,16));
        g.drawString(this.getName()+" A = "+this.getArea(), getX(), getY());
        g.setColor(color);
    }
    
    public abstract String getName();
    
    public abstract double getArea();
}
