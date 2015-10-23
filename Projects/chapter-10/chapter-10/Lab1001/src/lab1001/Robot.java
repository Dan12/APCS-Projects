package lab1001;

import java.awt.Image;
import java.util.ArrayList;

/*
 * File Name: Robot.java
 *   Created: Mar 16, 2015
 *    Author: 
 */

public abstract class Robot
{
    private Direction dir;
    private Image img;
    private Maze maze;
    private int curRow, curCol;
    
    public Robot(Maze m){
        maze = m;
        dir = Direction.SOUTH;
        img = dir.getImage();
        dir.getClockwiseDirection();
        curRow = maze.getStartRow();
        curCol = maze.getStartCol();
    }
    
    public int getRow(){
        return curRow;
    }
    
    public int getCol(){
        return curCol;
    }
    
    public Image getImage(){
        return img;
    }
    
    public Direction getDirection(){
        return dir;
    }
    
    public Maze getMaze(){
        return maze;
    }
    
    public boolean isFinished(){
        if(curCol == maze.getFinishCol() && curRow == maze.getFinishRow())
            return true;
        return false;
    }
    
    public ArrayList<Direction> getOpenNeighbors(){
        ArrayList<Direction> temp = new ArrayList<Direction>();
        if(maze.isInBounds(curRow-1, curCol) && maze.isOpen(curRow-1, curCol)) temp.add(Direction.NORTH);
        if(maze.isInBounds(curRow, curCol+1) && maze.isOpen(curRow, curCol+1)) temp.add(Direction.EAST);
        if(maze.isInBounds(curRow+1, curCol) && maze.isOpen(curRow+1, curCol)) temp.add(Direction.SOUTH);
        if(maze.isInBounds(curRow, curCol-1) && maze.isOpen(curRow, curCol-1)) temp.add(Direction.WEST);
        return temp;
    }
    
    public void move(Direction d){
        if(d == Direction.NORTH) curRow--;
        if(d == Direction.EAST) curCol++;
        if(d == Direction.SOUTH) curRow++;
        if(d == Direction.WEST) curCol--;
        dir = d;
        img = dir.getImage();
    }
    
    public abstract void makeMove();
    
}
