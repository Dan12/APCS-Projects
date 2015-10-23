package lab1300;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Lab1300A extends JPanel implements ActionListener
{
  
  //////////////////////////////////////////////////////////////////////////////
  //
  //  Use recursion to return the product of the odd natural numbers up to n 
  //  (the parameter n must be an odd natural number).  Note that the return 
  //  "type long" is an integer type that uses twice as many bits as an "int"
  //   uses, but overflow will still occur fairly rapidly.
  //
  
  private long productOdds(int n)
  {
    if(n>1)
      return n*productOdds(n-2);
    else
      return n;
    //return 0;
  }
 
  
  //////////////////////////////////////////////////////////////////////////////
  // You  do not need to modify this section
  //////////////////////////////////////////////////////////////////////////////
  
  private JTextArea a;
  private JTextField t;
  
  public Lab1300A(int w, int h)
  {
    super.setLayout(null);
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    JLabel j = new JLabel("Enter an odd whole number:  ");
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
      if (n % 2 == 0 || n < 0)
      {
        a.setText("Invalid Input!");
      }
      else
      {
        a.setText("The product of odd numbers from 1 to "+n);
        a.append("\nis "+this.productOdds(n));
      }
    }
    catch(NumberFormatException nfe)
    {
      a.setText("Invalid Input!");
    }
  }
  
}
