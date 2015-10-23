package lab1104;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains critters. <br />
 * This class is not tested on the APCS exam.
 */
public class Lab1104
{

  public static void main(String[] args)
  {
    ActorWorld world;
        
    world = new ActorWorld();
    world.add(new Location(9, 9), new Actor());
    world.add(new Location(0, 0), new Bug(Color.YELLOW));
    world.add(new Location(7, 2), new Bug(Color.CYAN));
    world.add(new Location(4, 9), new Rock(Color.MAGENTA));
    world.add(new Location(3, 3), new Rock(Color.GREEN));
    world.add(new Location(2, 9), new Flower(Color.GRAY));
    world.add(new Location(5, 5), new Flower(Color.PINK));
    world.add(new Location(1, 5), new Flower(Color.RED));
    //world.add(new Location(3, 5), new Jumper(Color.DARK_GRAY));
    world.show();
  }
}