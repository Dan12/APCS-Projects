package lab1000;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/*
 * File Name: Trapazoid.java
 *   Created: Mar 10, 2015
 *    Author: 
 */

public class Trapezoid extends Shape
{
    private int base1;
    private int base2;
    private int height;
    
    public Trapezoid(int x, int y, int w1, int w2, int h, Color c){
        super(x,y,c);
        base1 = w1;
        base2 = w2;
        height = h;
    }
    
    @Override
    public String getName(){
        return "Trapezoid";
    }

    @Override
    public double getArea()
    {
        return (base1*height)+((base2-base1)*height)/2.0;
    }
    
    public void drawOnto(Graphics g){
        super.drawOnto(g);
        Polygon p = new Polygon();
        p.addPoint(getX(), getY());
        p.addPoint(getX()+base1, getY());
        p.addPoint(getX()+base2, getY()+height);
        p.addPoint(getX(), getY()+height);
        g.fillPolygon(p);
    }
}
