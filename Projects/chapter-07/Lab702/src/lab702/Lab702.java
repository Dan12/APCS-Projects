package lab702;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;


/*
 * File Name: Lab702.java
 *   Created: Nov 03, 2013
 *    Author: David Rogers
 */

public class Lab702 extends JPanel implements ActionListener
{
  // Declare instance variables here...
  private JMenuBar mainMenuBar;
  
  private JMenu fileMenu;
  private JMenuItem openMenuItem, saveMenuItem, exitMenuItem;
  
  private JMenu helpMenu;
  private JMenuItem aboutMenuItem, commandsMenuItem;
  
  private JLabel instructionsLabel;
  
  private JScrollPane jsp;
  private JTextArea inputArea;
  
  private JFileChooser fc;
  
  private Image canvas;
  
  private Turtle yertle;
  
  private JFrame fr;
  
  private File currentFile;
  
  private String oldText;
  
  private InfoDialog helpDialog;
  
  // Constructor
  public Lab702(int w, int h, JFrame f)
  {
    currentFile = null;
    fr = f;
    oldText = "";
    
    super.setPreferredSize(new Dimension(w, h));    
    this.createMenu();
    fr.setJMenuBar(mainMenuBar);
    this.createComponents();
    this.createFileChooser();
    this.createHelpScreen();
    
    canvas = new BufferedImage(660, 660, BufferedImage.TYPE_INT_ARGB);
    yertle = new Turtle(canvas);
    yertle.processCommands(null);
  }
  
  private void createMenu()
  {
    Font vb15 = new Font("Verdana", Font.BOLD, 15);
    Font vb20 = new Font("Verdana", Font.BOLD, 20);

    fileMenu = new JMenu();
    fileMenu.setMnemonic(KeyEvent.VK_F);
    fileMenu.setText("File   ");
    fileMenu.setFont(vb20);
    
    openMenuItem = new JMenuItem("Open Turtle Logo File ");
    openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
    openMenuItem.setFont(vb15);
    openMenuItem.setMnemonic(KeyEvent.VK_O);
    openMenuItem.setIcon(new ImageIcon(getClass().getResource("icons/open.png")));
    openMenuItem.addActionListener(this);
    fileMenu.add(openMenuItem);

    saveMenuItem = new JMenuItem("Save Turtle Logo File ");
    saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    saveMenuItem.setFont(vb15);
    saveMenuItem.setMnemonic(KeyEvent.VK_S);
    saveMenuItem.setIcon(new ImageIcon(getClass().getResource("icons/save.png")));
    saveMenuItem.addActionListener(this);
    fileMenu.add(saveMenuItem);

    exitMenuItem = new JMenuItem("Exit ");
    exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
    exitMenuItem.setMnemonic(KeyEvent.VK_X);
    exitMenuItem.setFont(vb15);
    exitMenuItem.setIcon(new ImageIcon(getClass().getResource("icons/exit.png")));
    exitMenuItem.addActionListener(this);
    fileMenu.add(exitMenuItem);
    
    mainMenuBar = new JMenuBar();
    mainMenuBar.add(fileMenu);

    helpMenu = new JMenu();
    helpMenu.setMnemonic(KeyEvent.VK_H);
    helpMenu.setText("Help   ");
    helpMenu.setFont(vb20);

    commandsMenuItem = new JMenuItem();
    commandsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    commandsMenuItem.setFont(vb15);
    commandsMenuItem.setIcon(new ImageIcon(getClass().getResource("icons/qmark.png")));
    commandsMenuItem.setText("Help With Commands");
    commandsMenuItem.setMnemonic(KeyEvent.VK_H);
    commandsMenuItem.addActionListener(this);
    helpMenu.add(commandsMenuItem);

    aboutMenuItem = new JMenuItem();
    aboutMenuItem.setFont(vb15);
    aboutMenuItem.setMnemonic(KeyEvent.VK_A);
    aboutMenuItem.setIcon(new ImageIcon(getClass().getResource("icons/turtle.png")));
    aboutMenuItem.setText("About Turtle Logo");
    aboutMenuItem.addActionListener(this);
    helpMenu.add(aboutMenuItem);

    mainMenuBar.add(helpMenu);
  }
  
