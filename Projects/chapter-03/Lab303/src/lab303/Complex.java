package lab303;

/*
 * File Name: Complex.java
 *   Created: Oct 27, 2014
 *    Author:
 */

public class Complex extends Object implements Comparable
{
  private final double x,y;
  
  public Complex(double xn, double yn){
    x = xn+0.0;
    y = yn+0.0;
  }
  
  public String toString(){
    if(y>=0)
      return x+" + "+y+"i";
    else
      return x+" - "+Math.abs(y)+"i";
  }
  
  public double magnitude(){
    return Math.sqrt(x*x+y*y);
  }
  
  public Complex scalarMult(double k){
    return new Complex(x*k, y*k);
  }
  
  public Complex conjugate(){
    return new Complex(x, -1*y);
  }
  
  public Complex add(Complex c){
    return new Complex(this.x+c.x, this.y+c.y);
  }
  
  public Complex subtract(Complex c){
    return new Complex(this.x-c.x, this.y-c.y);
  }
  
  public Complex multiply(Complex c){
    return new Complex(this.x*c.x+(-this.y*c.y), this.x*c.y+this.y*c.x);
  }
  
  private Complex multiply(Complex c, Complex t){
    return new Complex(t.x*c.x+(-t.y*c.y), t.x*c.y+t.y*c.x);
  }
  
  private Complex conjugate(Complex c){
    return new Complex(c.x, -1*c.y);
  } 
  
  public Complex divide(Complex c){
    System.out.println(multiply(conjugate(c),c).x);
    return new Complex((x*c.x+y*c.y)/multiply(conjugate(c),c).x, (y*c.x-x*c.y)/multiply(conjugate(c),c).x);
  }
  
  public Complex reciprocal(){
    return new Complex(x/(x*x+y*y), -y/(x*x+y*y));
  }
  
  public boolean equals(Object o){
    Complex c = (Complex) o;
    if(this.x == c.x && this.y == c.y)
      return true;
    return false;
  }
  
  public int compareTo(Object o){
    Complex c = (Complex) o;
    if(c.magnitude() > this.magnitude())
      return -1;
    else if(c.magnitude() < this.magnitude())
      return 1;
    else
      return 0;
  }
}
