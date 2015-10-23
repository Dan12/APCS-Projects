//package src.danweberrpg;
package danweberrpg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

public class StickFigure extends Object
{
  
    int xPos;
    int yPos;
    //0-head,1-lUpperArm,2-lLowerArm,3-rUpperArm,4-rLowerArm,5-lUpperLeg,6-lLowerLeg,7-rUpperLeg,8-rLowerLeg,9-upperBack,10-lowerBack
    Limb[] limbs;
    int walkAnimStep = 1;
    boolean walkAnimResart = false;
    
    //fwd-front/back sd-side
    int sdArmAngle = 55;
    int sdLowerArmIncrease = 70;
    int sdLegExtendAngle = 35;
    
    int restUA = 25;
    int restLA = 10;
    
    int xIncrease = 7;
    int yIncrease = 7;
    
    boolean fwdRev = false;
    
    boolean fromRest = true;
    boolean toRest = false;
    boolean stopped = false;
    boolean atRest = true;
    
    int movePosition = -1;
    
    int actualXPos;
    int actualYPos;
    
    int viewWidth;
    int viewHeight;
    boolean mapCentered;
    
    boolean hasPath = false;
    int[] pathDirections;
    int[] pathTimes;
    int pathIndex = 0;
    int pathIndexCount = -1;
    
    String figureName;
    int nameWidth = -1;
    int fontSize = (int) (Main.halfLimbHeight*1.5);
    Font font = new Font("Consolas",Font.PLAIN, fontSize);
    
    UpMove upAnimation;
    DownMove downAnimation;
    LeftMove leftAnimation;
    RightMove rightAnimation;
    
    public StickFigure(int x, int y, boolean mc, String n){
        xPos = x;
        yPos = y;
        actualXPos = x;
        actualYPos = y;
        viewWidth = Main.screenWidth;
        viewHeight = Main.screenHeight;
        mapCentered = mc;
        figureName = n;
        
        limbs = new Limb[11];
        limbs[1] = new Limb(new Point(xPos,yPos-Main.limbHeight), new Point(xPos,yPos), 0, "lua",9,1);
        limbs[2] = new Limb(new Point(xPos,yPos), new Point(xPos,yPos+Main.limbHeight), 0, "lla",1,1);
        limbs[3] = new Limb(new Point(xPos,yPos-Main.limbHeight), new Point(xPos,yPos), 0, "rua",9,1);
        limbs[4] = new Limb(new Point(xPos,yPos), new Point(xPos,yPos+Main.limbHeight), 0, "rla",3,1);
        limbs[5] = new Limb(new Point(xPos,yPos+Main.limbHeight), new Point(xPos,yPos+Main.limbHeight*2), 0, "lul",10,1);
        limbs[6] = new Limb(new Point(xPos,yPos+Main.limbHeight*2), new Point(xPos,yPos+Main.limbHeight*3), 0, "lll",5,1);
        limbs[7] = new Limb(new Point(xPos,yPos+Main.limbHeight), new Point(xPos,yPos+Main.limbHeight*2), 0, "rul",10,1);
        limbs[8] = new Limb(new Point(xPos,yPos+Main.limbHeight*2), new Point(xPos,yPos+Main.limbHeight*3), 0, "rll",7,1);
        limbs[9] = new Limb(new Point(xPos,yPos-Main.limbHeight), new Point(xPos,yPos), 0, "ub",-1,2);
        limbs[10] = new Limb(new Point(xPos,yPos), new Point(xPos,yPos+Main.limbHeight), 0, "lb",-1,1);
        limbs[0] = new Limb(new Point(xPos,yPos-Main.limbHeight), new Point(xPos,yPos-Main.limbHeight), 0, "h",9,1);
        
        //rest posistion
        changeAngle(restUA, 1);
        changeAngle(restLA, 2);
        changeAngle(-restUA, 3);
        changeAngle(-restLA, 4);
        changeAngle(restUA, 5);
        changeAngle(restLA, 6);
        changeAngle(-restUA, 7);
        changeAngle(-restLA, 8);
        
        upAnimation = new UpMove(this);
        downAnimation = new DownMove(this);
        rightAnimation = new RightMove(this);
        leftAnimation = new LeftMove(this);
    }
    
