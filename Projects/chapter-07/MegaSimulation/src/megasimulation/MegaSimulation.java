package megasimulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * File Name: MegaSimulation.java
 *   Created: Jan 9, 2015
 *    Author: 
 */


public class MegaSimulation extends JPanel implements ActionListener
{
  // Declare instance variables here...
  static JFrame fr = new JFrame("Application: MegaSimulation");
  //x,y,ammo,dirmin,dirmax,colmin,colmax,solnum
  Army army1 = new Army(0,0,50,20,70,0,20,50);
  Army army2 = new Army(600,600,50,200,250,180,200,50);
//  Army army3 = new Army(600,0,50,110,160,80,100,100);
//  Army army4 = new Army(0,600,50,290,340,120,140,100);
  static boolean updating = true;
  Random rand = new Random();
  int maxNum = 500;
  
  public class Soldier{
    double ammo;
    double accuracy;
    double xPlace;
    double yPlace;
    double direction;
    double speed;
    Color co;
    int reloadTime = 0;
    boolean dead = false;
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    
    public Soldier(double x, double y, double a, double ac, double d, double s, Color c){
      xPlace = x;
      yPlace = y;
      ammo = a;
      accuracy = ac;
      direction = d;
      speed = s;
      co = c;
    }
    
    public void update(){
      if(!dead){
        xPlace += Math.cos(Math.toRadians(direction))*speed;
        yPlace += Math.sin(Math.toRadians(direction))*speed;
        direction += rand.nextDouble() * (rand.nextInt(3) - 1);
        reloadTime++;
        if(reloadTime > 20 && ammo > 0){
          double bulDir = direction + (rand.nextDouble()*(1-accuracy)*20 - (1-accuracy)*10);
          bullets.add(new Bullet(xPlace, yPlace, bulDir));
          reloadTime = 0;
          ammo--;
        }
      }
    }
  }
  
  public class Bullet{
    double x;
    double y;
    double direction;
    double speed = 7;
    
    public Bullet(double xp, double yp, double d){
      x = xp;
      y = yp;
      direction= d;
    }
    
    public void update(){
      x += Math.cos(Math.toRadians(direction))*speed;
      y += Math.sin(Math.toRadians(direction))*speed;
    }
  }
  
  public class Army{
    ArrayList<Soldier> army;
    int numSoldiers;
    int soldiersSpawned = 0;
    int startX;
    int startY;
    int soldierAmmo;
    int directionMin;
    int directionMax;
    int colorMin;
    int colorMax;
    
    public Army(int x, int y, int a, int dmin, int dmax, int cmin, int cmax, int ns){
      army = new ArrayList<Soldier>();
      startX = x;
      startY = y;
      soldierAmmo = a;
      directionMin = dmin;
      directionMax = dmax;
      colorMin = cmin;
      colorMax = cmax;
      numSoldiers = ns;
    }
    
    public void update(){
      if(soldiersSpawned < numSoldiers)
        army.add(new Soldier(startX, startY, soldierAmmo, rand.nextDouble(), rand.nextInt(directionMax - directionMin + 1) + directionMin, rand.nextDouble()*2+0.75, new Color(rand.nextInt(colorMax - colorMin + 1) + colorMin,rand.nextInt(colorMax - colorMin + 1) + colorMin,rand.nextInt(colorMax - colorMin + 1) + colorMin)));
      soldiersSpawned ++;
    }
  }
  
  // Constructor
  public MegaSimulation(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(255, 255, 255));
    
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    army1.update();
    DrawArmy(g, army1);
    army2.update();
    DrawArmy(g, army2);
    checkBulletHits();
//    army3.update();
//    DrawArmy(g, army3);
//    army4.update();
//    DrawArmy(g, army4);
    
  }
  
  public void checkBulletHits(){
    mainloop1:
    for(int  i = 0; i < army1.army.size(); i++){
      for(int ii = 0; ii < army2.army.size(); ii++){
        for(int iii = 0; iii < army2.army.get(ii).bullets.size(); iii++){
          if(army2.army.get(ii).bullets.get(iii).x <= army1.army.get(i).xPlace + 10 && army2.army.get(ii).bullets.get(iii).x + 4 >= army1.army.get(i).xPlace && army2.army.get(ii).bullets.get(iii).y <= army1.army.get(i).yPlace + 10 && army2.army.get(ii).bullets.get(iii).y+4 >= army1.army.get(i).yPlace && !army1.army.get(i).dead){
            army2.army.get(ii).bullets.remove(iii);
            iii--;
            army1.army.get(i).dead = true;
            i--;
            continue mainloop1;
          }
        }
      }
    }
    
    mainloop2:
    for(int  i = 0; i < army2.army.size(); i++){
      for(int ii = 0; ii < army1.army.size(); ii++){
        for(int iii = 0; iii < army1.army.get(ii).bullets.size(); iii++){
          if(army1.army.get(ii).bullets.get(iii).x <= army2.army.get(i).xPlace + 10 && army1.army.get(ii).bullets.get(iii).x + 4 >= army2.army.get(i).xPlace && army1.army.get(ii).bullets.get(iii).y <= army2.army.get(i).yPlace + 10 && army1.army.get(ii).bullets.get(iii).y+4 >= army2.army.get(i).yPlace && !army2.army.get(i).dead){
            army1.army.get(ii).bullets.remove(iii);
            iii--;
            army2.army.get(i).dead = true;
            i--;
            continue mainloop2;
          }
        }
      }
    }
  }
  
  public void DrawArmy(Graphics g, Army drawArmy){
    for(int i = 0; i < drawArmy.army.size(); i++){
      drawArmy.army.get(i).update();
      if(drawArmy.army.get(i).dead)
        g.setColor(Color.GRAY);
      else
        g.setColor(drawArmy.army.get(i).co);
      g.fillRect((int) drawArmy.army.get(i).xPlace, (int) drawArmy.army.get(i).yPlace, 10, 10);
      if(drawArmy.army.get(i).xPlace > 600 || drawArmy.army.get(i).xPlace < 0 || drawArmy.army.get(i).yPlace > 600 || drawArmy.army.get(i).yPlace < 0){
        drawArmy.army.remove(i);
        i--;
        continue;
      }
      for(int ii = 0; ii < drawArmy.army.get(i).bullets.size(); ii++){
        drawArmy.army.get(i).bullets.get(ii).update();
        g.setColor(Color.BLACK);
        g.fillRect((int) drawArmy.army.get(i).bullets.get(ii).x, (int) drawArmy.army.get(i).bullets.get(ii).y, 4, 4);
        if(drawArmy.army.get(i).bullets.get(ii).x > 600 || drawArmy.army.get(i).bullets.get(ii).x < 0 || drawArmy.army.get(i).bullets.get(ii).y > 600 || drawArmy.army.get(i).bullets.get(ii).y < 0){
          drawArmy.army.get(i).bullets.remove(ii);
          ii--;
        }  
      }
    }
  }
  
  public static void Updater(){
    while(updating){
     try {
				Thread.sleep(20);
			} catch (InterruptedException interruptedexception) {
				System.out.println(interruptedexception.getMessage());
			}
			fr.repaint();
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
    fr.setContentPane(new MegaSimulation(600, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
    Updater();
  }
  //</editor-fold>  

}
