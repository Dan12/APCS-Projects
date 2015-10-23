package survive;

import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main {
	
    public static int PLAYER_DIM = 20;
    public static int SPIKE_SIZE = 20;
    public static int SCREEN_HEIGHT = 800;
    public static int SCREEN_WIDTH = 800;
    public static int BAND_NUMS = 7;
    public static int BACKGROUND_BAND = 0;
    public static int SQUARE_BAND = 5;
    public static int SPIKE_BAND = 2;
    public static int PLAYER_BAND = 4;
    
    private static JFrame frame;
	
    public static void main(String[] args){
        frame = new JFrame("Survive");
        DrawPanel dp = new DrawPanel();
        
        frame.setContentPane(dp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(10, 10);
        frame.setVisible(true);
        frame.pack();

        dp.updateAction();
    }
        
    /*Map fuction: scales x, which has in_min and in_max range, to out_min and out_max
    **If scaled value is outside range of out_min and out_max, make it in range*/
    public static float map(float x, float in_min, float in_max, float out_min, float out_max){
        float ret = (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        if(ret > out_max)
            ret = out_max;
        if(ret < out_min)
          ret = out_min;
        return ret;
    }
    
    //close window, when esc key pressed
    public static void closeWindow(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