    public StickFigure(String[] s, int curOX, int curOY){
        figureName = s[0];
        int ox = Integer.parseInt(s[3])-curOX;
        int oy = Integer.parseInt(s[4])-curOY;
        xPos = Integer.parseInt(s[1]);
        yPos = Integer.parseInt(s[2]);
        viewWidth = Main.screenWidth;
        viewHeight = Main.screenHeight;
        mapCentered = false;
        
        actualXPos = xPos;
        actualYPos = yPos;
        
        xPos+=ox;
        yPos+=oy;
        
        limbs = new Limb[11];
        for(int i = 0; i < limbs.length; i++){
            if(i!=9 && i!=0)
                limbs[i] = new Limb(new Point(Integer.parseInt(s[6+i*5])+ox,Integer.parseInt(s[7+i*5])+oy), new Point(Integer.parseInt(s[8+i*5])+ox,Integer.parseInt(s[9+i*5])+oy), Integer.parseInt(s[5+i*5]), "", -1, 1);
            else if(i==9)
                limbs[i] = new Limb(new Point(Integer.parseInt(s[6+i*5])+ox,Integer.parseInt(s[7+i*5])+oy), new Point(Integer.parseInt(s[8+i*5])+ox,Integer.parseInt(s[9+i*5])+oy), Integer.parseInt(s[5+i*5]), "", -1, 2);
            else if(i==0)
                limbs[i] = new Limb(new Point(Integer.parseInt(s[6+i*5])+ox,Integer.parseInt(s[7+i*5])+oy), new Point(Integer.parseInt(s[6+i*5])+ox,Integer.parseInt(s[7+i*5])+oy), Integer.parseInt(s[5+i*5]), "", -1, 1);
        }
    }
    
    public String getInfoString(int ox, int oy){
        String temp = "";
        temp+=figureName+","+xPos+","+yPos+","+ox+","+oy+",";
        for(int i = 0; i < limbs.length; i++){
            temp+=limbs[i].toString();
            if(i<limbs.length-1)
                temp+=",";
        }
        return temp;
    }
    
    public void changeName(String s){
        figureName = s;
        nameWidth = -1;
    }
    
    public void drawFigure(Graphics g){     
        for(int i = 0; i <limbs.length; i++){
            if(limbs[i] != null){
                limbs[i].drawLimb(g);
            }
        }
        g.setFont(font);
        if(nameWidth == -1)
            nameWidth = (int) g.getFontMetrics().getStringBounds(figureName, g).getWidth();
        
        g.setColor(Color.WHITE);
        g.fillRect(xPos-nameWidth/2, yPos-(Main.limbHeight*2+Main.halfLimbHeight+fontSize), nameWidth, (int) (fontSize*1.2));
                
        g.setColor(Color.BLACK);
        g.drawString(figureName, xPos-nameWidth/2, yPos-(Main.limbHeight*2+Main.halfLimbHeight));
        
    }
    
    public void runAnim(){
        if(walkAnimStep >= 21){
            if(fromRest)
                fromRest = false;
            walkAnimStep = 1;
            if(toRest){
                movePosition = -1;
                toRest = false;
                fwdRev = false;
                atRest = true;
                fromRest = true;
            }
            if(stopped && !atRest)
                toRest = true;
            walkAnimResart = true;
        }
        
        if(hasPath){
            walkPath();
        }
        
        //up
        if(movePosition == 1)
            upAnimation.runAnimation();
            //upMove();
        
        //right
        if(movePosition == 2)
            rightAnimation.runAnimation();
            //rightMove();
        
        //left
        if(movePosition == 3)
            leftAnimation.runAnimation();
            //leftMove();
        
        //down
        if(movePosition == 4)
            downAnimation.runAnimation();
            //downMove();
        
        if(!atRest)
            walkAnimStep++;
        
        walkAnimResart = false;
    }
    