  private void createComponents()
  {
    super.setLayout(null);
    instructionsLabel = new JLabel("Enter commands below.  F6 to execute.");
    instructionsLabel.setFont(new Font("Verdana", Font.BOLD, 14));
    instructionsLabel.setBounds(5, 5, 320, 25);
    super.add(instructionsLabel);
    
    inputArea = new JTextArea();
    inputArea.setFont(new Font("Consolas", Font.BOLD, 18));
    inputArea.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent e)
      {
        if (e.getKeyCode() == KeyEvent.VK_F6)
        {
          Lab702.this.updateCanvas();
        }
      }
    });
    
    
    jsp = new JScrollPane();
    jsp.setBounds(5, 30, 315, 660);
    jsp.setViewportView(inputArea);
    super.add(jsp);
  }
  
  private void createFileChooser()
  {
     fc = new JFileChooser(new File("."));
    fc.removeChoosableFileFilter(fc.getFileFilter());
    fc.addChoosableFileFilter(new FileFilter()
    {
      @Override
      public String getDescription()
      {
        return "Turtle Logo Files (.tlf)";
      }
      @Override
      public boolean accept(File f)
      {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".tlf");
      }
    });
    
  }
  
  private void createHelpScreen()
  {
    helpDialog = new InfoDialog(fr, true);
    helpDialog.setTitle("Help with commands...");    
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.drawImage(canvas, 332, 30, this);
  }
  
  // Process GUI input in this method
  @Override  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == exitMenuItem)
    {
      if (this.checkChanged("before you exit?") == -1) return;
      System.exit(1);
    }
    if (e.getSource() == openMenuItem) this.openFile();
    if (e.getSource() == saveMenuItem) this.saveFile();
    if (e.getSource() == aboutMenuItem)
    {
      JOptionPane.showMessageDialog(this, "2013 Turtle Logo, version 1.0", "About Turtle Logo:", JOptionPane.INFORMATION_MESSAGE, aboutMenuItem.getIcon());
    }
    if (e.getSource() == commandsMenuItem)
    {
      helpDialog.setLocationRelativeTo(this);
      helpDialog.setVisible(true);
    }
  }
  
  private void updateCanvas()
  {
    String input = inputArea.getText();
    Scanner in = new Scanner(input);
    ArrayList<String> commands = new ArrayList<String>();
    while (in.hasNextLine())
    {
      commands.add(in.nextLine());
    }
    yertle.processCommands(commands);
    super.repaint();
  }
  
  private int saveFile()
  {
    int val = fc.showSaveDialog(this);
    if (val != JFileChooser.APPROVE_OPTION) return -1;
    else
    { 
      String name = "";
      try
      {
        File chosen = fc.getSelectedFile();
        name = chosen.getName();
        if (!name.endsWith(".tlf")) 
        {
          chosen = new File(""+fc.getSelectedFile()+".tlf");
          name += ".tlf";
        }
        if (chosen.exists() && !chosen.equals(currentFile))
        {
          int answer = JOptionPane.showConfirmDialog(this, "Overwrite "+chosen.getName()+"?", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
          if (answer != JOptionPane.YES_OPTION)
          {            
            return saveFile();
          }
        }
        currentFile = chosen;
        Scanner in = new Scanner(inputArea.getText());
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(chosen)));
        while (in.hasNextLine())
        {
          out.print(in.nextLine());
          out.println();
        }
        out.close();
        fr.setTitle("Turtle Logo: "+fc.getSelectedFile().getName().replace(".tlf", ""));
        this.oldText = inputArea.getText();
      }
      catch (IOException e)
      {
        JOptionPane.showMessageDialog(this, "Cannot save as "+name+" because this file\nis being used concurrently by another process.", "File Save Error:", JOptionPane.ERROR_MESSAGE);
        return -1;
      }
    }
    return 0;
  }
  
  private int checkChanged(String s)
  {  
    if (!oldText.equals(inputArea.getText()))
    {
      int val = JOptionPane.showConfirmDialog(this, "Do you want to save the current file "+s, "Save current file first?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (val == JOptionPane.NO_OPTION) return 0;
      else if (val == JOptionPane.YES_OPTION)
      {
        return this.saveFile();
      }
      else return -1; //cancel or closed options...
    }
    return 0;
  }
  
  private void openFile()
  {
    if (this.checkChanged("before opening another file?") == -1) return;
    if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
    {
      String name;
      File chosen = null;
      try
      {
        chosen = fc.getSelectedFile();
        name = chosen.getName();
        if (!name.endsWith(".tlf")) 
        {
          chosen = new File(""+fc.getSelectedFile()+".tlf");
          name += ".tlf";
        }
        Scanner in = new Scanner(chosen);
        inputArea.setText("");
        while (in.hasNextLine())
        {
          inputArea.append(in.nextLine().trim()+"\n");
        }
        in.close();
        fr.setTitle("Turtle Logo: "+name.substring(0, name.length()-4));
        yertle.processCommands(null);
        this.currentFile = fc.getSelectedFile();
        super.repaint();
        this.oldText = this.inputArea.getText();
      }
      catch (FileNotFoundException e)
      {
        JOptionPane.showMessageDialog(this, "The file "+chosen+"\nyou selected does not exist.", "File Not Found Error:", JOptionPane.ERROR_MESSAGE);
      }
      
    }
  }
  
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
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        final JFrame fr = new JFrame("Turtle Logo");
        final Lab702 content = new Lab702(1000, 700, fr);
        fr.setContentPane(content);
        fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        fr.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
              if (content.checkChanged("before you exit?") != -1) fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        fr.setLocation(10, 10);
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);  
      }
    });   
  }
  //</editor-fold>  

}
