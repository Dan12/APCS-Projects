package animate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class animate extends JPanel implements KeyListener{
	
	private static int renderSpeed = 17;
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean imgScaleUp = false;
	private boolean imgScaleDown = false;
	private boolean boxScaleHeightPos = false;
	private boolean boxScaleHeightNeg = false;
	private boolean boxScaleWidthPos = false;
	private boolean boxScaleWidthNeg = false;
  private int moveSpeed = 2;
	private int width = 100;
	private int height = 100;
	private int x = 100;
	private int y = 100;
	private String path = "/src/animate/spriteComp.png";
  private String infoPath = "/src/animate/info.txt";
	private ArrayList<position> points = new ArrayList<position>();
	private BufferedImage sprite = null;
	private double imgScale = 1;
	private int currentFrame = 0;
	private int image = 0;
	private int centerX = 200;
	private int centerY = 200;
	private int count = 0;
	private int delay = 2;
	private int tempCutX = 0;
	private int tempCutY = 0;
	private int tempX = 0;
	private int tempY = 0;
	private int tempH = 0;
	private int tempW = 0;
	private double tempS = 0;
	JButton capture;
	JButton delete;
	JButton play;
	JButton prev;
	JButton next;
	boolean playing = false;
	
	public animate(int w, int h, JFrame f){
		super.setOpaque(true);
		super.setPreferredSize(new Dimension(w, h));
		super.setBackground(new Color(0, 143, 173));
		super.setLayout(null);
		
		try {
			sprite = ImageIO.read(new File(new File("").getAbsolutePath().concat(path)));
		} catch (IOException e) {e.printStackTrace();}
		
		try {initSequence();} catch (IOException e) {e.printStackTrace();}
		
		capture = new JButton("Capture");
		capture.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		capture.setBounds(60, 10, 120, 30);
		capture.addActionListener(bCap);
		capture.setFocusable(false);
		super.add(capture);
		
		delete = new JButton("Delete");
		delete.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		delete.setBounds(240, 10, 120, 30);
		delete.addActionListener(bDel);
		delete.setFocusable(false);
		super.add(delete);
		
		play = new JButton("Play");
		play.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		play.setBounds(420, 10, 120, 30);
		play.addActionListener(bPlay);
		play.setFocusable(false);
		super.add(play);
		
		prev = new JButton("Prev");
		prev.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		prev.setBounds(120, 560, 120, 30);
		prev.addActionListener(bPrev);
		prev.setFocusable(false);
		super.add(prev);
		
		next = new JButton("Next");
		next.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		next.setBounds(360, 560, 120, 30);
		next.addActionListener(bNext);
		next.setFocusable(false);
		super.add(next);
		
		addKeyListener(this);
		super.setFocusable(true);
	}
	
	public void initSequence() throws IOException{
		BufferedReader text = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat(infoPath)));
		BufferedReader readLinesText = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat(infoPath)));
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
		for(int ii = 0; ii<i; ii++){
			String line[] = initText[ii].split(",");
			position temp = new position();
			temp.xCorner = Integer.parseInt(line[0]);
			temp.yCorner = Integer.parseInt(line[1]);
			temp.width = Integer.parseInt(line[2]);
			temp.height = Integer.parseInt(line[3]);
			temp.scale = Double.parseDouble(line[4]);
			points.add(temp);
		}
	}
	
	public void paintComponent(Graphics g){
		moveUpdate();
//		g.setColor(new Color(0,143,173));
//		g.fillRect(0, 0, 600, 600);
//		g.setColor(Color.BLACK);
		if(!playing){
			g.drawImage(sprite, x, y, (int)(sprite.getWidth()*imgScale), (int)(sprite.getHeight()*imgScale), this);
			g.drawRect(100, 100, width, height);
			g.drawLine(100+width/2-5, 100+height/2, width/2+5+100, height/2+100);
			g.drawLine(width/2+100, 100+height/2-5, 100+width/2, 100+height/2+5);
		}
		else{
			animSprite(g);
		}
	}
	
	public void animSprite(Graphics g){
		if(count>=delay){
			count = 0;
			tempCutX = centerX-points.get(image).width/2;
			tempCutY = centerY-points.get(image).height/2;
			tempX = tempCutX+(points.get(image).xCorner-100);
			tempY = tempCutY+(points.get(image).yCorner-100);
			tempH = points.get(image).height;
			tempW = points.get(image).width;
			tempS = points.get(image).scale;
			
			image++;
			if(image>points.size()-1)
				image = 0;
		}
		else
			count++;
		System.out.println(image);
		g.setClip(tempCutX, tempCutY, tempW, tempH);
		g.drawImage(sprite, tempX, tempY, (int)(sprite.getWidth()*tempS), (int)(sprite.getHeight()*tempS), this);
		g.setClip(0,0,600,600);
	}
	
	public void moveUpdate(){
		if(up)
			y-=moveSpeed;
		if(down)
			y+=moveSpeed;
		if(left)
			x-=moveSpeed;
		if(right)
			x+=moveSpeed;
		if(imgScaleUp)
			imgScale+=.01;
		if(imgScaleDown)
			imgScale-=.01;
		if(boxScaleHeightPos)
			height++;
		if(boxScaleHeightNeg)
			height--;
		if(boxScaleWidthPos)
			width++;
		if(boxScaleWidthNeg)
			width--;
	}
	
	public static void main(String args[]){
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame fr = new JFrame("Application: Physics");
		fr.setContentPane(new animate(600,600, fr));
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
	
	public class position{
		private int xCorner;
		private int yCorner;
		private int width;
		private int height;
		private double scale;
		public void init(int x, int y, int w, int h, double s){
			xCorner = x;
			yCorner = y;
			width = w;
			height = h;
			scale = s;
		}
	}
	
	ActionListener bCap = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			position temp = new position();
			temp.init(x, y, width, height, imgScale);
			points.add(currentFrame, temp);
			currentFrame ++;
			try {setFile();} catch (IOException e1) {e1.printStackTrace();}
			System.out.println("Added point "+x+","+y+","+width+","+height+","+imgScale+" at "+(currentFrame-1));
		}
	};
	
	ActionListener bDel = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = currentFrame-1;
			if(index>=0){
				System.out.println("Removed point "+points.get(index).xCorner+","+points.get(index).yCorner+","+points.get(index).width+","+points.get(index).height+","+points.get(index).scale+" at "+(index));
				points.remove(index);
				currentFrame--;
				if(currentFrame>0){
					setPoints();
					try {setFile();} catch (IOException e1) {e1.printStackTrace();}
				}
			}
			else
				System.out.println("Nothing Removed");
		}
	};
	
	ActionListener bPlay = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!playing && points.size() > 0){
				playing = true;
				play.setText("Stop");
			}
			else{
				playing = false;
				play.setText("Play");
			}
		}
	};
	
	ActionListener bPrev = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(points.size() > 0){
				currentFrame--;
				if(currentFrame<1)
					currentFrame = points.size();
				setPoints();
				try {setFile();} catch (IOException e1) {e1.printStackTrace();}
			}
			System.out.println("Frame #"+currentFrame);
		}
	};
	
	ActionListener bNext = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			if(points.size() > 0){
				currentFrame++;
				if(currentFrame>points.size())
					currentFrame = 1;
				setPoints();
				try {setFile();} catch (IOException e1) {e1.printStackTrace();}
			}
			System.out.println("Frame #"+currentFrame);
		}
	};
	
	public void setFile() throws IOException{
		PrintWriter text = new PrintWriter(new FileWriter(new File("").getAbsolutePath().concat(infoPath)));
		for(int i = 0; i < points.size(); i++){
			text.write(points.get(i).xCorner+","+points.get(i).yCorner+","+points.get(i).width+","+points.get(i).height+","+points.get(i).scale+"\n");
		}
		text.close();
	}
	
	public void setPoints(){
		int index = currentFrame-1;
		x = points.get(index).xCorner;
		y = points.get(index).yCorner;
		height = points.get(index).height;
		width = points.get(index).width;
		imgScale = points.get(index).scale;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent ke) {
		switch (ke.getKeyCode()) {
			case 37:// left arrow key
				left = true;
				break;
			case 39:// right arrow key
				right = true;
				break;
			case 38:// up arrow key
				up= true;
				break;
			case 40:// down arrow key
				down= true;
				break;
			case 87:// w key
				imgScaleUp = true;
				break;
			case 83:// s key
				imgScaleDown = true;
				break;
			case 65:// a key
				boxScaleWidthNeg = true;
				break;
			case 68:// d key
				boxScaleWidthPos = true;
				break;
			case 82:// r key
				boxScaleHeightNeg = true;
				break;
			case 70:// f key
				boxScaleHeightPos = true;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		switch (ke.getKeyCode()) {
		case 37:// left arrow key
			left = false;
			break;
		case 39:// right arrow key
			right = false;
			break;
		case 38:// up arrow key
			up= false;
			break;
		case 40:// down arrow key
			down= false;
			break;
		case 87:// w key
			imgScaleUp = false;
			break;
		case 83:// s key
			imgScaleDown = false;
			break;
		case 65:// a key
			boxScaleWidthNeg = false;
			break;
		case 68:// d key
			boxScaleWidthPos = false;
			break;
		case 82:// r key
			boxScaleHeightNeg = false;
			break;
		case 70:// f key
			boxScaleHeightPos = false;
			break;
			
		}	
	}

}
