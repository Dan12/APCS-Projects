package survive;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javax.swing.JFileChooser;

public class DrawPanel extends JFXPanel implements KeyListener, MouseMotionListener, MouseListener{

    private int updateMillis = 20;

    private Character player;
    
    private long duration = 0;
    
    private String songName = "Secrets.mp3";
    private MediaPlayer mediaPlayer;
    private int numBands = Main.BAND_NUMS;
    private double audioInterval = 0.05;
    private int widthBands = 30;
    private float[] bands;
    private float[] prevGain;
    private float[] gainChange;
    private boolean showBars = false;
    private boolean muteMusic = false;
    
    private int rChange = 1;
    private int bChange = 1;
    private int gChange = 1;
    private int rColor = 0;
    private int bColor = 30;
    private int gColor = 60;
    private int bandMax = 60;
    
    private Map map;
    
    private boolean paused = false;
    
    private boolean songLoaded = false;
    
    private boolean closeWindow = false;
    
    private Button changeMusicButton;
    private Button startScreenButton;
    
    private StartScreen startScreen;
    
    private LevelMaker levelMaker;
    
    private boolean playerUpdate = true;
    
    private LevelHandler levelHandler;
    
    private TimeHandler timeHandler;
    
    private MusicHandler musicHandler;
    
    //1 = play, 2 = map maker, 3 = level selector
    private int mode = 1;
    
    public DrawPanel(){
        //Set JPanel Preferences
        super.setOpaque(false);
        super.setPreferredSize(new Dimension(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT));
        super.setBackground(new Color(255, 255, 255));
        addKeyListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        super.setFocusable(true);
        super.setLayout(null);
        
        //Print out total lines of code in project
        LineCounter lineCounter = new LineCounter();

        //Setup bands and band change traking arrays
        bands = new float[numBands];
        gainChange = new float[numBands];
        prevGain = new float[numBands];
        
        timeHandler = new TimeHandler(this);
        levelHandler = new LevelHandler(this);
        musicHandler = new MusicHandler();
        
        map = new Map(levelHandler.getCurrentLevelLocation(),levelHandler.getCurrentLevelName(), this);
        player = new Character(map);
        
        //set up button
        changeMusicButton = new Button(Main.SCREEN_WIDTH/2-110, Main.SCREEN_HEIGHT/2-70, 220, 60, "Change Music",-1);
        startScreenButton = new Button(Main.SCREEN_WIDTH/2-110, Main.SCREEN_HEIGHT/2+10, 220, 60, "To Main Menu",-1);
        
        //set up music player
        mediaPlayerInit(musicHandler.getNextFile());
        
        //Set up start screen
        startScreen = new StartScreen();
        startScreen.getButtons().get(0).setText("Survive Level "+levelHandler.getCurrentLevel());
        
        levelMaker = new LevelMaker(this);    
        
        timeHandler.setToCount(map.getTimeToCount());
    }
	
