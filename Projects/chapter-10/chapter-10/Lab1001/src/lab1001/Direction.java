package lab1001;

import java.awt.Image;
import java.awt.Toolkit;

/*
 * File Name: Direction.java
 *   Created: Feb 23, 2014
 *    Author: 
 */

public enum Direction
{
  NORTH, EAST, SOUTH, WEST;
  
  private static final Image[] images = {Toolkit.getDefaultToolkit().getImage(Direction.class.getResource("resources/R2D2_North.png")),
                                         Toolkit.getDefaultToolkit().getImage(Direction.class.getResource("resources/R2D2_East.png")),
                                         Toolkit.getDefaultToolkit().getImage(Direction.class.getResource("resources/R2D2_South.png")),
                                         Toolkit.getDefaultToolkit().getImage(Direction.class.getResource("resources/R2D2_West.png"))};
  private static final Direction[] directions = {NORTH, EAST, SOUTH, WEST};

  public Image getImage()
  {
    return images[this.ordinal()];
  }
  
  public Direction getClockwiseDirection()
  {
    return directions[(this.ordinal() + 1) % 4];
  }
  
  public Direction getCounterclockwiseDirection()
  {
    return directions[(this.ordinal() + 3) % 4];
  } 
}
