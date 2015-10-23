package survive;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TimeHandler implements ActionListener{
    
    private Timer timer;
    private int millisGoneBy;
    private int millisToCount;
    private DrawPanel dp;
    private Font font;
    private int fontSize = 30;
    private int fontWidth = -1;
    private boolean timerRunning;
    
    public TimeHandler(DrawPanel dp){
        //timer = new Timer(1, this);
        millisGoneBy = 0;
        millisToCount = 0;
        this.dp = dp;
        font = new Font("Arial", Font.BOLD, fontSize);
        timerRunning = false;
    }
    
    public void setToCount(int c){
        millisToCount = c;
    }
    
    public void startTimer(){
        //timer.start();
        timerRunning = true;
    }
    
    public void stopTimer(){
        //timer.stop();
        timerRunning = false;
    }
    
    public void resetTimer(){
        millisGoneBy = 0;
    }
    
    public void drawTimer(Graphics g){
        float timePassed = (((float)(millisToCount-millisGoneBy))/1000);
        String time = String.format("%2.4f", timePassed);
        if(timePassed < 10)
            time = "0"+time;
        if(fontWidth == -1)
            fontWidth = (int) (g.getFontMetrics(font).getStringBounds(time, g).getWidth());
        g.setColor(Color.WHITE);
        g.fillRect(Main.SCREEN_WIDTH/2-(fontWidth+10)/2, 10, fontWidth+10, fontSize);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(time, Main.SCREEN_WIDTH/2-fontWidth/2, fontSize+5);
    }

    public void updateTimer(){
        if(timerRunning){
             millisGoneBy += dp.getMilliUpdate();
            if(millisGoneBy >= millisToCount){
                dp.resetPlayer();
                millisGoneBy = 0;
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        millisGoneBy++;
        if(millisGoneBy >= millisToCount){
            dp.resetPlayer();
            millisGoneBy = 0;
        }
    }
}
