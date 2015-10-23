package lab900;

import java.awt.Color;
import java.awt.Graphics;

public class FourSquare extends Object implements Drawable
{
    private int xPos,yPos,width,row,col;
    private Color color;
    private boolean quadToggled = false;
    
    public FourSquare(int x, int y, int w, Color c){
        xPos = x;
        yPos = y;
        width = w;
        color = c;
    }
    
    public void setColor(Color c){
        color = c;
    }
    
    public void toggleQuadrant(int q){
        switch(q){
            case 1: row = 0;col = 1; break;
            case 2: row = 0;col = 0; break;
            case 3: row = 1;col = 0; break;
            case 4: row = 1;col = 1; break;
        }
        quadToggled = true;
    }

    @Override
    public void drawOnto(Graphics g)
    {
        g.setColor(color);
        g.drawLine(xPos, yPos, xPos, yPos+width);
        g.drawLine(xPos+width/2, yPos, xPos+width/2, yPos+width);
        g.drawLine(xPos+width, yPos, xPos+width, yPos+width);
        g.drawLine(xPos, yPos, xPos+width, yPos);
        g.drawLine(xPos, yPos+width/2, xPos+width, yPos+width/2);
        g.drawLine(xPos, yPos+width, xPos+width, yPos+width);
        if(quadToggled)
            g.fillRect(xPos+(width/2)*col, yPos+(width/2)*row, width/2, width/2);
    }

    @Override
    public void drawOnto(Graphics g, Color c)
    {
        g.setColor(c);
        g.drawLine(xPos, yPos, xPos, yPos+width);
        g.drawLine(xPos+width/2, yPos, xPos+width/2, yPos+width);
        g.drawLine(xPos+width, yPos, xPos+width, yPos+width);
        g.drawLine(xPos, yPos, xPos+width, yPos);
        g.drawLine(xPos, yPos+width/2, xPos+width, yPos+width/2);
        g.drawLine(xPos, yPos+width, xPos+width, yPos+width);
        if(quadToggled)
            g.fillRect(xPos+(width/2)*col, yPos+(width/2)*row, width/2, width/2);
    }

    @Override
    public void drawOnto(Graphics g, double d)
    {
        g.setColor(color);
        g.drawLine(xPos, yPos, xPos, (int) (yPos+width*d));
        g.drawLine((int) (xPos+(width/2)*d), yPos, (int) (xPos+(width/2)*d), (int) (yPos+width*d));
        g.drawLine((int) (xPos+width*d), yPos, (int) (xPos+width*d), (int) (yPos+width*d));
        g.drawLine(xPos, yPos, (int) (xPos+width*d), yPos);
        g.drawLine(xPos, (int) (yPos+(width/2)*d), (int) (xPos+width*d), (int) (yPos+(width/2)*d));
        g.drawLine(xPos, (int) (yPos+width*d), (int) (xPos+width*d), (int) (yPos+width*d));
        if(quadToggled)
            g.fillRect((int) (xPos+((width/2)*col)*d), (int) (yPos+((width/2)*row)*d), (int) ((width/2)*d), (int) ((width/2)*d));
    }
}
