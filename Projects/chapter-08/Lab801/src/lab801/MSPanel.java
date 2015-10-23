package lab801;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * File Name: Jan 24, 2013
 *    Author: 
 */

public class MSPanel extends JPanel implements ActionListener, MouseListener
{
  public static final int BUTTON_SIZE = 32;

  private int numRow;
  private int numCol;
  private int numMine;
  private MSButton[][] buttons;
  private MSLabel[][] labels;
  
  private JFrame fr;
  
  private MSTimer msTimer;
  
  private boolean firstUncovered = false;
  
  private MSCounter msCount;
  
  private boolean gameOver = false;
  private boolean gameLose = false;
  
  private MSConclude finishMessage;
  
  public MSPanel(int nr, int nc, int nm, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(nc*BUTTON_SIZE, nr*BUTTON_SIZE+60));
    super.setBackground(new Color(224, 224, 224));   
    super.setLayout(null);
    
    numRow = nr;
    numCol = nc;
    numMine = nm;
    
    buttons = new MSButton[nr][nc];
    labels = new MSLabel[nr][nc];
    
    for(int i = 0; i < nr; i++){
        for(int k = 0; k < nc; k++){
            MSButton temp = new MSButton(i, k);
            super.add(temp);
            temp.addActionListener(this);
            temp.addMouseListener(this);
            buttons[i][k] = temp;
        }
    }
    
    msTimer = new MSTimer(0, numRow*BUTTON_SIZE+15, 150, 40);
    
    super.add(msTimer);
    
    msCount = new MSCounter(numCol*BUTTON_SIZE-90, numRow*BUTTON_SIZE+15, 90, 40, numMine);
    
