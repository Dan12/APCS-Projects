package lab302;

/*
 * File Name: Block.java
 *   Created: Sep 30, 2013
 *    Author: 
 */

public class Block extends Object
{
  private double mass;
  private double xVal;
  private double vel;
  private int screenWidth;
  private final int bWidth = 50;
  
  public Block(double m, double x, double v, int sw){
    mass = m;
    xVal = x;
    vel = v;
    screenWidth = sw;
  }
  
  public double getMass(){
    return mass;
  }
  
  public double getVelocity(){
    return vel;
  }
  
  public double getX(){
    return xVal;
  }
  
  public int getBlockWidth(){
    return bWidth;
  }
  
  public double getEnergy(){
    return 0.5*mass*vel*vel;
  }
  
  public String toString(){
    return String.format("x = %4.1f", xVal)+"   "+String.format("v = %4.1f", vel)+"   "+String.format("E = %8.1f", getEnergy())+" ";
  }
  
  public void move(double tI){
    xVal += vel*tI;
    if(xVal<0 || xVal>screenWidth-bWidth)
      vel = -vel;
  }
  
  public void checkCollision(Block b2){
    if(this.xVal+this.bWidth >= b2.xVal && this.xVal<b2.xVal+b2.bWidth){
      double tempVel = this.vel;
      this.vel = (this.vel*(this.mass-b2.mass)+2*b2.mass*b2.vel)/(this.mass+b2.mass);
      b2.vel = (b2.vel*(b2.mass-this.mass)+2*this.mass*tempVel)/(this.mass+b2.mass);
    }
  }
  
}
