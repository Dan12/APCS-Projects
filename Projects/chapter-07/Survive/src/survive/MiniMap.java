package survive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

public class MiniMap {
    private Map map;
    private int width;
    private int height;
    private int xPos;
    private int yPos;
    private int windowX;
    private int windowY;
    private double mapScale;
    private int windowWidth;
    private int windowHeight;
    private int mapInitDragX = -1;
    private int mapInitDragY = -1;
    private int mapDragX = 0;
    private int mapDragY = 0;
    private boolean mousePressed = false;
    private LevelMaker levelMaker;
	
    //Constructor: xPos, yPos, width, height, map, and level maker
    public MiniMap(int x, int y, int w, int h, Map m, LevelMaker l){
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        map = m;
        windowX = 0;
        windowY = 0;
        levelMaker = l;
        resize();
    }

    //draw mini map
    public void drawMiniMap(Graphics g){
        //background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(xPos, yPos, width, height);

        //squares and spikes
        for(Square s : map.getSquareList()){
            g.setColor(Color.BLUE);
            g.fillRect(xPos+(int) ((s.getX()+map.getOffsetX())/mapScale), yPos+(int) ((s.getY()+map.getOffsetY())/mapScale), (int) (s.getWidth()/mapScale), (int) (s.getHeight()/mapScale));
            g.setColor(Color.RED);
            for(Spike sp : s.getSpikeList()){
                for(Polygon p : sp.getPolygonList()){
                    g.fillRect(xPos+(int) ((p.getBounds().getX()+map.getOffsetX())/mapScale), yPos+(int) ((p.getBounds().getY()+map.getOffsetY())/mapScale), (int) (Main.SPIKE_SIZE/mapScale), (int) (Main.SPIKE_SIZE/mapScale));
                }
            }   
        }

        //draw line check if it is on
        if(!levelMaker.getLineCheck().isOff()){
            g.setColor(Color.GREEN);
            g.drawLine(xPos+(int) ((levelMaker.getLineCheck().getX1Array()[0]+map.getOffsetX())/mapScale), yPos+(int) ((levelMaker.getLineCheck().getY1Array()[0]+map.getOffsetY())/mapScale), xPos+(int) ((levelMaker.getLineCheck().getX1Array()[2]+map.getOffsetX())/mapScale), yPos+(int) ((levelMaker.getLineCheck().getY1Array()[2]+map.getOffsetY())/mapScale));
            g.drawLine(xPos+(int) ((levelMaker.getLineCheck().getX2Array()[0]+map.getOffsetX())/mapScale), yPos+(int) ((levelMaker.getLineCheck().getY2Array()[0]+map.getOffsetY())/mapScale), xPos+(int) ((levelMaker.getLineCheck().getX2Array()[2]+map.getOffsetX())/mapScale), yPos+(int) ((levelMaker.getLineCheck().getY2Array()[2]+map.getOffsetY())/mapScale));
        }

        //draw view area
        g.setColor(Color.RED);
        g.drawRect(xPos+windowX+mapDragX, yPos+windowY+mapDragY, windowWidth-1, windowHeight-1);
        g.drawLine(xPos+windowX+mapDragX+(windowWidth/2-4),yPos+windowY+mapDragY+(windowHeight/2),xPos+windowX+mapDragX+(windowWidth/2+4),yPos+windowY+mapDragY+(windowHeight/2));
        g.drawLine(xPos+windowX+mapDragX+(windowWidth/2),yPos+windowY+mapDragY+(windowHeight/2-4),xPos+windowX+mapDragX+(windowWidth/2),yPos+windowY+mapDragY+(windowHeight/2+4));
    }

    //resize the mini map when map size is changed
    public void resize(){
        if(map.getWidth() > map.getHeight())
            mapScale = (map.getWidth()/(double)width);
        else
            mapScale = (map.getHeight()/(double)height);
        if(map.getWidth() <= Main.SCREEN_WIDTH)
            windowWidth = width;
        else
            windowWidth = (int) (((Main.SCREEN_WIDTH)/((double) map.getWidth()))*width);
        if(map.getHeight()<= Main.SCREEN_HEIGHT)
            windowHeight = height;
        else
            windowHeight = (int) (((Main.SCREEN_HEIGHT)/((double) map.getHeight()))*height);
    }

    //change position of the mini map view port based on key movements
    public void changedPosition(Map map){
        if(!mousePressed){
            windowX = (int) (map.getOffsetX()/mapScale);
            windowY = (int) (map.getOffsetY()/mapScale);
        }
    }

    //if mouse dragged inside window, move window and set map offsets
    public void mouseDragged(MouseEvent e){
        if(e.getX() >= xPos+windowX+mapDragX && e.getX() <= xPos+windowX+windowWidth+mapDragX && e.getY() >= yPos+windowY+mapDragY && e.getY() <= yPos+windowY+windowHeight+mapDragY){
            mapDragX = e.getX()-mapInitDragX;
            mapDragY = e.getY()-mapInitDragY;
            if(mapDragX+windowX < 0)
                mapDragX = -windowX;
            if(mapDragX+windowX+windowWidth > width)
                mapDragX = width-(windowX+windowWidth);
            if(mapDragY+windowY < 0)
                mapDragY = -windowY;
            if(mapDragY+windowY+windowHeight > height)
                mapDragY = height-(windowY+windowHeight);
            map.setPosition((int) ((windowX+mapDragX)*mapScale), (int) ((windowY+mapDragY)*mapScale));
        }
    }

    //reset mouse variables
    public void mouseReleased(MouseEvent e){
        mapInitDragX = -1;
        mapInitDragY = -1;
        windowX+=mapDragX;
        windowY+=mapDragY;
        mapDragX = 0;
        mapDragY = 0;
        mousePressed = false;
    }

    //set mouse initial drag
    public void mousePressed(MouseEvent e){
        if(e.getX() >= xPos+windowX && e.getX() <= xPos+windowX+windowWidth && e.getY() >= yPos+windowY && e.getY() <= yPos+windowY+windowHeight){
            mapInitDragX = e.getX();
            mapInitDragY = e.getY();
            mousePressed = true;
        }
    }
}