    super.add(msCount);
  }
      
  @Override
  public int getWidth()
  {
    return numCol*BUTTON_SIZE;  
  }
  
  @Override
  public int getHeight()
  {
    return numRow*BUTTON_SIZE+60;
  }
  
  private ArrayList<MSButton> getNeighbors(MSButton b)
  {
      int r = b.getRow();
      int c = b.getColumn();
      ArrayList<MSButton> temp = new ArrayList<MSButton>();
      
      for(int i = -1; i <= 1; i++){
          for(int k = -1; k <= 1; k++){
              if(r+i >= 0 && r+i < numRow && c+k >= 0 && c+k < numCol && !(i == 0 && k == 0))
                  temp.add(buttons[r+i][c+k]);
          }
      }
      
    return temp;
  }

  public void reset()
  {
    for(int i = 0; i < numRow; i++){
        for(int k = 0; k < numCol; k++){
            buttons[i][k].setVisible(true);
            buttons[i][k].setIcon(null);
            if(labels[i][k].getValue() == 9)
                labels[i][k].setIcon(MSResources.BOMB);
            else{
                labels[i][k].setIcon(null);
                labels[i][k].setText(""+labels[i][k].getValue());
            }

        }
    }
    //firstUncovered = false;
    msCount.reset();
    gameOver = false;
  }
     
  private void plantMines(MSButton b)
  {
      finishMessage = new MSConclude(fr, true);
    Random rng = new Random();  //remove the 1 when testing is complete and the app is finished...
    int numPlanted = 0;
    while(numPlanted < numMine){
        int rPlant = rng.nextInt(numRow);
        int cPlant = rng.nextInt(numCol);
        if(!(rPlant == b.getRow() && cPlant == b.getColumn()) && labels[rPlant][cPlant] == null){
            MSLabel temp = new MSLabel(rPlant, cPlant, 9);
            labels[rPlant][cPlant] = temp;
            super.add(temp);
            numPlanted++;
        }
    }
    
    calcNumbers();
  }
    
  private void calcNumbers()
  {
    for(int i = 0; i < buttons.length; i++){
        for(int k = 0; k < buttons[0].length; k++){
            if(labels[i][k] == null){
                ArrayList<MSButton> tempArr = getNeighbors(buttons[i][k]);
                int numAround = 0;
                for(int j = 0; j < tempArr.size(); j++){
                    int tR = tempArr.get(j).getRow();
                    int tC = tempArr.get(j).getColumn();
                    if(labels[tR][tC] != null){
                        if(labels[tR][tC].isBomb())
                            numAround++;
                    }
                }
                MSLabel tempLab = new MSLabel(i, k, numAround);
                super.add(tempLab);
                tempLab.addMouseListener(this);
                labels[i][k] = tempLab;
            }
        }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() instanceof MSButton && !gameOver){
        MSButton temp = (MSButton) e.getSource();
        if(temp.getIcon() == null){
            temp.setVisible(false);
            //System.out.println(getNeighbors(temp));
            if(!firstUncovered){
                plantMines(temp);
//                for(int i = 0; i < buttons.length; i++){
//                    for(int k = 0; k < buttons[0].length; k++){
//                        buttons[i][k].setVisible(false);
//                    }
//                }
                firstUncovered = true;
                msTimer.start();
            }
            clearTheButtons(temp);
        }
    }
  }
  
  public void clearTheButtons(MSButton b){
    if(labels[b.getRow()][b.getColumn()].getValue() != 0){
        b.clearButton();
        gameOver = checkGameFinished();
        if(labels[b.getRow()][b.getColumn()].getValue() == 9){
            gameOver = true;
            gameLose = true;
        }
        if(gameOver)
            gameIsOver(b.getRow(),b.getColumn());
        return;
    }
    else{
        b.clearButton();
        gameOver = checkGameFinished();
        ArrayList<MSButton> temp = getNeighbors(b);
        for(int i = 0; i < temp.size(); i++){
            if(temp.get(i).isVisible() && !temp.get(i).flagDisplayed())
                clearTheButtons(temp.get(i));
        }
    }
  }
  
  public void gameIsOver(int r, int c){
      msTimer.stop();
      if(!gameLose){
          finishMessage.setLocationRelativeTo(this);
          finishMessage.showDialog(true);
      }
      else{
        for(int i = 0; i < numRow; i++){
            for(int k = 0; k < numCol; k++){
                if(buttons[i][k].isVisible() && labels[i][k].getValue() == 9){
                    buttons[i][k].clearButton();
                    if(buttons[i][k].flagDisplayed())
                        labels[i][k].setIcon(MSResources.BOMB_FLAG);
                    if(buttons[i][k].questionDisplayed())
                        labels[i][k].setIcon(MSResources.BOMB_QUESTION);
                }
                else if(labels[i][k].getValue() != 9 && buttons[i][k].flagDisplayed()){
                    buttons[i][k].clearButton();
                    labels[i][k].setIcon(MSResources.BOMB_X);
                }
                if(i == r && k == c)
                    labels[i][k].setIcon(MSResources.BOMB_RED);
            }
        }
        finishMessage.setLocationRelativeTo(this);
        finishMessage.showDialog(false);
      }
  }
  
  public boolean checkGameFinished(){
      int numButtons = 0;
      int numFlagged = numMine;
      for(int i = 0; i < numRow; i++){
          for(int k = 0; k < numCol; k++){
              if(!buttons[i][k].isVisible() || buttons[i][k].flagDisplayed() || buttons[i][k].questionDisplayed())
                  numButtons++;
              if(buttons[i][k].flagDisplayed())
                  numFlagged--;
          }
      }
      if(numButtons >= numRow*numCol && numFlagged == 0)
        return true;
      else
        return false;
  }
  
  @Override
  public void mousePressed(MouseEvent e)
  {
    if(e.getModifiers() == InputEvent.BUTTON3_MASK){ //right mouse button
        if(e.getSource() instanceof MSButton && !gameOver){
            MSButton temp = (MSButton) e.getSource();
            temp.cycleIcon();
            if(temp.flagDisplayed())
                msCount.decrease();
            if(temp.questionDisplayed())
                msCount.increase();
        }
    }
  }
  
  @Override 
  public void mouseClicked(MouseEvent e)
  {
      if(e.getSource() instanceof MSLabel && !gameOver){
          MSLabel temp = (MSLabel) e.getSource();
          if(e.getClickCount()  == 2 && e.getModifiers() == InputEvent.BUTTON1_MASK){
              ArrayList<MSButton> tempNeighbors = getNeighbors(buttons[temp.getRow()][temp.getColumn()]);
              int flagNeigh = 0;
              for(int i = 0; i < tempNeighbors.size(); i++){
                  if(tempNeighbors.get(i).flagDisplayed())
                      flagNeigh ++;
              }
              if(flagNeigh >= temp.getValue()){
                for(int i = 0; i < tempNeighbors.size(); i++){
                  if(!tempNeighbors.get(i).flagDisplayed())
                      clearTheButtons(tempNeighbors.get(i));
                }  
              }
          }
      }
  }

  @Override public void mouseReleased(MouseEvent e){/*non-implemented method*/}
  @Override public void mouseEntered(MouseEvent e){/*non-implemented method*/}
  @Override public void mouseExited(MouseEvent e){/*non-implemented method*/}
}
