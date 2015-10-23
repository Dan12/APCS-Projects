package lab703;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font; 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * File Name: Lab703.java
 *   Created: Nov 5, 2013
 *    Author:
 */


public class Lab703 extends JPanel implements MouseListener
{
  // Declare instance variables here...
  private Image bg, back;
  ArrayList<Card> deck = new ArrayList<Card>();
  ArrayList<ArrayList<Card>> master = new ArrayList<ArrayList<Card>>();
  Random rand = new Random();
  int discardVal = -1;
  boolean gameWin = false;
  boolean gameLose = false;
 
  // Constructor
  public Lab703(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    back = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("images/back.png"));
    bg = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource("images/bg.jpg"));
    super.addMouseListener(this);
    this.getCards();
    this.newGame();
  }
  
  private void getCards()
  {
    //To be completed by you...
    String s = "";
    for(int ii = 0; ii < 4; ii++){
      switch(ii){
        case 0:
          s = "c";
          break;
        case 1:
          s = "d";
          break;
        case 2:
          s = "s";
          break;
        case 3:
          s = "h";
          break;
      }
      for(int i = 0; i < 13; i ++){
        deck.add(new Card("images/"+s+""+(i+1)+".png",s,(i+1),0,0));
        System.out.println("images/"+s+""+(i+1)+".png");
      }
    }
    deck.add(new Card("images/joker.png","joker",-1,0,0));
    deck.add(new Card("images/joker.png","joker",-1,0,0));
  }    
  
  private void newGame()
  {
    int i = 53;
    for(int c = 0; c < 9; c++){
      master.add(new ArrayList<Card>());
    }
    for(int c = 0; c < 9; c++){
      if(c<7){
        for(int k = 0; k < 5; k++){
          //int i = rand.nextInt(deck.size());
          deck.get(i).setCooridinates(25+128*(c), 20*(k+1));
          master.get(c).add(deck.get(i));
          deck.remove(i);
          i--;
        }
      }
      else if(c == 7){
        for(int k = 0; k < 18; k++){
          //int i = rand.nextInt(deck.size());
          deck.get(i).setCooridinates(20*(k+1), 350);
          master.get(c).add(deck.get(i));
          deck.remove(i);
          i--;
        }
      }
      else{
        deck.get(0).setCooridinates(700, 450);
        discardVal = deck.get(0).val;
        master.get(c).add(deck.get(0));
        deck.clear();
        i--;
      }
    }
  }
 
  // Perform any custom painting (if necessary) in this method
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.drawImage(bg, 0, 0, this);
    //To be completed by you...
    g.setFont(new Font("Arial", Font.BOLD, 40));
    g.setColor(Color.WHITE);
    if(!gameWin && !gameLose)
      g.drawString("Free Cards", 25, 340);
    else{
      if(gameWin)
        g.drawString("You Win", 25, 340);
      else
        g.drawString("You Lose", 25, 340);
    }
    g.drawString("Discard", 700, 440);
    boolean animate = false;
    for(int i = 0; i < master.size(); i++){
      for(int ii = 0; ii < master.get(i).size(); ii++){
        if(master.get(i).get(ii).startAnim){
            animate = true;
            master.get(i).get(ii).animate();
        }
        if(i == 7 && ii < master.get(7).size()-1)
          g.drawImage(back, (int)master.get(i).get(ii).xPos, (int)master.get(i).get(ii).yPos, this);
        else
          g.drawImage(master.get(i).get(ii).cImage, (int)master.get(i).get(ii).xPos, (int)master.get(i).get(ii).yPos, this);
      }
    }
    if(animate){
        try {
                Thread.sleep(10);
        } catch (InterruptedException interruptedexception) {
                System.out.println(interruptedexception.getMessage());
        }
        super.repaint();
    }
  }

  private boolean isGameWon()
  {
    if(master.get(8).size() == 54)
      return true;
    return false;
  }
 
  private boolean isGameLost()
  {
    for(int i = 0; i < master.size(); i++){
      for(int ii = 0; ii < master.get(i).size(); ii++){
        int val = master.get(i).get(ii).val;
        if(master.get(7).size() > 0)
          return false;
        if(ii == master.get(i).size()-1 && i < 7){
          if(val == -1 || discardVal == -1 || val+1 == discardVal || val-1 == discardVal || (val == 1 && discardVal == 13) || (val == 13 && discardVal == 1)){
            return false;
          }
        }
      }
    } 
    return true;
  }
  
  // Process mouse input in this method
  @Override
  public void mousePressed(MouseEvent e)
  {
    int mx = e.getX(), my = e.getY();
    if (e.getButton() == MouseEvent.BUTTON1 && (!gameLose && !gameWin)) //left mouse button clicked...
    {
      if(my < 350){
        int c = (mx-25)/128;
        int val = master.get(c).get(master.get(c).size()-1).val;
        if(val == -1 || discardVal == -1 || val+1 == discardVal || val-1 == discardVal || (val == 1 && discardVal == 13) || (val == 13 && discardVal == 1)){
          discardVal = val;
          master.get(c).get(master.get(c).size()-1).startAnimate(700, 450);
          master.get(8).add(master.get(c).get(master.get(c).size()-1));
          master.get(c).remove(master.get(c).size()-1);
        }
      }
      else if(mx >= 20*(master.get(7).size()) && mx <= 20*(master.get(7).size())+79){
        int val = master.get(7).get(master.get(7).size()-1).val;
        discardVal = val;
        master.get(7).get(master.get(7).size()-1).startAnimate(700, 450);
        master.get(8).add(master.get(7).get(master.get(7).size()-1));
        master.get(7).remove(master.get(7).size()-1);
      }
    }
    gameWin = isGameWon();
    gameLose = isGameLost();
    super.repaint();
  }
  
  //<editor-fold defaultstate="collapsed" desc="--You do not need to implement these MouseListener methods--">
  @Override
  public void mouseClicked(MouseEvent e)
  {
    //Not implemented...
  }
  @Override
  public void mouseEntered(MouseEvent e)
  {
    //Not implemented...
  }
  @Override
  public void mouseExited(MouseEvent e)
  {
    //Not implemented...
  }
  @Override
  public void mouseReleased(MouseEvent e)
  {
    //Not implemented...
  }
  //</editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab703");
    fr.setContentPane(new Lab703(900, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);
  }
  //</editor-fold>
}