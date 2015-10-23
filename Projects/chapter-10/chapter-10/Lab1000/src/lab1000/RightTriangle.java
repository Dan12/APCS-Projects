package lab1000;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/*
 * File Name: RightTriangle.java
 *   Created: Mar 10, 2015
 *    Author: 
 */

public class RightTriangle extends Shape
{
    private int width;
    private int height;
    
    public RightTriangle(int x, int y, int w, int h, Color c){
        super(x,y,c);
        width = w;
        height = h;
    }
    
    @Override
    public String getName(){
        return "Right Triangle";
    }

    @Override
    public double getArea()
    {
        return (width*height)/2.0;
    }
    
    public void drawOnto(Graphics g){
        super.drawOnto(g);
        Polygon p = new Polygon();
        p.addPoint(getX(), getY());
        p.addPoint(getX()+width, getY());
        p.addPoint(getX(), getY()+height);
        g.fillPolygon(p);
    }
}
