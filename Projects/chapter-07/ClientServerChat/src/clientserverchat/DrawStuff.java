package clientserverchat;

//Runnable JPanel Class

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DrawStuff extends JPanel implements Runnable,KeyListener{

    public JTextField enterField = new JTextField(40);
    public JTextArea messageDisplayArea = new JTextArea(8, 40);
    private JScrollPane scrollView;
    
    public static int squareSize = 30;
    private long duration = 0;
    private int xPos = 340;
    private int yPos = 50;
    private boolean uA = false;
    private boolean dA = false;
    private boolean lA = false;
    private boolean rA = false;
    private final int speed = 5;
    Font font;
    
    public int screenWidth = 800;
    public int screenHeight = 800;
    public int screenWidthPlus = 1100;

    public DrawStuff(){
        //Layout gui
        super.setPreferredSize(new Dimension(screenWidthPlus,screenHeight));
        super.setBackground(Color.GRAY);
        super.setFocusable(true);
        super.setLayout(null);
        scrollView = new JScrollPane(messageDisplayArea);
        enterField.setEditable(false);
        messageDisplayArea.setEditable(false);
        enterField.setBounds(screenWidth+5, 0, screenWidthPlus-screenWidth-10, 30);
        scrollView.setBounds(screenWidth+5, 30, screenWidthPlus-screenWidth-10, screenHeight);
        super.add(enterField);
        super.add(scrollView);
        addKeyListener(this);
        
        enterField.addActionListener(new ActionListener() {
            //textfield enter key pressed
            public void actionPerformed(ActionEvent e) {
                Client.clientOut.println(enterField.getText());
                System.out.println(enterField.getText());
                enterField.setText("");
            }
        });
        
        font = new Font("Consolas",Font.PLAIN, squareSize);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //change position of squares. MODIFY THIS
        actionManager();
        
        //waits until client is done setting squares
        while(Client.settingSquares){System.out.println("Waiting");}
        Client.drawingSquares = true;
        //draws all of the squares gotten by the server except for your square
        for(int i = 0; i < Client.squares.size(); i++){
            if(!Client.squares.get(i).squareName.equals(Client.name)){
                Client.squares.get(i).drawSquare(g);
            }
        }
        Client.drawingSquares = false;

        //draw everything. MODIFY THIS
        g.setColor(Color.BLACK);
        g.fillRect(xPos, yPos, squareSize, squareSize);
        g.fillRect(screenWidth, 0, screenWidthPlus-screenWidth, screenHeight+100);
        g.fillRect(0, screenHeight, screenWidth+100, screenHeight+100);

        //Draws you name above square
        g.setColor(Color.WHITE);  
        if(Client.name != null){
            g.drawString(Client.name, xPos+squareSize/2-((int) g.getFontMetrics().getStringBounds(Client.name, g).getWidth()/2), yPos-squareSize/2);
            //sends data to server
            Client.clientOut.println("SQUARE "+this.getStringInfo());
        }
    }

    //fps manager
    @Override
    public void run() {
        while(true){
            try {
                if(duration < 20)
                Thread.sleep(20-duration);
            } catch (InterruptedException e) {System.out.println(e);}
            long start = System.nanoTime();
            while(Client.settingSquares){System.out.println("Waiting");}
            repaint();
            long end = System.nanoTime();
            duration = (end-start)/1000000;
//                System.out.println("Duration: "+duration);
        }
    }
    
    public void actionManager(){
        //handle keyinputs
        if(lA && xPos<screenWidth-squareSize)
            xPos+=speed;
        if(rA && xPos>0)
            xPos-=speed;
        if(dA && yPos<screenHeight-squareSize)
            yPos+=speed;
        if(uA && yPos>0)
            yPos-=speed;
        if(xPos > screenWidth-squareSize)
            xPos = screenWidth-squareSize;
        if(xPos < 0)
            xPos = 0;
        if(yPos > screenHeight-squareSize)
            yPos = screenHeight-squareSize;
        if(yPos < 0)
            yPos = 0;
    }

    //handle keyinputs
    @Override
    public void keyPressed(KeyEvent e) {
        if(enterField.isEditable()){
            if(e.getKeyCode() == 39)
                lA = true;
            if(e.getKeyCode() == 37)
                rA = true;
            if(e.getKeyCode() == 38)
                uA = true;
            if(e.getKeyCode() == 40)
                dA = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 39)
            lA = false;
        if(e.getKeyCode() == 37)
            rA = false;
        if(e.getKeyCode() == 38)
            uA = false;
        if(e.getKeyCode() == 40)
            dA = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    //this will be the data sent to server
    public String getStringInfo(){
        return Client.name+","+xPos+","+yPos;
    }
}

