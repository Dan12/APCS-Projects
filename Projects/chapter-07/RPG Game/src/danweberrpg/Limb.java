//package src.danweberrpg;
package danweberrpg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Limb extends Object
{
    private Point point1;
    private Point point2;
    private double rAngle;
    private boolean isHead;
    private int minY;
    private double yDiff;
    String ID;
    int cID;
    private int pivotPoint;
    
    public Limb(Point p1, Point p2, double a, String id, int cid, int pp){
        point1 = new Point(p1.x, p1.y);
        point2 = new Point(p2.x, p2.y);
        rAngle = a;
        isHead = point1.equals(point2);
        updateMin();
        ID = id;
        cID = cid;
        yDiff = point2.y-point1.y;
        pivotPoint = pp;
    }
    
    @Override
    public String toString(){
        return (int)rAngle+","+point1.x+","+point1.y+","+point2.x+","+point2.y;
    }
    
    public void drawLimb(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform old = g2d.getTransform();
        if(pivotPoint == 1)
            g2d.rotate(Math.toRadians(rAngle), point1.x, point1.y);
        else
            g2d.rotate(Math.toRadians(rAngle), point2.x, point2.y);
        //draw shape/image (will be rotated)
 
        g.setColor(Color.BLACK);
        if(isHead)
            g.fillOval(point1.x-Main.headDiam/2, point1.y-Main.headDiam+Main.halfLimbWidth, Main.headDiam, Main.headDiam);
        else
            g.fillRoundRect(point1.x-Main.halfLimbWidth, minY-Main.halfLimbWidth, Main.limbWidth, (int)(Math.abs(yDiff)+Main.limbWidth), Main.limbWidth, Main.limbWidth);
        
        g2d.setTransform(old);
    }
    
    public double[] changeAngle(double c){
        rAngle+=c;
        double temp[] = new double[2];
        if(pivotPoint == 1){
            temp[0] = point1.getX()+Math.sin(Math.toRadians(rAngle+180))*Math.abs(yDiff);
            temp[1] = point1.getY()+Math.cos(Math.toRadians(rAngle))*Math.abs(yDiff);
        }
        else{
            temp[0] = point2.getX()+Math.sin(Math.toRadians(rAngle))*Math.abs(yDiff);
            temp[1] = point2.getY()+Math.cos(Math.toRadians(rAngle+180))*Math.abs(yDiff);
        }
        return temp;
    }
    
    public double[] changeLength(double c){
        double[] temp = new double[2];
        
        if(pivotPoint == 1){
            point2.setLocation(point2.getX(), point2.getY()+c);
            if(point2.getY() < point1.getY())
                point2.setLocation(point2.getX(), point1.getY());
            yDiff = point2.y-point1.y;
            temp[0] = point1.getX()+Math.sin(Math.toRadians(rAngle+180))*Math.abs(yDiff);
            temp[1] = point1.getY()+Math.cos(Math.toRadians(rAngle))*Math.abs(yDiff);
        }
        else{
            point1.setLocation(point1.getX(), point1.getY()+c);
            if(point1.getY() > point2.getY())
                point1.setLocation(point1.getX(), point2.getY());
            yDiff = point2.y-point1.y;
            temp[0] = point2.getX()+Math.sin(Math.toRadians(rAngle))*Math.abs(yDiff);
            temp[1] = point2.getY()+Math.cos(Math.toRadians(rAngle+180))*Math.abs(yDiff);
        }
        updateMin();
        
        return temp;
    }
    
    public void updateRotation(double x, double y){
        point1.setLocation(x, y);
        point2.setLocation(x, y+yDiff);
        updateMin();
    }
    
    public void updatePosition(int cx, int cy){
        point1.x+=cx;
        point1.y+=cy;
        point2.x+=cx;
        point2.y+=cy;
        updateMin();
    }
    
    public double getAngle(){
        return rAngle;
    }
    
    public double getYDiff(){
        return yDiff;
    }
    
    public void updateMin(){
        if(point1.y < point2.y)
            minY = point1.y;
        else
            minY = point2.y;
    }
    
}
