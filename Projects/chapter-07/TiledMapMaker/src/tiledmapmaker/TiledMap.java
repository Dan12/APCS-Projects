package tiledmapmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class TiledMap {
    private String imageLocation;
    private String imageName;
    public String textFileName;
    public String textFileLocation;
    private BufferedImage tiledImage;
    private BufferedImage[][] tilesImages;
    private int tileWidth;
    private int tileHeight;
    private int initTileWidth;
    private int initTileHeight;
    private int rows;
    private int cols;
    private int mapTileWidth;
    private int mapTileHeight;
    public Tile[][] tiles;
    int offsetX;
    int offsetY;
    private int tilePadding;
    private int tileScale;
    int mapPixelWidth;
    int mapPixelHeight;
    private int cursorX;
    private int cursorY;
    private int tileShowPadding = 4;
    private int tileShowX = TiledMapMaker.screenWidth+20;
    private int tileShowY = 100;
    private int tileShowCols = 4;
    private int tileShowWidth = 30;
    private int tileShowHeight = 30;
    private Font font;
    private Rectangle highLight;
    private Tile selectedTile;
    private int dragRectX;
    private int dragRectY;
    private boolean dragging;
    boolean dragLimitX = false;
    boolean dragLimitY = false;
    
    public TiledMap(String fileLocation, String textName){
        textFileLocation = fileLocation;
        textFileName = textName;
        
        File inFile = new File(fileLocation+"/"+textName);
        
        Scanner inLine = null;
        Scanner inLineLines = null;
        try {inLine = new Scanner(inFile); inLineLines = new Scanner(inFile);} catch (FileNotFoundException ex) {}
        
        int lines = 0;
        while(inLineLines.hasNext()){
            lines++;
            System.out.println(inLineLines.nextLine());
        }
        
        String[] linesIn = new String[lines];
        
        for(int i = 0; i < lines; i++){
            linesIn[i] = inLine.nextLine();
        }
        
        String[] properties = linesIn[0].substring(1, linesIn[0].length()-1).split(",");
        
        for(int i = 0; i < properties.length; i++){
            String[] pair = properties[i].split("=");
            if(pair[0].equals("file-name")){ imageName = pair[1]; imageLocation = fileLocation+"/"+imageName;}
            else if(pair[0].equals("tile-width")) tileWidth = Integer.parseInt(pair[1]);
            else if(pair[0].equals("tile-height")) tileHeight = Integer.parseInt(pair[1]);
            else if(pair[0].equals("tile-padding")) tilePadding = Integer.parseInt(pair[1]);
            else if(pair[0].equals("tile-scale")) tileScale = Integer.parseInt(pair[1]);
            else if(pair[0].equals("rows")) rows = Integer.parseInt(pair[1]);
            else if(pair[0].equals("cols")) cols = Integer.parseInt(pair[1]);
            else if(pair[0].equals("map-tile-width")) mapTileWidth = Integer.parseInt(pair[1]);
            else if(pair[0].equals("map-tile-height")) mapTileHeight = Integer.parseInt(pair[1]);
        }
        
        initTileWidth = tileWidth;
        initTileHeight= tileHeight;
        
        try {
            tiledImage = ImageIO.read(new File(imageLocation));
        } catch (IOException ex) {}
        
        boolean[][] saveTiles = new boolean[rows][cols];
        tilesImages = new BufferedImage[rows][cols];
        
        mapPixelHeight = mapTileWidth*tileHeight*tileScale;
        mapPixelWidth = mapTileHeight*tileWidth*tileScale;
        
        tiles = new Tile[mapTileHeight][mapTileWidth];
        
        for(int i = 1; i < lines; i++){
            properties = linesIn[i].substring(1, linesIn[i].length()-1).split(",");
            if(properties[0].equals("t")){
                String[] tileProp = new String[4];
                for(int j = 1; j < properties.length; j++){
                    String[] pair = properties[j].split("=");
                    tileProp[j-1] = pair[1];
                }
                Tile temp = new Tile(tileProp);
                tiles[temp.getMapRow()][temp.getMapCol()] = temp;
                saveTiles[temp.getTileRow()][temp.getTileCol()] = true;
            }
            else if(properties[0].equals("g")){
                String[] gVals = new String[6];
                for(int j = 1; j < properties.length; j++){
                    String[] pair = properties[j].split("=");
                    gVals[j-1] = pair[1];
                }
                int sX = Integer.parseInt(gVals[2]);
                int sY = Integer.parseInt(gVals[3]);
                int eX = Integer.parseInt(gVals[4])+1;
                int eY = Integer.parseInt(gVals[5])+1;
                for(int r = 0; r < eY-sY; r++){
                    for(int c = 0; c < eX-sX; c++){
                        String[] tileProp = new String[4];
                        tileProp[0] = gVals[0]; tileProp[1] = gVals[1];
                        tileProp[2] = (sY+r)+"";
                        tileProp[3] = (sX+c)+"";
                        Tile temp = new Tile(tileProp);
                        tiles[temp.getMapRow()][temp.getMapCol()] = temp;
                        saveTiles[temp.getTileRow()][temp.getTileCol()] = true;
                    }
                }
            }
        }
        
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                //if(saveTiles[r][c])
                    tilesImages[r][c] = tiledImage.getSubimage(c*tileWidth+tilePadding*(c+1), r*tileHeight+tilePadding*(r+1),tileWidth,tileHeight);
            }
        }
        
        font = new Font("Arial",Font.BOLD,30);
    }
    
    public void drawMap(Graphics g){
        for(int r = 0; r < tiles.length; r++){
            for(int c = 0; c < tiles[0].length; c++){
                if((c+1)*tileWidth*tileScale >= offsetX && (r+1)*tileHeight*tileScale >= offsetY && c*tileWidth*tileScale <= offsetX+TiledMapMaker.screenWidth && r*tileHeight*tileScale <= offsetY+TiledMapMaker.screenHeight){
                    if(tiles[r][c]!=null){
                        //System.out.println(tiles[r][c]);
                        g.drawImage(tilesImages[tiles[r][c].getTileRow()][tiles[r][c].getTileCol()], tiles[r][c].getMapCol()*tileWidth*tileScale-offsetX, tiles[r][c].getMapRow()*tileHeight*tileScale-offsetY, tileWidth*tileScale, tileHeight*tileScale, null);
                    }
                    else{
                        g.fillRect(c*tileWidth*tileScale-offsetX, r*tileHeight*tileScale-offsetY, tileWidth*tileScale, tileHeight*tileScale);
                    }
                }
            }
        }
    }
    
    public void mouseClicked(int x, int y){
        if(x>=tileShowX && x <= tileShowX+(tileShowWidth+tileShowPadding)*tilesImages[0].length && y>=tileShowY && y <= tileShowY+(tileShowHeight+tileShowPadding)*tilesImages.length){
            int selectRow = (cursorY-tileShowY)/(tileShowHeight+tileShowPadding);
            int selectCol = (cursorX-tileShowX)/(tileShowWidth+tileShowPadding);
            selectedTile = new Tile(new String[]{""+(selectRow+1),""+(selectCol+1),""+(-1),""+(-1)});
        }
        else if(x<=TiledMapMaker.screenWidth && y<=TiledMapMaker.screenHeight && selectedTile != null){
            x+=offsetX;
            y+=offsetY;
            int curTileCol = x/(tileWidth*tileScale);
            int curTileRow = y/(tileHeight*tileScale);
            tiles[curTileRow][curTileCol] = new Tile(new String[]{""+(selectedTile.getTileRow()+1),""+(selectedTile.getTileCol()+1),""+(curTileRow+1),""+(curTileCol+1)});
        }
        else{
            selectedTile = null;
        }
    }
    
    public void nullTile(){
        selectedTile = null;
    }
    
    public void mouseDragged(int x, int y){
        dragging = true;
        if(x > TiledMapMaker.screenWidth-tileWidth)
            x = TiledMapMaker.screenWidth-tileWidth;
        if(y > TiledMapMaker.screenHeight-tileHeight)
            y = TiledMapMaker.screenHeight-tileHeight;
        if(x > mapPixelWidth-tileWidth)
            x = mapPixelWidth-tileWidth;
        if(y > mapPixelHeight-tileHeight)
            y = mapPixelHeight-tileHeight;
        x+=offsetX;
        y+=offsetY;
        highLight = new Rectangle(dragRectX-(dragRectX%(tileWidth*tileScale)), dragRectY-(dragRectY%(tileHeight*tileScale)), ((x-(x%(tileWidth*tileScale)))+(tileWidth*tileScale))-(dragRectX-(dragRectX%(tileWidth*tileScale))), ((y-(y%(tileHeight*tileScale)))+(tileHeight*tileScale))-(dragRectY-(dragRectY%(tileHeight*tileScale))));
        highLight.setLocation(highLight.x-offsetX, highLight.y-offsetY);
        if(x-offsetX >= TiledMapMaker.screenWidth-tileWidth)
            dragLimitX = true;
        else
            dragLimitX = false;
        if(y-offsetY >= TiledMapMaker.screenHeight-tileHeight)
            dragLimitY = true;
        else
            dragLimitY = false;
            
    }
    
    public void mouseReleased(int x, int y){
        if(x > mapPixelWidth-tileWidth && x <= TiledMapMaker.screenWidth)
            x = mapPixelWidth-tileWidth;
        if(y > mapPixelHeight-tileHeight && y <= TiledMapMaker.screenHeight)
            y = mapPixelHeight-tileHeight;
        if(x<=mapPixelWidth && y<=mapPixelHeight && selectedTile != null && dragging){
            if(x>TiledMapMaker.screenWidth-tileWidth)
                x = TiledMapMaker.screenWidth-tileWidth;
            if(y>TiledMapMaker.screenHeight-tileHeight)
                y = TiledMapMaker.screenHeight-tileHeight;
            x+=offsetX;
            y+=offsetY;
            int curTileEndCol = (x/(tileWidth*tileScale))+1;
            int curTileEndRow = (y/(tileHeight*tileScale))+1;
            int curTileStartCol = dragRectX/(tileWidth*tileScale);
            int curTileStartRow = dragRectY/(tileHeight*tileScale);
            for(int r = curTileStartRow; r < curTileEndRow; r++){
                for(int c = curTileStartCol; c < curTileEndCol; c++){
                    tiles[r][c] = new Tile(new String[]{""+(selectedTile.getTileRow()+1),""+(selectedTile.getTileCol()+1),""+(r+1),""+(c+1)});
                }
            }
        }
        if(x<=TiledMapMaker.screenWidth && y<=TiledMapMaker.screenHeight && dragging){
            highLight = new Rectangle(x-(x%(tileWidth*tileScale)), y-(y%(tileHeight*tileScale)), (tileWidth*tileScale), (tileHeight*tileScale));
            highLight.setLocation(highLight.x-offsetX, highLight.y-offsetY);
        }
        dragging = false;
        dragLimitX = false;
        dragLimitY = false;
    }
    
    public void mousePressed(int x, int y){
        dragRectX = x+offsetX;
        dragRectY = y+offsetY;
    }
    
    public void deletePressed(){
        if(dragging){
            for(int r = (highLight.y+offsetY)/(tileHeight*tileScale); r < (highLight.y+offsetY)/(tileHeight*tileScale)+highLight.height/(tileHeight*tileScale); r++){
                for(int c = (highLight.x+offsetX)/(tileWidth*tileScale); c < (highLight.x+offsetX)/(tileWidth*tileScale)+highLight.width/(tileWidth*tileScale); c++){
                    tiles[r][c] = null;
                }
            }
            dragging = false;
        }
        else{
            tiles[highLight.y/(tileHeight*tileScale)][highLight.x/(tileWidth*tileScale)] = null;
        }
    }
    
    public void setCursorPos(int x, int y){
        cursorX = x;
        cursorY = y;
    
        if(cursorX>=tileShowX && cursorX <= tileShowX+(tileShowWidth+tileShowPadding)*tilesImages[0].length && cursorY>=tileShowY && cursorY <= tileShowY+(tileShowHeight+tileShowPadding)*tilesImages.length){
            highLight = new Rectangle(tileShowX+((cursorX-tileShowX)/(tileShowWidth+tileShowPadding))*(tileShowWidth+tileShowPadding), tileShowY+((cursorY-tileShowY)/(tileShowHeight+tileShowPadding))*(tileShowWidth+tileShowPadding), tileShowWidth, tileShowHeight);
        }
        else if(cursorX<=TiledMapMaker.screenWidth && cursorY<=TiledMapMaker.screenHeight){
            cursorX+=offsetX;
            cursorY+=offsetY;
            highLight = new Rectangle((cursorX/(tileWidth*tileScale))*(tileWidth*tileScale), (cursorY/(tileHeight*tileScale))*(tileHeight*tileScale), tileWidth*tileScale, tileHeight*tileScale);
            highLight.setLocation(highLight.x-offsetX, highLight.y-offsetY);
        }
        else{
            highLight = null;
        }
    }
    
    public void drawTiles(Graphics g){
        int showRow = 0;
        int showCol = 0;
        for(int r = 0; r < tilesImages.length; r++){
            for(int c = 0; c < tilesImages[0].length; c++){
                g.drawImage(tilesImages[r][c], tileShowX+c*(tileShowWidth+tileShowPadding), tileShowY+r*(tileShowHeight+tileShowPadding), tileShowWidth, tileShowHeight, null);
            }
        }
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("Map", tileShowX, tileShowY/2);
    }
    
    public void drawHighLight(Graphics g){
        if(highLight != null){
            g.setColor(new Color(255,0,0,100));
            g.fillRect(highLight.x, highLight.y, highLight.width, highLight.height);
            if(highLight.x<TiledMapMaker.screenWidth && selectedTile != null){
                if(!dragging)
                    g.drawImage(tilesImages[selectedTile.getTileRow()][selectedTile.getTileCol()], highLight.x, highLight.y, tileWidth*tileScale, tileHeight*tileScale, null);
                else{
                    for(int r = 0; r < highLight.height/(tileHeight*tileScale); r++){
                        for(int c = 0; c < highLight.width/(tileWidth*tileScale); c++){
                            g.drawImage(tilesImages[selectedTile.getTileRow()][selectedTile.getTileCol()], highLight.x+(c*tileWidth*tileScale), highLight.y+(r*tileHeight*tileScale), tileWidth*tileScale, tileHeight*tileScale, null);
                        }   
                    }
                }
            }
        }
    }
    
    public void changeTileMapPreviewSize(int tw, int th){
        tileWidth = tw;
        tileHeight = th;
        mapPixelHeight = mapTileWidth*tileHeight*tileScale;
        mapPixelWidth = mapTileHeight*tileWidth*tileScale;
    }
    
    public void changeOffset(int cx, int cy){
        offsetX+=cx;
        offsetY+=cy;
        if(offsetX<0)
            offsetX = 0;
        if(offsetY<0)
            offsetY = 0;
        if(mapPixelWidth-offsetX<TiledMapMaker.screenWidth)
            offsetX = mapPixelWidth-TiledMapMaker.screenWidth;
        if(mapPixelHeight-offsetY<TiledMapMaker.screenHeight)
            offsetY = mapPixelWidth-TiledMapMaker.screenHeight;
        if(mapPixelWidth<TiledMapMaker.screenWidth)
            offsetX = 0;
        if(mapPixelHeight<TiledMapMaker.screenHeight)
            offsetY = 0;
    }
    
    @Override
    public String toString(){
        return "<file-name="+imageName+",tile-width="+initTileWidth+",tile-height="+initTileHeight+",tile-padding="+tilePadding+",tile-scale="+tileScale+",rows="+tilesImages.length+",cols="+tilesImages[0].length+",map-tile-width="+mapTileWidth+",map-tile-height="+mapTileHeight+">";
    }
    
    public class Tile{
        private int tileRow;
        private int tileCol;
        private int mapRow;
        private int mapCol;
        
        public Tile(String[] p){
            tileRow = Integer.parseInt(p[0])-1;
            tileCol = Integer.parseInt(p[1])-1;
            mapRow = Integer.parseInt(p[2])-1;
            mapCol = Integer.parseInt(p[3])-1;
        }
        
        public int getMapRow(){
            return mapRow;
        }
        
        public int getMapCol(){
            return mapCol;
        }
        
        public int getTileRow(){
            return tileRow;
        }
        
        public int getTileCol(){
            return tileCol;
        }
        
        @Override
        public String toString(){
            return tileCol+","+tileRow+","+mapCol+","+mapRow;
        }
    }
}
