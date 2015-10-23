package lab1001;


import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Maze
{
    
    String[][] mazeText;
    int rows,colums;
    int cellSize;
    int[] sPos;
    int[] ePos;
    Lab1001 lab;
    
  public Maze(File f, Lab1001 p)
  {
      lab = p;
      sPos = new int[2];
      ePos = new int[2];
    if(f != null){
        String[] lines;
        Scanner in = null;
        try{in = new Scanner(f);}
        catch (FileNotFoundException ex){Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);}
        rows = in.nextInt();
        colums = in.nextInt();
        in.nextLine();
        lines = new String[rows];
        for(int k = 0; k < rows; k++)
            lines[k] = in.nextLine();
        mazeText = new String[rows][colums];
        for(int i = 0; i < rows; i++){
            String[] cols = lines[i].split("");
            for(int j = 0; j < colums; j++){
                mazeText[i][j] = cols[j];
                if(cols[j].equals("S")){
                    sPos[0] = i;
                    sPos[1] = j;
                }
                if(cols[j].equals("F")){
                    ePos[0] = i;
                    ePos[1] = j;
                }
            }
        }
    }
    else{
        rows = 1;
        colums = 3;
        mazeText = new String[1][3];
        mazeText[0][0] = "S";mazeText[0][1] = ".";mazeText[0][2] = "F";
        sPos[0] = 0;sPos[1] = 0;ePos[0] = 0;ePos[1] = 2;
    }
  }

  public void drawOnto(Graphics g)
  {
      cellSize = lab.getCellWidth();
      for(int i = 0; i < rows; i++){
          for(int j = 0; j < colums; j++){
              if(mazeText[i][j].equals("S")){
                  g.setColor(Color.CYAN);
                  g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
              }
              else if(mazeText[i][j].equals("#")){
                  g.setColor(Color.GRAY);
                  g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
              }
              else if(mazeText[i][j].equals("F")){
                  g.setColor(Color.GREEN);
                  g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
              }
              else if(mazeText[i][j].equals("X")){
                  g.setColor(Color.BLACK);
                  g.drawLine(j*cellSize, i*cellSize, (j+1)*cellSize, (i+1)*cellSize);
                  g.drawLine((j+1)*cellSize, i*cellSize, (j)*cellSize, (i+1)*cellSize);
              }
          }
      }
  }

  public int getStartRow()
  {
    return sPos[0];
  }

  public int numRows()
  {
    return rows;
  }
  
  public int numCols()
  {
    return colums;
  }
  
  public int getStartCol()
  {
    return sPos[1];
  }

  public int getFinishRow()
  {
    return ePos[0];
  }

  public int getFinishCol()
  {
    return ePos[1];
  }

  public boolean isInBounds(int r, int c)
  {
    if(r >= 0 && r < rows && c >= 0 && c < colums)
        return true;
    return false;
  }

  public boolean isOpen(int r, int c)
  {
      if(!mazeText[r][c].equals("#"))
          return true;
    return false;
  }
  
  public void markDeadEnd(int r, int c)
  {
    mazeText[r][c] = "X";
  }
  
  public boolean isDeadEnd(int r, int c){
      return mazeText[r][c].equals("X");
  }
  
  public void clearDeadEnds()
  {
    
  }

}