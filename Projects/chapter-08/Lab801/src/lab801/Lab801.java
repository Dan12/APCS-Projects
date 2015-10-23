package lab801;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*
 * File Name: Lab801.java
 *   Created: Dec 31, 2013
 *    Author: David Rogers
 */

public class Lab801 extends JPanel implements ActionListener
{
  //<editor-fold defaultstate="collapsed" desc="--Instance variables declarations--">
  private JFrame fr;
  private MSPanel msp;
  
  private JMenuBar menuBar;
 
  private JMenu gameMenu, difficultyMenu, helpMenu;
  private JMenuItem newMI, restartMI, exitMI, beginnerMI, intermediateMI, advancedMI, customMI, aboutMI, viewHelpMI;
  private JMenuItem currentDifficulty;
  private ButtonGroup difficultyGroup;
  private MSCustomSizeDialog customDialog;
  private MSInstructions instructions;
  //</editor-fold> 
  
  //<editor-fold defaultstate="collapsed" desc="--Lab 801 Constructor--">
  public Lab801(JFrame f)
  {
    super.setOpaque(true);
    super.setBackground(new Color(224, 224, 224));   
    super.setLayout(null);
    
    fr = f;
    Font vb12 = new Font("Verdana", Font.BOLD, 12);
    Font vb16 = new Font("Verdana", Font.BOLD, 16);

    gameMenu = new JMenu();
    gameMenu.setMnemonic(KeyEvent.VK_G);
    gameMenu.setText("Game   ");
    gameMenu.setFont(vb16);
    
    newMI = new JMenuItem("Start New Game ");
    newMI.setFont(vb12);
    newMI.addActionListener(this);
    gameMenu.add(newMI);
        
    restartMI = new JMenuItem("Restart This Game ");
    restartMI.setFont(vb12);
    restartMI.addActionListener(this);
    gameMenu.add(restartMI);
    
    exitMI = new JMenuItem("Exit ");
    exitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
    exitMI.setFont(vb12);
    exitMI.addActionListener(this);
    gameMenu.add(exitMI);
    
    difficultyMenu = new JMenu();
    difficultyMenu.setMnemonic(KeyEvent.VK_D);
    difficultyMenu.setText("Difficulty   ");
    difficultyMenu.setFont(vb16);
        
    difficultyGroup = new ButtonGroup();
    
    beginnerMI = new JRadioButtonMenuItem("Beginner ");
    beginnerMI.setFont(vb12);
    beginnerMI.addActionListener(this);
    beginnerMI.setSelected(true);
    difficultyGroup.add(beginnerMI);
    difficultyMenu.add(beginnerMI);

    intermediateMI = new JRadioButtonMenuItem("Intermediate ");
    difficultyGroup.add(intermediateMI);
    difficultyMenu.add(intermediateMI);
    intermediateMI.setFont(vb12);
    intermediateMI.addActionListener(this);
    
    advancedMI = new JRadioButtonMenuItem("Advanced ");
    difficultyGroup.add(advancedMI);
    difficultyMenu.add(advancedMI);
    advancedMI.setFont(vb12);
    advancedMI.addActionListener(this);
    
    customMI = new JRadioButtonMenuItem("Custom... ");
    difficultyGroup.add(customMI);
    difficultyMenu.add(customMI);
    customMI.setFont(vb12);
    customMI.addActionListener(this);
    
    helpMenu = new JMenu();
    helpMenu.setMnemonic(KeyEvent.VK_H);
    helpMenu.setText("Help   ");
    helpMenu.setFont(vb16);
    
    viewHelpMI = new JMenuItem("View Help ");
    viewHelpMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    viewHelpMI.setFont(vb12);
    viewHelpMI.addActionListener(this);
    helpMenu.add(viewHelpMI);
    
    aboutMI = new JMenuItem("About Minesweeper ");
    aboutMI.setFont(vb12);
    aboutMI.addActionListener(this);
    helpMenu.add(aboutMI);
        
    menuBar = new JMenuBar();
    menuBar.add(gameMenu);
    menuBar.add(difficultyMenu);
    menuBar.add(helpMenu);
    fr.setJMenuBar(menuBar);

    
    customDialog = new MSCustomSizeDialog(fr, true);
    customDialog.setVisible(false);
    
    instructions = new MSInstructions(fr, true);
    instructions.setVisible(false);
    
    this.start(beginnerMI);
    
    //prevents the 1st button from appearing "selected" with dashed border
    JButton b = new JButton();
    b.setSelected(true);
    super.add(b);
  }
  //</editor-fold> 
  
  //<editor-fold defaultstate="collapsed" desc="--Process menu selections--"> 
  @Override  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == currentDifficulty && currentDifficulty != customMI) return;
    if (e.getSource() == exitMI) System.exit(0);
    if (e.getSource() == restartMI) msp.reset();
    if (e.getSource() == newMI) this.start(currentDifficulty);
    if (e.getSource() == beginnerMI) this.start(beginnerMI);
    if (e.getSource() == intermediateMI) this.start(intermediateMI);
    if (e.getSource() == advancedMI) this.start(advancedMI);
    if (e.getSource() == customMI)
    {
      customDialog.setLocationRelativeTo(this);
      int success = customDialog.showDialog();
      if (success != -1) this.start(customMI);
      else currentDifficulty.setSelected(true);
    }
    if (e.getSource() == viewHelpMI)
    {
      instructions.setLocationRelativeTo(this);
      instructions.setVisible(true);
    }
    if (e.getSource() == aboutMI)
    {
      JOptionPane.showMessageDialog(this, "Minesweeper, version 1.DAN.IS.AWESOME", "About Minesweeper...", JOptionPane.INFORMATION_MESSAGE, MSResources.BOMB32X32);
    }
    super.repaint();
  }
  //</editor-fold> 
  
  //<editor-fold defaultstate="collapsed" desc="--Start a new game--">
  private void start(JMenuItem x)
  {
    currentDifficulty = x;
    if (msp != null) super.remove(msp);
    
    if (x == beginnerMI) msp = new MSPanel(9, 9, 10, fr);
    if (x == intermediateMI) msp = new MSPanel(16, 16, 40, fr);
    if (x == advancedMI) msp = new MSPanel(16, 30, 99, fr);
    if (x == customMI) msp = new MSPanel(customDialog.getRows(), customDialog.getCols(), customDialog.getMines(), fr);
    msp.setBounds(35, 40, msp.getWidth(), msp.getHeight());
    super.add(msp);
    super.setPreferredSize(new Dimension(msp.getWidth() + 70, msp.getHeight() + 50)); 
    fr.pack();
  }
  //</editor-fold> 
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {  
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e)
    {
    }
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame fr = new JFrame("Minesweeper");
        fr.setContentPane(new Lab801(fr));
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(100, 100);
        fr.setResizable(false);
        fr.pack();
        fr.setIconImage(MSResources.BOMB.getImage());
        SwingUtilities.updateComponentTreeUI(fr);
        fr.setVisible(true);
      }
    });
  }
  //</editor-fold>  
}
