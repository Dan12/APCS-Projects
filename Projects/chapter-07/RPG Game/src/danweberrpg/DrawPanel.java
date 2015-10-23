//package src.danweberrpg;
package danweberrpg;

//Runnable JPanel Class

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DrawPanel extends JPanel implements Runnable,KeyListener{
        
    private boolean lAC = false;
    private boolean rAC = false;
    private boolean uAC = false;
    private boolean dAC = false;
    private boolean zKP = false;
    private boolean xKP = false;
    private boolean fKP = false;
    private boolean vKP = false;
    
    private int limbControl = 0;
    private int limbRotationSpeed = 5;
    private int limbStretchSpeed = 5;
    
    public TiledMap map;
    
    public ArrayList<StickFigure> stickfigures;
    
    private ArrayList<StickFigure> AIs;
    
    public StickFigure st;
    
    private long duration = 0;
    
    public boolean settingSharedObject = false;
    public boolean drawingSharedObject = false;
    
    public String clientName;
    
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    JScrollPane scrollArea;

    public DrawPanel(int w, int h){
        super.setOpaque(true);
        super.setPreferredSize(new Dimension(w, h));
        super.setBackground(new Color(225, 225, 225));
        addKeyListener(this);
        super.setFocusable(true);
        super.setLayout(null);
        
        textField.setEditable(false);
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        // Add Listeners
        textField.addActionListener(new ActionListener() {
            //textfield enter key pressed
            public void actionPerformed(ActionEvent e) {
                Client.clientOut.println(textField.getText());
                System.out.println(textField.getText());
                textField.setText("");
                DrawPanel.this.requestFocusInWindow();
            }
        });
        
        super.addMouseListener(new MouseListener()
        {
            @Override
            public void mousePressed(MouseEvent e){
                DrawPanel.this.requestFocusInWindow();
            }
            
            @Override
            public void mouseClicked(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e){}
        });
        
        scrollArea = new JScrollPane(messageArea);
        scrollArea.setFocusable(false);
        textField.setBounds(Main.screenWidth, 0, Main.screenPlusMessage-Main.screenWidth, 30);
        scrollArea.setBounds(Main.screenWidth+5, 30, Main.screenPlusMessage-Main.screenWidth-10, Main.screenHeight-30);
        
        super.add(scrollArea);
        super.add(textField);
        
        String filePath = new File("").getAbsolutePath();
        //map = new TiledMap(filePath.concat("/src/src/res"), "desert-tiled.txt");
        map = new TiledMap(filePath.concat("/src/res"), "desert-tiled.txt");

        st = new StickFigure(200, 200, true, "-");
        stickfigures = new ArrayList<StickFigure>();

        AIs = new ArrayList<StickFigure>();
        StickFigure temp = new StickFigure(1000, 400, false, "AI 1");
        //1-up,2-right,3-left,4-down
        temp.setPath(new int[]{2,4,3,1}, new int[]{3,3,3,3});
        AIs.add(temp);
    }


    @Override
    public void run(){
        while(true){
            try {
              if(duration < 20)
                  Thread.sleep(20-duration);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            long start = System.nanoTime();
            while(settingSharedObject){System.out.println("Waiting in run");}
            actions();
            super.repaint();
            long end = System.nanoTime();
            duration = ((end-start)/1000000);
//            System.out.println("Duration: "+duration);
        }
    }

    public void actions(){
        if(!st.toRest){
            if(lAC)
                st.movePosition = 2;
            if(rAC)
                st.movePosition = 3;
            if(uAC)
                st.movePosition = 1;
            if(dAC)
                st.movePosition = 4;

            if(!uAC && !lAC && !rAC && !dAC)
                st.stopped = true;
            else{
                st.atRest = false;
                st.stopped = false;
            }
        }

        st.runAnim();

        for(int i = 0; i < AIs.size(); i++)
            AIs.get(i).runAnim();

        if((st.xPos>=Main.screenWidth/2 && map.mapPixelWidth-map.offsetX>Main.screenWidth) || (st.xPos<=Main.screenWidth/2 && map.offsetX>0)){
            int mapOffXInit = map.offsetX;
            map.changeOffset(st.xPos-Main.screenWidth/2, 0);
            st.setPosistion(Main.screenWidth/2, st.yPos);   
            for(int i = 0; i < AIs.size(); i++)
                AIs.get(i).changePosition(mapOffXInit-map.offsetX, 0);
        }   
        if((st.yPos>=Main.screenHeight/2 && map.mapPixelHeight-map.offsetY>Main.screenHeight) || (st.yPos<=Main.screenHeight/2 && map.offsetY>0)){
            int mapOffYInit = map.offsetY;
            map.changeOffset(0, st.yPos-Main.screenHeight/2);
            st.setPosistion(st.xPos, Main.screenHeight/2);   
            for(int i = 0; i < AIs.size(); i++)
                AIs.get(i).changePosition(0, mapOffYInit-map.offsetY);
        }

        st.setActualPosition(st.xPos+map.offsetX, st.yPos+map.offsetY);
        for(int i = 0; i < AIs.size(); i++)
                AIs.get(i).setActualPosition(AIs.get(i).xPos, AIs.get(i).yPos);

        if(zKP)
            st.changeAngle(-limbRotationSpeed, limbControl);
        if(xKP)
            st.changeAngle(limbRotationSpeed, limbControl);
        if(fKP)
            st.changeLength(limbStretchSpeed, limbControl);
        if(vKP)
            st.changeLength(-limbStretchSpeed, limbControl);
    }

    // Perform any custom painting (if necessary) in this method
    @Override  
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      g.setColor(Color.BLACK);
      map.drawMap(g);

      while(settingSharedObject){System.out.println("Waiting in Paint");}
      drawingSharedObject = true;
      for(int i = 0; i < stickfigures.size(); i++){
          if(!stickfigures.get(i).figureName.equals(clientName))
              stickfigures.get(i).drawFigure(g);
      }
      drawingSharedObject = false;

      for(int i = 0; i < AIs.size(); i++){
          AIs.get(i).drawFigure(g);
      }

      st.drawFigure(g);
      
      g.setColor(Color.BLACK);
      g.fillRect(Main.screenWidth, 0, Main.screenPlusMessage-Main.screenWidth, Main.screenHeight);

      if(clientName != null)
        Client.clientOut.println("SQUARE "+st.getInfoString(map.offsetX, map.offsetY));
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == 39)
            lAC = true;
        if(e.getKeyCode() == 37)
            rAC = true;
        if(e.getKeyCode() == 38)
            uAC = true;
        if(e.getKeyCode() == 40)
            dAC = true;
        if(e.getKeyCode() > 47 && e.getKeyCode() < 58)
            limbControl = e.getKeyCode()-48;
        if(e.getKeyCode() == 79)
            limbControl = 10;
        if(e.getKeyCode() == 88)
            xKP = true;
        if(e.getKeyCode() == 90)
            zKP = true;
        if(e.getKeyCode() == 70)
            fKP = true;
        if(e.getKeyCode() == 86)
            vKP = true;
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == 39)
            lAC = false;
        if(e.getKeyCode() == 37)
            rAC = false;
        if(e.getKeyCode() == 38)
            uAC = false;
        if(e.getKeyCode() == 40)
            dAC = false;
        if(e.getKeyCode() == 88)
            xKP = false;
        if(e.getKeyCode() == 90)
            zKP = false;
        if(e.getKeyCode() == 70)
            fKP = false;
        if(e.getKeyCode() == 86)
            vKP = false;
    }
}
