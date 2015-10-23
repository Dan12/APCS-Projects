package lab300;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/*
 * File Name: Lab300.java
 *   Created: Oct 20, 2014
 *    Author: 
 */


public class Lab300 extends JScrollPane
{
  
  public void runTests()
  {
    out.append("Testing output begins here...\n\n");
    CandyBucket jake = new CandyBucket("Jake the dog");
    CandyBucket finn = new CandyBucket("Finn the human");
    out.append(jake.toString()+"\n");
    out.append(finn.toString()+"\n");
    
    out.append(jake.add("twix"));   out.append(jake.add("snickers"));   out.append(jake.add("mounds"));
    out.append(finn.add("meatloaf"));
    out.append(jake.toString()+"\n");
    out.append(finn.toString()+"\n");
    
    boolean b1 = jake.contains("zagnut"), b2 = finn.contains("meatloaf");
    out.append(b1+"  "+b2+"\n");
    
    jake.add("twix");   out.append(jake.add("snickers"));   out.append(jake.add("twix"));
    out.append("\n"+jake.toString()+"\n");
    int a = jake.remove("snickers");
    out.append("Removed "+a+" snickers from Jake's bucket.\n");
    out.append(jake.toString()+"\n");
    finn.remove(null);   finn.remove("");
    out.append(finn.toString()+"\n");
    
    CandyBucket bonnibel = jake.emptyBucket("Princess Bubblegum");
    out.append("\n"+jake.toString()+"\n");
    out.append(bonnibel.toString()+"\n");
    
    bonnibel.transferTo(finn, "twix");
    jake.transferTo(finn, null);
    jake.transferTo(finn, "gummy bears");
    finn.transferTo(jake, "meatloaf");
    bonnibel.transferTo(bonnibel, "mounds");
    out.append("\n"+finn.toString()+"\n");
    out.append(bonnibel.toString()+"\n");
    out.append(jake.toString()+"\n");
    
        CandyBucket aa = new CandyBucket("A");
    CandyBucket b = new CandyBucket("B");
    CandyBucket c = new CandyBucket("C");
    java.util.Random r = new java.util.Random(2);
    for (int k = 0; k < 15; k++)
    {
      if (r.nextInt(2) == 0)
      {
        aa.add("twix");
        b.add("snickers");
        b.add("twizzlers");
      }
      else
      {
        aa.add("skittles");
        b.add("mars");
        aa.add("snickers");
      }
    }
   
    aa.transferTo(b, "snickers");
    CandyBucket d = c.emptyBucket("D");
    c.transferTo(b, "mars");
    b.transferTo(c, "twizzlers");
    b.transferTo(aa, null);
    b.transferTo(aa, "");
    aa.transferTo(d, "skittles");
    CandyBucket e = b.emptyBucket("E");
    e.transferTo(b, "mars");
    
    out.append(aa+"\n\n");
    out.append(b+"\n\n");
    out.append(c+"\n\n");
    out.append(d+"\n\n");
    out.append(e+"\n\n");
    
    out.append("\nEnd of tests.");
  }
    
  //<editor-fold defaultstate="collapsed" desc="-- This method will initialize the output window --">
  private JTextArea out;
  public Lab300(int w, int h, JFrame f)
  {
    super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    super.setPreferredSize(new Dimension(w, h));
    out = new JTextArea();
    out.setFont(new Font("Consolas", Font.BOLD, 18));
    out.setWrapStyleWord(true);
    out.setLineWrap(true);
    out.setEditable(false);
    super.setViewportView(out);
    this.runTests();
  }
  //</editor-fold> 
  
  //<editor-fold defaultstate="collapsed" desc="-- This method will launch your application --">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab300");
    fr.setContentPane(new Lab300(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
  }
  //</editor-fold> 
 
}
