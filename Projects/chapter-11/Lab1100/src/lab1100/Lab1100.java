
package lab1100;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/*
 * File Name: Lab24.java
 *   Created: Feb 4, 2013
 *    Author: David Rogers
 * 
 * ASCII Maze generator: http://www.delorie.com/game-room/mazes/genmaze.cgi
 */


public class Lab1100 extends JPanel implements ActionListener
{
  //<editor-fold defaultstate="collapsed" desc="--Instance variables declarations--">
  private JMenuBar menuBar;
  private JMenu fileMenu, robotMenu;
  private JMenuItem restartMI, loadMI, quitMI, randomRobotMI, leftHandRobotMI, recursionRobotMI, currentRobotMI;
  private ButtonGroup robotGroup;
  private JButton move, run;
  private JFrame fr;
  private JFileChooser fc; 
  private int cellWidth;
  
  private Maze m;
  
  
  private Timer time;
  //</editor-fold> 
  
  //private Robot robbie;
    
  //<editor-fold defaultstate="collapsed" desc="--Lab1100 Constructor--">
  public Lab1100(int w, int h, JFrame f)
  {
    fr = f;
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(Color.WHITE);   
    Font vb12 = new Font("Verdana", Font.BOLD, 12);
    Font vb16 = new Font("Verdana", Font.BOLD, 16);
        
    fileMenu = new JMenu();
    fileMenu.setMnemonic(KeyEvent.VK_F);
    fileMenu.setText("File     ");
    fileMenu.setFont(vb16);
    
    restartMI = new JMenuItem("Restart this maze ");
    restartMI.setFont(vb12);
    restartMI.addActionListener(this);
    fileMenu.add(restartMI);
    
    loadMI = new JMenuItem("Load a maze ");
    loadMI.setFont(vb12);
    loadMI.addActionListener(this);
    fileMenu.add(loadMI);
    
    quitMI = new JMenuItem("Quit ");
    quitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
    quitMI.setFont(vb12);
    quitMI.addActionListener(this);
    fileMenu.add(quitMI);
        
    robotMenu = new JMenu();
    robotMenu.setMnemonic(KeyEvent.VK_R);
    robotMenu.setText("Robot     ");
    robotMenu.setFont(vb16);
    robotGroup = new ButtonGroup();
    
    randomRobotMI = new JRadioButtonMenuItem("Random Robot ");
    randomRobotMI.setFont(vb12);
    randomRobotMI.addActionListener(this);
    randomRobotMI.setSelected(true);
    currentRobotMI = randomRobotMI;
    robotGroup.add(randomRobotMI);
    robotMenu.add(randomRobotMI);
    
    leftHandRobotMI = new JRadioButtonMenuItem("Left Hand Robot ");
    leftHandRobotMI.setFont(vb12);
    leftHandRobotMI.addActionListener(this);
    robotGroup.add(leftHandRobotMI);
    robotMenu.add(leftHandRobotMI);
    
    recursionRobotMI = new JRadioButtonMenuItem("Recursion Robot ");
    recursionRobotMI.setFont(vb12);
    recursionRobotMI.addActionListener(this);
    robotGroup.add(recursionRobotMI);
    robotMenu.add(recursionRobotMI);
    
    menuBar = new JMenuBar();
    menuBar.add(fileMenu);
    menuBar.add(robotMenu);
    f.setJMenuBar(menuBar);
    
    fc = new JFileChooser(new File("."));
    fc.removeChoosableFileFilter(fc.getFileFilter());
    fc.addChoosableFileFilter(new FileFilter()
    {
      @Override
      public String getDescription()
      {
        return "Plain Text Files (.txt)";
      }
      @Override
      public boolean accept(File f)
      {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
      }
    });
    
    move = new JButton("Move Once");
    move.addActionListener(this);
    menuBar.add(move);
    
    run = new JButton("Auto Move");
    run.addActionListener(this);
    menuBar.add(run);
    
    this.loadMaze(null);
    //robbie = new RandomRobot(m);
  }
  //</editor-fold>
 
  /**
   * 
   * @return Returns the width of each square cell (in pixels)
   */
  public int getCellWidth()
  {
    return cellWidth;
  }
    
  // Perform any custom painting (when necessary) in this method
  @Override
  public void paintComponent(Graphics g)
  {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, super.getWidth(), super.getHeight());
    m.drawOnto(g);
    //g.drawImage(robbie.getImage(), robbie.getCol()*cellWidth, robbie.getRow()*cellWidth, cellWidth, cellWidth, this);
  }
    
  // Process GUI input in this method
  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == time || e.getSource() == move)
    {
      /*if (!robbie.isFinished())
      {
        robbie.makeMove();
      }
      if (robbie.isFinished())
      {
        move.setEnabled(false);
        run.setEnabled(false);
        time.stop();
      }
      if (e.getSource() == move)
      {
        time.stop();
      }*/
    }
    else if (e.getSource() == run)
    {
      if (time.isRunning())
      {
        time.stop();
      }
      else
      {
        time.start();
      }
    }
    else if (e.getSource() == loadMI)
    {
      if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
      {
        this.loadMaze(fc.getSelectedFile());
        this.start();
      }
    }
    else if (e.getSource() == quitMI)
    {
      System.exit(1);
    }
    else if (e.getSource() == restartMI)
    {
      this.start();
    }
    else if (e.getSource() instanceof JMenuItem && currentRobotMI != (JMenuItem)e.getSource())
    {
      currentRobotMI = (JMenuItem) e.getSource();
      this.start();
    }
    super.repaint();
  }
  
  private void start()
  {
    time.stop();
    //if (currentRobotMI == randomRobotMI) robbie = new RandomRobot(m);
    //else if (currentRobotMI == leftHandRobotMI) robbie = new LeftHandRobot(m);
    //else robbie = new RecursionRobot(m);
    move.setEnabled(true);
    run.setEnabled(true);
  }
  
  
  //<editor-fold defaultstate="collapsed" desc="--This method call the Maze constructor and resize the app--">
  private void loadMaze(File f)
  {
    m = new Maze(f, this);
    String fileName = "Default Maze";
    if (f != null) fileName = f.getName();
    super.setOpaque(true);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int w = d.width * 85 / 100, h = d.height * 85 / 100;
    cellWidth = Math.min(64, Math.min(w / m.numCols(), h / m.numRows()));
    super.setPreferredSize(new Dimension(m.numCols()*cellWidth, m.numRows()*cellWidth));
    super.setBackground(Color.WHITE);   
    fr.setTitle("Robot Maze: "+fileName);
    fr.setLocation(10, 10);
    fr.pack();
    time = new Timer(50, this);
    time.stop();
  }
  //</editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    try
    {
      UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.BOLD, 14));
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } 
    catch (Exception e)
    {
       e.printStackTrace();
    }
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Robot Maze");
    fr.setContentPane(new Lab1100(800, 600, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);
  }
  //</editor-fold>
}
