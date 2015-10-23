package learner2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Learner2 {
    
    String lName;
    float lXPos;
    float lYPos;
    float lDir;
    float speed = 5;
    int lRadius = 30;
    Color color;
    NeuralNetwork nw;
    ArrayList<Bullet> bullets = new ArrayList<>();
    Random rand = new Random();
    int rechargeTime = 100;
    int rechargeTimeAt = 0;
    boolean canShoot = false;
    int lWinWidth;
    int lWinHeight;
    int hit = 0;
    int gHit = 0;
    int prevGHit = 0;
    int prevHit = 0;
    
    float lS1;
    float lB1;
    float lX1;
    float lY1;
    
    float lIS1;
    float lIX1;
    float lIY1;
    
    float lS2;
    float lB2;
    float lX2;
    float lY2;
    
    float lIS2;
    float lIX2;
    float lIY2;
    
    float lFOV = 10;
    
    float maxX;
    float minX;
    float maxY;
    float minY;
    
    float lBB1;
    float lBX1Int;
    float lBY1Int;

    float lBB2;
    float lBX2Int;
    float lBY2Int;
    
    ArrayList<ActionReactionResult> data = new ArrayList<>();
    
    public Learner2(float x, float y, Color c, int ww, int wh, String n){
        lXPos = x;
        lYPos = y;
        color = c;
        lDir = 90;
        System.out.println("Learner: "+n);
        nw = new NeuralNetwork();
        lWinWidth = ww;
        lWinHeight = wh;
        lName = n;
    }
    
    public void drawLearner(Graphics g){
        g.setColor(Color.BLACK);
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).drawBullet(g);
        }
        g.drawLine((int)(lXPos), (int)(lYPos), (int)(lX1), (int)(lY1));
        g.drawLine((int)(lXPos), (int)(lYPos), (int)(lX2), (int)(lY2));
        g.setColor(color);
        g.fillOval((int)lXPos-lRadius, (int)lYPos-lRadius, lRadius*2, lRadius*2);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(lName, (int) (lXPos-lRadius*0.3), (int) (lYPos + lRadius*0.3));
    }
    
    public void update(Learner2 other){
        setBoundingBox();
        setLines();
        
        if(!canShoot)
            rechargeTimeAt++;
        if(rechargeTimeAt>rechargeTime){
           canShoot = true;
           rechargeTimeAt = 0;
        }
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).update();
            if(distance(bullets.get(i).bXPos, other.lXPos, bullets.get(i).bYPos, other.lYPos, other.lRadius+bullets.get(i).bRadius)){
              bullets.remove(i);
              i--;
              this.hit++;
              other.gHit++;
            }
            else if(bullets.get(i).bXPos+bullets.get(i).bRadius < 0 || bullets.get(i).bXPos-bullets.get(i).bRadius > lWinWidth || bullets.get(i).bYPos+bullets.get(i).bRadius < 0 || bullets.get(i).bYPos-bullets.get(i).bRadius > lWinHeight){
                bullets.remove(i);
                i--;
            }
        }
        //actions(0-4): not see anything, see learner, see bullet, got hit, hit learner
        //x++,x--,y++,y--,dir++,dir--,shoot
        int a;
        if(gotHit())
          a = 3;
        else if(hitLearner())
          a = 4;
        else if(seeBullet(other))
          a = 2;
        else if(seeLearner(other))
          a = 1;
        else
          a = 0;
        react(a);
        //System.out.println(a);
    }
    
    public boolean distance(double x1, double x2, double y1, double y2, double d){
      if((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2) <= d*d)
        return true;
      return false;
    }
    
    //actions(0-4): see learner, see bullet, get hit, hit learner, see nothing
    //x++,x--,y++,y--,dir++,dir--,shoot
    public void react(int a){
        int r = this.nw.reaction(a);
        //System.out.println(r);
        switch(r){
            case 1:
                lXPos+=Math.cos(Math.toRadians(lDir))*speed;
                break;
            case 2:
                lXPos-=Math.cos(Math.toRadians(lDir))*speed;
                break;
            case 3:
                lYPos+=Math.sin(Math.toRadians(lDir))*speed;
                break;
            case 4:
                lYPos-=Math.sin(Math.toRadians(lDir))*speed;
                break;
            case 5:
                lDir+=speed;
                break;
            case 6:
                lDir-=speed;
                break;
            case 7:
                if(canShoot){
                    shoot();
                    canShoot = false;
                }
                break;
        }
        if(lXPos<lRadius)
            lXPos = lRadius;
        if(lXPos>lWinWidth-lRadius)
            lXPos = lWinWidth-lRadius;
        if(lYPos<lRadius)
            lYPos = lRadius;
        if(lYPos>lWinHeight-lRadius)
            lYPos = lWinHeight-lRadius;
        data.add(new ActionReactionResult(a, r, hit, gHit));
    }
    
    public void shoot(){
        bullets.add(new Bullet(lXPos,lYPos,lDir));
    }
    
    public boolean seeBullet(Learner2 other){
        for(int i = 0; i < other.bullets.size(); i++){
            if(other.bullets.get(i).bXPos-other.bullets.get(i).bRadius < maxX && other.bullets.get(i).bXPos+other.bullets.get(i).bRadius > minX && other.bullets.get(i).bYPos-other.bullets.get(i).bRadius < maxY && other.bullets.get(i).bYPos+other.bullets.get(i).bRadius > minY){
                //System.out.println("Bullet Inside Box");
                
                other.bullets.get(i).setLine(lIS1,lIS2,lDir,lFOV);

                setInvInt(other.bullets.get(i).bB1,other.bullets.get(i).bB2);
                
                float d1 = distSq(lIX1,lIY1,lIX2,lIY2);
                float d2 = distSq(lIX1,lIY1,other.bullets.get(i).bX1Int,other.bullets.get(i).bY1Int);
                float d3 = distSq(lIX2,lIY2,other.bullets.get(i).bX2Int,other.bullets.get(i).bY2Int);

                if(d1 > d2 && d1 > d3){
                    //System.out.println("Bullet In Sight");
                    return true;
                }
                else
                    return false;
            }
            else
                return false;
        }
        return false;
    }
    
    public boolean seeLearner(Learner2 other){
        if(other.lXPos-other.lRadius < maxX && other.lXPos+other.lRadius > minX && other.lYPos-other.lRadius < maxY && other.lYPos+other.lRadius > minY){
            //System.out.println("Learner Inside Box");
            
            other.setLine(lIS1,lIS2,lDir,lFOV);

            setInvInt(other.lBB1,other.lBB2);

            float d1 = distSq(lIX1,lIY1,lIX2,lIY2);
            float d2 = distSq(lIX1,lIY1,other.lBX1Int,other.lBY1Int);
            float d3 = distSq(lIX2,lIY2,other.lBX2Int,other.lBY2Int);

            if(d1 > d2 && d1 > d3){
                //System.out.println("Learner In Sight");
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    
    public boolean gotHit(){
      if(gHit>prevGHit){
        prevGHit = gHit;
        return true;
      }
      prevGHit = gHit;
      return false;
    }
    
    public boolean hitLearner(){
      if(hit>prevHit){
        prevHit = hit;
        return true;
      }
      prevHit = hit;
      return false;
    }
    
    public void setLines(){
        float cD1 = (float)(Math.toRadians(lDir+lFOV));
        lX1 = lXPos+(float)(Math.cos(cD1)*lRadius);
        lY1 = lYPos+(float)(Math.sin(cD1)*lRadius);
        
        float cD2 = (float)(Math.toRadians(lDir-lFOV));
        lX2 = lXPos+(float)(Math.cos(cD2)*lRadius);
        lY2 = lYPos+(float)(Math.sin(cD2)*lRadius);
        
        lS1 = (lY1-lYPos)/(lX1-lXPos);
        lB1 = lYPos-(lS1*lXPos);
        
        lS2 = (lY2-lYPos)/(lX2-lXPos);
        lB2 = lYPos-(lS2*lXPos);
        
        lIS1 = -1*(1/lS1);
        lIS2 = -1*(1/lS2);
        
        //System.out.println(lX1+","+lY1+","+lX2+","+lY2);
        
        if(lX1 > lXPos){
            if(600*lS1+lB1 > 600 || 600*lS1+lB1 < 0){
                if(lY1 > lYPos)
                    lY1 = 600;
                else
                    lY1 = 0;
                lX1 = (lY1-lB1)/lS1;
            }
            else{
                lX1 = 600;
                lY1 = lS1*lX1+lB1;
            }
        }
        else{
            if(lB1 > 600 || lB1 < 0){
                if(lY1 > lYPos)
                    lY1 = 600;
                else
                    lY1 = 0;
                lX1 = (lY1-lB1)/lS1;
            }
            else{
                lX1 = 0;
                lY1 = lS1*lX1+lB1;
            }
        }
        if(lX2 > lXPos){
            if(600*lS2+lB2 > 600 || 600*lS2+lB2 < 0){
                if(lY2 > lYPos)
                    lY2 = 600;
                else
                    lY2 = 0;
                lX2 = (lY2-lB2)/lS2;
            }
            else{
                lX2 = 600;
                lY2 = lS2*lX2+lB2;
            }
        }
        else{
            if(lB2 > 600 || lB2 < 0){
                if(lY2 > lYPos)
                    lY2 = 600;
                else
                    lY2 = 0;
                lX2 = (lY2-lB2)/lS2;
            }
            else{
                lX2 = 0;
                lY2 = lS2*lX2+lB2;
            }
        }
    }
    
    public void setBoundingBox(){
        maxX = lX1;
        if(lX2 > maxX)
            maxX = lX2;
        if(lXPos > maxX)
            maxX = lXPos;
            
        minX = lX1;
        if(lX2 < minX)
            minX = lX2;
        if(lXPos < minX)
            minX = lXPos;
            
        maxY = lY1;
        if(lY2 > maxY)
            maxY = lY2;
        if(lYPos > maxY)
            maxY = lYPos;
            
        minY = lY1;
        if(lY2 < minY)
            minY = lY2;
        if(lYPos < minY)
            minY = lYPos;
            
        //System.out.println(minX+","+maxX+","+minY+","+maxY);
    }
    
    public void setInvInt(float b1, float b2){
        lIX1 = (lB1-b1)/(lIS1-lS1);
        lIY1 = lS1*lIX1 + lB1;
        
        lIX2 = (lB2-b2)/(lIS2-lS2);
        lIY2 = lS2*lIX2 + lB2;
    }
    
    public void setLine(float s1, float s2, float dir, float f){
        float theta1 = (float)(Math.toRadians(dir+(90+f)));
        lBX1Int = lXPos + (float)Math.cos(theta1)*lRadius;
        lBY1Int = lYPos + (float)Math.sin(theta1)*lRadius;
        //System.out.println(lBX1Int+","+lBY1Int);

        lBB1 = lYPos - (s1*lXPos);

        float theta2 = (float)(Math.toRadians(dir+(270-f)));
        lBX2Int = lXPos + (float)Math.cos(theta2)*lRadius;
        lBY2Int = lYPos + (float)Math.sin(theta2)*lRadius;

        lBB2 = lYPos - (s2*lXPos);
    }
    
    public void mutate(){
        //analyze actions and reactions from current gen and generate new gen characeristics
    }
    
    public float distSq(float x1, float y1, float x2, float y2){
        return ((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
    
    public class Bullet{
        float bXPos;
        float bYPos;
        float bDir;
        int bSpeed = 5;
        int bRadius = 8;
        
        float bB1;
        float bX1Int;
        float bY1Int;

        float bB2;
        float bX2Int;
        float bY2Int;
        
        public Bullet(float x, float y, float d){
            bXPos = x;
            bYPos = y;
            bDir = d;
        }
        
        public void drawBullet(Graphics g){
            g.setColor(Color.BLACK);
            g.fillOval((int)bXPos-bRadius, (int)bYPos-bRadius, bRadius*2, bRadius*2);
        }
        
        public void update(){
            bXPos+=Math.cos(Math.toRadians(bDir))*bSpeed;
            bYPos+=Math.sin(Math.toRadians(bDir))*bSpeed;
        }
        
        public void setLine(float s1, float s2, float dir, float f){
            float theta1 = (float)(Math.toRadians(dir+(90+f)));
            bX1Int = bXPos + (float)Math.cos(theta1)*bRadius;
            bY1Int = bYPos + (float)Math.sin(theta1)*bRadius;
            //System.out.println(bX1Int+","+bY1Int);

            bB1 = bYPos - (s1*bXPos);

            float theta2 = (float)(Math.toRadians(dir+(270-f)));
            bX2Int = bXPos + (float)Math.cos(theta2)*bRadius;
            bY2Int = bYPos + (float)Math.sin(theta2)*bRadius;

            bB2 = bYPos - (s2*bXPos);
        }
    }
    
    public class ActionReactionResult{
        int arrAction;
        int arrReaction;
        int arrResultHit;
        int arrResultGotHit;
        
        public ActionReactionResult(int a, int r, int rh, int rgh){
            arrAction = a;
            arrReaction = r;
            arrResultGotHit = rgh;
            arrResultHit = rh;
        }
    }
    
}
