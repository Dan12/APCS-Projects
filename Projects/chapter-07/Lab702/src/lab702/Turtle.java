package lab702;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/*
 * File Name: Turtle.java
 *   Created: Nov 03, 2013
 *    Author:
 */

public class Turtle extends Object
{
  //========================================================================================
  // private instance variables (add others as needed)
  //========================================================================================
  private final double centerX, centerY, turtleRadius;
  private double xPos, yPos, direction;
  private boolean penDown = true;
  private final Image background;
  private int turtleXPoints[] = new int[4], turtleYPoints[] = new int[4];
  private ArrayList<Double> vars = new ArrayList<Double>();
  private ArrayList<recursion> recur = new ArrayList<recursion>();
  private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
  private Color color = Color.WHITE;
  private final int methodRe = 1;

  //========================================================================================
  // public methods (modify code as needed)
  //========================================================================================
  public Turtle(Image bg)
  {
    this.background = bg;
    this.centerX = background.getWidth(null) / 2.0;
    this.centerY = background.getHeight(null) / 2.0;
    this.turtleRadius = 12;
    this.xPos = centerX;
    this.yPos = centerY;
    this.direction = 270;
    setPoints();
    
    //to be completed by you...

    this.resetTurtle();
    this.drawTurtle();
  }

  public void processCommands(ArrayList<String> list)
  {
    Graphics g = background.getGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0,  background.getWidth(null), background.getHeight(null));
    color = Color.WHITE;
    this.resetTurtle();
    vars.clear();
    for(int i = 0; i < 26; i++){
      vars.add(0.0);
    }
    recur.clear();
    if (list != null) this.process(list, 0, list.size()-1);
    this.drawTurtle();
    //no need to modify this method...
  }

  //========================================================================================
  // private helper methods
  //========================================================================================
  private void resetTurtle()
  {
    //to be completed by you...
    this.xPos = centerX;
    this.yPos = centerY;
    this.direction = 270;
    setPoints();
  }

  private void drawTurtle()
  {
    Graphics g = background.getGraphics();
    setPoints();
    g.setColor(Color.MAGENTA);
    g.drawPolygon(turtleXPoints, turtleYPoints, turtleXPoints.length);
    //to be completed by you...
  }
  
  private void setPoints(){
    this.turtleXPoints[0] = (int) (xPos+turtleRadius*Math.cos(Math.toRadians(direction)));
    this.turtleYPoints[0] = (int) (yPos+turtleRadius*Math.sin(Math.toRadians(direction)));
    this.turtleXPoints[1] = (int) (xPos+turtleRadius*Math.cos(Math.toRadians(direction+120)));
    this.turtleYPoints[1] = (int) (yPos+turtleRadius*Math.sin(Math.toRadians(direction+120)));
    this.turtleXPoints[2] = (int) xPos;
    this.turtleYPoints[2] = (int) yPos;
    this.turtleXPoints[3] = (int) (xPos+turtleRadius*Math.cos(Math.toRadians(direction-120)));
    this.turtleYPoints[3] = (int) (yPos+turtleRadius*Math.sin(Math.toRadians(direction-120)));
  }

  private double getValue(String s)
  {
    //to be completed by you...
    if(s.startsWith("_")){
      int index = alphabet.indexOf(s.charAt(1));
      return vars.get(index);
    }
    else if(s.startsWith("#"))
      return Integer.parseInt(s.substring(1), 16);
    else{
      return Double.parseDouble(s);
    }
    //return 0.0;
  }

  private void process(ArrayList<String> list, int startLine, int endLine)
  {
    for(int i = startLine; i <= endLine; i++){
      String line = list.get(i).toLowerCase();
      ArrayList<String> tokens = new ArrayList<String>(java.util.Arrays.asList(line.split("\\s")));
      
      for(int ii = 0; ii < tokens.size(); ii++){
        System.out.print(tokens.get(ii)+",");
      }
      System.out.println("");
      
      if(tokens.get(0).equals("//")){}
      else if(tokens.get(0).equals("hm")) resetTurtle();
      else if(tokens.get(0).equals("fd")) move(getValue(tokens.get(1)));
      else if(tokens.get(0).equals("bk")) move(-1*getValue(tokens.get(1)));
      else if(tokens.get(0).equals("rt")) turn(getValue(tokens.get(1)));
      else if(tokens.get(0).equals("lt")) turn(-1*getValue(tokens.get(1)));
      else if(tokens.get(0).equals("pu")) penDown = false;
      else if(tokens.get(0).equals("pd")) penDown = true;
      else if(tokens.get(0).equals("pc")) color = new Color((int) getValue(tokens.get(1)));
      else if(tokens.get(0).equals("re")) {
        if(methodRe == 1){
          int endL = endLine;
          int startL = i+1;
          System.out.println("Print Recusion sl: "+startL);
          while(true){
            System.out.println(list.get(endL));
            if(list.get(endL).equals("er")){
              endL--;
              break;
            }
            endL--;
          }
          ArrayList<String> newList = new ArrayList<String>();
          int lineNum = startL;
          while(lineNum <= endL){
            newList.add(list.get(lineNum));
            System.out.println(list.get(lineNum));
            lineNum++;
          }
          System.out.println("End Print");
          int recurTime = (int) getValue(tokens.get(1));
          for(int iii = 1; iii < recurTime; iii++){
            process(newList, 0, newList.size()-1);
          }
        }
        if(methodRe == 2)
          recur.add(new recursion(i, (int) getValue(tokens.get(1))));
      }
      else if(tokens.get(0).equals("er")) {
        if(methodRe == 2){
          recur.get(recur.size()-1).repeatTime --;
          if(recur.get(recur.size()-1).repeatTime <= 0)
            recur.remove(recur.size()-1);
          else
            i = recur.get(recur.size()-1).lineStart;
        }
      }
      else if(tokens.get(0).startsWith("_")){
        int index = alphabet.indexOf(tokens.get(0).charAt(1));
        double prevVal = vars.get(index);
        if(tokens.get(1).equals(":=")) vars.set(index, getValue(tokens.get(2)));
        if(tokens.get(1).equals("+=")) vars.set(index, prevVal+getValue(tokens.get(2)));
        if(tokens.get(1).equals("-=")) vars.set(index, prevVal-getValue(tokens.get(2)));
        if(tokens.get(1).equals("*=")) vars.set(index, prevVal*getValue(tokens.get(2)));
        if(tokens.get(1).equals("/=")) vars.set(index, prevVal/getValue(tokens.get(2)));
        System.out.println(vars.get(index));
      }
    }
    //drawTurtle();
    //to be completed by you...
  }
  
  public class recursion{
    int lineStart;
    int repeatTime;
    
    public recursion(int ls, int re){
      lineStart = ls;
      repeatTime = re;
    }
  }

  private void turn(double deg)
  {
    //to be completed by you...
    direction += deg;
  }

  private void move(double dist)
  {
    Graphics g = background.getGraphics();
    double prevX = this.xPos;
    double prevY = this.yPos;
    this.xPos += dist*Math.cos(Math.toRadians(direction));
    this.yPos += dist*Math.sin(Math.toRadians(direction));
    if(penDown){
      g.setColor(color);
      g.drawLine((int)prevX, (int)prevY, (int)this.xPos, (int)this.yPos);
    }
    //to be completed by you...
  }
}
