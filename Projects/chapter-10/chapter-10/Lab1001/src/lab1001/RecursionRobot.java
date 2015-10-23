package lab1001;

import java.util.ArrayList;

public class RecursionRobot extends Robot
{

    ArrayList<Direction> path;
    private int i;
    
    public RecursionRobot(Maze m)
    {
        super(m);
        Scout s = new Scout(m);
        path = s.findExitPath();
        i = 0;
    }

    @Override
    public void makeMove()
    {
        move(path.get(i));
        i++;
    }
  
}
