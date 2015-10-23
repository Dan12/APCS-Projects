package logicgatepack;

import java.awt.Color;
import java.awt.Graphics;

public class Connector {
    
    int conX1;
    int conY1;
    int conXO1;
    int conYO1;
    int conX2;
    int conY2;
    int conXO2;
    int conYO2;
    String cID1;
    String cID2;
    
    public Connector(int x, int y, String id, int xo, int yo){
        conX1 = x;
        conX2 = x+xo;
        conY1 = y;
        conY2 = y+yo;
        conXO1 = xo;
        conYO1 = yo;
        conXO2 = 0;
        conYO2 = 0;
        cID1 = id;
    }
    
    public void drawConnector(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(conX1+conXO1, conY1+conYO1, conX2+conXO2, conY2+conYO2);
    }
    
    public void moveStart(int x, int y){
        conX1 = x;
        conY1 = y;
    }
    
    public void moveEnd(int x, int y){
        conX2 = x;
        conY2 = y;
    }
    
    public void placeEnd(int x, int y, String id, int xo, int yo){
        conX2 = x;
        conY2 = y;
        conXO2 = xo;
        conYO2 = yo;
        cID2 = id;
    }
    
    public void changePosition(int x, int y){
        conX1 += x;
        conY1 += y;
        conX2 += x;
        conY2 += y;
    }
    
}
