package logicgatepack;

import java.io.IOException;
import javax.imageio.ImageIO;


public class Source{
    
    String sID;
    int sVal;
    int sXPos;
    int sYPos;
    ClickableButton sButton;
    
    public Source(int id){
        try {
            sButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/sourceoff.jpg")),0,0,50,50);
        } catch (IOException ex) {System.out.println(ex);}
        
        sID = "s"+id;
        sVal = 0;
    }
    
    public void toggleVal(){
        if(sVal == 0){
            try {
                sButton.cImg = ImageIO.read(Main.class.getResource("/res/sourceon.jpg"));
            } catch (IOException ex) {System.out.println(ex);}
            sVal = 1;
        }
        else if(sVal == 1){
            try {
                sButton.cImg = ImageIO.read(Main.class.getResource("/res/sourceoff.jpg"));
            } catch (IOException ex) {System.out.println(ex);}
            sVal = 0;
        }
    }
    
    public void setPosition(int x, int y){
        sXPos = x;
        sYPos = y;
        sButton.cXPos = sXPos;
        sButton.cYPos = sYPos;
    }
    
    public void changePositions(int x, int y){
        sXPos += x;
        sYPos += y;
        sButton.cXPos = sXPos;
        sButton.cYPos = sYPos;
    }
    
}