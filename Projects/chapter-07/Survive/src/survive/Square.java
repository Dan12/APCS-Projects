package survive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.util.ArrayList;

public class Square {
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int bandMax = 12;
    private ArrayList<Spike> spikes;
    private boolean isFinish;

    /*Constructor
    **set square x,y,width,height and 
    **spike details(xPos,yPos,side,spacing,frequency*/
    public Square(int x, int y, int w, int h, int[][] spikeDetails, boolean f){
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        isFinish = f;

        spikes = new ArrayList<Spike>();

        //parameters: square xPos, yPos, side, spacing, frequency
        for(int i = 0; i < spikeDetails.length; i++){
            if(spikeDetails[i][4]!=-1)
                spikes.add(new Spike(this,spikeDetails[i][0],spikeDetails[i][1],spikeDetails[i][2],spikeDetails[i][3],spikeDetails[i][4]));
        }
    }

    //draw square and make fill gradient based on square band
    public void drawSquare(Graphics g, float[] bands){
        Graphics2D g2 = (Graphics2D) g;
        RadialGradientPaint gp = null;
        if(!isFinish)
            gp = new RadialGradientPaint(xPos+width/2, yPos+height/2, (width+height)/2, new float[]{Main.map(bands[Main.SQUARE_BAND],0,bandMax,0,0.2f),0.6f}, new Color[]{new Color(0,255,150),new Color(0,0,255,120)});
        else
            gp = new RadialGradientPaint(xPos+width/2, yPos+height/2, (width+height)/2, new float[]{Main.map(bands[Main.SQUARE_BAND],0,bandMax,0,0.2f),0.6f}, new Color[]{new Color(0,0,255,120),new Color(0,255,150)});
        g2.setPaint(gp);
        g2.fillRect(xPos, yPos, width, height);

        for(Spike s : spikes)
            s.drawOnto(g, bands);
    }

    /*check if sqaure is in the viewport.
    **Will save time when drawing if square is not visible
    **checks bounds as if square had spikes on all sides*/
    public boolean inViewPort(){
        return (xPos-Main.SPIKE_SIZE < Main.SCREEN_WIDTH && xPos+width+Main.SPIKE_SIZE > 0 && yPos-Main.SPIKE_SIZE < Main.SCREEN_HEIGHT && yPos+height+Main.SPIKE_SIZE > 0);
    }

    //return true if square with x,y,width,height intersects this square
    public boolean intersectsSquare(int x, int y, int w, int h){
        return (x+w>xPos && x<xPos+width && y+h>yPos && y<yPos+height);
    }

    //change position of square and spikes attached to square
    public void changePosition(int cx, int cy){
        xPos+=cx;
        yPos+=cy;

        for(Spike s : spikes)
            s.changePosition(cx, cy);
    }

    //sets square position
    public void setPosition(int x, int y){
        changePosition(x-xPos, y-yPos);
    }

    //checks if character is in the area SPIKE_WIDTH larger than the square
    public boolean characterInBounds(Character c){
        return (c.getX() <= xPos+width+Main.SPIKE_SIZE && c.getX()+c.getDim() >= xPos-Main.SPIKE_SIZE && c.getY() <= yPos+height+Main.SPIKE_SIZE && c.getY()+c.getDim() >= yPos-Main.SPIKE_SIZE);
    }

    //returns true if point x,y is inside the area SPIKE_WIDTH larger that the square
    public boolean pointInBounds(int x, int y){
        return (x <= xPos+width+Main.SPIKE_SIZE && x >= xPos-Main.SPIKE_SIZE && y <= yPos+height+Main.SPIKE_SIZE && y >= yPos-Main.SPIKE_SIZE);
    }

    //returns the side of the square that the point x,y is one
    public int sideOfPoint(int x, int y){
        if(x >= xPos && x <= xPos+width && y <= yPos)
            return 0;
        else if(x < xPos && y > yPos && y < yPos + height)
            return 3;
        else if(x > xPos+width && y > yPos && y < yPos + height)
            return 1;
        else if(x >= xPos && x <= xPos+width && y >= yPos + width)
            return 2;
        else
            return -1;
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public String getInfo(Map map){
        return (xPos+map.getOffsetX())+","+(yPos+map.getOffsetY())+","+width+","+height;
    }

    public ArrayList<Spike> getSpikeList(){
        return spikes;
    }

    public void addSpike(Spike s){
        spikes.add(s);
    }
    
    public void setAsFinish(){
        isFinish = true;
    }
    
    public void unsetAsFinish(){
        isFinish = false;
    }
    
    public int[][] getSpikeDetails(){
        int[][] sd = new int[spikes.size()][5];
        for(int i = 0; i < spikes.size(); i++){
            sd[i][0] = spikes.get(i).getX();
            sd[i][1] = spikes.get(i).getY();
            sd[i][2] = spikes.get(i).getSide();
            sd[i][3] = spikes.get(i).getSpacing();
            sd[i][4] = spikes.get(i).getFrequency();
        }
        return sd;
    }
}
