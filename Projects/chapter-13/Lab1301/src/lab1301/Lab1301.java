package lab1301;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * File Name: Lab1301.java
 *   Created: Dec 13, 2013
 *    Author:
 */


public class Lab1301 extends JPanel implements MouseListener, MouseMotionListener, ChangeListener
{
  // Declare instance variables here...
  private pixel[][] grid;
  private final int ROWS = 100;
  private final int COLS = 160;
  private final int WIDTH;
  JSlider colorCB = new JSlider();
  JSlider colorCG = new JSlider();
  JSlider colorCR = new JSlider();
  JTextArea colorP = new JTextArea();
  int r = 0;
  int g = 0;
  int b = 255;


  @Override
  public void mousePressed(MouseEvent e)
  {
    int c = e.getX() / WIDTH;
    int r = e.getY() / WIDTH;
    if (c >= 0 && c < COLS && r >= 0 && r < ROWS)
    {
      if (e.getButton() == MouseEvent.BUTTON1) //left mouse button clicked
      {
        grid[r][c].setActive(!grid[r][c].active);
      }
      if (e.getButton() == MouseEvent.BUTTON3) //right mouse button clicked
      {
        //call your recursive flood fill method here...
        if(!grid[r][c].active)
          floodFill(r,c);
      }
    }
    super.repaint();
  }

  @Override
  public void stateChanged(ChangeEvent e)
  {
    super.repaint();
  }
  
  public class pixel{
    boolean active;
    int re;
    int gr;
    int bl;
    public pixel(){
      active = false;
      re = 255;
      gr = 255;
      bl = 255;
    }
    public void setActive(boolean a){
      active = a;
      re = colorCR.getValue();
      gr = colorCG.getValue();
      bl = colorCB.getValue();
    }
  }
  
  public void floodFill(int r, int c){
    System.out.println(r+","+c);
    if(r < 0 || r >= ROWS || c < 0 || c >= COLS){
      System.out.println("Returned");
      return;
    }
    if(!grid[r][c].active){
      grid[r][c].setActive(true);
    }
    if(r-1 >= 0)
      if(!grid[r-1][c].active)
        floodFill(r-1, c);
    if(c+1 < COLS)
      if(!grid[r][c+1].active)
        floodFill(r,c+1);
    if(r+1 < ROWS)
      if(!grid[r+1][c].active)
        floodFill(r+1, c);
    if(c-1 >= 0)
      if(!grid[r][c-1].active)
        floodFill(r,c-1);
    System.out.println("Finished");
  }

  //<editor-fold defaultstate="collapsed" desc="--You do not need to modify these methods.--">
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseMoved(MouseEvent e) {}
  public void mouseDragged(MouseEvent e)
  {
    int c = e.getX() / WIDTH;
    int r = e.getY() / WIDTH;
    if (c >= 0 && c < COLS && r >= 0 && r < ROWS)
    {
      if (SwingUtilities.isLeftMouseButton(e)) //left mouse button clicked
      {
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) grid[r][c].setActive(true);
        if (e.getModifiersEx() == (MouseEvent.CTRL_DOWN_MASK | MouseEvent.BUTTON1_DOWN_MASK)) grid[r][c].setActive(false);
      }
    }
    super.repaint();
  }


  // Constructor
  public Lab1301(int w, int h, JFrame f)
  {
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    super.setBackground(Color.WHITE);
    super.setLayout(null);
    grid = new pixel[ROWS][COLS];
    for(int r = 0; r < ROWS; r++){
      for(int c = 0; c < COLS; c++){
        grid[r][c] = new pixel();
      }
    }
    super.addMouseListener(this);
    super.addMouseMotionListener(this);
    WIDTH = (int) Math.min(w/COLS, h/ROWS);
    JMenuBar mb = new JMenuBar();
    colorCR.setMaximum(255);
    colorCR.setMinimum(0);
    colorCR.setBounds(10, 570, 80, 30);
    colorCR.addChangeListener(this);
    colorCB.setMaximum(255);
    colorCB.setMinimum(0);
    colorCG.setBounds(110, 570, 80, 30);
    colorCB.addChangeListener(this);
    colorCG.setMaximum(255);
    colorCG.setMinimum(0);
    colorCB.setBounds(210, 570, 80, 30);
    colorCG.addChangeListener(this);
    Border brd = BorderFactory.createLineBorder(Color.black);
    colorP.setBounds(310, 570, 40, 20);
    colorP.setVisible(true);
    colorP.setText("");
    colorP.setBorder(brd);
    super.add(colorCR);
    super.add(colorCB);
    super.add(colorCG);
    super.add(colorP);
    JButton reset = new JButton();
    reset.setMnemonic(KeyEvent.VK_R);
    reset.setText("Reset   ");
    reset.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        grid = new pixel[ROWS][COLS];
        for(int r = 0; r < ROWS; r++){
          for(int c = 0; c < COLS; c++){
            grid[r][c] = new pixel();
          }
        }
        Lab1301.super.repaint();
      }
    });

    mb.add(reset);
    f.setJMenuBar(mb);
  }

  // Perform any custom painting (if necessary) in this method
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, super.getWidth(), super.getHeight());
    int maxX = COLS * WIDTH;
    int maxY = ROWS * WIDTH;
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, super.getWidth(), super.getHeight());
    g.setColor(Color.BLACK);
    for (int x = 0; x <= maxX; x += WIDTH) g.drawLine(x, 0, x, maxY);
    for (int y = 0; y <= maxY; y += WIDTH) g.drawLine(0, y, maxX, y);
    //g.setColor(new Color(Integer.parseInt(colorCR.getText()),Integer.parseInt(colorCG.getText()),Integer.parseInt(colorCB.getText())));
    colorP.setBackground(new Color(colorCR.getValue(),colorCG.getValue(),colorCB.getValue()));
    for (int r = 0; r < ROWS; r++)
    {
      for (int c = 0; c < COLS; c++)
      {
        if (grid[r][c].active == true){ 
          g.setColor(new Color(grid[r][c].re,grid[r][c].gr,grid[r][c].bl));
          g.fillRect(c*WIDTH+1, r*WIDTH+1, WIDTH-1, WIDTH-1);
        }
      }
    }
  }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: Lab1301");
    fr.setContentPane(new Lab1301(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);
  }
  //</editor-fold>

}
