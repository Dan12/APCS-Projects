package logicgatepack;

import java.io.IOException;
import javax.imageio.ImageIO;

public class LED{
    
    String lID;
    int lInput;
    boolean lOn;
    String lInputID;
    int lXPos;
    int lYPos;
    ClickableButton lButton;
    
    public LED(int id){
        try {
            lButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/ledoff.jpg")),0,0,50,50);
        } catch (IOException ex) {System.out.println(ex);}
        
        lID = "l"+id;
        lInput = -1;
        lOn = false;
        lInputID = "";
    }
    
    public void setInputId(String id){
        lInputID = id;
    }
    
    public void setInputVal(int v){
        lInput = v;
    }
    
    public boolean setState(){
        if(lInput != -1){
            if(lInput == 1){
                try {
                    lButton.cImg = ImageIO.read(Main.class.getResource("/res/ledon.jpg"));
                } catch (IOException ex) {System.out.println(ex);}
                lOn = true;
            }
            else{
                try {
                    lButton.cImg = ImageIO.read(Main.class.getResource("/res/ledoff.jpg"));
                } catch (IOException ex) {System.out.println(ex);}
                lOn = false;
            }
            return true;
        }
        else{
            try {
                lButton.cImg = ImageIO.read(Main.class.getResource("/res/ledoff.jpg"));
            } catch (IOException ex) {System.out.println(ex);}
            lOn = false;
            return false;
        }
    }
    
    public void setPosition(int x, int y){
        lXPos = x;
        lYPos = y;
        lButton.cXPos = lXPos;
        lButton.cYPos = lYPos;
    }
    
    public void changePositions(int x, int y){
        lXPos += x;
        lYPos += y;
        lButton.cXPos = lXPos;
        lButton.cYPos = lYPos;
    }
    
}