package survive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.util.ArrayList;

public class Character {
    private int xVel;
    private int yVel;
    private int xPos;
    private int yPos;
    private int attachedToIndex;
    private int startAttachIndex;
    private int speed = 4;
    private int dim = 2;
    private int bandMax = 30;
    private int initOffsetX;
    private int initOffsetY;
    private int startAnimCount = 1;
    private int startAnimCountFrames = 8;
    private int finishAnimCount = 1;
    private int finishAnimCountFrames = 70;
    private int endXChange = 0;
    private int endYChange = 0;
    private boolean finishAnim = false;
    private int xDiff = Main.PLAYER_DIM/2;
    private int yDiff = Main.PLAYER_DIM/2;
    private double tempX;
    private double tempY;
    private double tempDim;
    private boolean cancelFinish = false;

    public boolean spacePress = false;
    public boolean startAnim = true;

    /*Constructor
    **set initial position and velocity, which is the top 
    **of the first square going to the right by default*/
    public Character(Map map){
        startAttachIndex = map.startSquareIndex;
        attachedToIndex = startAttachIndex;
        xPos = map.getSquareList().get(attachedToIndex).getX()-Main.PLAYER_DIM/2;
        yPos = map.getSquareList().get(attachedToIndex).getY()-Main.PLAYER_DIM;
        xVel = speed;
        yVel = 0;
        map.mapUpdate(this);
        initOffsetX = map.getOffsetX();
        initOffsetY = map.getOffsetY();
    }
    
    public void newMap(Map map){
        startAttachIndex = map.startSquareIndex;
        attachedToIndex = startAttachIndex;
        xPos = map.getSquareList().get(attachedToIndex).getX()-Main.PLAYER_DIM/2;
        yPos = map.getSquareList().get(attachedToIndex).getY()-Main.PLAYER_DIM;
        xVel = speed;
        yVel = 0;
        map.mapUpdate(this);
        initOffsetX = map.getOffsetX();
        initOffsetY = map.getOffsetY();
    }

    //Draw Method, draws a red square
    public void drawCharacter(Graphics g, float[] bands){
        Graphics2D g2 = (Graphics2D) g;
        RadialGradientPaint gp = new RadialGradientPaint(xPos+xDiff+dim/2, yPos+yDiff+dim/2, dim/2, new float[]{Main.map(bands[Main.PLAYER_BAND],0,bandMax,0,0.25f),Main.map(bands[Main.PLAYER_BAND],0,bandMax,0.07f,0.99f),Main.map(bands[Main.PLAYER_BAND],0,bandMax,0.9f,1.0f)}, new Color[]{new Color(120,120,255), new Color(255,150,0),new Color(255,0,0)});
        g2.setPaint(gp);
        g2.fillRect(xPos+xDiff, yPos+yDiff, dim, dim);
    }

    /*update method, checks if space pressed and executes actions
    **based on if player is attached to something or floating*/
    public void update(Map map){
        xPos+=xVel;
        yPos+=yVel;
         
        map.mapUpdate(this);
        
        if(spacePress && attachedToIndex != -1)
            pressedSpace(map.getSquareList());
        else if(spacePress && attachedToIndex == -1)
            spacePress = false;
        
        if(startAnim)
            runStartAnim(map);

        if(!finishAnim){
            if(attachedToIndex != -1)
                attachdToSquare(map.getSquareList());
            else
                floating(map.getSquareList());
        }
        else{
            runFinishAnim(map);
        }
        
        cancelFinish = false;
        if(!finishAnim)
            checkCollisions(map);
        
        if(!cancelFinish && map.isFinishing() && !finishAnim){
            setFinishAnim();
        }
        else if(cancelFinish && map.isFinishing()){
            cancelFinish = false;
            map.cancelFinishAnim();
        }
    }
    
    //set up the start animation
    public void setStartAnim(Map map){
        xPos = map.getSquareList().get(attachedToIndex).getX()-Main.PLAYER_DIM/2;
        yPos = map.getSquareList().get(attachedToIndex).getY()-Main.PLAYER_DIM;
        xVel = speed;
        startAnim = true;
    }
    
