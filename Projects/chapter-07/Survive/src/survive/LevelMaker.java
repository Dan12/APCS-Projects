package survive;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LevelMaker{
    private int componentWidth = 100;
    private int componentHeight = 50;
    private int componentPaddingY = 25;
    private int componentPaddingX = 50;
    private int windowOffsetX = 200;
    private int windowOffsetY = 100;
    private int toolXPadding = 10;
    
    private String fileParent = "";
    private String fileName = "";
    
    private Map map;
    
    private int tool = 4;
    
    private boolean lkP,rkP,ukP,dkP;
    private int moveSpeed = 5;
    
    //0-add square,1-add spike,2-remove square,3-remove spike
    private ArrayList<Button> toolButtons;
    //0-load,1-new,2-save
    private ArrayList<Button> fileButtons;
    
    private ArrayList<ArrayList<LabeledTextField>> textFields;
    
    private MiniMap miniMap;
    
    private LineCheck lineCheck;
    
    private Square tempSquare;
    private Spike tempSpike;
    
    private DrawPanel dp;
    
    //Constructor, pass drawpanel for text field context
    public LevelMaker(DrawPanel dp){  
        fileButtons = new ArrayList<Button>();
        fileButtons.add(new Button(componentPaddingX, Main.SCREEN_HEIGHT+componentPaddingY-windowOffsetY, componentWidth, componentHeight,"Load",-1));
        fileButtons.add(new Button((componentPaddingX+componentWidth)*2-componentWidth, Main.SCREEN_HEIGHT+componentPaddingY-windowOffsetY, componentWidth, componentHeight,"New",-1));
        fileButtons.add(new Button((componentPaddingX+componentWidth)*3-componentWidth, Main.SCREEN_HEIGHT+componentPaddingY-windowOffsetY, componentWidth, componentHeight,"Save",-1));
        fileButtons.add(new Button((componentPaddingX+componentWidth)*4-componentWidth, Main.SCREEN_HEIGHT+componentPaddingY-windowOffsetY, componentWidth, componentHeight,"Exit",-1));
             
        toolButtons = new ArrayList<Button>();
        toolButtons.add(new Button(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY, windowOffsetX-toolXPadding*2, componentHeight/2, "Add Square",16));
        toolButtons.add(new Button(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*2+componentHeight/2, windowOffsetX-toolXPadding*2, componentHeight/2, "Add Spike",16));
        toolButtons.add(new Button(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*3+componentHeight, windowOffsetX-toolXPadding*2, componentHeight/2, "Remove Square",16));
        toolButtons.add(new Button(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*4+(componentHeight/2)*3, windowOffsetX-toolXPadding*2, componentHeight/2, "Remove Spike",16));
        toolButtons.add(new Button(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*5+componentHeight*2, windowOffsetX-toolXPadding*2, componentHeight/2, "Edit Map",16));
        toolButtons.add(new Button(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*6+(componentHeight/2)*5, windowOffsetX-toolXPadding*2, componentHeight/2, "Edit Square",16));
        toolButtons.add(new Button(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*7+componentHeight*3, windowOffsetX-toolXPadding*2, componentHeight/2, "Edit Spike",16));
        toolButtons.get(tool).selected();
        
        this.dp = dp;
        
        String filePath = new File("").getAbsolutePath();
        map = new Map(filePath.concat("/src/assets"), "default.txt", dp);
        map.showNumbers();
        
        fileParent = filePath.concat("/src/assets");
       
        textFields = new ArrayList<ArrayList<LabeledTextField>>(); 
        for(int i = 0; i < toolButtons.size(); i++)
            textFields.add(new ArrayList<LabeledTextField>());
        textFields.get(0).add(new LabeledTextField("Square Width", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, "100"));
        textFields.get(0).add(new LabeledTextField("Square Height", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX/2, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, "100"));
        textFields.get(1).add(new LabeledTextField("Spike Frequency", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, "1"));
        textFields.get(1).add(new LabeledTextField("Spike Spacing", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX/2, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, "0"));
        textFields.get(1).add(new LabeledTextField("Spike Start", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*7+componentHeight*6, dp, "1"));
        textFields.get(4).add(new LabeledTextField("Map Width", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, ""+map.getWidth()));
        textFields.get(4).add(new LabeledTextField("Map Height", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX/2, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, ""+map.getHeight()));
        textFields.get(4).add(new LabeledTextField("Start Square", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*7+componentHeight*6, dp, ""+map.startSquareIndex));
        textFields.get(4).add(new LabeledTextField("End Square", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX/2, windowOffsetX+componentPaddingY*7+componentHeight*6, dp, ""+map.endSquareIndex));
        textFields.get(5).add(new LabeledTextField("Square Width", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, "100"));
        textFields.get(5).add(new LabeledTextField("Square Height", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX/2, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, "100"));
        textFields.get(6).add(new LabeledTextField("Spike Frequency", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, "1"));
        textFields.get(6).add(new LabeledTextField("Spike Spacing", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX/2, windowOffsetX+componentPaddingY*6+componentHeight*5, dp, "0"));
        textFields.get(6).add(new LabeledTextField("Spike Start", Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, windowOffsetX+componentPaddingY*7+componentHeight*6, dp, "1"));
        
        miniMap = new MiniMap(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, toolXPadding, windowOffsetX-toolXPadding*2, windowOffsetX-toolXPadding*2, map, this);
    
        lineCheck = new LineCheck();
    }
    
    //Draw map, temo square and spike, buttons, and mini map
    public void drawScreen(Graphics g){
        
        map.drawMap(g, new float[Main.BAND_NUMS]);
          
        if(tempSquare != null)
            tempSquare.drawSquare(g, new float[Main.BAND_NUMS]);
        
        if(tempSpike != null)
            tempSpike.drawOnto(g, new float[Main.BAND_NUMS]);
        
        lineCheck.drawLine(g);
        
        g.setColor(new Color(255,255,255,100));
        g.fillRect(0, Main.SCREEN_HEIGHT-windowOffsetY, Main.SCREEN_WIDTH-windowOffsetX, windowOffsetY);
        g.fillRect(Main.SCREEN_WIDTH-windowOffsetX, 0, windowOffsetX, Main.SCREEN_HEIGHT);
        
        for(Button b : fileButtons)
            b.drawButton(g);
        for(Button b : toolButtons)
            b.drawButton(g);
        for(ArrayList<LabeledTextField> ls : textFields){
            for(LabeledTextField l : ls)
                l.drawLabel(g);
        }
        
        miniMap.drawMiniMap(g);
        
        g.setColor(Color.BLACK);
        g.drawLine(Main.SCREEN_WIDTH/2, Main.SCREEN_HEIGHT/2-10, Main.SCREEN_WIDTH/2, Main.SCREEN_HEIGHT/2+10);
        g.drawLine(Main.SCREEN_WIDTH/2-10, Main.SCREEN_HEIGHT/2, Main.SCREEN_WIDTH/2+10, Main.SCREEN_HEIGHT/2);
    }
    
    /*called when text field loses focus, 
    **makes sure text field contains a number,
    **sets map properties*/
    public void textFieldUpdated(LabeledTextField l){
        int outerIndex = 0;
        int innerIndex = 0;
        outerloop: for(int i = 0; i < textFields.size(); i++){
            for(int k = 0; k < textFields.get(i).size(); k++){
                if(textFields.get(i).get(k).equals(l)){
                    outerIndex = i;
                    innerIndex = k;
                    break outerloop;
                }
            }
        }
        
        updateTextFields(outerIndex, innerIndex);
    }
    
    private void updateTextFields(int outerIndex, int innerIndex){
        String text = textFields.get(outerIndex).get(innerIndex).getText();
        try{
            Integer.parseInt(text);
        }catch(NumberFormatException n){text = "0";}
        int number = Integer.parseInt(text);
        
        if(outerIndex == 0 || outerIndex == 5){
            if(tempSquare != null)
                tempSquare = new Square(tempSquare.getX(), tempSquare.getY(), Integer.parseInt(textFields.get(outerIndex).get(0).getText()), Integer.parseInt(textFields.get(outerIndex).get(1).getText()), tempSquare.getSpikeDetails(),false);
        }
        else if(outerIndex == 1 || outerIndex == 6){
            if(tempSpike != null)
                tempSpike = new Spike(tempSpike.getSquare(), tempSpike.getX(), tempSpike.getY(), tempSpike.getSide(), Integer.parseInt(textFields.get(outerIndex).get(1).getText()), Integer.parseInt(textFields.get(outerIndex).get(0).getText()));  
        }
        else if(outerIndex == 4){
            if(innerIndex == 0)
                map.setWidth(number);
            else if(innerIndex == 1)
                map.setHeight(number);
            else if(innerIndex == 2)
                map.startSquareIndex = number;
            else if(innerIndex == 3){
                map.endSquareIndex = number;
                if(map.endSquareIndex < map.getSquareList().size())
                    for(int i = 0; i < map.getSquareList().size(); i++){
                        if(i == map.endSquareIndex)
                            map.getSquareList().get(i).setAsFinish();
                        else
                            map.getSquareList().get(i).unsetAsFinish();
                    }
            }
            miniMap.resize();
        }
    }
    
    /*key update, called every repaint, moves map 
    **around more accuaratley than mini map*/
    public void updateKeys(){
        if(lkP && map.getOffsetX() > 0)
            map.changePosition(-moveSpeed, 0);
        if(rkP && map.getWidth()-map.getOffsetX()>Main.SCREEN_WIDTH-windowOffsetX)
            map.changePosition(moveSpeed, 0);
        if(ukP && map.getOffsetY() > 0)
            map.changePosition(0, -moveSpeed);
        if(dkP && map.getHeight()-map.getOffsetY()>Main.SCREEN_HEIGHT-windowOffsetY)
            map.changePosition(0, moveSpeed);
        
        if(map.getOffsetX() < 0)
            map.setPosition(0, map.getOffsetY());
        if(map.getWidth() > Main.SCREEN_WIDTH && map.getWidth()-map.getOffsetX()<Main.SCREEN_WIDTH-windowOffsetX)
            map.setPosition(map.getWidth()-(Main.SCREEN_WIDTH-windowOffsetX), map.getOffsetY());
        if(map.getOffsetY() < 0)
            map.setPosition(map.getOffsetX(),0);
        if(map.getHeight()> Main.SCREEN_HEIGHT && map.getHeight()-map.getOffsetY()<Main.SCREEN_HEIGHT-windowOffsetY)
            map.setPosition(map.getOffsetX(),map.getHeight()-(Main.SCREEN_HEIGHT-windowOffsetY));
        
        miniMap.changedPosition(map);
    }

    /*Gets all of the map's squares and spikes
    **as strings to write to map text file*/
    private String mapToText(Map map){
    	String ret = map.startSquareIndex+","+map.endSquareIndex+","+map.getTimeToCount()+"\n";
    	for(Square sq : map.getSquareList()){
            ret+="sq"+sq.getInfo(map)+"\n";
            for(Spike sp : sq.getSpikeList()){
                ret+="sp"+sp.getInfo()+"\n";
            }
    	}
        return ret;
    }
    
    //when save button pressed, write map as text to text file
    private void savePressed(){
        FileWriter write = null;
        try {
            write = new FileWriter(map.getFile(), false);
        } catch (IOException ex) {}
        PrintWriter print_line = new PrintWriter(write);

        String fileText = mapToText(map);

        print_line.printf("%s",fileText);

        print_line.close();            
    }
    
    //when load pressed, open up dialog and load chosen map
    private void loadPressed(){
        JFileChooser fc = new JFileChooser(new File(new File("").getAbsolutePath().concat("/src/assets/")));
        fc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    File file = fc.getSelectedFile();
                    if(file != null){
                        System.out.println(file.getName()+","+file.getAbsolutePath()+","+file.getParent());
                        updateMap(file.getParent(),file.getName());
                    }
                    else
                        System.out.println("Open command cancelled by user.");
            }
        });
        int val = fc.showOpenDialog(null);
    }
    
    //when new pressed, prompt for name of text file and generate new map
    private void newPressed(){
        String textFileName = JOptionPane.showInputDialog(null, "Name the text file", "File Name", JOptionPane.QUESTION_MESSAGE);
                
        if(textFileName != null){
            FileWriter write = null;
            try {
                write = new FileWriter(fileParent+"/"+textFileName+".txt", false);
            } catch (IOException ex) {}
            PrintWriter print_line = new PrintWriter(write);

            print_line.printf("0,0,10000\n");

            print_line.close();

            updateMap(fileParent,textFileName);
        }
    }
    
    //initial map opening, sets text fields to default values
    private void updateMap(String fp, String tfn){
        if(tfn.contains(".txt"))
           tfn = tfn.substring(0, tfn.length()-4);
        map = new Map(fp, tfn+".txt",dp);
        map.showNumbers();
        miniMap = new MiniMap(Main.SCREEN_WIDTH+toolXPadding-windowOffsetX, toolXPadding, windowOffsetX-toolXPadding*2, windowOffsetX-toolXPadding*2, map, this);          
        textFields.get(0).get(0).setText("100");
        textFields.get(0).get(1).setText("100");
        textFields.get(1).get(0).setText("1");
        textFields.get(1).get(1).setText("0");
        textFields.get(1).get(2).setText("1");
        textFields.get(4).get(0).setText(""+map.getWidth());
        textFields.get(4).get(1).setText(""+map.getHeight());
        textFields.get(4).get(2).setText(""+map.startSquareIndex);
        textFields.get(4).get(3).setText(""+map.endSquareIndex);
        textFields.get(5).get(0).setText("100");
        textFields.get(5).get(1).setText("100");
        textFields.get(6).get(0).setText("1");
        textFields.get(6).get(1).setText("0");
        textFields.get(6).get(2).setText("1");
    }
    
    /*shows all text fields associated to tool 
    **and hides all others*/
    public void showLevelMaker(){
        for(int k = 0; k < textFields.size(); k++){
            if(k == tool){
                for(LabeledTextField l : textFields.get(k))
                    l.setVisibility(true);
            }
            else{
                for(LabeledTextField l : textFields.get(k))
                    l.setVisibility(false);
            }
        }
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == 37)
            lkP = true;
        if(e.getKeyCode() == 38)
            ukP = true;
        if(e.getKeyCode() == 39)
            rkP = true;
        if(e.getKeyCode() == 40)
            dkP = true;
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == 37)
            lkP = false;
        if(e.getKeyCode() == 38)
            ukP = false;
        if(e.getKeyCode() == 39)
            rkP = false;
        if(e.getKeyCode() == 40)
            dkP = false;
    }

    public void mouseClicked(MouseEvent e, DrawPanel dp){
        //load
        if(fileButtons.get(0).mouseEvent(e.getX(), e.getY(), true))
            loadPressed();
        //save
        if(fileButtons.get(2).mouseEvent(e.getX(), e.getY(), true))
            savePressed();
        //new
        if(fileButtons.get(1).mouseEvent(e.getX(), e.getY(), true))
            newPressed();
        //exit, hide all text fields and set draw panel mode to 1
        if(fileButtons.get(3).mouseEvent(e.getX(), e.getY(), true)){
            for(ArrayList<LabeledTextField> ar : textFields){
                for(LabeledTextField l : ar){
                    l.setVisibility(false);
                }
            }
            dp.closeLevelMaker();
        }
        
        //check if tool button was pressed and show related text fields
        for(int i = 0; i < toolButtons.size(); i++){
            if(toolButtons.get(i).mouseEvent(e.getX(), e.getY(), true)){
                //if tool other than current pressed
                if(i != tool){
                    tempSquare = null;
                    tempSpike = null;
                }
                tool = i;
                for(int k = 0; k < textFields.size(); k++){
                    if(k == i){
                        for(LabeledTextField l : textFields.get(k))
                            l.setVisibility(true);
                    }
                    else{
                        for(LabeledTextField l : textFields.get(k))
                            l.setVisibility(false);
                    }
                }
            }
        }
        for(int i = 0; i < toolButtons.size(); i++){
            if(i == tool)
                toolButtons.get(i).selected();
            else
            	toolButtons.get(i).unselected();
        }
        
        if(mouseOnScreen(e)){
            //if adding/moving a square and clicked, add square to the map
            if(tempSquare != null){
                map.addSquare(new Square(tempSquare.getX(), tempSquare.getY(), tempSquare.getWidth(), tempSquare.getHeight(), tempSquare.getSpikeDetails(),false));
                if(tempSquare.getX()+tempSquare.getWidth() > map.getWidth()-200){
                    textFields.get(4).get(0).setText((tempSquare.getX()+tempSquare.getWidth()+200)+"");
                    updateTextFields(4, 0);
                }
                if(tempSquare.getY()+tempSquare.getHeight() > map.getHeight()-200){
                    textFields.get(4).get(1).setText((tempSquare.getY()+tempSquare.getHeight()+200)+"");
                    updateTextFields(4, 1);
                }
                if(tool == 5)
                    tempSquare = null;
            }
            //if adding/moving a spike and clicked, add spike to the map
            if(tempSpike != null){
                tempSpike.getSquare().addSpike(new Spike(tempSpike.getSquare(), tempSpike.getX(), tempSpike.getY(), tempSpike.getSide(), tempSpike.getSpacing(), tempSpike.getFrequency())); 
            }
                
        	//remove clicked square if remove square tool active
            if(tool == 2){
                for(int i = 0; i < map.getSquareList().size(); i++){
                    if(map.getSquareList().get(i).intersectsSquare(e.getX(), e.getY(), 0, 0)){
                        map.getSquareList().remove(i);
                        break;
                    }
                }
            }
            //remove clicked spike if remove spike tool selected
            else if(tool == 3){
                for(Square s : map.getSquareList()){
                    if(s.pointInBounds(e.getX(), e.getY())){
                        for(int i = 0; i < s.getSpikeList().size(); i++){
                            boolean removeSpike = false;
                            for(Polygon p : s.getSpikeList().get(i).getPolygonList()){
                                if(p.contains(e.getX(), e.getY())){
                                    removeSpike = true;
                                    break;
                                }
                            }
                            if(removeSpike){
                                s.getSpikeList().remove(i);
                                break;
                            }
                        }
                    }
                }
            }
            //edit square tool
            else if(tool == 5){
                for(int i = 0; i < map.getSquareList().size(); i++){
                    if(map.getSquareList().get(i).intersectsSquare(e.getX(), e.getY(), 0, 0)){
                        tempSquare = new Square(map.getSquareList().get(i).getX(), map.getSquareList().get(i).getY(), map.getSquareList().get(i).getWidth(), map.getSquareList().get(i).getHeight(), map.getSquareList().get(i).getSpikeDetails(), false);
                        map.getSquareList().remove(i);
                        textFields.get(5).get(0).setText(tempSquare.getWidth()+"");
                        textFields.get(5).get(1).setText(tempSquare.getHeight()+"");
                        break;
                    }
                }
            }
            //edit spike tool
            else if(tool == 6){
                if(tempSpike == null){
                    for(Square s : map.getSquareList()){
                        if(s.pointInBounds(e.getX(), e.getY())){
                            for(int i = 0; i < s.getSpikeList().size(); i++){
                                boolean spikeSelect = false;
                                for(Polygon p : s.getSpikeList().get(i).getPolygonList()){
                                    if(p.contains(e.getX(), e.getY())){
                                        spikeSelect = true;
                                        break;
                                    }
                                }
                                if(spikeSelect){
                                    tempSpike = new Spike(s.getSpikeList().get(i).getSquare(), s.getSpikeList().get(i).getX(), s.getSpikeList().get(i).getY(), s.getSpikeList().get(i).getSide(), s.getSpikeList().get(i).getSpacing(), s.getSpikeList().get(i).getFrequency());
                                    s.getSpikeList().remove(i);
                                    textFields.get(6).get(0).setText(tempSpike.getFrequency()+"");
                                    textFields.get(6).get(1).setText(tempSpike.getSpacing()+"");
                                    textFields.get(6).get(2).setText(tempSpike.getX()+"");
                                    break;
                                }
                            }
                        }
                    }
                }
                else{
                    tempSpike = null;
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e){
    	//update button highlights
        for(Button b : fileButtons)
            b.mouseEvent(e.getX(), e.getY(), false);
        for(Button b : toolButtons)
            b.mouseEvent(e.getX(), e.getY(), false);
        if(mouseOnScreen(e)){
            /*make new square and move it to cursor position
            **if create square tool selected*/
            if(tool == 0 || tool == 5){
                if(tempSquare == null && tool == 0)
                    tempSquare = new Square(e.getX(), e.getY(), Integer.parseInt(textFields.get(0).get(0).getText()), Integer.parseInt(textFields.get(0).get(1).getText()), new int[][]{{-1,-1,-1,-1,-1}},false);
                else{
                    if(tempSquare != null)
                        tempSquare.setPosition(e.getX(), e.getY());
                }
            }
            
            /*make new spike and move it to cursor position
            **if create spike tool selected*/
            else if((tool == 1 || tool == 6) && Integer.parseInt(textFields.get(1).get(0).getText()) > 0){
                if((tool == 6 && tempSpike != null) || tool == 1){
                    boolean setSpike = false;
                    for(Square s : map.getSquareList()){
                        if(s.pointInBounds(e.getX(), e.getY())){
                            int side = s.sideOfPoint(e.getX(), e.getY());
                            if(side != -1){
                                int startX = Integer.parseInt(textFields.get(1).get(2).getText());
                                int startY = startX;

                                if(startX != -1){
                                    if((side+1)%2 == 0){
                                        startX = 0;
                                        startY = e.getY()-s.getY()-Main.SPIKE_SIZE/2;
                                    }
                                    else{
                                        startX = e.getX()-s.getX()-Main.SPIKE_SIZE/2;
                                        startY = 0;
                                    }                     
                                }

                                if(tempSpike == null || tempSpike.getSide() != side){
                                    tempSpike = new Spike(s, startX, startY, side, Integer.parseInt(textFields.get(1).get(1).getText()), Integer.parseInt(textFields.get(1).get(0).getText()));  
                                }
                                else{
                                    if(startX != -1){
                                        if((side+1)%2 == 1)
                                            tempSpike.setOriginalPosition(e.getX()-Main.SPIKE_SIZE/2, tempSpike.getPolygonList().get(0).getBounds().y);
                                        else
                                            tempSpike.setOriginalPosition(tempSpike.getPolygonList().get(0).getBounds().x, e.getY()-Main.SPIKE_SIZE/2);
                                    }
                                }

                                setSpike = true;
                            }
                            break;
                        }
                    }
                    if(!setSpike && tool == 1)
                        tempSpike = null;
                }
            }        
            //if edit map tool selected, display the check lines, 
            else if(tool == 4){
                boolean onSquare = false;
                for(Square s : map.getSquareList()){
                    if(s.pointInBounds(e.getX(), e.getY())){
                        int side = s.sideOfPoint(e.getX(), e.getY());
                        if(side != -1){
                            lineCheck.onSquare(e.getX(), e.getY(), side, map, s);
                            onSquare = true;
                            break;
                        }
                    }
                }
                if(!onSquare)
                    lineCheck.offSquare();
            }
        }
    }
    
    /*returns true if cursor is on the map screen,
    **false if the cursor is in the button area*/
    public boolean mouseOnScreen(MouseEvent e){
        return (e.getX() >= 0 && e.getX() <= Main.SCREEN_WIDTH-windowOffsetX && e.getY() >= 0 && e.getY() <= Main.SCREEN_HEIGHT-windowOffsetY);
    }
    
    //send mouse press events to minimap
    public void mousePressed(MouseEvent e){
    	miniMap.mousePressed(e);
    }
    
    //send mouse release events to minimap
    public void mouseReleased(MouseEvent e){
    	miniMap.mouseReleased(e);
    }

    //send mouse drag events to minimap
    public void mouseDragged(MouseEvent e){
    	miniMap.mouseDragged(e);
    }
    
    public int getWindowOffsetX(){
        return windowOffsetX;
    }
    
    public int getWindowOffsetY(){
        return windowOffsetY;
    }
    
    public Map getMap(){
        return map;
    }
    
    public LineCheck getLineCheck(){
        return lineCheck;
    }
}
