package survive;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class StartScreen{
    private ArrayList<Button> startButtons;
    private int animSteps = 60;
    private int animStepCount = 1;
    private Color color = new Color(0,0,0,0);
    private boolean runAnimation = false;
    private boolean showButtons = true;
    private boolean showScreen = true;
    private boolean runFinishAnim = false;
    private boolean isExitingPlay = false;
  
    //Constructor: Initialize buttons
    public StartScreen(){
        startButtons = new ArrayList<Button>();
        startButtons.add(new Button(Main.SCREEN_WIDTH/2-110, Main.SCREEN_HEIGHT/2-150, 220, 60, "Start Game",-1));
        startButtons.add(new Button(Main.SCREEN_WIDTH/2-110, Main.SCREEN_HEIGHT/2-70, 220, 60, "Map Maker",-1));
        startButtons.add(new Button(Main.SCREEN_WIDTH/2-110, Main.SCREEN_HEIGHT/2+10, 220, 60, "Exit Game",-1));
        startButtons.add(new Button(Main.SCREEN_WIDTH/2-110, Main.SCREEN_HEIGHT/2+90, 220, 60, "Chose Level",-1));
    }

    //drw buttons
    public void drawScreen(Graphics g){
        if(showScreen){
            if(showButtons){
                g.setColor(new Color(150,150,150,150));
                g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
                for (Button b : startButtons){
                    b.drawButton(g);
                }
            }
            g.setColor(color);
            g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        }
    }

    public ArrayList<Button> getButtons(){
        return startButtons;
    }
    
    public void setAnimation(){
        color = new Color(0,0,0,0);
        animStepCount = 1;
        runAnimation = true;
    }
    
    public void setExitPlayingAnimation(){
        setFinishAnimaton();
        isExitingPlay = true;
    }
    
    public void setFinishAnimaton(){
        showScreen = true;
        showButtons = false;
        runAnimation = true;
        animStepCount = 1;
        runFinishAnim = true;
    }
    
    public void updateAnimation(DrawPanel dp, Map map){
        if(animStepCount <= animSteps){
            if(!runFinishAnim){
                if(animStepCount <= animSteps/2)
                    color = new Color(200,200,200,(int)Main.map(animStepCount,1,animSteps/2,0,255));
                else
                    color = new Color(150,150,150,(int)Main.map(animStepCount,animSteps,animSteps/2,0,255));
                if(animStepCount == animSteps/2){
                    showButtons = false;
                    dp.haltPlayerUpdate();
                    dp.resetPlayer();
                }
            }
            else{
                if(animStepCount <= animSteps/2)
                    color = new Color(200,200,200,(int)Main.map(animStepCount,1,animSteps/2,0,255));
                else if(animStepCount >= animSteps/2+map.getResetSteps()+1)
                    color = new Color(200,200,200,(int)Main.map(animStepCount,animSteps,animSteps/2+map.getResetSteps()+1,0,255));
                if(animStepCount == animSteps/2){
                    dp.resumePlayerUpdate();
                    if(!isExitingPlay)
                        dp.nextLevel();
                }
                if(animStepCount == animSteps/2+map.getResetSteps()+1){
                    showButtons = true;
                    dp.stopTimer();
                    dp.resetTimer();
                }
            }
            animStepCount++;
        }
        else{
            if(!runFinishAnim){
                showScreen = false;
                dp.resumePlayerUpdate();
            }
            else{
                color = new Color(0,0,0,0);
            }
            if(isExitingPlay)
                isExitingPlay = false;
            runAnimation = false;
            runFinishAnim = false;
        }
    }
    
    public boolean isAnimRunning(){
        return runAnimation;
    }
    
    public void closeScreen(){
        showScreen = false;
    }
    
    public boolean isShowing(){
        return showScreen;
    }
}
