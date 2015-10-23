package tiledmapmaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * File Name: Window.java
 *   Created: Apr 22, 2015
 *    Author: 
 */

public class Window extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
    
    private TiledMap map;
    
    private JButton loadMap;
    private JButton newMap;
    private JButton saveMap;
    private JSlider zoomMap;
    
    private int componentWidth = 100;
    private int componentHeight = 50;
    private int componentPaddingY = 25;
    private int componentPaddingX = 50;
    
    private boolean leftK = false;
    private boolean rightK = false;
    private boolean upK = false;
    private boolean downK = false;
    
    private int offsetSpeed = 5;
    
    boolean mouseDown = false;
    
    private String fileParent = "";
    private String fileName = "";
    
    public Window(){
        super.setOpaque(true);
        super.setPreferredSize(new Dimension(TiledMapMaker.screenWidthPlus, TiledMapMaker.screenHeightPlus));
        super.setBackground(new Color(225, 225, 225));
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        super.setFocusable(true);
        super.setLayout(null);
        
        loadMap = new JButton("Load");
        loadMap.setBounds(componentPaddingX, TiledMapMaker.screenHeight+componentPaddingY, componentWidth, componentHeight);
        newMap = new JButton("New");
        newMap.setBounds((componentPaddingX+componentWidth)*2-componentWidth, TiledMapMaker.screenHeight+componentPaddingY, componentWidth, componentHeight);
        saveMap = new JButton("Save");
        saveMap.setBounds((componentPaddingX+componentWidth)*3-componentWidth, TiledMapMaker.screenHeight+componentPaddingY, componentWidth, componentHeight);
        zoomMap = new JSlider(1, 50, 10);
        zoomMap.setBounds((componentPaddingX+componentWidth)*4-componentWidth, TiledMapMaker.screenHeight+componentPaddingY, componentWidth, componentHeight);
        
        zoomMap.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e){
                map.changeTileMapPreviewSize(zoomMap.getValue(), zoomMap.getValue());
                map.changeOffset(0, 0);
                repaint();
            }
        });
        
        zoomMap.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                Window.this.requestFocusInWindow();
            }
            
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        
        newMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mapWidth = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the map tile width", "Map Width", JOptionPane.QUESTION_MESSAGE));
                int mapHeight = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the map tile height", "Map Height", JOptionPane.QUESTION_MESSAGE));
                
                JOptionPane.showMessageDialog(null, "Select Tile Image File", "Tile Image", JOptionPane.QUESTION_MESSAGE);
                
                JFileChooser fc = new JFileChooser(new File(new File("").getAbsolutePath().concat("/src/res/")));
                fc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            File file = fc.getSelectedFile();
                            if(file != null){
                                fileParent = file.getParent();
                                fileName = file.getName();
                            }
                            else
                                System.out.println("Open command cancelled by user.");
                    }
                });
                int val = fc.showOpenDialog(null);
                
                int tileWidth = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the image tile width", "Tile Width", JOptionPane.QUESTION_MESSAGE));
                int tileHeight = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the image tile height", "Tile Height", JOptionPane.QUESTION_MESSAGE));
                int tilePadding = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the image tile padding", "Tile Padding", JOptionPane.QUESTION_MESSAGE));
                
                BufferedImage tiledImage = null;
                try {
                    tiledImage = ImageIO.read(new File(fileParent+"/"+fileName));
                } catch (IOException ex) {}
                int tileCols = tiledImage.getWidth()/(tileWidth+tilePadding);
                int tileRows = tiledImage.getHeight()/(tileHeight+tilePadding);
                String textFileName = JOptionPane.showInputDialog(null, "Name the text file", "File Name", JOptionPane.QUESTION_MESSAGE);
                
                FileWriter write = null;
                try {
                    write = new FileWriter(fileParent+"/"+textFileName+".txt", true);
                } catch (IOException ex) {}
                PrintWriter print_line = new PrintWriter(write);

                print_line.printf("<file-name=%s,tile-width=%d,tile-height=%d,tile-padding=%d,tile-scale=1,rows=%d,cols=%d,map-tile-width=%d,map-tile-height=%d>", fileName,tileWidth,tileHeight,tilePadding,tileRows,tileCols,mapWidth,mapHeight);

                print_line.close();
                
                map = new TiledMap(fileParent+"/", textFileName+".txt");
                
                Window.this.requestFocusInWindow();
            }
        });
        
        loadMap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(new File(new File("").getAbsolutePath().concat("/src/res/")));
                fc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            File file = fc.getSelectedFile();
                            if(file != null){
                                System.out.println(file.getName()+","+file.getAbsolutePath()+","+file.getParent());
                                map = new TiledMap(file.getParent()+"/", file.getName());
                                map.changeTileMapPreviewSize(zoomMap.getValue(), zoomMap.getValue());
                            }
                            else
                                System.out.println("Open command cancelled by user.");
                    }
                });
                int val = fc.showOpenDialog(null);
                Window.this.requestFocusInWindow();
            }
        });
        
        saveMap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                FileWriter write = null;
                try {
                    write = new FileWriter(map.textFileLocation+"/"+map.textFileName, false);
                } catch (IOException ex) {}
                PrintWriter print_line = new PrintWriter(write);

                String fileText = map.toString()+compressMap(map);
                
                print_line.printf("%s",fileText);

                print_line.close();
                
                Window.this.requestFocusInWindow();
            }
        });
        
        super.add(loadMap);
        super.add(newMap);
        super.add(saveMap);
        super.add(zoomMap);
        
        String filePath = new File("").getAbsolutePath();
        map = new TiledMap(filePath.concat("/src/res"), "desert-tiled.txt");
        map.changeTileMapPreviewSize(zoomMap.getValue(), zoomMap.getValue());
        
    }
    
    @Override  
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        keyActions();
        
        map.drawMap(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, TiledMapMaker.screenHeight, TiledMapMaker.screenWidthPlus, TiledMapMaker.screenHeightPlus-TiledMapMaker.screenHeight);
        g.fillRect(TiledMapMaker.screenWidth, 0, TiledMapMaker.screenWidthPlus-TiledMapMaker.screenWidth, TiledMapMaker.screenHeightPlus);

        map.drawTiles(g);
        map.drawHighLight(g);
        try {
          Thread.sleep(10);
        } catch (InterruptedException ex) {}
        repaint();
    }
    
    public void keyActions(){
        if(!mouseDown){
        if(upK)
            map.changeOffset(0, -offsetSpeed);
        if(downK)
            map.changeOffset(0, offsetSpeed);
        if(leftK)
            map.changeOffset(-offsetSpeed, 0);
        if(rightK)
            map.changeOffset(offsetSpeed, 0);
        }
        if(map.dragLimitX)
            map.changeOffset(offsetSpeed, 0);
        if(map.dragLimitY)
            map.changeOffset(0, offsetSpeed);
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
        map.mouseDragged(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e){
        map.setCursorPos(e.getX(), e.getY());
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        //esc-27, enter-10, delete-8
        if(e.getKeyCode() == 37)
            leftK = true;
        if(e.getKeyCode() == 38)
            upK = true;
        if(e.getKeyCode() == 39)
            rightK = true;
        if(e.getKeyCode() == 40)
            downK = true;
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        map.mousePressed(e.getX(), e.getY());
        mouseDown = true;
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == 37)
            leftK = false;
        if(e.getKeyCode() == 38)
            upK = false;
        if(e.getKeyCode() == 39)
            rightK = false;
        if(e.getKeyCode() == 40)
            downK = false;
        if(e.getKeyCode() == 27)
            map.nullTile();
        if(e.getKeyCode() == 8)
            map.deletePressed();
    }

    @Override
    public void mouseClicked(MouseEvent e){
        map.mouseClicked(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e){
        map.mouseReleased(e.getX(), e.getY());
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}
  
    @Override
    public void keyTyped(KeyEvent e){}
    
    public String compressMap(TiledMap m){
        String ret = "";
        int[][] tileRows = new int[m.tiles.length][m.tiles[0].length];
        int[][] tileCols = new int[m.tiles.length][m.tiles[0].length];
        
        for(int r = 0; r < tileRows.length; r++){
            for(int c = 0; c < tileRows[0].length; c++){
                if(m.tiles[r][c]!=null){
                    tileRows[r][c] = m.tiles[r][c].getTileRow();
                    tileCols[r][c] = m.tiles[r][c].getTileCol();
                }
                else{
                    tileRows[r][c] = -1;
                    tileCols[r][c] = -1;
                }
            }
        }

        for(int r = 0; r < tileRows.length; r++){
            for(int c = 0; c < tileRows[0].length; c++){
                if(tileRows[r][c] == -1){}
                else{
                    int startRow = r;
                    int startCol = c;
                    int curRow = r;
                    int curCol = c;
                    int endRow = r;
                    int endCol = c;
                    boolean isGroup = false;
                    while(true){
                        if(curCol+1<tileRows[0].length){
                            curCol++;
                            if(tileRows[startRow][curCol] != tileRows[startRow][startCol] || tileCols[startRow][curCol] != tileCols[startRow][startCol]){
                                curCol--;
                                break;
                            }
                            else{
                                tileRows[startRow][curCol] = -1;
                                tileCols[startRow][curCol] = -1;
                                isGroup = true;
                            }
                        }
                        else
                            break;
                    }
                    endCol = curCol;
                    while(true){
                        if(curRow+1<tileRows.length){
                            curRow++;
                            if(isNextRowSame(tileRows, tileCols, startRow, startCol, curRow, endCol)){
                                for(int col = startCol; col <= endCol; col++){
                                    tileRows[curRow][col] = -1;
                                    tileCols[curRow][col] = -1;
                                }
                                isGroup = true;
                            }
                            else{
                                curRow--;
                                break;
                            }
                        }
                        else
                            break;
                    }
                    endRow = curRow;
                    if(isGroup)
                        ret+="\n<g,tile-row="+(tileRows[startRow][startCol]+1)+",tile-col="+(tileCols[startRow][startCol]+1)+",x-s="+(startCol+1)+",y-s="+(startRow+1)+",x-e="+(endCol+1)+",y-e="+(endRow+1)+">";
                    else
                        ret+="\n<t,tile-row="+(tileRows[startRow][startCol]+1)+",tile-col="+(tileCols[startRow][startCol]+1)+",map-row="+(startRow+1)+",map-col="+(startCol+1)+">";
                }
            }
        }
        
        return ret;
    }
    
    public boolean isNextRowSame(int[][] tr, int[][] tc, int r, int c, int rs, int ec){
        boolean ret = true;
        for(int col = c; col <= ec; col++){
            if(tr[rs][col] != tr[r][c] || tc[rs][col] != tc[r][c]){
                ret = false;
                break;
            }
        }
        return ret;
    }
}
