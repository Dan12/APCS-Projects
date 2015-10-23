package frametest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/*
 * File Name: FrameTest.java
 *   Created: Oct 1, 2014
 *    Author: 
 */


public class FrameTest extends JPanel implements ActionListener,KeyListener
{
	// Declare instance variables here...

	JButton editB;
	JScrollPane editScroll;
	int x = 0;
	JTextArea editor;
	boolean toggle = false;
	int y = 0;
	int z = 0;
	boolean xPos = false;
	boolean xNeg = false;
	boolean yPos = false;
	boolean yNeg = false;
	boolean zPos = false;
	boolean zNeg = false;
	boolean firstPaint = false;
	
	int orLength = 125;
	int coX = 30;
	int coY = 0;
	int coZ = 45;
	int xX = orLength;
	int xY = 0;
	int yX = 0;
	int yY = 0;
	int zX = 0;
	int zY = orLength;
	int centerX = 250;
	int centerY = 250;
	
	int angleChange = 3;
	static int renderSpeed = 1;
	
	ArrayList<Part> partList = new ArrayList<Part>();
	
	ArrayList<Part> copyPartList = new ArrayList<Part>();
	
	int camera[] = { 0, 1000, 0 };
	int curCamera[] = { 0, 1000, 0 };

	// Constructor FrameTest, sets up layout
	public FrameTest(int w, int h, JFrame f)
	{
		super.setOpaque(true);
		super.setPreferredSize(new Dimension(w, h));
		super.setBackground(new Color(225, 225, 225));
		super.setLayout(null);
		editor = new JTextArea();
		editScroll = new JScrollPane();
		editScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		editScroll.getViewport().add(editor);
		editScroll.setBounds(10, 50, 480, 440);
		editB = new JButton("Edit Parts");
		super.add(editB);
		super.add(editScroll);
		editScroll.setVisible(false);
		editB.setBounds(180, 10, 140, 30);
		editB.addActionListener(this);
		editB.setFocusable(false);
		editScroll.setFocusable(false);
		addKeyListener(this);
		super.setFocusable(true);
	}

	// Paint Method, calls all update methods before painting
	@Override  
	public void paintComponent(Graphics g)
	{
		if (!firstPaint){
			try {
				initParts();
			} catch (IOException e) {
				e.printStackTrace();
			}
			firstPaint = true;
		}
		move();
		setDisplayCoordinates();
		setCamera();
		super.paintComponent(g);
		
		g.setColor(Color.RED);
		g.drawLine(centerX + xX, centerY + xY, centerX - xX, centerY - xY);

		g.setColor(Color.BLUE);
		g.drawLine(centerX + yX, centerY + yY, centerX - yX, centerY - yY);

		g.setColor(Color.BLACK);
		g.drawLine(centerX + zX, centerY + zY, centerX - zX, centerY - zY);
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		g.setColor(Color.BLACK);
		g.drawString("X: "+x+" Deg   Y: "+y+" Deg   Z: "+z+"Deg", 20, 480);
		
		if (!partList.isEmpty()){
			drawAll(g);
		}
	}
	
	// Draws all parts specified in partList
	public void drawAll(Graphics g) {
		copyPartList.clear();
		for (int i = 0; i < partList.size(); i++) {
			rotMatrix(partList.get(i));
		}
		setOrder();
		for (int i = 0; i < partList.size(); i++) {
			setPoints(copyPartList.get(i), g, copyPartList.get(i).getColor()[0], copyPartList.get(i).getColor()[1], copyPartList.get(i).getColor()[2]);
		}
	}
	
	// Sets depth order of all parts
	public void setOrder() {
		for (int i = 0; i < copyPartList.size(); i++) {
			for (int ii = 0; ii < copyPartList.size() - 1; ii++) {
				if (camDist(copyPartList.get(ii)) < camDist(copyPartList.get(ii+1))) {
					Part tempPart = copyPartList.get(ii);
					copyPartList.set(ii, copyPartList.get(ii+1));
					copyPartList.set(ii+1, tempPart);
				}
			}
		}
	}
	
