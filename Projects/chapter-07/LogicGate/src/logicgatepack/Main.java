package logicgatepack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel implements MouseListener, MouseMotionListener{
    
    int wi = 1000;
    int he = 600;
    
    int gIDAt = 0;
    int sIDAt = 0;
    int lIDAt = 0;
    
    ArrayList<Gate> gates;
    ArrayList<Source> sources;
    ArrayList<LED> leds;
    ClickableButton[] gateButtons;
    ClickableButton[] clickButtons;
    
    ClickableButton soButton;
    ClickableButton leButton;
    ClickableButton roButton;
    
    int gatesSolved = 0;
    
    //0: point, 1: line, 2: move;
    int clickMode = 0;
    
    int gatesIndexPressed = -1;
    boolean gatesDragged = false;
    boolean finishedConnector = false;
    boolean startedConnector = false;
    String conInputID1 = "";
    String conInputID2 = "";
    String conOutputID = "";
    
    int ledsIndexPressed = -1;
    boolean ledsDragged = false;
    
    int sourcesIndexPressed = -1;
    boolean sourcesDragged = false;
    
    int xPlace = 0;
    int yPlace = 0;
    
    int prevDragX = -1;
    int prevDragY = -1;
    int xDragDiff = 0;
    int yDragDiff = 0;
    
    ArrayList<Connector> connectors;
    
    public Main(JFrame f){
        super.setOpaque(true);
        super.setPreferredSize(new Dimension(wi, he));
        super.setBackground(new Color(255, 255, 255));
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        gateButtons = new ClickableButton[4];
        clickButtons = new ClickableButton[3];
        
        try {
            gateButtons[0] = new ClickableButton(ImageIO.read(Main.class.getResource("/res/not.jpg")), wi-70, 20, 50, 50);
            gateButtons[1] = new ClickableButton(ImageIO.read(Main.class.getResource("/res/and.jpg")), wi-70, 90, 50, 50);
            gateButtons[2] = new ClickableButton(ImageIO.read(Main.class.getResource("/res/or.jpg")), wi-70, 160, 50, 50);
            gateButtons[3] = new ClickableButton(ImageIO.read(Main.class.getResource("/res/xor.png")), wi-70, 230, 50, 50);
            soButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/sourceoff.jpg")), wi-70, 300, 50, 50);
            leButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/ledoff.jpg")), wi-70, 370, 50, 50);
            clickButtons[0] = new ClickableButton(ImageIO.read(Main.class.getResource("/res/point.jpg")), 20, he-40, 20, 20);
            clickButtons[1] = new ClickableButton(ImageIO.read(Main.class.getResource("/res/connect.jpg")), 70, he-40, 20, 20);
            clickButtons[2] = new ClickableButton(ImageIO.read(Main.class.getResource("/res/move.jpg")), 120, he-40, 20, 20);
            roButton = new ClickableButton(ImageIO.read(Main.class.getResource("/res/remove.jpg")), wi-70, he-70, 50, 50);
        } catch (IOException ex) {System.out.println(ex);}
        
        gates = new ArrayList<>();
        sources = new ArrayList<>();
        leds = new ArrayList<>();
        connectors = new ArrayList<>();
    }
    
    @Override  
    public void paintComponent(Graphics g){
        
        long startTime = System.nanoTime();
        
        super.paintComponent(g);
        
        for(int i = 0; i < connectors.size(); i++)
            connectors.get(i).drawConnector(g);
        for(int i = 0; i < gates.size(); i++)
            gates.get(i).gButton.drawButton(g, this);
        for(int i = 0; i < leds.size(); i++)
            leds.get(i).lButton.drawButton(g, this);
        for(int i = 0; i < sources.size(); i++)
            sources.get(i).sButton.drawButton(g, this);
        
        
        for(int i = 0; i < gateButtons.length; i++)
            gateButtons[i].drawButton(g, this);
        for(int i = 0; i < clickButtons.length; i++)
            clickButtons[i].drawButton(g, this);
        soButton.drawButton(g, this);
        leButton.drawButton(g, this);
        roButton.drawButton(g, this);
        g.setColor(new Color(100,100,100,100)); 
        g.fillRect(20+50*clickMode, 560, 20, 20);
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        //System.out.println(duration);
        
        try {
            if(duration < 20)
              Thread.sleep(20-duration);
        } catch (InterruptedException ex) {System.out.println(ex);}
        
        super.repaint();
    }
    
    public void solve(){
        gatesSolved = 0;
        
        //solve gates
        while(gatesSolved < gates.size()){
            for(int i = 0; i < gates.size(); i++){
                if((gates.get(i).gInputID1.equals("") && gates.get(i).gInputID2.equals("") && gates.get(i).gType == 0) || ((gates.get(i).gInputID1.equals("") || gates.get(i).gInputID2.equals("")) && gates.get(i).gType != 0)){
                    gatesSolved++;
                    continue;
                }
                for(int k = 0; k < sources.size(); k++)
                    checkIDAtGate(sources.get(k).sID, sources.get(k).sVal, i);
                for(int f = 0; f < gates.size(); f++)
                    checkIDAtGate(gates.get(f).gID, gates.get(f).gOutVal, i);
            }
        }
        
        //set leds to proper vals
        for(int i = 0; i < leds.size(); i++){
            for(int k = 0; k < sources.size(); k++)
                checkIDAtLed(sources.get(k).sID, sources.get(k).sVal, i);
            for(int f = 0; f < gates.size(); f++)
                checkIDAtLed(gates.get(f).gID, gates.get(f).gOutVal, i);
        }
    }
    
    public void checkIDAtLed(String id, int val, int i){
        if(leds.get(i).lInputID.equals(id)){
            leds.get(i).setInputVal(val);
            leds.get(i).setState();
        }
    }
    
    public void checkIDAtGate(String id, int val, int i){
        if(gates.get(i).gInputID1.equals(id)){
            gates.get(i).setInput1Val(val);
            boolean success = gates.get(i).setOutput();
            if(success)
                gatesSolved++;
        }
        if(gates.get(i).gInputID2.equals(id)){
            gates.get(i).setInput2Val(val);
            boolean success = gates.get(i).setOutput();
            if(success)
                gatesSolved++;
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        for(int i = 0; i < clickButtons.length; i++){
            if(clickButtons[i].clickedButton(e.getX(), e.getY())){
                clickMode = i;
                break;
            }
        }
        if(clickMode == 0){
            for(int i = 0; i < gateButtons.length; i++){
                if(gateButtons[i].clickedButton(e.getX(), e.getY())){
                    gates.add(newGate(i));
                    gates.get(gates.size()-1).setPosition(e.getX(), e.getY());
                    gatesIndexPressed = gates.size()-1;
                    break;
                }
            }
            if(leButton.clickedButton(e.getX(), e.getY())){
                leds.add(newLed());
                leds.get(leds.size()-1).setPosition(e.getX(), e.getY());
                ledsIndexPressed = leds.size()-1;
            }
            else if(soButton.clickedButton(e.getX(), e.getY())){
                sources.add(newSource());
                sources.get(sources.size()-1).setPosition(e.getX(), e.getY());
                sourcesIndexPressed = sources.size()-1;
            }
            else{
                for(int i = 0; i < gates.size(); i++){
                    if(gates.get(i).gButton.clickedButton(e.getX(), e.getY()))
                        gatesIndexPressed = i;
                }
                for(int i = 0; i < leds.size(); i++){
                    if(leds.get(i).lButton.clickedButton(e.getX(), e.getY()))
                        ledsIndexPressed = i;
                }
                for(int i = 0; i < sources.size(); i++){
                    if(sources.get(i).sButton.clickedButton(e.getX(), e.getY()))
                        sourcesIndexPressed = i;
                }
            }
        }
        
        else if(clickMode == 1){
            if(roButton.clickedButton(e.getX(), e.getY()) && startedConnector){
                connectors.remove(connectors.size()-1);
                closeConnector();
            }
            for(int i = 0; i < gates.size(); i++){
                if(gates.get(i).gButton.clickedButton(e.getX(), e.getY())){
                    if(startedConnector){     
                        setXYConnectorOffest(e.getX(), e.getY(), i);
                        
                        if(!conInputID1.equals(conOutputID) && !conInputID2.equals(conOutputID) && !conOutputID.isEmpty()){
                            connectors.get(connectors.size()-1).placeEnd(gates.get(i).gXPos, gates.get(i).gYPos, gates.get(i).gID, xPlace, yPlace);
                            finishedConnector = true;
                        }
                    }
                    else{
                        gatesIndexPressed = i;
                        
                        setXYConnectorOffest(e.getX(), e.getY(), i);
                        
                        startedConnector = true;
                        connectors.add(new Connector(gates.get(i).gXPos, gates.get(i).gYPos,gates.get(i).gID, xPlace, yPlace));
                    }
                }
            }
            for(int i = 0; i < leds.size(); i++){
                if(leds.get(i).lButton.clickedButton(e.getX(), e.getY())){
                    xPlace = 25;
                    yPlace = 50;
                    if(startedConnector){
                        connectors.get(connectors.size()-1).placeEnd(leds.get(i).lXPos, leds.get(i).lYPos, leds.get(i).lID, xPlace, yPlace);
                        finishedConnector = true;
                        conInputID1 = leds.get(i).lID;
                    }
                    else{
                      ledsIndexPressed = i; 
                      startedConnector = true;
                      conInputID1 = leds.get(i).lID;
                      connectors.add(new Connector(leds.get(i).lXPos, leds.get(i).lYPos,leds.get(i).lID, xPlace, yPlace));
                    }
                }
            }
            for(int i = 0; i < sources.size(); i++){
                if(sources.get(i).sButton.clickedButton(e.getX(), e.getY())){
                    xPlace = 50;
                    yPlace = 25;
                    if(startedConnector){
                        connectors.get(connectors.size()-1).placeEnd(sources.get(i).sXPos, sources.get(i).sYPos, sources.get(i).sID, xPlace, yPlace);
                        finishedConnector = true;
                        conOutputID = sources.get(i).sID;
                    }
                    else{
                      sourcesIndexPressed = i; 
                      startedConnector = true;
                      conOutputID = sources.get(i).sID;
                      connectors.add(new Connector(sources.get(i).sXPos, sources.get(i).sYPos,sources.get(i).sID, xPlace, yPlace));
                    }
                }
            }
        }
    }
    
    public void setXYConnectorOffest(int x, int y, int i){
        if(gates.get(i).gType == 0){
            if(x-gates.get(i).gXPos <= 25){
                conInputID1 = gates.get(i).gID;
                xPlace = 0;
            }
            else{
                conOutputID = gates.get(i).gID;
                xPlace = 48;
            }
            yPlace = 28;
        }

        if(gates.get(i).gType >= 1){
            if(x-gates.get(i).gXPos <= 25){
                if(y-gates.get(i).gYPos <= 25){
                    conInputID1 = gates.get(i).gID;
                    yPlace = 10;
                }
                else{
                    conInputID2 = gates.get(i).gID;
                    yPlace = 40;
                }
                xPlace = 0;
            }
            else{
                conOutputID = gates.get(i).gID;
                xPlace = 48;
                yPlace = 25;
            }
        }
    }
    
    public void setConnections(){
        int inputGateIndex = -1;
        int inputLEDIndex = -1;
        
        for(int i = 0; i < gates.size(); i++){
            if(gates.get(i).gID.equals(conInputID1) || gates.get(i).gID.equals(conInputID2))
                inputGateIndex = i;
        }
        for(int i = 0; i < leds.size(); i++){
            if(leds.get(i).lID.equals(conInputID1))
                inputLEDIndex = i;
        }
        if(inputGateIndex != -1){
            if(conInputID2.equals("")){
                gates.get(inputGateIndex).setInputID1(conOutputID);
                System.out.println("Setting "+conInputID1+"'s 1st input to "+conOutputID+"'s output");
            }
            else{
                gates.get(inputGateIndex).setInputID2(conOutputID);
                System.out.println("Setting "+conInputID2+"'s 2nd input to "+conOutputID+"'s output");
            }
        }
        if(inputLEDIndex != -1){
            leds.get(inputLEDIndex).setInputId(conOutputID);
            System.out.println("Setting "+conInputID1+"'s input to "+conOutputID+"'s output");
        }
        solve();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(clickMode == 0){
            if(gatesDragged){
                if(roButton.clickedButton(e.getX(), e.getY())){
                    removeObject(gates.get(gatesIndexPressed).gID);
                    gates.remove(gatesIndexPressed);
                    solve();
                }
                gatesDragged = false;
                gatesIndexPressed = -1;
            }
            else if(ledsDragged){
                if(roButton.clickedButton(e.getX(), e.getY())){
                    removeObject(leds.get(ledsIndexPressed).lID);
                    leds.remove(ledsIndexPressed);
                    solve();
                }
                ledsDragged = false;
                ledsIndexPressed = -1;
            }
            else if(sourcesDragged){
                if(roButton.clickedButton(e.getX(), e.getY())){
                    removeObject(sources.get(sourcesIndexPressed).sID);
                    sources.remove(sourcesIndexPressed);
                    solve();
                }
                sourcesDragged = false;
                sourcesIndexPressed = -1;
            }
            else if(!sourcesDragged && sourcesIndexPressed != -1){
                sources.get(sourcesIndexPressed).toggleVal();
                solve();
                sourcesIndexPressed = -1;
            }
        }
        else if(clickMode == 1){
            if(!startedConnector){
                gatesIndexPressed = -1;
                ledsIndexPressed = -1;
                sourcesIndexPressed = -1;
            }
            if(finishedConnector){
                setConnections();
                closeConnector();
            }
            xPlace = 0;
            yPlace = 0;
        }
        else if(clickMode == 2){
            prevDragX = -1;
            prevDragY = -1;
        }
    }
    
    public void closeConnector(){
        gatesIndexPressed = -1;
        ledsIndexPressed = -1;
        sourcesIndexPressed = -1;
        startedConnector = false;
        finishedConnector = false;
        conInputID1 = "";
        conInputID2 = "";
        conOutputID = "";
    }
    
    public void removeObject(String objId){
        for(int i = 0; i < connectors.size(); i++){
            if(connectors.get(i).cID1.equals(objId)){
                connectors.remove(i);
                i--;
            }
            else if(connectors.get(i).cID2.equals(objId)){
                connectors.remove(i);
                i--;
            }
        }
        for(int i = 0; i < gates.size(); i ++){
            if(gates.get(i).gInputID1.equals(objId)){
                gates.get(i).setInputID1("");
                gates.get(i).setInput1Val(-1);
                gates.get(i).setOutput();
            }
            if(gates.get(i).gInputID2.equals(objId)){
                gates.get(i).setInputID2("");
                gates.get(i).setInput2Val(-1);
                gates.get(i).setOutput();
            }
        }
        for(int i = 0; i < leds.size(); i ++){
            if(leds.get(i).lInputID.equals(objId)){
                leds.get(i).setInputId("");
                leds.get(i).setInputVal(0);
                leds.get(i).setState();
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        if(startedConnector)
            connectors.get(connectors.size()-1).moveEnd(e.getX(), e.getY());
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if(clickMode == 0){
            if(gatesIndexPressed != -1){
                gates.get(gatesIndexPressed).setPosition(e.getX(), e.getY());
                moveConnector(gates.get(gatesIndexPressed).gID, gates.get(gatesIndexPressed).gXPos, gates.get(gatesIndexPressed).gYPos);
                gatesDragged = true;
            }   
            else if(ledsIndexPressed != -1){
                leds.get(ledsIndexPressed).setPosition(e.getX(), e.getY());
                moveConnector(leds.get(ledsIndexPressed).lID, leds.get(ledsIndexPressed).lXPos, leds.get(ledsIndexPressed).lYPos);
                ledsDragged = true;
            }
            else if(sourcesIndexPressed != -1){
                sources.get(sourcesIndexPressed).setPosition(e.getX(), e.getY());
                moveConnector(sources.get(sourcesIndexPressed).sID, sources.get(sourcesIndexPressed).sXPos, sources.get(sourcesIndexPressed).sYPos);
                sourcesDragged = true;
            }
        }
        if(clickMode == 2){
            if(prevDragX == -1){
                prevDragX = e.getX();
                prevDragY = e.getY();
            }
            xDragDiff = e.getX() - prevDragX;
            yDragDiff = e.getY() - prevDragY;
            for(int i = 0; i < gates.size(); i++)
                gates.get(i).changePositions(xDragDiff, yDragDiff);
            for(int i = 0; i < leds.size(); i++)
                leds.get(i).changePositions(xDragDiff, yDragDiff);
            for(int i = 0; i < sources.size(); i++)
                sources.get(i).changePositions(xDragDiff, yDragDiff);
            for(int i = 0; i < connectors.size(); i++)
                connectors.get(i).changePosition(xDragDiff, yDragDiff);
            prevDragX = e.getX();
            prevDragY = e.getY();
        }
    }
    
    public void moveConnector(String id, int x, int y){
        for(int i = 0; i < connectors.size(); i++){
            if(connectors.get(i).cID1.equals(id))
                connectors.get(i).moveStart(x, y);
            if(connectors.get(i).cID2.equals(id))
                connectors.get(i).moveEnd(x,y);
        }
    }
    
    public static void main(String[] args){
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame fr = new JFrame("Logic Gate Simulator");
        Main m = new Main(fr);
        fr.setContentPane(m);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(10, 10);
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true); 
    }
    
    public Gate newGate(int t){
        Gate temp = new Gate(t,gIDAt);
        gIDAt++;
        return temp;
    }
    
    public Source newSource(){
        Source temp = new Source(sIDAt);
        sIDAt++;
        return temp;
    } 
    
    public LED newLed(){
        LED temp = new LED(lIDAt);
        lIDAt++;
        return temp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}