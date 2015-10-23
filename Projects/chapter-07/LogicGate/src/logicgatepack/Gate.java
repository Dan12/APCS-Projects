package logicgatepack;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Gate{
    
    //Type(0-2): NOT, AND, OR
    
    String gID;
    int gType;
    int gOutVal;
    String gInputID1;
    String gInputID2;
    int gInputVal1;
    int gInputVal2;
    boolean gOutValSet;
    int gXPos;
    int gYPos;
    ClickableButton gButton;
    
    public Gate(int t, int id){
        try {
            switch(t){
                case 0:
                    gButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/not.jpg")),0,0,50,50);
                    break;
                case 1:
                    gButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/and.jpg")),0,0,50,50);
                    break;
                case 2:
                    gButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/or.jpg")),0,0,50,50);
                    break;
                case 3:
                    gButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/xor.png")),0,0,50,50);
                    break;
            }
        } catch (IOException ex) {System.out.println(ex);}
        
        gType = t;
        gID = "g"+id;
        gOutValSet = false;
        gInputID1 = "";
        gInputID2 = "";
        gInputVal1 = -1;
        gInputVal2 = -1;
        gOutVal = -1;
        if(gType == 0)
            gInputVal2 = 0;
    }
    
    public void setInputID1(String id){
        gInputID1 = id;
    }
    
    public void setInputID2(String id){
        gInputID2 = id;
    }
    
    public void setInput1Val(int v){
        gInputVal1 = v;
    }
    
    public void setInput2Val(int v){
        gInputVal2 = v;
    }
    
    public boolean setOutput(){
        if(gInputVal1 != -1 && gInputVal2 != -1){
            switch(gType){
                case 0: //NOT
                    if(gInputVal1 == 1)
                        gOutVal = 0;
                    else
                        gOutVal = 1;
                    break;
                case 1: //AND
                    if(gInputVal1 == 1 && gInputVal2 == 1)
                        gOutVal = 1;
                    else
                        gOutVal = 0;
                    break;
                case 2: //OR
                    if(gInputVal1 == 1 || gInputVal2 == 1)
                        gOutVal = 1;
                    else
                        gOutVal = 0;
                    break;
                case 3: //XOR
                    if((gInputVal1 == 1 || gInputVal2 == 1) && (gInputVal1 != 1 || gInputVal2 != 1))
                        gOutVal = 1;
                    else
                        gOutVal = 0;
                    break;
            }
            gOutValSet = true;
            return true;
        }
        else{
            gOutVal = -1;
            return false;
        }
    }
    
    public void setPosition(int x, int y){
        gXPos = x;
        gYPos = y;
        gButton.cXPos = gXPos;
        gButton.cYPos = gYPos;
    }
    
    public void changePositions(int x, int y){
        gXPos += x;
        gYPos += y;
        gButton.cXPos = gXPos;
        gButton.cYPos = gYPos;
    }
    
}