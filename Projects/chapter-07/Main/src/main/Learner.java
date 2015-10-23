package main;

import java.util.*;

public class Learner{
    float xL;
    float yL;
    float directionL;
    ArrayList<Result> results = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<ActionSequence> goodMoves = new ArrayList<>();
    ArrayList<ActionSequence> badMoves = new ArrayList<>();
    Random rand = new Random();
    int score;
    float radiusL = 30;
    int action = 0;
    int actionTime = 0;
    int actionDoTime = 40;
    int initSampleSize = 10000;
    int initSampleAt = 0;
    boolean initDecider = true;
    
    public Learner(float x, float y){
        xL = x;
        yL = y;
        directionL = 0;
        score = 0;
    }
    
    public void update(Learner other){
      choseAction();
      switch(action){
        case 0:
          if(xL < 600-radiusL)
            xL+=Math.cos(Math.toRadians(directionL));
          break;
        case 1:
          if(yL < 600-radiusL)
            yL+=Math.sin(Math.toRadians(directionL));
          break;
        case 2:
          if(xL > 0+radiusL)
            xL-=Math.cos(Math.toRadians(directionL));
          break;
        case 3:
          if(yL > 0+radiusL)
            yL-=Math.sin(Math.toRadians(directionL));
          break;
        case 4:
          directionL++;
          break;
        case 5:
          directionL--;
          break;
        case 6:
          shoot();
          break;
      }
      if(xL > 600-radiusL)
        xL = 600-radiusL;
      if(yL > 600-radiusL)
        yL = 600-radiusL;
      if(xL < 0+radiusL)
        xL = 0+radiusL;
      if(yL < 0+radiusL)
        yL = 0+radiusL;
      for(int i = 0; i < bullets.size(); i++){
        bullets.get(i).update();
        if(bullets.get(i).checkHit(other)){
          this.score++;
          other.score--;
          bullets.remove(i);
          i--;
        }
      }
      results.add(new Result(action,score));
    }
    
    public void choseAction(){
      if(initDecider)
        initActionChoser();
      else{}
    }
    
    public void initActionChoser(){
      initSampleAt++;
      if(initSampleAt >= initSampleSize){
        initDecider = false;
        analyzResults();
      }
      actionTime++;
      if(actionTime>actionDoTime){
        action = rand.nextInt(7);
        actionDoTime = rand.nextInt(20)+1;
        actionTime = 0;
      }
    }
    
    public void shoot(){
      bullets.add(new Bullet(xL,yL,directionL,10,5));
    }
    
    public class Bullet{
        float xB;
        float yB;
        float directionB;
        float radiusB;
        float speedB;
        
        public Bullet(float x, float y, float d, float r, float s){
            xB = x;
            yB = y;
            directionB = d;
            radiusB = r;
            speedB = s;
        }
        
        public void update(){
          xB += Math.cos(Math.toRadians(directionB))*speedB;
          yB += Math.sin(Math.toRadians(directionB))*speedB;
        }
        
        public boolean checkHit(Learner other){
          if((this.xB-other.xL)*(this.xB-other.xL) + (this.yB-other.yL)*(this.yB-other.yL) <= (this.radiusB+other.radiusL)*(this.radiusB+other.radiusL)){
            return true;
          }
          return false;
        }
    }
    
    public class Result{
        int move;
        int result;
        
        public Result(int m, int r){
            move = m;
            result = r;
        }
    }
    
    public void analyzResults(){
      for(int i = 1; i < results.size(); i++){
        if(results.get(i).result > results.get(i-1).result){
          goodMoves.add(new ActionSequence());
          int start = i-10;
          int end = i+10;
          if(start < 0)
            start = 0;
          if(end > results.size()-1)
            end = results.size()-1;
          for(int j = start; j < end; j++){
            goodMoves.get(goodMoves.size()-1).moves.add(results.get(j).move);
          }
        }
        if(results.get(i).result < results.get(i-1).result){}
      }
      System.out.println(goodMoves.get(0)+"  "+goodMoves.get(1));
    }
    
    public class ActionSequence{
      ArrayList<Integer> moves;
      
      public ActionSequence(){
        moves = new ArrayList<Integer>();
      }
      
      @Override
      public String toString(){
        String s = "";
        for(int i = 0; i < moves.size(); i++)
          s = s+""+moves.get(i)+",";
        return s;
      }
    }
}