    public void setFinishAnim(){
        xVel = 0;
        yVel = 0;
        finishAnim = true;
        tempX = xPos;
        tempY = yPos;
        tempDim = dim;
        finishAnimCount = 0;
    }
    
    public void runFinishAnim(Map map){
        if(finishAnimCount <= finishAnimCountFrames/2){
            tempX += ((double)endXChange)/(finishAnimCountFrames/2);
            tempY += ((double)endYChange)/(finishAnimCountFrames/2);
            xPos = (int) tempX;
            yPos = (int) tempY;
        }
        else if(finishAnimCount <= finishAnimCountFrames){
            tempX += ((double)dim/2)/(finishAnimCountFrames/2);
            tempY += ((double)dim/2)/(finishAnimCountFrames/2);
            tempDim -= ((double)dim)/(finishAnimCountFrames/2);
            xPos = (int) tempX;
            yPos = (int) tempY;
            dim = (int) tempDim;
            if(dim < 2)
                dim = 2;
        }
        else{
            finishAnim = false;
            dim = 2;
            xPos = map.getSquareList().get(map.endSquareIndex).getX()+map.getSquareList().get(map.endSquareIndex).getWidth()/2;
            yPos = map.getSquareList().get(map.endSquareIndex).getY()+map.getSquareList().get(map.endSquareIndex).getHeight()/2;
            map.finishedEndAnim();
        }
        finishAnimCount++;
    }
    
    //run the start animation using a counter
    private void runStartAnim(Map map){
        if(startAnimCount <= startAnimCountFrames){
            dim = (int) Main.map(startAnimCount,0,startAnimCountFrames,0,Main.PLAYER_DIM);
            xDiff = (int) Main.map(startAnimCountFrames-startAnimCount, 0, startAnimCountFrames, 0, Main.PLAYER_DIM/2);
            yDiff = xDiff;
            startAnimCount++;
        }
        else{
            dim = Main.PLAYER_DIM;
            startAnim = false;
            xDiff = 0;
            yDiff = 0;
            startAnimCount = 1;
        }
    }
    
    //check for spike collisions on map and call on hit if intersection
    public void checkCollisions(Map map){
        for(Square sq : map.getSquareList()){
            for(Spike sp : sq.getSpikeList()){
                if(sp.characterCollision(this)){
                    gotHit(map);
                    return;
                }
            }
        }
        if(xPos < 0 || xPos+dim > Main.SCREEN_WIDTH || yPos < 0 || yPos+dim > Main.SCREEN_HEIGHT)
            gotHit(map);
    }
    
    //reset character to initial position
    public void gotHit(Map map){
        map.resetMap(this);
        cancelFinish = true;
        attachedToIndex = startAttachIndex;
        xPos = -Main.PLAYER_DIM;
        yPos = -Main.PLAYER_DIM;
        xVel = 0;
        yVel = 0;
        dim = 2;
        xDiff = Main.PLAYER_DIM/2;
        yDiff = Main.PLAYER_DIM/2;
    }
    
    //change the x and y position of the character
    public void changePosition(int cx, int cy){
        xPos+=cx;
        yPos+=cy;
        tempX+=cx;
        tempY+=cy;
    }
    
    //if space pressed, add speed tangent to current path
    private void pressedSpace(ArrayList<Square> squares){
        if(xVel == 0){
            if(xPos<squares.get(attachedToIndex).getX())
                xVel = -speed;
            else
                xVel = speed;
        }
        else{
            if(yPos<squares.get(attachedToIndex).getY())
                yVel = -speed;
            else
                yVel = speed;
        }
        spacePress = false;
        attachedToIndex = -1;
    }
    
