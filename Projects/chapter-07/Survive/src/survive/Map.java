package survive;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private ArrayList<Square> squares;
    private int offsetX;
    private int offsetY;
    private int mapWidth;
    private int mapHeight;
    private int resetMapXStep = 0;
    private int resetMapYStep = 0;
    private int resetAnimCount = 1;
    private boolean resetMap = false;
    private int resetAnimSteps = 6;
    private String fileLocation;
    private String fileName;
    private boolean showNumbers = false;
    private boolean finishAnimation = false;
    private DrawPanel dp;
    private int timeToCount;
    
    public int startSquareIndex;
    public int endSquareIndex;
    
    //Contstructor with arraylist of squares
    public Map(ArrayList<Square> sq, int s, int e, int c, DrawPanel d){
        fileLocation = "";
        fileName = "";
        
        startSquareIndex = s;
        endSquareIndex = e;
        
        dp = d;
        timeToCount = c;
        dp.setTimerCount(timeToCount);
        
        initMap(sq);
    }
    
    //Constructor, get arraylist of squares from file
    public Map(String fp, String fn, DrawPanel d){
        fileLocation = fp;
        fileName = fn;
        
        dp = d;
        
        File inFile = new File(fileLocation+"/"+fileName);
        
        Scanner inLine = null;
        try {inLine = new Scanner(inFile);}catch (FileNotFoundException ex) {System.out.println(ex);}
        
        String startEnd = inLine.nextLine();
        
        String[] firstLine = startEnd.split(",");
        
        startSquareIndex = Integer.parseInt(firstLine[0]);
        endSquareIndex = Integer.parseInt(firstLine[1]);
        timeToCount = Integer.parseInt(firstLine[2]);
        dp.setTimerCount(timeToCount);
        
        String linesIn = "";
        while(inLine.hasNext()){
            linesIn += inLine.nextLine();
        }
        
        String[] squareLines = linesIn.split("sq");
        
        ArrayList<Square> tempSquares = new ArrayList<Square>();
        
        for(String s : squareLines){
            if(!s.equals("")){
        	String[] components = s.split("sp");
        	int[][] spikeProperties = new int[components.length-1][5];
        	for(int i = 1; i < components.length; i++){
        		String[] properties = components[i].split(",");
        		if(properties.length == 5)
                            spikeProperties[i-1] = new int[]{Integer.parseInt(properties[0]),Integer.parseInt(properties[1]),Integer.parseInt(properties[2]),Integer.parseInt(properties[3]),Integer.parseInt(properties[4])};
        		else
                            spikeProperties[i-1] = new int[]{-1,-1,-1,-1,-1};
        	}
            if(spikeProperties.length==0)
                spikeProperties = new int[][]{{-1,-1,-1,-1,-1}};
        	String[] squareProperties = components[0].split(",");
            if(tempSquares.size() == endSquareIndex)
                tempSquares.add(new Square(Integer.parseInt(squareProperties[0]),Integer.parseInt(squareProperties[1]),Integer.parseInt(squareProperties[2]),Integer.parseInt(squareProperties[3]),spikeProperties,true));
            else
                tempSquares.add(new Square(Integer.parseInt(squareProperties[0]),Integer.parseInt(squareProperties[1]),Integer.parseInt(squareProperties[2]),Integer.parseInt(squareProperties[3]),spikeProperties,false));
            }
        }
        
        initMap(tempSquares);
    }
    
    //init map with arraylist of squares
    private void initMap(ArrayList<Square> sq){
        squares = new ArrayList<Square>();
        int maxX = 0;
        int maxY = 0;
        
        for(Square s : sq){
            squares.add(s);
            if(s.getX()+s.getWidth() > maxX)
                maxX = s.getX()+s.getWidth();
            if(s.getY()+s.getHeight() > maxY)
                maxY = s.getY()+s.getHeight();
        }
        
        mapWidth = maxX+200;
        mapHeight = maxY+200;
        
        offsetX = 0;
        offsetY = 0;
    }
    
    /*updates map to scroll and keep the character
    **in the center of the map*/
    public void mapUpdate(Character c){
        if(resetMap)
            resetMapAnim(c);
        else{
            if((c.getX()+c.getDim()/2 > Main.SCREEN_WIDTH/2 && mapWidth-offsetX>Main.SCREEN_WIDTH) || c.getX()+c.getDim()/2<=Main.SCREEN_WIDTH/2 && offsetX>0){
                changePosition((c.getX()+c.getDim()/2)-Main.SCREEN_WIDTH/2, 0);
                c.changePosition(Main.SCREEN_WIDTH/2-(c.getX()+c.getDim()/2), 0);
            }
            if((c.getY()+c.getDim()/2 > Main.SCREEN_HEIGHT/2 && mapHeight-offsetY>Main.SCREEN_HEIGHT) || c.getY()+c.getDim()/2<=Main.SCREEN_HEIGHT/2 && offsetY>0){
                changePosition(0, (c.getY()+c.getDim()/2)-Main.SCREEN_HEIGHT/2);
                c.changePosition(0, Main.SCREEN_HEIGHT/2-(c.getY()+c.getDim()/2));
            }
        }
        //finish animation
        if(c.getAttachedIndex() == endSquareIndex && !finishAnimation){
            finishAnimation = true;
            dp.stopTimer();
            c.setFinishChanges((squares.get(endSquareIndex).getX()+squares.get(endSquareIndex).getWidth()/2)-(c.getX()+c.getDim()/2), (squares.get(endSquareIndex).getY()+squares.get(endSquareIndex).getHeight()/2)-(c.getY()+c.getDim()/2));
        }
    }
    
    //reset map animation; from current position to start position
    private void resetMapAnim(Character c){
        if(resetAnimCount <= resetAnimSteps){
            changePosition(resetMapXStep, resetMapYStep);
            resetAnimCount++;
        }
        else{
            changePosition(c.getInitOffsetX()-offsetX, c.getInitOffsetY()-offsetY);
            resetAnimCount = 1;
            resetMap = false;
            c.setStartAnim(this);
            dp.resetTimer();
            dp.startTimer();
        }
    }
    
    //setup reset animation
    public void resetMap(Character c){
        dp.stopTimer();
        resetMap = true;
        resetMapXStep = (c.getInitOffsetX()-offsetX)/resetAnimSteps;
        resetMapYStep = (c.getInitOffsetY()-offsetY)/resetAnimSteps;
    }
    
    //change offsets and square positions
    public void changePosition(int cx, int cy){
        offsetX+=cx;
        offsetY+=cy;
        
        for(Square s : squares)
            s.changePosition(-cx, -cy);
    }
    
    //sets the position/offsets of the map
    public void setPosition(int x, int y){
        changePosition(x-offsetX, y-offsetY);
    }
    
    //draw map and squares
    public void drawMap(Graphics g, float[] bands){
        for(int i = 0; i < squares.size(); i++){
            if(squares.get(i).inViewPort()){
                squares.get(i).drawSquare(g, bands);
                if(showNumbers){
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial",Font.PLAIN,(squares.get(i).getWidth()+squares.get(i).getWidth())/4));
                    g.drawString(""+i, squares.get(i).getX()+squares.get(i).getWidth()/3, squares.get(i).getY()+squares.get(i).getHeight()/2+squares.get(i).getHeight()/4);
                }
            }
        }
    }
    
    //add new square to list
    public void addSquare(Square s){
        squares.add(s);
    }
    
    public ArrayList<Square> getSquareList(){
        return squares;
    }
       
    public int getOffsetX(){
        return offsetX;
    }
    
    public int getOffsetY(){
        return offsetY;
    }
    
    public int getHeight(){
        return mapHeight;
    }
    
    public int getWidth(){
        return mapWidth;
    }
    
    public void setHeight(int h){
        mapHeight = h;
    }
    
    public void setWidth(int w){
        mapWidth = w;
    }
    
    public String getFile(){
        return fileLocation+"/"+fileName;
    }
    
    public void showNumbers(){
        showNumbers = true;
    }
    
    public boolean isFinishing(){
        return finishAnimation;
    }
    
    public void finishedEndAnim(){
        finishAnimation = false;
        dp.finishLevel();
    }
    
    public void cancelFinishAnim(){
        finishAnimation = false;
    }
    
    public int getResetSteps(){
        return resetAnimSteps;
    }
    
    public int getTimeToCount(){
        return timeToCount;
    }
}
