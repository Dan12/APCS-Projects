package lab1105;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * A <code>ChameleonCritter</code> takes on the color of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ChameleonCritter extends Critter
{

  /**
   * Randomly selects a neighbor and changes this critter's color to be the
   * same as that neighbor's. If there are no neighbors, no action is taken.
   */
  @Override
  public void processActors(ArrayList<Actor> actors)
  {
    int n = actors.size();
    if (n == 0)
    {
      return;
    }
    int r = (int) (Math.random() * n);

    Actor other = actors.get(r);
    setColor(other.getColor());
  }

  /**
   * Turns towards the new location as it moves.
   */
  @Override
  public void makeMove(Location loc)
  {
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
  }
}
