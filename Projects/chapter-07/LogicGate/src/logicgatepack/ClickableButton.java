package logicgatepack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class ClickableButton{
    Image cImg;
    int cXPos;
    int cYPos;
    int cWidth;
    int cHeight;

    public ClickableButton(Image i, int x, int y, int w, int h){
        cImg = i;
        cXPos = x;
        cYPos = y;
        cWidth = w;
        cHeight = h;
    }

    public void drawButton(Graphics g, ImageObserver t){
        g.drawImage(cImg, cXPos, cYPos, cWidth, cHeight, t);
        g.setColor(Color.BLACK);
        g.drawRect(cXPos, cYPos, cWidth, cHeight);
    }

    public boolean clickedButton(int cX, int cY){
        if(cX >= cXPos && cX <= cXPos+cWidth && cY >= cYPos && cY <= cYPos+cHeight)
            return true;
        return false;
    }
}