    public void setPath(int[] d, int[] t){
        hasPath = true;
        pathDirections = new int[d.length];
        pathTimes = new int[t.length];
        
        for(int i = 0; i < d.length; i++){pathDirections[i] = d[i];}
        for(int i = 0; i < t.length; i++){pathTimes[i] = t[i];}
        atRest = false;
        stopped = false;
    }
    
    public void walkPath(){
        if(walkAnimStep == 1)
            pathIndexCount++;
        if(pathIndexCount>=pathTimes[pathIndex]){
            pathIndex++;
            pathIndexCount = 0;
        }
        if(pathIndex>=pathDirections.length)
            pathIndex = 0;
        movePosition = pathDirections[pathIndex];
    }
    
    public void setAngle(double s, int l){
        double at = limbs[l].getAngle();
        changeAngle(s-at, l);
    }
    
    public void setLength(double len, int l){
        double at = limbs[l].getYDiff();
        changeLength(len-at, l);
    }
    
    public void setPosistion(int x, int y){
        changePosition(x-xPos, y-yPos);
    }
    
    public void changePosition(int cx, int cy){
        for(int i = 0; i < limbs.length; i++)
            limbs[i].updatePosition(cx, cy);
        xPos+=cx;
        yPos+=cy;
        if(mapCentered){
            if(xPos<0)
                setPosistion(0, yPos);
            if(xPos>viewWidth)
                setPosistion(viewWidth, yPos);
            if(yPos<0)
                setPosistion(xPos, 0);
            if(yPos>viewHeight)
                setPosistion(xPos, viewHeight);
        }
    }
    
    public void setActualPosition(int x, int y){
        actualXPos = x;
        actualYPos = y;
    }
    
    public void changeAngle(double c, int l){
        //System.out.println("----------");
        double[] temp = limbs[l].changeAngle(c);
        for(int i = 0; i <limbs.length; i++){
            if(limbs[i].cID == l){
                limbs[i].updateRotation(temp[0], temp[1]);
                int j = i+1;
                if(limbs[j].cID == i){
                    double[] temp2 = limbs[i].changeAngle(0);
                    limbs[j].updateRotation(temp2[0], temp2[1]);
                }
            }
            //System.out.println(i+": "+limbs[i].getAngle());
        }
    }
    
    public void changeLength(double c, int l){
        //System.out.println("----------");
        double temp[] = limbs[l].changeLength(c);
        for(int i = 0; i <limbs.length; i++){
            if(limbs[i].cID == l){
                limbs[i].updateRotation(temp[0],temp[1]);
                int j = i+1;
                if(limbs[j].cID == i){
                    double temp2[] = limbs[i].changeLength(0);
                    limbs[j].updateRotation(temp2[0],temp2[1]);
                }
            }
            //System.out.println(i+": "+limbs[i].getYDiff());
        }
    }
    
    public void goToAngle(int l, double strta, double enda, int strtt, int endt, int curt, boolean rev){
        if(rev){
            double tempa = enda;
            enda = strta;
            strta = tempa;
        }
            
        if(curt >= strtt && curt <= endt){
            if(curt < endt){
                setAngle(((enda-strta)/(endt-strtt))*(curt-strtt)+strta, l);
            }
            else{
                setAngle(enda, l);
            }
        }
    }
    
    public void goToLength(int l, double strtl, double endl, int strtt, int endt, int curt, boolean rev){
        if(rev){
            double templ = endl;
            endl = strtl;
            strtl = templ;
        }
        
        if(curt >= strtt && curt <= endt){
            if(curt < endt){
                setLength(((endl-strtl)/(endt-strtt))*(curt-strtt)+strtl, l);
            }
            else{
                setLength(endl, l);
            }
        }
    }
    
}