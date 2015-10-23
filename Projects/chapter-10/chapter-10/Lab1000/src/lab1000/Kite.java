package lab1000;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/*
 * File Name: Kite.java
 *   Created: Mar 10, 2015
 *    Author: 
 */

public class Kite extends Shape
{
    private int width1;
    private int width2;
    private int height;
    
    public Kite(int x, int y, int w1, int w2, int h, Color c){
        super(x,y,c);
        width1 = w1;
        width2 = w2;
        height = h;
    }
    
    @Override
    public String getName(){
        return "Kite";
    }

    @Override
    public double getArea()
    {
        return (width1*height)/2+(width2*height)/2;
    }
    
    public void drawOnto(Graphics g){
        super.drawOnto(g);
        Polygon p = new Polygon();
        p.addPoint(getX(), getY()+height/2);
        p.addPoint(getX()+width1, getY());
        p.addPoint(getX()+width1+width2, getY()+height/2);
        p.addPoint(getX()+width1, getY()+height);
        g.fillPolygon(p);
    }
}
