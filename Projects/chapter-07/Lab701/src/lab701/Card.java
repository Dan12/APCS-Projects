package lab701;

import java.awt.Image;
import java.awt.Toolkit;

public class Card extends Object 
{
  String filename;
  String suit;
  int val;
  Image cImage;
  
  public Card(String f, String s, int v){
    filename = f;
    suit = s;
    val = v;
    cImage = Toolkit.getDefaultToolkit().getImage(super.getClass().getResource(filename));
  }
}


