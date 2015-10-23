package clientserverchat;

import java.awt.Color;
import java.awt.Graphics;

public class Square{
       public String squareName;
       public int xPos;
       public int yPos;
       
       public Square(String n, int x, int y){
           squareName = n;
           xPos = x;
           yPos = y;
       }
       
       public void drawSquare(Graphics g){
           g.setColor(Color.WHITE);
           g.drawString(squareName, xPos+DrawStuff.squareSize/2-((int) g.getFontMetrics().getStringBounds(squareName, g).getWidth()/2), yPos-DrawStuff.squareSize/2);
           g.setColor(Color.BLACK);
           g.fillRect(xPos, yPos, DrawStuff.squareSize, DrawStuff.squareSize);
       }
   }
