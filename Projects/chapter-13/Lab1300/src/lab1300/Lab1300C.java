package lab1300;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Lab1300C extends JPanel implements ActionListener
{
  
  //////////////////////////////////////////////////////////////////////////////
  //
  //  Use recursion to return the sum of the positive multiples of 7 that are
  //  also less than or equal to n (n must be a whole number).
  //
  //////////////////////////////////////////////////////////////////////////////
  private long sumPosMultsOf7(int n)
  {
    if(n>=7)
      return (n/7)*7+sumPosMultsOf7(n-7);
    else
      return 0;
    //return 0;
  }

 
  
  //////////////////////////////////////////////////////////////////////////////
  // You  do not need to modify this section
  //////////////////////////////////////////////////////////////////////////////
  
  private JTextArea a;
  private JTextField t;
  
  public Lab1300C(int w, int h)
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
        a.setText("The sum of the positive multiples of 7 up to and ");
        a.append("\nincluding "+n+" is "+this.sumPosMultsOf7(n));

      }
    }
    catch(NumberFormatException nfe)
    {
      a.setText("Invalid Input!");
    }
  }
  
}