    //if player attached to square move along its sides
    private void attachdToSquare(ArrayList<Square> squares){
        if(xVel>0 && xPos>squares.get(attachedToIndex).getX()+squares.get(attachedToIndex).getWidth()){
            xPos = squares.get(attachedToIndex).getX()+squares.get(attachedToIndex).getWidth();
            xVel = 0;
            if(yPos<squares.get(attachedToIndex).getY())
                yVel = speed;
            else
                yVel = -speed;
        }
        else if(xVel<0 && xPos+dim<squares.get(attachedToIndex).getX()){
            xPos = squares.get(attachedToIndex).getX()-dim;
            xVel = 0;
            if(yPos<squares.get(attachedToIndex).getY())
                yVel = speed;
            else
                yVel = -speed;
        }
        else if(yVel<0 && yPos+dim<squares.get(attachedToIndex).getY()){
            yPos = squares.get(attachedToIndex).getY()-dim;
            yVel = 0;
            if(xPos<squares.get(attachedToIndex).getX())
                xVel = speed;
            else
                xVel = -speed;
        }
        else if(yVel>0 && yPos>squares.get(attachedToIndex).getY()+squares.get(attachedToIndex).getHeight()){
            yPos = squares.get(attachedToIndex).getY()+squares.get(attachedToIndex).getHeight();
            yVel = 0;
            if(xPos<squares.get(attachedToIndex).getX())
                xVel = speed;
            else
                xVel = -speed;
        }
    }
    
    /*if player not attached to a square check if you hit any square.
    **if player hit a square attach to it*/
    private void floating(ArrayList<Square> squares){
        for(int i = 0; i < squares.size(); i++){
            if(squares.get(i).intersectsSquare((int)xPos, (int)yPos, dim, dim)){
                attachedToIndex = i;
                if(xVel>0 && yVel>0){
                    if(Math.abs((xPos+dim)-squares.get(attachedToIndex).getX())>Math.abs((yPos+dim)-squares.get(attachedToIndex).getY())){
                        xVel = speed;
                        yVel = 0;
                        yPos = squares.get(attachedToIndex).getY()-dim;
                    }
                    else{
                        yVel = speed;
                        xVel = 0;
                        xPos = squares.get(attachedToIndex).getX()-dim;
                    }     
                }
                if(xVel>0 && yVel<0){
                    if(Math.abs((xPos+dim)-squares.get(attachedToIndex).getX())>Math.abs(yPos-(squares.get(attachedToIndex).getY()+squares.get(attachedToIndex).getHeight()))){
                        xVel = speed;
                        yVel = 0;
                        yPos = squares.get(attachedToIndex).getY()+squares.get(attachedToIndex).getHeight();
                    }
                    else{
                        yVel = -speed;
                        xVel = 0;
                        xPos = squares.get(attachedToIndex).getX()-dim;
                    }
                }
                if(xVel<0 && yVel>0){
                    if(Math.abs(xPos-(squares.get(attachedToIndex).getX()+squares.get(attachedToIndex).getWidth()))>Math.abs((yPos+dim)-squares.get(attachedToIndex).getY())){
                        xVel = -speed;
                        yVel = 0;
                        yPos = squares.get(attachedToIndex).getY()-dim;
                    }
                    else{
                        yVel = speed;
                        xVel = 0;
                        xPos = squares.get(attachedToIndex).getX()+squares.get(attachedToIndex).getWidth();
                    }
                }
                if(xVel<0 && yVel<0){
                    if(Math.abs(xPos-(squares.get(attachedToIndex).getX()+squares.get(attachedToIndex).getWidth()))>Math.abs(yPos-(squares.get(attachedToIndex).getY()+squares.get(attachedToIndex).getHeight()))){
                        xVel = -speed;
                        yVel = 0;
                        yPos = squares.get(attachedToIndex).getY()+squares.get(attachedToIndex).getHeight();
                    }
                    else{
                        yVel = -speed;
                        xVel = 0;
                        xPos = squares.get(attachedToIndex).getX()+squares.get(attachedToIndex).getWidth();
                    }
                }
            }
        }
    }
    
    public int getX(){
            return xPos; 
    }

    public int getY(){
            return yPos; 
    }

    public int getDim(){
        return dim; 
    }

    public int getInitOffsetX(){
            return initOffsetX;
    }

    public int getInitOffsetY(){
            return initOffsetY;
    }
    
    public int getAttachedIndex(){
        return attachedToIndex;
    }
    
    public void setFinishChanges(int x, int y){
        endXChange = x;
        endYChange = y;
    }
}
