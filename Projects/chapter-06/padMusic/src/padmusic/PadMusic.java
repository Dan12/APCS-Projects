package padmusic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/*
 * File Name: PadMusic.java
 *   Created: Nov 4, 2014
 *    Author: 
 */


public class PadMusic extends JFXPanel implements KeyListener, MouseListener
{
  // Declare instance variables here...
  
  int width = 76;  
  
  int row = 0;
  int colum = 0;
  
  double stopCut = 110;
  
  double bpmTime = 1846*2;
    
  int keyArray1[] = {49,50,51,52,53,54,55,56,57,48};
  int keyArray2[] = {81,87,69,82,84,89,85,73,79,80};
  int keyArray3[] = {65,83,68,70,71,72,74,75,76,59};
  int keyArray4[] = {90,88,67,86,66,78,77,44,46,47};
  
  String source1[] = {"","","","","","","","","",""};
  String source2[] = {"","","","","","","","","",""};
  String source3[] = {"","","","","","","","","",""};
  String source4[] = {"","","","","","","","","",""};
  
  ArrayList<String> sources = new ArrayList<String>();
  
  int keyArray[][] = {
    keyArray1,
    keyArray2,
    keyArray3,
    keyArray4
  };
  
  String local = new File("").getAbsolutePath();
  
  
  String source[][] = {
    source1,
    source2,
    source3,
    source4
  };
  
  boolean keyPressed[][] = new boolean[source.length][source[0].length];
  
  MediaPlayer samples[][] = new MediaPlayer[source.length][source[0].length];
  
  JComboBox d;
  
  boolean populated = false;
  