	// Draws the specified part with rgb value
	public void setPoints(Part inputPart, Graphics g, int red, int green, int blue) {
		int part[][] = new int[inputPart.getParams().size()][3];
		for(int i = 0; i<inputPart.getParams().size(); i++){
			for(int ii = 0; ii<3; ii++){
				part[i][ii] = inputPart.getParams().get(i)[ii];
			}
		}
		int xArray[] = new int[part.length];
		for (int i = 0; i < part.length; i++) {
			xArray[i] = part[i][0];
		}
		int yArray[] = new int[part.length];
		for (int i = 0; i < part.length; i++) {
			yArray[i] = part[i][1];
		}
		int zArray[] = new int[part.length];
		for (int i = 0; i < part.length; i++) {
			zArray[i] = part[i][2];
		}
		int xPoints[] = new int[part.length];
		int yPoints[] = new int[part.length];
		for (int i = 0; i < part.length; i++) {
			int offsetY = 0;

			if (coX <= 90)
				offsetY = map(coX, 0, 90, zArray[i], 0);
			else if (coX > 90 && coX <= 180)
				offsetY = map(coX, 91, 180, 0, -zArray[i]);
			else
				offsetY = map(coX, 181, 360, -zArray[i], zArray[i]);

			double slope2 = 0;
			double slope1 = 0;
			if (yX != 0)
				slope1 = (double) (yY) / (yX);
			if (xX != 0)
				slope2 = (double) (xY) / (xX);

			double b1 = map(xArray[i], 0, orLength, 0, xY)
					- (slope1 * map(xArray[i], 0, orLength, 0, xX));
			double b2 = map(yArray[i], 0, orLength, 0, yY)
					- (slope2 * map(yArray[i], 0, orLength, 0, yX));
			double intX = 0.0;
			double intY = 0.0;

			if (slope1 - slope2 != 0) {
				intX = (b2 - b1) / (slope1 - slope2);
				intY = slope1 * intX + b1;
			} else {
				if ((coY < 3 || coY > 358) || (coY < 183 && coY > 177)) {
					intX = map(xArray[i], 0, orLength, 0, zX)
							+ map(yArray[i], 0, orLength, 0, yX);
				} else if ((coX <= 89 || coX >= 360)
						|| (coX > 179 && coX <= 269)) {
					intX = map(yArray[i], 0, orLength, 0, yX);
					intY = map(xArray[i], 0, orLength, 0, zY);
				} else if ((coX > 89 && coX <= 179) || coX > 269
						&& coX < 360) {
					intX = map(xArray[i], 0, orLength, 0, zX);
					intY = map(yArray[i], 0, orLength, 0, yY);
				}
			}

			xPoints[i] = (int) (centerX + intX);
			yPoints[i] = (int) (centerY + intY - offsetY);
		}
		g.setColor(new Color(red, green, blue, 255));
		g.fillPolygon(xPoints, yPoints, xPoints.length);
		g.setColor(Color.BLACK);
		g.drawPolygon(xPoints, yPoints, xPoints.length);
//		for (int i = 0; i < xPoints.length; i++) {
//			g.drawString("" + i + "", xPoints[i], yPoints[i]);
//		}
	}
	
