package lab900;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Message extends Object implements Drawable
{
  
    private int xPos,yPos,size;
    private String mes;
    
    public Message(String m, int x, int y, int s){
        mes = m;
        xPos = x;
        yPos = y;
        size = s;
    }
    
    public void append(String e){
        mes+=e;
    }

    @Override
    public void drawOnto(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Consalas",Font.PLAIN,size));
        g.drawString(mes, xPos, yPos);
    }

    @Override
    public void drawOnto(Graphics g, Color c)
    {
        g.setColor(c);
        g.setFont(new Font("Arial",Font.PLAIN,size));
        g.drawString(mes, xPos, yPos);
    }

    @Override
    public void drawOnto(Graphics g, double d)
    {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.PLAIN,(int) (size*d)));
        g.drawString(mes, xPos, yPos);
    }
}