  // Constructor
  public PadMusic(int w, int h, JFrame f)
  {
    keyPressed = new boolean[source.length][source[0].length];
    File folder = new File(new File("").getAbsolutePath().concat("/src/padmusic/assets/"));
    File[] listOfFiles = folder.listFiles();

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile() && (listOfFiles[i].toString().endsWith(".wav") || listOfFiles[i].toString().endsWith(".mp3"))) {
        System.out.println("File " + listOfFiles[i].getName());
        sources.add(listOfFiles[i].getName());
      }
    }
    
    d = new JComboBox(sources.toArray());
    
    d.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
          if(populated){
            System.out.println(((JComboBox) e.getSource()).getSelectedItem()+" at "+row+","+colum);
            source[row][colum] = ((JComboBox) e.getSource()).getSelectedItem().toString();
            if(samples[row][colum] != null)
              if(samples[row][colum].getStatus().equals(Status.PLAYING))
                samples[row][colum].stop();
            samples[row][colum] = new MediaPlayer(new Media(new File(new File("").getAbsolutePath().concat("/src/padmusic/assets/")+source[row][colum]).toURI().toString()));
            samples[row][colum].setCycleCount(MediaPlayer.INDEFINITE);
            samples[row][colum].play();
            samples[row][colum].pause();
            //while(samples[row][colum].getStopTime().toString().equals("UNKNOWN")){
                //System.out.println("Trying");
            //}
            //samples[row][colum].setStopTime(Duration.millis(samples[row][colum].getStopTime().toMillis()-stopCut));
            //samples[row][colum].setStopTime(Duration.millis(bpmTime));
            System.out.println(samples[row][colum].getStopTime());
            d.setVisible(false);
            populated = false;
          }
      }
    });
    
    super.setOpaque(true);
    super.setPreferredSize(new Dimension(w, h));
    super.setBackground(new Color(225, 225, 225));
    super.setLayout(null);
    d.setBounds(300, 10, 280, 40);
    d.setVisible(false);
    super.add(d);
    d.setFocusable(false);
    d.setFont(new Font("TimesRoman", Font.PLAIN, 10));
    addKeyListener(this);
    addMouseListener(this);
    super.setFocusable(true);
    for(int i = 0; i < source.length; i++){
      for(int ii = 0; ii < source[0].length; ii++){
        if(!source[i][ii].equals("")){
            samples[i][ii] = new MediaPlayer(new Media(new File(new File("").getAbsolutePath().concat("/src/padmusic/assets/")+source[i][ii]).toURI().toString()));
            //samples[i][ii].setCycleCount(MediaPlayer.INDEFINITE);
            samples[i][ii].play();
            samples[i][ii].pause();
            while(samples[i][ii].getStopTime().toString().equals("UNKNOWN")){
                //System.out.println("Trying");
            }
            //System.out.println(samples[i][ii].getStopTime());
            //samples[i][ii].setStopTime(Duration.millis(samples[i][ii].getStopTime().toMillis()-stopCut));
            //samples[i][ii].setStopTime(Duration.millis(400));
        }
        else
            samples[i][ii] = null;
        keyPressed[i][ii] = false;
      }
    }
  }
  
  // Perform any custom painting (if necessary) in this method
  @Override  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    for(int i = 0; i < samples.length; i++){
        for(int ii = 0; ii < samples[0].length; ii++){
            if(samples[i][ii] != null){
                if(samples[i][ii].getStatus().equals(Status.PAUSED) || samples[i][ii].getStatus().equals(Status.READY))
                    g.setColor(Color.ORANGE);
                else if(samples[i][ii].getStatus().equals(Status.PLAYING))
                    g.setColor(Color.GREEN);
                g.fillRect(ii*(width+2), i*(width+2), width, width);
                Duration x = samples[i][ii].getCurrentTime();
                Duration s = samples[i][ii].getStopTime();
//                System.out.println(x.toString()+" , "+s.toString());
                double r = 0;
                double c = 0;
                if(!s.toString().equals("UNKNOWN"))
                    r = Double.parseDouble(s.toString().replace(" ms", ""));
                if(!x.toString().equals("UNKNOWN"))
                    c = Double.parseDouble(x.toString().replace(" ms", ""));
//                System.out.println((int)((d/r)*40));
                g.setColor(Color.BLACK);
                g.drawLine(ii*(width+2)+4, i*(width+2)+(width/2-4), ii*(width+2)+4, i*(width+2)+(width/2+4));
                g.drawLine(ii*(width+2)+(width-4), i*(width+2)+(width/2-4), ii*(width+2)+(width-4), i*(width+2)+(width/2+4));
                g.drawLine(ii*(width+2)+4, i*(width+2)+(width/2), ii*(width+2)+4+(int)((c/r)*(width-8)), i*(width+2)+(width/2));
            }
            else{
                g.setColor(Color.GRAY);
                g.fillRect(ii*(width+2), i*(width+2), width, width);
            }
        }
    }
  }
  
  //<editor-fold defaultstate="collapsed" desc="--This method will launch your application--">
  public static void main(String[] args)
  {
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame fr = new JFrame("Application: PadMusic");
    fr.setContentPane(new PadMusic(780, 320, fr));
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.setLocation(10, 10);
    fr.setResizable(false);
    fr.pack();
    fr.setVisible(true);  
    do {
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException interruptedexception) {
//            System.out.println(interruptedexception.getMessage());
//        }
        fr.repaint();
    } while (true);
  }
  //</editor-fold>  

  @Override
  public void keyTyped(KeyEvent e)
  {
    super.repaint();
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    System.out.println(e.getKeyCode());
    int ke = e.getKeyCode();
    for(int i = 0; i < keyArray.length; i++){
      for(int ii = 0; ii < keyArray[0].length; ii++){
        if(keyArray[i][ii] == ke && !keyPressed[i][ii] && samples[i][ii] != null){
//          System.out.println(samples[i][ii].getStatus());
          if(samples[i][ii].getStatus().equals(Status.READY) || samples[i][ii].getStatus().equals(Status.PAUSED)){
//            System.out.println("Play");
            samples[i][ii].play();
            keyPressed[i][ii] = true;
          }
          else{
//            System.out.println("Zero");
            samples[i][ii].seek(Duration.ZERO);
            keyPressed[i][ii] = true;
          }
        }
        if(keyArray[i][ii] == ke && samples[i][ii] == null){
            for(int h = 0; h < samples.length; h++){
                if(samples[h][ii] != null){
//                    System.out.println("StopAll");
                    if(samples[h][ii].getStatus().equals(Status.PLAYING));
                        samples[h][ii].pause();
                    samples[h][ii].seek(Duration.ZERO);
                    keyPressed[h][ii] = false;
                }
            }
            for(int h = 0; h < samples[i].length; h++){
                if(samples[i][h] != null){
//                    System.out.println("StopAll");
                    if(samples[i][h].getStatus().equals(Status.PLAYING));
                        samples[i][h].pause();
                    samples[i][h].seek(Duration.ZERO);
                    keyPressed[i][h] = false;
                }
            }
        }
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    int ke = e.getKeyCode();
    for(int i = 0; i < keyArray.length; i++){
      for(int ii = 0; ii < keyArray[0].length; ii++){
        if(keyArray[i][ii] == ke)
          keyPressed[i][ii] = false;
      }
    }
  }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Click");
        int x = e.getX();
        int y = e.getY();
        row = y/(width+2);
        colum = x/(width+2);
        if(populated)
            populated = false;
        d.setVisible(true);
        d.setBounds(x-20, y-20, 280, 40);
        for(int i = 0; i < d.getItemCount(); i++){
            //System.out.println(d.getItemAt(i).toString().equals(source[row][colum]));
            if(d.getItemAt(i).toString().equals(source[row][colum])){
                d.setSelectedIndex(i);
            }
        }
        populated = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