	// Applies rotation matrix to partList part and adds it to copyPartList
	public void rotMatrix(Part part) {
		Part copyPart = new Part();
//		 double cosY = Math.cos(Math.toRadians(y));
//		 double cosX = Math.cos(Math.toRadians(x));
//		 double cosZ = Math.cos(Math.toRadians(z));
//		 double sinY = Math.sin(Math.toRadians(y));
//		 double sinX = Math.sin(Math.toRadians(x));
//		 double sinZ = Math.sin(Math.toRadians(z));
		for (int i = 0; i < part.getParams().size(); i++) {
			//doesn't work
			// copyPart[i][0] = (int)
			// (cosZ*cosY*part[i][0]+(-sinZ)*part[i][1]+sinY*part[i][2]);
			// copyPart[i][1] = (int)
			// (sinZ*part[i][0]+cosZ*cosX*part[i][1]+(-sinX)*part[i][2]);
			// copyPart[i][2] = (int)
			// ((-sinY)*part[i][0]+sinX*part[i][1]+cosX*cosY*part[i][2]);
			//new
//			copyPart[i][0] = (int) (cosY*cosZ*part[i][0]+cosY*cosZ*part[i][1]+(-sinY)*part[i][2]);
//			copyPart[i][1] = (int) ((cosZ*sinX*sinY-cosX*sinZ)*part[i][0]+(cosX*cosZ+sinX*sinY*sinZ)*part[i][1]+cosY*sinX*part[i][2]);
//			copyPart[i][2] = (int) ((cosX*cosZ*sinY+sinX*sinZ)*part[i][0]+((-cosZ)*sinX+cosX*sinY*sinZ)*part[i][1]+cosX*cosY*part[i][2]);
			//original
			Integer copyPartArr[] = new Integer[3];
			copyPartArr[0] = (int) (Math.cos(Math.toRadians(y))* part.getParams().get(i)[0] + Math.sin(Math.toRadians(y))* Math.sin(Math.toRadians(x)) * part.getParams().get(i)[1] - Math.sin(Math.toRadians(y))* Math.cos(Math.toRadians(x))* part.getParams().get(i)[2]);
			copyPartArr[1] = (int) (Math.cos(Math.toRadians(x))* part.getParams().get(i)[1] + Math.sin(Math.toRadians(x)) * part.getParams().get(i)[2]);
			copyPartArr[2] = (int) (Math.sin(Math.toRadians(y))* part.getParams().get(i)[0] + Math.cos(Math.toRadians(y))* (-Math.sin(Math.toRadians(x)) * part.getParams().get(i)[1]) + Math.cos(Math.toRadians(y))* Math.cos(Math.toRadians(x))* part.getParams().get(i)[2]);
			copyPart.setParams(copyPartArr);
		}
		copyPart.setColor(part.getColor());
		copyPartList.add(copyPart);
	}

	// Sets X, Y, and Z rotation values based on Key Input
	public void move(){
		if (yNeg) {
			y-=angleChange;
			if (y < 0) {
				y += 360;
			}
		}
		if (yPos) {
			y+=angleChange;
			if (y > 360) {
				y -= 360;
			}
		}
		if (xNeg) {
			x-=angleChange;
			if (x < 0) {
				x += 360;
			}
		}
		if (xPos) {
			x+=angleChange;
			if (x > 360) {
				x -= 360;
			}
		}
		if (zNeg) {
			z-=angleChange;
			if (z < 0) {
				z += 360;
			}
		}
		if (zPos) {
			z+=angleChange;
			if (z > 360) {
				z -= 360;
			}
		}
	}
	
	// Set screen X and Y coordinates for axes 
	public void setDisplayCoordinates() {
		//move X
		int b = 0;
		xX = (int) (-(Math.cos(Math.toRadians(coZ + 90))) * orLength);
		if (coX<=90)
            b = map(coX,0,90,0,orLength);         
        else if (coX>90 && coX<=180)
            b = map(y,91,180,orLength,0);
        else if (coX>180 && coX<=270)
            b = map(coX,181,270,0,orLength);
        else
            b = map(coX,271,360,orLength,0);
		xY = (int) Math.sqrt((1 - ((double) (xX * xX) / (orLength * orLength)))* (b * b));

		//move Z
		if (coX<=180)
            zY = map(coX,0,180,-orLength,orLength);
        else
            zY = map(coX,181,360,orLength,-orLength);

		//move Y
		yX = (int) (-(Math.cos(Math.toRadians(coZ)))*orLength);
		b = 0;
		if (coX<=90)
            b = map(coX,0,90,0,orLength);         
        else if (coX>90 && coX<=180)
            b = map(coX,91,180,orLength,0);
        else if (coX>180 && coX<=270)
            b = map(coX,181,270,0,orLength);
        else
            b = map(coX,271,360,orLength,0);  
		yY = (int) Math.sqrt((1 - ((double) (yX * yX) / (orLength * orLength)))* (b * b));
	}

