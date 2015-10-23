package physics;

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
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class physics extends JPanel implements KeyListener,MouseListener,ActionListener
{
	// Declare instance variables here...

	static int renderSpeed = 10;
	boolean left = false;
	boolean right = false;
	boolean jump = false;

	int moveVel = 4;
	int boxX = 0;
	int jumpNum = 1;
	int jumps = jumpNum; 
	boolean doubleJump = false;
	boolean jumpReset = true;
	double jumpVel = -8;
	double grav = .3;

	BufferedImage sprite = null;
	BufferedImage dustAnim = null;
	physicsObject animSprite = new physicsObject();
	animatedSprite player = new animatedSprite();
	animatedSprite dust = new animatedSprite();
	int animDelay = 2;
	int dustX = 100;
	int dustY = 200;

	ArrayList<int[]> platform = new ArrayList<int[]>();

  platformOb platforms[] = {
    new platformOb(new int[]{50, 500, 100, 20}, "plat"),
    new platformOb(new int[]{472, 500, 4, 4}, "plat"),
    new platformOb(new int[]{560, 360, 100, 20}, "plat"),
    new platformOb(new int[]{700, 280, 100, 20}, "plat"),
    new platformOb(new int[]{900, 100, 100, 20}, "plat"),
    new platformOb(new int[]{1100, -60, 100, 20}, "plat"),
  };

	int containerLimit[] = {0,0,3000,1300};

	int xDisplacement = 0;
	int yDisplacement = 0;

	JButton editB;
	boolean buttonToggle = true;
	boolean createNew = false;

	// Constructor
	public physics(int w, int h, JFrame f)
	{
		super.setOpaque(true);
		super.setPreferredSize(new Dimension(w, h));
		super.setBackground(new Color(255, 255, 255));
		super.setLayout(null);
		editB = new JButton("Make New Platforms");
		editB.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		editB.setBounds(210, 10, 180, 30);
		editB.addActionListener(this);
		editB.setFocusable(false);
		super.add(editB);
		try
		{
			sprite = ImageIO.read(new File(new File("").getAbsolutePath().concat("/src/physics/spritesheet.png")));
			dustAnim = ImageIO.read(new File(new File("").getAbsolutePath().concat("/src/physics/dustSpriteSheet.png")));
			//sprite = ImageIO.read(new File(new File("").getAbsolutePath().concat("/src/spritesheet.png")));
			//dustAnim = ImageIO.read(new File(new File("").getAbsolutePath().concat("/src/dustSpriteSheet.png")));
		}
		catch (IOException ex)
		{System.out.println(ex.getMessage());}

		//1 = downblock
		//2 = upblock
		//3 = rightblock
		//4 = leftblock

		animSprite.init(70, 350, 54, 94, 0, 0, 0, grav);
		animSprite.addBounds(new int[]{0,560,containerLimit[2],560,1}, "ground");
		animSprite.addBounds(new int[]{0,640-containerLimit[3],0,560,4}, "rightWall");
		animSprite.addBounds(new int[]{containerLimit[2],640-containerLimit[3],containerLimit[2],560,3}, "leftWall");
		animSprite.addBounds(new int[]{0,640-containerLimit[3],containerLimit[2],640-containerLimit[3],2}, "ceiling");
		
		player.init(4, 4, 100, 100, 400, 400);
		player.colNum = 1;
		dust.init(2, 3, 200, 100, 600, 200);

		for(int i = 0; i<platforms.length; i++){
			rectToBounds(platforms[i].pCoordinates[0], platforms[i].pCoordinates[1], platforms[i].pCoordinates[2], platforms[i].pCoordinates[3], platforms[i].hitMess);
		}

		addKeyListener(this);
		addMouseListener(this);
		super.setFocusable(true);
	}

	// Perform any custom painting (if necessary) in this method
	@Override  
	public void paintComponent(Graphics g)
	{
		int x = animSprite.getX()-23;
		int y = animSprite.getY();
		player.boxDisplacement = x;
		if(!animSprite.onGround)
			player.setImage(new int[]{14},animDelay);
		else if(animSprite.getXVel()==0)
			player.setImage(new int[]{1},animDelay);
		else
			player.setImage(0,15,animDelay);

		setDisplacment();
		moveUpdate();
		animSprite.update();

		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.setClip(x-xDisplacement, y-yDisplacement, player.frameWidth, player.frameHeight);
    if(!animSprite.dead)
      g.drawImage(sprite, -100*player.colNum*player.flip+player.boxDisplacement-xDisplacement, -100*player.rowNum+y-yDisplacement, player.sheetHeight*player.flip, player.sheetWidth, this);
    else
      g.drawImage(sprite, -100*player.colNum*player.flip+player.boxDisplacement-xDisplacement, -100*player.rowNum+100 +y-yDisplacement, player.sheetHeight*player.flip, -1*player.sheetWidth, this);
    g.setClip(0,0,600,600);
		g.fillRect(0, 560-yDisplacement, 600, 40);
		g.fillRect(0, (600-containerLimit[3])-yDisplacement, 600, 40);
		drawPlatforms(g);
		if(doubleJump){
			dust.setImage(0, 5, 3);
			g.setClip(dustX-xDisplacement,dustY-yDisplacement,dust.frameWidth,dust.frameHeight);
			g.drawImage(dustAnim, dustX+(-200*dust.colNum)-xDisplacement, dustY+(-100*dust.rowNum)-yDisplacement, dust.sheetWidth, dust.sheetHeight, this);
			if(dust.image == 5){
				doubleJump = false;
				dust.image = 0;
			}
		}
	}
  
  public class platformOb{
    int[] pCoordinates = new int[4];
    String hitMess = "";
    
    public platformOb(int[] p, String s){
      for(int i = 0; i < 4; i++){
        pCoordinates[i] = p[i];
      }
      hitMess = s;
    }
  }
	
	public class cutAnimatedSprite{
		private ArrayList<position> points = new ArrayList<position>();
		private String pointSource = "";
		private int tempCutX = 0;
		private int tempCutY = 0;
		private int tempX = 0;
		private int tempY = 0;
		private int tempH = 0;
		private int tempW = 0;
		private double tempS = 0;
		private int count = 0;
		private int delay = 0;
		private int centerX = 0;
		private int centerY = 0;
		private int image = 0;
		
		public void init() throws IOException{
			BufferedReader text = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat(pointSource)));
			BufferedReader readLinesText = new BufferedReader(new FileReader(new File("").getAbsolutePath().concat(pointSource)));
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
		
		public void setCenter(int cX, int cY){
			centerX = cY;
			centerY = cY;
		}
		
		public void setImage(){
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
		}
	}
	
	public void drawCutAnim(Graphics g, cutAnimatedSprite cut, BufferedImage img){
		g.setClip(cut.tempCutX, cut.tempCutY, cut.tempW, cut.tempH);
		g.drawImage(img, cut.tempX, cut.tempY, (int)(img.getWidth()*cut.tempS), (int)(img.getHeight()*cut.tempS), this);
		g.setClip(0,0,600,600);
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
	
	public class animatedSprite{
		private int r;
		private int c;
		private int frameWidth;
		private int frameHeight;
		private int sheetWidth;
		private int sheetHeight;
		
		counter animDelayCount = new counter();
		counter animFrameCount = new counter();
		
		private int rowNum = 0;
		private int colNum = 0;
		private int flip = 1;
		private int image = 0;
		private int boxDisplacement = 0;
		
		public void init(int rNum, int cNum, int fW, int fH, int sW, int sH){
			r = rNum;
			c = cNum;
			frameHeight = fH;
			frameWidth = fW;
			sheetHeight = sH;
			sheetWidth = sW;
		}
		

		//cNum =2 rNum=1 image=2 r=2 c=3
		
		public void setImage(int start, int finish, int delay){
			if(flip<0)
				boxDisplacement+=100;
			if(animDelayCount.getCount()>delay-1){
				animDelayCount.reset();
				image++;
				if (image>finish)
					image = start;
				colNum = image%c;
				rowNum = image/c;
			}
			else
				animDelayCount.count();
		}

		public void setImage(int[] sequence, int delay){
			if(flip<0)
				boxDisplacement+=100;
			if(animDelayCount.getCount()>delay-1){
				animDelayCount.reset();
				image = sequence[animFrameCount.getCount()];
				colNum = image%c;
				rowNum = image/r;
				animFrameCount.count();
				if(animFrameCount.getCount()>=sequence.length)
					animFrameCount.reset();
			}
			else
				animDelayCount.count();
		}
		
	}

	public void setDisplacment(){
		if(animSprite.getX()>275 && animSprite.getX()<containerLimit[2]-325){
			xDisplacement = animSprite.getX()-275;
		}
		if(animSprite.getY()<250 && animSprite.getY()>(600-(containerLimit[3])+250)){
			yDisplacement = animSprite.getY()-250;
		}
		if(animSprite.getX()<275)
			xDisplacement = 0;
		if(animSprite.getX()>containerLimit[2]-325)
			xDisplacement = containerLimit[2]-600;
		if(animSprite.getY()>250)
			yDisplacement = 0;
		if(animSprite.getY()<(600-(containerLimit[3])+250))
			yDisplacement = 600-containerLimit[3];
	}

	public void drawPlatforms(Graphics g){
		for(int i  = 0; i < platforms.length; i++){
			g.fillRect(platforms[i].pCoordinates[0]-xDisplacement, platforms[i].pCoordinates[1]-yDisplacement, platforms[i].pCoordinates[2], platforms[i].pCoordinates[3]);
		}
		for(int i = 0; i < platform.size(); i++){
			g.fillRect(platform.get(i)[0]-xDisplacement, platform.get(i)[1]-yDisplacement, platform.get(i)[2], platform.get(i)[3]);
		}
		
	}

	public void rectToBounds(int x1, int y1, int x2, int y2, String h){
		//rightblock
		if(x1!=0){
			animSprite.addBounds(new int[]{x1,y1+3,x1,y1+y2,3},h);
		}
		//leftblock
		if(x1+x2!=600){
			animSprite.addBounds(new int[]{x1+x2,y1+3,x1+x2,y1+y2,4},h);
		}
		//downblock
		if(y1!=0){
			animSprite.addBounds(new int[]{x1+1,y1,x1+x2-1,y1,1},h);
		}
		//downblock
		if(y1+y2!=560){
			animSprite.addBounds(new int[]{x1+1,y1+y2,x1+x2-1,y1+y2,2},h);
		}
	}

	public void moveUpdate(){
		if(right && !createNew){
			animSprite.setXVel(moveVel);
			player.flip = -1;
		}
		if(left && !createNew){
			animSprite.setXVel(-moveVel);
			player.flip = 1;
		}
		if(!left && !right)
			animSprite.setXVel(0);
		if(jump && jumps>0 && jumpReset && !createNew){
			animSprite.onGround = false;
			animSprite.setYVel(jumpVel);
			animSprite.setYAccel(grav);
			jumps--;
			if(jumps < jumpNum-1){
				doubleJump = true;
        dust.image = 1;
				dustX = animSprite.getX()-75;
				dustY = animSprite.getY()+40;
			}
			jumpReset = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(createNew){
			platform.add(new int[]{e.getX()+xDisplacement,e.getY()+yDisplacement,100,20});
			rectToBounds(e.getX()+xDisplacement,e.getY()+yDisplacement,100,20, "plat");
		}
	}

	@Override
	public void mousePressed(MouseEvent e){}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(buttonToggle){
			buttonToggle = false;
			editB.setText("Exit Platform Maker");
			createNew = true;
		}
		else if(!buttonToggle){
			buttonToggle = true;
			editB.setText("Make New Platforms");
			createNew = false;
		}
	}

	public class physicsObject{
		double xVelocity = 0;
		double yVelocity = 0;
		double xAcceleration = 0;
		double yAcceleration = 0;
		int xPos = 0;
		int yPos = 0;
		int xDim = 0;
		int yDim = 0;
		ArrayList<int[]> boundRectangles = new ArrayList<int[]>();
    ArrayList<String> boundMess = new ArrayList<String>();
		boolean onGround = true;
    boolean dead = false;

		public void init(int x,int y,int width, int height, double xVel, double yVel, double xAccel, double yAccel){
			xPos = x;
			yPos = y;
			xVelocity = xVel;
			yVelocity = yVel;
			xAcceleration = xAccel;
			yAcceleration = yAccel;
			xDim = width;
			yDim = height;
		}

		public void addBounds(int[] rect, String h){
			boundRectangles.add(rect);
      boundMess.add(h);
		}

		public void setXAccel(double accel){
			xAcceleration = accel;
		}

		public void setYAccel(double accel){
			yAcceleration = accel;
		}

		public void setXVel(double vel){
			xVelocity = vel;
		}

		public void setYVel(double vel){
			yVelocity = vel;
		}

		public void update(){
			for(int i = 0; i < boundRectangles.size(); i++){
				if(yPos+yDim>=boundRectangles.get(i)[1] && yPos<=boundRectangles.get(i)[3] && xPos+xDim>=boundRectangles.get(i)[0] && xPos<=boundRectangles.get(i)[2]){
					if(yVelocity>0 && boundRectangles.get(i)[4] == 1){
						yVelocity = 0;
						yPos = boundRectangles.get(i)[1]-yDim;
						onGround = true;
            if(boundMess.get(i).equals("ground")){
              dead = true;
              right = false;
              left = false;
              jump = false;
            }
						jumps = jumpNum;
					}
					if(yVelocity<0 && boundRectangles.get(i)[4] == 2){
						yVelocity = 0;
						yPos = boundRectangles.get(i)[3];
					}
					if(xVelocity<0 && boundRectangles.get(i)[4] == 4){
						xVelocity = 0;
						xAcceleration = 0;
						xPos = boundRectangles.get(i)[2];
					}
					if(xVelocity>0 && boundRectangles.get(i)[4] == 3){
						xVelocity = 0;
						xAcceleration = 0;
						xPos = boundRectangles.get(i)[0]-xDim;
					}
				}
			}
			xVelocity += xAcceleration;
			yVelocity += yAcceleration;
			xPos += xVelocity;
			yPos += yVelocity;
		}

		public int getX(){
			return xPos;
		}

		public int getY(){
			return yPos;
		}

		public double getXVel(){
			return xVelocity;
		}

		public double getYVel(){
			return yVelocity;
		}

		public double getXAccel(){
			return xAcceleration;
		}

		public double getYAccel(){
			return yAcceleration;
		}
	}

	public class counter{
		int count = 0;
		public void count(){
			count++;
		}
		public int getCount(){
			return count;
		}
		public void reset(){
			count = 0;
		}
	}

	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame fr = new JFrame("Application: Physics");
		fr.setContentPane(new physics(600, 600, fr));
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

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void keyPressed(KeyEvent ke)
	{
		switch (ke.getKeyCode()) {
		case 37:// left arrow key
      if(!animSprite.dead)
        left = true;
			break;
		case 39:// right arrow key
      if(!animSprite.dead)
        right = true;
			break; 
		case 32:// space key
      if(!animSprite.dead)
        jump = true;
			break;
    case 10:// return key
      animSprite.dead = false;
      animSprite.xPos = 70;
      animSprite.yPos = 350;
      break;
		}
	}

	@Override
	public void keyReleased(KeyEvent ke)
	{
		switch (ke.getKeyCode()) {
		case 37:// left arrow key
			left = false;
			break;
		case 39:// right arrow key
			right = false;
			break;
		case 32:// space key
			jump = false;
			jumpReset = true;
			break;
		}
	}

}