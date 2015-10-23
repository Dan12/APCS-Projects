package lab801;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class MSResources extends Object
{
  //By making the only constructor private, prevent other classes from instantiating a MSResources object 
  private MSResources()
  {
  }
  
  public static final ImageIcon BOMB, BOMB32X32, BOMB_X, BOMB_RED, BOMB_FLAG, BOMB_QUESTION, QUESTION, FLAG, HOURGLASS;
  public static final  Font DIGITAL_NUMBERS;
  static
  {
    int size = MSPanel.BUTTON_SIZE * 4 / 5;
    Toolkit tk = Toolkit.getDefaultToolkit();
    BOMB = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/bomb.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)); 
    BOMB_X = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/bombX.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)); 
    BOMB_RED = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/bombRed.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)); 
    BOMB_FLAG = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/bombFlag.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)); 
    BOMB_QUESTION = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/bombQuestion.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)); 
    QUESTION = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/question.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)); 
    FLAG = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/flag.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH)); 
    HOURGLASS = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/clock.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));  
    BOMB32X32 = new ImageIcon(tk.getImage(MSResources.class.getResource("resources/bomb.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH)); 
    Font f = new Font("Consolas", Font.BOLD, 24);
    try
    {
      f = Font.createFont(Font.TRUETYPE_FONT, MSTimer.class.getResourceAsStream("resources/Digital.ttf")).deriveFont((float)40);
    }
    catch (Exception e)
    {
    }
    DIGITAL_NUMBERS = f;
    GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(DIGITAL_NUMBERS);
  }
}
