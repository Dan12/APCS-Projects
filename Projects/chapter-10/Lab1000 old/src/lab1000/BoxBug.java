package lab1000;

import info.gridworld.actor.Bug;
/**
* A BoxBug traces out a square “box” of a given size.
* The implementation of this class is testable on the APCS Exam.
*/

public class BoxBug extends Bug
{
  private int steps;
  private int sideLength;
  
  /**
  * Constructs a box bug that traces a square of a given side length
  * @param length the side length
  */
  public BoxBug(int length)
  {
    steps = 0;
    sideLength = length;
  }
  
  /**
  * Moves to the next location of the square.
  */
  @Override
  public void act()
  {
    if (steps < sideLength && canMove())
    {
      move();
      steps++;
    }
    else
    {
      turn();
      turn();
      steps = 0;
    }
  }
}