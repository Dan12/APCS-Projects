package lab1001;

import java.util.ArrayList;

/*
 * File Name: LeftHandRobot.java
 *   Created: Mar 16, 2015
 *    Author: 
 */

public class LeftHandRobot extends Robot
{

    public LeftHandRobot(Maze m)
    {
        super(m);
    }

    @Override
    public void makeMove()
    {
        ArrayList<Direction> temp = this.getOpenNeighbors();
        if(this.getDirection() == Direction.NORTH){
            if(temp.contains(Direction.WEST))
                this.move(Direction.WEST);
            else if(temp.contains(Direction.NORTH))
                this.move(Direction.NORTH);
            else if(temp.contains(Direction.EAST))
                this.move(Direction.EAST);
            else
                this.move(Direction.SOUTH);
        }
        else if(this.getDirection() == Direction.SOUTH){
            if(temp.contains(Direction.EAST))
                this.move(Direction.EAST);
            else if(temp.contains(Direction.SOUTH))
                this.move(Direction.SOUTH);
            else if(temp.contains(Direction.WEST))
                this.move(Direction.WEST);
            else
                this.move(Direction.NORTH);
        }
        else if(this.getDirection() == Direction.EAST){
            if(temp.contains(Direction.NORTH))
                this.move(Direction.NORTH);
            else if(temp.contains(Direction.EAST))
                this.move(Direction.EAST);
            else if(temp.contains(Direction.SOUTH))
                this.move(Direction.SOUTH);
            else
                this.move(Direction.WEST);
        }
        else if(this.getDirection() == Direction.WEST){
            if(temp.contains(Direction.SOUTH))
                this.move(Direction.SOUTH);
            else if(temp.contains(Direction.WEST))
                this.move(Direction.WEST);
            else if(temp.contains(Direction.NORTH))
                this.move(Direction.NORTH);
            else
                this.move(Direction.EAST);
        }
    }
  
}