	@Override  
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //play mode
        if(mode == 1){
            //background gradient
            RadialGradientPaint gp = new RadialGradientPaint(Main.SCREEN_WIDTH/2, Main.SCREEN_HEIGHT/2, (Main.SCREEN_WIDTH+Main.SCREEN_HEIGHT)/2, new float[]{Main.map(bands[Main.BACKGROUND_BAND],0,bandMax,0,0.3f),0.7f}, new Color[]{new Color(255,255,255),new Color(rColor,gColor,bColor)});
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(gp);
            g2.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

            //bars
            if(showBars){
                g.setColor(Color.BLACK);
                for(int i = 0; i < bands.length; i++){
                    g.fillRect(i*widthBands, 0, widthBands-4, (int)bands[i]*2);
                }
            }

            //draw squares
            map.drawMap(g, bands);

            //draw player
            player.drawCharacter(g, bands);
            
            timeHandler.drawTimer(g);

            //Draw start screen
            startScreen.drawScreen(g);
        }
        //level maker mode
        else if(mode == 2){
            levelMaker.drawScreen(g);
        }
        else if(mode == 3){
            levelHandler.drawLevelSelctor(g);
        }
        //draw pause screen
        if(paused && songLoaded){
            g.setColor(new Color(150,150,150,150));
            g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
            changeMusicButton.drawButton(g);
            startScreenButton.drawButton(g);
        }
    }
	
    public void updateAction(){
        //Loop until close window
        while(!closeWindow){
            try {
                if(duration < updateMillis)
                    Thread.sleep(updateMillis-duration);
            } catch (InterruptedException e) {System.out.println(e);}
            long startTime = System.nanoTime();
            
            if(mode == 1)
                playUpdate();
            else if(mode == 2)
                levelMakerUpdate();
            
            songUpdate();
            
            //repaint frame
            repaint();
            
            timeHandler.updateTimer();
            
            //get duration of updates and repainting, will be subtracted from sleep delay
            long endTime = System.nanoTime();
            duration = (endTime-startTime)/1000000;
            //System.out.println(duration);
        }
        
        //run shut down method
        shutDown();
    }
    
    public void songUpdate(){
        //check if song has loaded; unpause if loaded
        if(!songLoaded){
            if(!mediaPlayer.getStatus().equals(Status.UNKNOWN)){
                songLoaded = true;
                if(!startScreen.isShowing() && mode == 1 && paused)
                    resumeGame();
                mediaPlayer.play();
            }
        }
        else{
            if(mediaPlayer.getCurrentTime().equals(mediaPlayer.getStopTime()))
                mediaPlayerInit(musicHandler.getNextFile());
        }
    }
    
    //updates for playing game
    public void playUpdate(){
        //call all updates
        if(!paused){
            if(playerUpdate)
                player.update(map);
            updateColor();
        }
        if(startScreen.isAnimRunning())
            startScreen.updateAnimation(this,map);
    }
    
    //updates for level maker
    public void levelMakerUpdate(){
        levelMaker.updateKeys();
    }
    
    //close level maker by setting screen mode to 1
    public void closeLevelMaker(){
        mode = 1;
    }
    
    //closes and objects
    private void shutDown(){
        mediaPlayer.stop();
        mediaPlayer.dispose();
        Main.closeWindow();
    }
    
    //slowly change background color around
    private void updateColor(){
        rColor += rChange;
        bColor += bChange;
        gColor += gChange;
        
        if(rColor > 90){
            rColor = 90;
            rChange *= -1;
        }
        if(rColor < 0){
            rColor = 0;
            rChange *= -1;
        }
        if(gColor > 90){
            gColor = 90;
            gChange *= -1;
        }
        if(gColor < 0){
            gColor = 0;
            gChange *= -1;
        }
        if(bColor > 90){
            bColor = 90;
            bChange *= -1;
        }
        if(bColor < 0){
            bColor = 0;
            bChange *= -1;
        }
    }
    
    //Toggle between pause and resume when enter key pressed
    private void pauseKeyPress(){
        if(!paused)
            pauseGame();
        else
            resumeGame();
    }
    
    //pause music and set pause variable to true
    private void pauseGame(){
        paused = true;
        mediaPlayer.pause();
        stopTimer();
    }
    
    //resume music and set pause variable to false
    private void resumeGame(){
        paused = false;
        mediaPlayer.play();
        startTimer();
    }
    
    public void newLevel(){
        changeLevel();
        exitLevelSelector();
    }
    
    public void exitLevelSelector(){
        mode = 1;
    }
    
    public void nextLevel(){
        levelHandler.nextLevel();
        changeLevel();
        resetPlayer();
    }
    
    private void changeLevel(){
        map = new Map(levelHandler.getCurrentLevelLocation(), levelHandler.getCurrentLevelName(), this);
        player.newMap(map);
        startScreen.getButtons().get(0).setText("Survive Level "+levelHandler.getCurrentLevel());
    }
    
    private void toStartScreen(){
        System.out.println("hey");
        resumeGame();
        stopTimer();
        startScreen.setExitPlayingAnimation();
    }
    
    public void resetPlayer(){
        player.gotHit(map);
    }
    
    public void setTimerCount(int c){
        timeHandler.setToCount(c);
    }
    
    public void startTimer(){
        timeHandler.startTimer();
    }
    
    public void resetTimer(){
        timeHandler.resetTimer();
    }
    
    public void stopTimer(){
        timeHandler.stopTimer();
    }
        
    public void haltPlayerUpdate(){
        playerUpdate = false;
    }
    
    public void resumePlayerUpdate(){
        playerUpdate = true;
    }
    
    public void finishLevel(){
        haltPlayerUpdate();
        startScreen.setFinishAnimaton();
    }
    
    private void mediaPlayerInit(File f){
        mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
        mediaPlayerSetup();
        mediaPlayer.play();
    }
    
    //standard mediaplayer set up
    private void mediaPlayerSetup(){
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setAudioSpectrumInterval(audioInterval);
        mediaPlayer.setAudioSpectrumNumBands(numBands);
        //Listener called every audioInterval and returns numBands bands of information
        mediaPlayer.setAudioSpectrumListener(new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                //store all bands and band chages
                for(int i = 0; i < numBands; i++){
                    bands[i] = 60+magnitudes[i];
                    gainChange[i] = bands[i]-prevGain[i];
                    prevGain[i] = bands[i];
                }
            }
        });
        mediaPlayer.setMute(muteMusic);
        songLoaded = false;
    }

    //launch filechooser to change song
    public void changeSong(){
        JFileChooser fc = new JFileChooser(new File(new File("").getAbsolutePath().concat("/src/assets/")));
        fc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    File file = fc.getSelectedFile();
                    if(file != null){
                        songName = file.getName();
                        mediaPlayer.stop();
                        mediaPlayer = new MediaPlayer(new Media(new File(file.getParent()+"/"+songName).toURI().toString()));
                        mediaPlayerSetup();
                        musicHandler.setSongAt(songName);
                    }
                    else
                        System.out.println("Open command cancelled by user.");
            }
        });
        int val = fc.showOpenDialog(null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //sets player's spacePress to true, lauches player off square
        if(e.getKeyCode() == 32 && mode == 1 && !startScreen.isShowing() && !map.isFinishing())
            player.spacePress = true;
        
        //sets closeWindow to true, which exits update while loop and closes the window
        if(e.getKeyCode() == 27)
            closeWindow = true;
        
        //if enter key pressed, toggle paused and unpaused
        if(e.getKeyCode() == 10)
            pauseKeyPress();
        
        //send key press event to levelMaker
        if(!paused)
            levelMaker.keyPressed(e);
    }
    
    //mousemoved event, only applies to button
    @Override
    public void mouseMoved(MouseEvent e) {
        if(paused){
            changeMusicButton.mouseEvent(e.getX(), e.getY(), false);
            startScreenButton.mouseEvent(e.getX(), e.getY(), false);
        }
        //Start screen hover events
        else if(startScreen.isShowing() && !paused && mode == 1){
            ArrayList<Button> startButtons = startScreen.getButtons();
            for(Button b : startButtons){
              b.mouseEvent(e.getX(), e.getY(), false);
            }
        }
        
        //send mouse move event to levelMaker
        if(!paused && mode == 2)
            levelMaker.mouseMoved(e);
        
        if(mode == 3)
            levelHandler.mouseMoved(e);
    }

    //mouseclicked event, only applies to button
    @Override
    public void mouseClicked(MouseEvent e) {
        if(paused){
            if(changeMusicButton.mouseEvent(e.getX(), e.getY(), true))
                changeSong();
            if(startScreenButton.mouseEvent(e.getX(), e.getY(), true))
                toStartScreen();
        }
        //Start screen clicked events
        else if (startScreen.isShowing() && !paused && mode == 1){
            ArrayList<Button> startButtons = startScreen.getButtons();
            if (startButtons.get(0).mouseEvent(e.getX(), e.getY(), true)){
                startScreen.setAnimation();
            }
            if(startButtons.get(1).mouseEvent(e.getX(), e.getY(), true)){
                mode = 2;
                levelMaker.showLevelMaker();
            }
            if(startButtons.get(2).mouseEvent(e.getX(), e.getY(), true))
                closeWindow = true;
            if(startButtons.get(3).mouseEvent(e.getX(), e.getY(), true)){
                mode = 3;
            }
        }
        
        if(mode == 3)
            levelHandler.mouseClicked(e);
        
        //send mouse clicked event to levelMaker
        if(!paused && mode == 2)
            levelMaker.mouseClicked(e,this);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //send key released event to levelMaker
        if(!paused)
            levelMaker.keyReleased(e);
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        //send mouse drag event to levelMaker
        if(!paused)
            levelMaker.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //send mouse press event to levelMaker
        if(!paused)
            levelMaker.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //send mouse release event to levelMaker
        if(!paused)
            levelMaker.mouseReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    public LevelMaker getLevelMaker(){
        return levelMaker;
    }
    
    public int getMilliUpdate(){
        return updateMillis;
    }
}
