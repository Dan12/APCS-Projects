package survive;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LevelHandler {
    
    private int numLevels;
    private int currentLevel;
    private File[] levels;
    private Font font;
    private int fontSize = 60;
    private int buttonDim = 100;
    private int highestLevelReached;
    private int buffer;
    private Button exitButton;
    private DrawPanel dp;
    
    public LevelHandler(DrawPanel dp){
        exitButton = new Button(Main.SCREEN_WIDTH/2-100, Main.SCREEN_HEIGHT-100, 200, 60, "Exit", 45);
        this.dp = dp;
        
        File directory = new File(new File("").getAbsolutePath().concat("/src/assets"));
        File[] tempFiles = directory.listFiles();
        File infoFile = null;
        for(File f : tempFiles){
            if(f.getName().contains(".txt") && f.getName().contains("level"))
                numLevels++;
            if(f.getName().contains("info"))
                infoFile = f;
        }
        levels = new File[numLevels];
        int i = 0;
        for(File f : tempFiles){
            if(f.getName().contains(".txt") && f.getName().contains("level")){
                levels[i] = f;
                i++;
            }
        }
        
        Scanner infoIn = null;
        try {infoIn = new Scanner(infoFile);}catch (FileNotFoundException ex) {System.out.println(ex);}
        String info = infoIn.nextLine();
        currentLevel = Integer.parseInt(info);
        highestLevelReached = currentLevel;
        
        font = new Font("Arial", Font.BOLD, fontSize);
        
        buffer = (Main.SCREEN_WIDTH-400)/5;
    }
    
    public void drawLevelSelctor(Graphics g){
        g.setColor(new Color(100,100,100));
        g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        g.setFont(font);
        for(int i = 0; i < levels.length; i++){
            if(i+1 <= highestLevelReached)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.RED);
            g.fillRoundRect(buffer*(((i)%4)+1)+100*((i)%4), 20+120*(i/4), buttonDim, buttonDim, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString(""+(i+1), buffer*(((i)%4)+1)+100*((i)%4)+fontSize/2, 20+120*(i/4)+fontSize/4+fontSize);
        }
        
        exitButton.drawButton(g);
    }
    
    public void mouseClicked(MouseEvent e){
        for(int i = 0; i < numLevels; i++){
            if(buttonPressed(i, e.getX(), e.getY())){
                currentLevel = i+1;
                dp.newLevel();
            }
        }
        
        if(exitButton.mouseEvent(e.getX(), e.getY(), true))
            dp.exitLevelSelector();
    }
    
    public void mouseMoved(MouseEvent e){
        exitButton.mouseEvent(e.getX(), e.getY(), false);
    }
    
    private boolean buttonPressed(int buttonNum, int x, int y){
        int xPos = buffer*(((buttonNum)%4)+1)+100*((buttonNum)%4);
        int yPos = 20+120*(buttonNum/4);
        
        return (x > xPos && x < xPos+buttonDim && y > yPos && y < yPos+buttonDim);
    }
    
    public void nextLevel(){
        if(currentLevel < levels.length)
            currentLevel++;
        
        if(currentLevel > highestLevelReached){
            highestLevelReached = currentLevel;
            //write level to file
            FileWriter write = null;
            try {
                write = new FileWriter(new File("").getAbsolutePath().concat("/src/assets").concat("/info.txt"), false);
            } catch (IOException ex) {}
            PrintWriter print_line = new PrintWriter(write);
            String fileText = ""+highestLevelReached;
            print_line.printf("%s",fileText);
            print_line.close(); 
        }
    }
    
    public int getCurrentLevel(){
        return currentLevel;
    }
    
    public String getCurrentLevelName(){
        return levels[currentLevel-1].getName();
    }
    
    public String getCurrentLevelLocation(){
        return levels[currentLevel-1].getParent();
    }
}
