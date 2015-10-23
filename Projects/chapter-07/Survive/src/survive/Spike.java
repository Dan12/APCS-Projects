package survive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

public class Spike{
    private Square sq;
    private int freq, xPos, yPos, originalX, originalY, side, spacing;
    private ArrayList<Polygon> spikes;
    private int bandMax = 60;
    private Point gradientStart;
    private Point gradientEnd;
    private double spacing1, spacing2;
  
    //Constructor: square that spike is on, startx, starty, side, spacing, frequency
    public Spike(Square s, int sx, int sy, int sd, int sp, int f){
        sq = s;
        freq = f;
        side = sd;
        spacing = sp;
        xPos = sx;
        yPos = sy;
        originalX = xPos;
        originalY = yPos;

        int startX = sq.getX();
        int startY = sq.getY();
        if(xPos != -1)
            startX += xPos;
        if(yPos != -1)
            startY += yPos;

        if(side == -1)
            side = (int) (Math.random()*4);

        if(spacing == -1){
            int totalWidth = Main.SPIKE_SIZE*freq;
            spacing1 = (double) (sq.getWidth()-totalWidth)/(freq+1);
            spacing2 = (double) (sq.getHeight()-totalWidth)/(freq+1);
        }
        else{
            spacing1 = spacing;
            spacing2 = spacing;
        }

        spikes = new ArrayList<Polygon>();

        for (int i = 1; i <= freq; i++){ //Initialize spikes
            Polygon spike = new Polygon();
            switch(side){
                case 0: //Top
                    gradientStart = new Point(startX, startY);
                    gradientEnd = new Point(startX, startY-Main.SPIKE_SIZE);
                    spike.addPoint((int) (startX+spacing1*i+Main.SPIKE_SIZE*(i-1)), startY);
                    spike.addPoint((int) (startX+spacing1*i+Main.SPIKE_SIZE+Main.SPIKE_SIZE*(i-1)), startY);
                    spike.addPoint((int) (startX+spacing1*i+Main.SPIKE_SIZE/2+Main.SPIKE_SIZE*(i-1)), startY-Main.SPIKE_SIZE);
                    spikes.add(spike);
                    break;
                case 1: //Right
                    gradientStart = new Point(startX+sq.getWidth(), startY);
                    gradientEnd = new Point(startX+sq.getWidth()+Main.SPIKE_SIZE, startY);
                    spike.addPoint(startX+sq.getWidth(), (int) (startY+spacing2*i+Main.SPIKE_SIZE*(i-1)));
                    spike.addPoint(startX+sq.getWidth(), (int) (startY+spacing2*i+Main.SPIKE_SIZE+Main.SPIKE_SIZE*(i-1)));
                    spike.addPoint(startX+sq.getWidth()+Main.SPIKE_SIZE, (int) (startY+spacing2*i+Main.SPIKE_SIZE/2+Main.SPIKE_SIZE*(i-1)));
                    spikes.add(spike);
                    break;
                case 2: //Bottom
                    gradientStart = new Point(startX, startY+sq.getHeight());
                    gradientEnd = new Point(startX, startY+sq.getHeight()+Main.SPIKE_SIZE);
                    spike.addPoint((int) (startX+spacing1*i+Main.SPIKE_SIZE*(i-1)), startY+sq.getHeight());
                    spike.addPoint((int) (startX+spacing1*i+Main.SPIKE_SIZE+Main.SPIKE_SIZE*(i-1)), startY+sq.getHeight());
                    spike.addPoint((int) (startX+spacing1*i+Main.SPIKE_SIZE/2+Main.SPIKE_SIZE*(i-1)), startY+sq.getHeight()+Main.SPIKE_SIZE);
                    spikes.add(spike);
                    break;
                case 3: //Left
                    gradientStart = new Point(startX, startY);
                    gradientEnd = new Point(startX-Main.SPIKE_SIZE, startY);
                    spike.addPoint(startX, (int) (startY+spacing2*i+Main.SPIKE_SIZE*(i-1)));
                    spike.addPoint(startX, (int) (startY+spacing2*i+Main.SPIKE_SIZE+Main.SPIKE_SIZE*(i-1)));
                    spike.addPoint(startX-Main.SPIKE_SIZE, (int) (startY+spacing2*i+Main.SPIKE_SIZE/2+Main.SPIKE_SIZE*(i-1)));
                    spikes.add(spike);
                    break;
            }
        }
    }
  
    //check if player collided with triangle
    public boolean characterCollision(Character c){
        if(sq.characterInBounds(c)){
            for (int i = 0; i < spikes.size(); i++){ //Bot Right
                if (spikes.get(i).contains(c.getX()+c.getDim()-1, c.getY()+c.getDim()-1)) return true;
            }
            for (int i = 0; i < spikes.size(); i++){ //Bot Left
                if (spikes.get(i).contains(c.getX()+1, c.getY()+c.getDim()-1)) return true;
            }
            for (int i = 0; i < spikes.size(); i++){//Top Left
                if (spikes.get(i).contains(c.getX()+1, c.getY()+1)) return true;
            }
            for (int i = 0; i < spikes.size(); i++){//Top Right
                if (spikes.get(i).contains(c.getX()+c.getDim()-1, c.getY()+1)) return true;
            }
        }
        return false; 
    }
  
    //change spikes position
    public void changePosition(int cx, int cy){
        for (int i = 0; i < spikes.size(); i++){
            spikes.get(i).translate(cx, cy);
        }
        gradientStart.translate(cx, cy);
        gradientEnd.translate(cx, cy);
        xPos+=cx;
        yPos+=cy;
    }
  
    //set spike position
    public void setPosition(int x, int y){
        changePosition(x-spikes.get(0).getBounds().x, y-spikes.get(0).getBounds().y);
    }
    
    public void setOriginalPosition(int x, int y){
       changeOriginalPosition(x-spikes.get(0).getBounds().x, y-spikes.get(0).getBounds().y);
    }
    
    public void changeOriginalPosition(int cx, int cy){
        for (int i = 0; i < spikes.size(); i++){
            spikes.get(i).translate(cx, cy);
        }
        gradientStart.translate(cx, cy);
        gradientEnd.translate(cx, cy);
        originalX+=cx;
        originalY+=cy;
    }
  
    //draw spikes
    public void drawOnto(Graphics g, float[] bands){
        Graphics2D g2 = (Graphics2D) g;
        LinearGradientPaint gp = new LinearGradientPaint(gradientStart, gradientEnd, new float[]{Main.map(bands[Main.SPIKE_BAND], 0, bandMax, 0, 0.99f),Main.map(bands[Main.SPIKE_BAND], 0, bandMax, 0.5f, 1.0f)}, new Color[]{Color.RED,Color.BLACK});
        g2.setPaint(gp);
        for (Polygon spike : spikes) {
            g2.fillPolygon(spike);
        }
    }
  
    public int getFrequency(){
        return freq; 
    }
	  
    public Square getSquare(){
        return sq; 
    }
	  
    public ArrayList<Polygon> getPolygonList(){
        return spikes;
    }
	  
    public int getX(){
        return originalX;
    }
	  
    public int getY(){
      	return originalY;
    }
	  
    public int getSide(){
        return side;
    }
	  
    public int getSpacing(){
        return spacing;
    }
  
    public String getInfo(){
        return originalX+","+originalY+","+side+","+spacing+","+freq;
    }
}
