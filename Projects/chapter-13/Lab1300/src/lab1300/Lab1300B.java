package lab1300;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Lab1300B extends JPanel implements ActionListener
{
  
  //////////////////////////////////////////////////////////////////////////////
  //
  //  Use recursion to return 5^n (the parameter n must be a whole number).
  //  Overflow occurs fairly rapidly.
  //
  //////////////////////////////////////////////////////////////////////////////
  private long nthPowerOf5(int n)
  {
    if(n>=1)
      return 5*nthPowerOf5(n-1);
    else
      return 1;
    //return 0;
  }
 
  
  //////////////////////////////////////////////////////////////////////////////
  // You  do not need to modify this section
  //////////////////////////////////////////////////////////////////////////////
  
  private JTextArea a;
  private JTextField t;
  
  public Lab1300B(int w, int h)
  {
    super.setLayout(null);
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    JLabel j = new JLabel("Enter a positive whole number:  ");
    super.add(j);
    j.setBounds(10, 10, 200, 25);
    j.setHorizontalAlignment(JLabel.RIGHT);
    t = new JTextField();
    t.setBounds(210, 10, 50, 25);
    super.add(t);
    JButton go = new JButton("Calculate");
    super.add(go);
    go.setBounds(270, 10, 100, 25);
    go.addActionListener(this);
    a = new JTextArea();
    a.setBounds(10, 50, w-20, h-90);
    a.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
    a.setEditable(false);
    a.setText("Waiting...");
    super.add(a);
  }

  public void actionPerformed(ActionEvent e)
  {
    try
    {
      int n = Integer.parseInt(t.getText());
      if (n < 0)
      {
        a.setText("Invalid Input!");
      }
      else
      {
        a.setText("The value of 5 to the power of "+n);
        a.append("\nis "+this.nthPowerOf5(n));
      }
    }
    catch(NumberFormatException nfe)
    {
      a.setText("Invalid Input!");
    }
  }
  
}
