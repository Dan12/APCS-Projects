package survive;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button {
    private int width;
    private int height;
    private int xPos;
    private int yPos;
    private String text;
    private Font font;
    private int padding = 4;
    private int stringXMove = -1;
    private int stringYMove;
    private int fontSize = 30;
    private Color color;
    private boolean selected = false;
    
    //Constructor, specifies x,y,width,height, and text of button
    public Button(int x, int y, int w, int h, String n, int fs){
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        text = n;
        if(fs != -1)
            fontSize = fs;
        font = new Font("Arial", Font.PLAIN, fontSize);
        stringYMove = height-(height-fontSize)/2;
        color = Color.LIGHT_GRAY;
    }
    
    //Draws Button
    public void drawButton(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRoundRect(xPos, yPos, width, height, padding, padding);
        g.setColor(color);
        if(selected)
            g.setColor(Color.GRAY);
        g.fillRect(xPos+padding, yPos+padding, width-padding*2, height-padding*2);
        g.setColor(Color.BLACK);
        g.setFont(font);
        if(stringXMove == -1)
            stringXMove = (int) ((width-g.getFontMetrics(font).getStringBounds(text, g).getWidth())/2);
        g.drawString(text, xPos+stringXMove, yPos+stringYMove-fontSize/8);
    }
    
    /*mouse event calls, shades button on mouse hover, 
    **returns true if button clicked*/
    public boolean mouseEvent(int x, int y, boolean clicked){
        if(!clicked){
            if(x > xPos && x < xPos+width && y > yPos && y < yPos+height)
                color = Color.GRAY;
            else
                color = Color.LIGHT_GRAY;
        }
        else{
            if(x > xPos && x < xPos+width && y > yPos && y < yPos+height)
                return true;
        }
        return false;
    }
    
    public void setText(String s){
        text = s;
        stringXMove = -1;
    }
    
    public void setFontSize(int s){
        if(s != -1)
            fontSize = s;
        font = new Font("Arial", Font.PLAIN, fontSize);
        stringYMove = height-(height-fontSize)/2;
    }
    
    public void selected(){
    	selected = true;
    }
    
    public void unselected(){
    	selected = false;
    }
}
