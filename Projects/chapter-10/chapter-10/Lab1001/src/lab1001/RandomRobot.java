package lab1001;

import java.util.ArrayList;
import java.util.Random;

/*
 * File Name: RandomRobot.java
 *   Created: Mar 16, 2015
 *    Author: 
 */

public class RandomRobot extends Robot
{

    public RandomRobot(Maze m){
        super(m);
    }

    @Override
    public void makeMove(){
        ArrayList<Direction> temp = this.getOpenNeighbors();
        Random rndm = new Random();
        int dir = rndm.nextInt(temp.size());
        move(temp.get(dir));
    }
  
}