	// Handle toggle when button pressed
	@Override  
	public void actionPerformed(ActionEvent e)
	{
		if (!toggle){
			editScroll.setVisible(true);
			toggle = true;
			editB.setText("Save and Exit");
			editScroll.setFocusable(true);
		}
		else {
			editScroll.setVisible(false);
			toggle = false;
			try {
				setLines();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			editB.setText("Edit Parts");
			editScroll.setFocusable(false);
			super.repaint();
		}
	}

	// Part object
	public class Part{
		ArrayList<Integer[]> params = new ArrayList<Integer[]>();
		int color[] = {0,0,0};
		
		public void setParams(Integer paramArr[]){
			params.add(paramArr);
		}
		
		public void setColor(int colorArr[]){
			for(int i = 0; i<colorArr.length; i++){
				color[i] = colorArr[i];
			}
		}
		
		public ArrayList<Integer[]> getParams(){
			return params;
		}
		
		public int[] getColor(){
			return color;
		}
	}
	
	// Get Part from JTextArea
	public void setLines() throws IOException{
		String editText = editor.getText().toLowerCase();
		String editorText[] = editText.split("end\n");
		partList.clear();
		for (int l = 0; l < editorText.length; l++){
			if (editorText[l].indexOf("p") != -1 && editorText[l].indexOf("\n") != -1){
				String textLines[] = editorText[l].split("\n");
				String type = "";
				int startIndex = 0;
				while(true){
					type = textLines[startIndex];
					if(type.equals("p")){
						break;
					}
					startIndex++;
				}
				Part temp = new Part();
				int subIndex = 1;
				if (textLines[textLines.length-1].equals("end"))
					subIndex = 2;
				for(int p = 0; p<textLines.length-(1+subIndex+startIndex); p++){
					String coArr[] = textLines[1+p+startIndex].split(",");
					Integer coIntArr[] = new Integer[3];
					for(int i = 0; i<3; i++){
						coIntArr[i] = Integer.parseInt(coArr[i]);
					}
					temp.setParams(coIntArr);
				}
				String colArr[] = textLines[textLines.length-subIndex].split(",");
				int colIntArr[] = new int[3];
				for(int i = 0; i<3; i++){
					colIntArr[i] = Integer.parseInt(colArr[i]);
				}
				temp.setColor(colIntArr);
				partList.add(temp);
			}
			if(editorText[l].indexOf("w") != -1){
				double paramaters[] = new double[5];
				String textLines[] = editorText[l].split("\n");
				String type = "";
				int startIndex = 0;
				while(true){
					type = textLines[startIndex];
					if(type.equals("w")){
						break;
					}
					startIndex++;
				}
				String wheelArr[] = textLines[1+startIndex].split(",");
				for(int i = 0; i < 5; i++){
					paramaters[i] = Double.parseDouble(wheelArr[i]);
				}
				BufferedReader text = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat("/src/frametest/wheel.txt")));
				int ii = 142;
				String wheelText = "";
				for (int i = 0; i < ii; i++){
					wheelText = wheelText+text.readLine()+"\n";
				}
				String wheelTextEditor[] = wheelText.split("end\n");
				for(int i = 0; i<wheelTextEditor.length; i++){
					String wheelTextLines[] = wheelTextEditor[i].split("\n");
					String wheelType = "";
					int wheelStartIndex = 0;
					while(true){
						wheelType = wheelTextLines[wheelStartIndex];
						if(wheelType.equals("p")){
							break;
						}
						wheelStartIndex++;
					}
					Part temp = new Part();
					int subIndex = 1;
					if (wheelTextLines[wheelTextLines.length-1].equals("end"))
						subIndex = 2;
					for(int p = 0; p<wheelTextLines.length-(1+subIndex+wheelStartIndex); p++){
						String coArr[] = wheelTextLines[1+p+wheelStartIndex].split(",");
						Integer coIntArr[] = new Integer[3];
						for(int j = 0; j<3; j++){
							coIntArr[j] = (int) (paramaters[3]*paramaters[4]*((double) (Integer.parseInt(coArr[j]))));
							coIntArr[j] = (int) (coIntArr[j]+paramaters[j]);
						}
						temp.setParams(coIntArr);
						String colArr[] = wheelTextLines[wheelTextLines.length-subIndex].split(",");
						int colIntArr[] = new int[3];
						for(int j = 0; j<3; j++){
							colIntArr[j] = Integer.parseInt(colArr[j]);
						}
						temp.setColor(colIntArr);
						partList.add(temp);
					}
				}
			}
		}
		PrintWriter text = new PrintWriter(new FileWriter(new File("").getAbsolutePath().concat("/src/frametest/info.txt")));
		text.printf("%s", editor.getText());
		text.close();
	}
	
	
	// Set Camera X, Y, and Z coordinates
	public void setCamera() {
		curCamera[0] = (int) (camera[1] * Math.sin(Math.toRadians(coZ) * Math.cos(Math.toRadians(coX))));
		curCamera[1] = (int) (camera[1] * Math.sin(Math.toRadians(coZ) * Math.cos(Math.toRadians(coX))));
		curCamera[2] = (int) (camera[1] * Math.sin(Math.toRadians(coX)));
	}

	// Returns average coordinate of part in specified index(X index, y index, or Z index)
	public int averageCo(Part part, int coI) {
		int ave = 0;
		for (int i = 0; i < part.getParams().size() - 1; i++) {
			ave += part.getParams().get(i)[coI];
		}
		return ave / (part.getParams().size());
	}
	
	// Returns camera distance from part based on average X, Y, and Z values of part
	public double camDist(Part part) {
		return Math.sqrt(((averageCo(part, 0) - curCamera[0]) * (averageCo(part, 0) - curCamera[0])) + ((averageCo(part, 1) - curCamera[1]) * (averageCo(part, 1) - curCamera[1])) + ((averageCo(part, 2) - curCamera[2]) * (averageCo(part, 2) - curCamera[2])));
	}
	
	// Initialize Parts already in text document info.txt
	public void initParts() throws IOException{
		BufferedReader text = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat("/src/frametest/info.txt")));
		BufferedReader readLinesText = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat("/src/frametest/info.txt")));
		int i = 0;
		String aLine;
		while ((aLine = readLinesText.readLine()) !=null){
			i++;
		}
		readLinesText.close();
		String initText[] = new String[i];
		for (int ii = 0; ii < i; ii++){
			initText[ii] = text.readLine();
		}
		String toEditor = "";
		for (int ii = 0; ii < i; ii++){
			toEditor = toEditor+""+initText[ii]+"\n";
		}
		editor.setText(toEditor);
		setLines();
		super.repaint();
		text.close();
	}

	
	// Main Method
	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame fr = new JFrame("Draw");
		fr.setContentPane(new FrameTest(500, 500, fr));
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setLocation(10, 10);
		fr.setResizable(false);
		fr.pack();
		fr.setVisible(true);
		do {
			try {
				Thread.sleep(renderSpeed);
			} catch (InterruptedException interruptedexception) {
				System.out.println(interruptedexception.getMessage());
			}
			fr.repaint();
		} while (true);
	}

	
	// Nothing for keyTyped
	@Override
	public void keyTyped(KeyEvent e) {}

	// Set value to true for keyPressed
	@Override
	public void keyPressed(KeyEvent ke) {
		switch (ke.getKeyCode()) {
		case 37:// left arrow key
			xPos = true;
			break;
		case 39:// right arrow key
			xNeg = true;
			break;
		case 38:// up arrow key
			yPos = true;
			break;
		case 40:// down arrow key
			yNeg = true;
			break;
		case 44:// , key
			zPos = true;
			break;
		case 46:// . key
			zNeg = true;
			break;
		}
	}

	// Set value to false for keyReleased
	@Override
	public void keyReleased(KeyEvent ke) {
		switch (ke.getKeyCode()) {
		case 37:// left arrow key
			xPos = false;
			break;
		case 39:// right arrow key
			xNeg = false;
			break;
		case 38:// up arrow key
			yPos = false;
			break;
		case 40:// down arrow key
			yNeg = false;
			break;
		case 44:// , key
			zPos = false;
			break;
		case 46:// . key
			zNeg = false;
			break;
		}
	}
	
	
	// Map function from Arduino
	public int map(int x, int in_min, int in_max, int out_min, int out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min)
				+ out_min;
	}
}