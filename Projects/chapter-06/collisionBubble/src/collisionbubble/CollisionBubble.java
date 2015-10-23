package collisionbubble;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * File Name: CollisionBubble.java
 *   Created: Oct 27, 2014
 *    Author: 
 */


public class CollisionBubble extends JPanel implements ActionListener
{
  // Declare instance variables here...
  
  private static int renderSpeed = 10;
  private bubble bubbles[] = {
    new bubble(50,10,2,2,0,0,40,1,1,Color.PINK),
    new bubble(0,0,3,2,0,0,50,1,1,Color.CYAN),
    new bubble(0,100,4,1,0,0,30,1,1,Color.BLUE),
    new bubble(300,0,6,0,0,0,70,1,1,Color.GREEN),
    new bubble(200,200,3,5,0,0,30,1,1,Color.YELLOW),
  };
  
  // Constructor
  public CollisionBubble(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(255, 255, 255));
    for(int i = 0; i < bubbles.length; i++){
      bubbles[i].bName = i+"";
    }
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    for(int i = 0; i < bubbles.length; i++){
        bubbles[i].update();
    }
    for(int i = 0; i < bubbles.length; i++){
      bubbles[i].checkCollideAll(g);
      g.setColor(bubbles[i].color);
      g.fillOval((int)(bubbles[i].xPos), (int)(bubbles[i].yPos), bubbles[i].radius, bubbles[i].radius);
//      g.setColor(Color.BLACK);
//      g.drawLine((int)(bubbles[i].xPos+bubbles[i].radius/2), (int)(bubbles[i].yPos+bubbles[i].radius/2), (int)(bubbles[i].xPos+bubbles[i].radius/2+bubbles[i].xVel*4), (int)(bubbles[i].yPos+bubbles[i].radius/2+bubbles[i].yVel*4));
    }
  }
  
  public class bubble{
    double xPos;
    double yPos;
    double xVel;
    double yVel;
    int radius;
    Color color;
    double yAcel;
    double xAcel;
    double elasticity;
    double friction;
    String bName = "";
    String prevHit = "";
    int frameHit = 0;
    
    public bubble(int x, int y, double xv, double yv, double xa, double ya, int r, double e, double f, Color c){
      xPos = x;
      yPos = y;
      xVel = xv;
      yVel = yv;
      radius = r;
      color = c;
      xAcel = xa;
      yAcel = ya;
      elasticity = e;
      friction = f;
    }
    
    public double getCenterX(){
      return xPos+radius/2;
    }
    
    public double getCenterY(){
      return yPos+radius/2;
    }
    
    public void checkCollision(bubble b2, Graphics g){
      if(this.xPos+this.radius >= b2.xPos && this.xPos <= b2.xPos+b2.radius && this.yPos+this.radius >= b2.yPos && this.yPos <= b2.yPos+b2.radius && !prevHit.equals(b2.toString())){
        if(distance(this.xPos+this.radius/2, b2.xPos+b2.radius/2, this.yPos+this.radius/2, b2.yPos+b2.radius/2) <= this.radius/2+b2.radius/2){
          double collPX = (((this.xPos+this.radius/2)*b2.radius)+((b2.xPos+b2.radius/2)*this.radius))/(this.radius+b2.radius);
          double collPY = (((this.yPos+this.radius/2)*b2.radius)+((b2.yPos+b2.radius/2)*this.radius))/(this.radius+b2.radius);
          g.setColor(Color.BLACK);
          g.fillOval((int)(collPX-4), (int)(collPY-4),8,8);
          double cX1 = this.xPos+this.radius/2;
          double cY1 = this.yPos+this.radius/2;
          double cX2 = b2.xPos+this.radius/2;
          double cY2 = b2.yPos+this.radius/2;
          double vX1 = this.xVel;
          double vY1 = this.yVel;
          double vX2 = b2.xVel;
          double vY2 = b2.yVel;
          double mOrig = (cY1-cY2)/(cX1-cX2);
          double bOrig = cY1- mOrig*cX1;
          double mPerp = (-1)/(mOrig);
          double bPerp2 = (cY2+vY2)-(mPerp*(cX2+vX2));
          double xI2 = (bPerp2-bOrig)/(mOrig-mPerp);
          double yI2 = mPerp*xI2+bPerp2;
          double bPerp1 = (cY1+vY1)-(mPerp*(cX1+vX1));
          double xI1 = (bPerp1-bOrig)/(mOrig-mPerp);
          double yI1 = mPerp*xI1+bPerp1;
          xI1 = (xI1 - cX1);
          yI1 = yI1 - cY1;
          xI2 = xI2 - cX2;
          yI2 = yI2 - cY2;
          this.xVel = vX1+xI2-xI1;
          this.yVel = vY1+yI2-yI1;
          b2.xVel = vX2+xI1-xI2;
          b2.yVel = vY2+yI1-yI2;
          xPos += xVel;
          yPos += yVel;
          b2.xPos += b2.xVel;
          b2.yPos += b2.yVel;
          b2.prevHit = this.toString();
          frameHit = 0;
          b2.frameHit = 0;
        }
      }
      else{
        if(frameHit > 1)
          prevHit = "";
      }
    }
    
    public String toString(){
      return bName;
    }
    
    public void checkCollideAll(Graphics g){
        for(int i = 0; i < bubbles.length; i++){
          if(bubbles[i]!=this)
              checkCollision(bubbles[i], g);
      }
    }
    
    public double distance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
    }
    
    public void update(){
      frameHit++;
      if(xPos <= 0){
        if(Math.abs(xVel)>xAcel)
            xVel = -xVel*elasticity;
        else
            xVel = 0;
        xPos = 0;
      }
      if(xPos+radius >= 400){
        if(Math.abs(xVel)>xAcel)
            xVel = -xVel*elasticity;
        else
            xVel = 0;
        xPos = 400-radius;
      }
      if(yPos <= 0){
        if(Math.abs(yVel)>yAcel)
            yVel = -yVel*elasticity;
        else
            yVel = 0;
        yPos = 0;
      }
      if(yPos+radius >= 400){
        if(Math.abs(yVel)>yAcel){
            yVel = -yVel*elasticity;
        }    
        else
            yVel = 0;
        yPos = 400-radius;
        if(Math.abs(xVel)>xAcel)
            xVel = xVel*friction;
        else
            xVel = 0;
      }
      xVel += xAcel;
      yVel += yAcel;
      xPos += xVel;
      yPos += yVel;
    }
  }
  
  // Process GUI input in this method
  @Override  
  public void actionPerformed(ActionEvent e)
  {
    super.repaint();
  }
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: CollisionBubble");
    fr.setContentPane(new CollisionBubble(400, 400, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true); 
    do {
			try {
				Thread.sleep(renderSpeed);
			} catch (InterruptedException interruptedexception) {
				System.out.println(interruptedexception.getMessage());
			}
			fr.repaint();
		} while (true);
  }
  //</editor-fold>  

}
