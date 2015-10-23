package lab1000;

import java.awt.Color;
import java.awt.Graphics;

/*
 * File Name: Square.java
 *   Created: Mar 10, 2015
 *    Author: 
 */

public class Square extends Rectangle
{

    public Square(int x, int y, int w, Color c)
    {
        super(x, y, w, w, c);
    }
    
    @Override
    public String getName(){
        return "Square";
    }
  
}
