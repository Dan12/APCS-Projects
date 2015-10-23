package lab1000;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

/**
 * This class runs a world that contains a bug and a rock, added at random
 * locations. Click on empty locations to add additional actors. Click on
 * populated locations to invoke methods on their occupants.
 * 
 * This class is not tested on the APCS A exam.
 */

public class Lab1000 extends Object
{
  public static void main(String[] args)
  {
    ActorWorld world = new ActorWorld(new BoundedGrid(9, 9));
    world.add(new Location(4, 4), new Rock());
    world.add(new Bug());
    //world.add(new BoxBug(5));
    //world.add(new CircleBug(3));
    //world.add(new ZBug(6));
    //world.add(new DancingBug("5 2 1 1"));
    //world.add(new DancingBug("0 1 2 1 1 7 6 7 1 2 "));
    world.show();
  }
}