package lab1001;

import java.util.ArrayList;

/*
 * File Name: Scout.java
 *   Created: Mar 18, 2015
 *    Author: 
 */

public class Scout extends Object
{
  
    private Maze maze;
    private Direction prevDir;
    private ArrayList<ArrayList<Direction>> paths;
    private int curPath;
    private boolean foundEnd;
    
    public Scout(Maze m){
        maze = m;
        prevDir = null;
        paths = new ArrayList<ArrayList<Direction>>();
        paths.add(new ArrayList<Direction>());
        curPath = 0;
        foundEnd = false;
    }
    
    public ArrayList<Direction> findExitPath(){
        findExit(maze.getStartRow(), maze.getStartCol());
        ArrayList<Direction> temp = new ArrayList<Direction>();
        for(int i = 0; i < paths.size(); i++){
            System.out.println("------");
            for(int j = 0; j < paths.get(i).size(); j++){
                System.out.println(paths.get(i).get(j));
                temp.add(paths.get(i).get(j));
            }
        }
        return temp;
    }
    
    public ArrayList<Direction> getOpenNeighbors(int r, int c){
        ArrayList<Direction> temp = new ArrayList<Direction>();
        if(maze.isInBounds(r-1, c) && maze.isOpen(r-1, c)) temp.add(Direction.NORTH);
        if(maze.isInBounds(r, c+1) && maze.isOpen(r, c+1)) temp.add(Direction.EAST);
        if(maze.isInBounds(r+1, c) && maze.isOpen(r+1, c)) temp.add(Direction.SOUTH);
        if(maze.isInBounds(r, c-1) && maze.isOpen(r, c-1)) temp.add(Direction.WEST);
        return temp;
    }
    
    private boolean findExit(int r, int c){
        //System.out.println(r+","+c);
        ArrayList<Direction> temp = getOpenNeighbors(r, c);
        if(r != maze.getFinishRow() && c != maze.getFinishCol() && r != maze.getStartRow() && c != maze.getStartCol())
            maze.markDeadEnd(r, c);
        for(int i = 0; i < temp.size(); i++){
            int[] temparr = setNext(r, c, temp.get(i));
            if(maze.isDeadEnd(temparr[0], temparr[1])){
                temp.remove(i);
                i--;
            }
        }
        if((r == maze.getFinishRow() && c == maze.getFinishCol())){
            foundEnd = true;
            return false;
        }
        if(temp.isEmpty() && prevDir != null){
            paths.remove(curPath);
            curPath--;
            return true;
        }
        else{
            //go down every other path
            if(prevDir != null && temp.indexOf(prevDir) != -1)
                temp.remove(temp.indexOf(prevDir));
            int num = 0;
            boolean isFirst = false;
            for(int i = 0; i < temp.size(); i++){
                if(!foundEnd){
                    //System.out.println("Path "+curPath+","+prevDir+","+temp.get(i));
                    prevDir = temp.get(i).getClockwiseDirection().getClockwiseDirection();
                    int tempArr[] = setNext(r,c,temp.get(i));
                    if(temp.size() != 1 || prevDir == null){
                        paths.add(new ArrayList<Direction>());
                        curPath++;
                        isFirst = true;
                    }
                    paths.get(curPath).add(temp.get(i));
                    maze.markDeadEnd(r, c);
                    boolean de = findExit(tempArr[0],tempArr[1]);
                    if(de)
                        num++;
                    //System.out.println(de + " " + num);
                }
            }
            if(num == temp.size()){
                if(isFirst){
                    System.out.println(curPath);
                    paths.remove(curPath);
                    curPath--;
                }
                return true;
            }
            return false;
        }
    }
    
    private int[] setNext(int r, int c, Direction d){
        int[] temp = new int[] {r,c};
        if(d == Direction.NORTH) temp[0]--;
        if(d == Direction.EAST) temp[1]++;
        if(d == Direction.SOUTH) temp[0]++;
        if(d == Direction.WEST) temp[1]--;
        return temp;
    } 
}
