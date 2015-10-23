package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Test extends JFrame implements KeyListener,ActionListener {
	public Test() {
		super("Draw");
		panel = new Draw();
		add(panel);
		addKeyListener(this);
    createB = new JButton("Create Part");
    createB.addActionListener(this);
    //super.add(createB);
    //super.setLayout(null);
    //createB.setBounds(200, 20, 100, 40);
	}

	public void Go() {
		do {
			try {
				Thread.sleep(10);
			} catch (InterruptedException interruptedexception) {
				System.out.println(interruptedexception.getMessage());
			}
			repaint();
		} while (true);
	}

	public static void main(String args[]) {
		Test frame = new Test();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(3);
		frame.setLocation(100, 100);
		frame.setVisible(true);
		frame.setBackground(Color.WHITE);
		frame.Go();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		switch (ke.getKeyCode()) {
		case 44:// , key
			panel.zneg = true;
			break;
		case 46:// . key
			panel.zpos = true;
			break;
		case 38:// up arrow key
			panel.ypos = true;
			break;
		case 40:// down arrow key
			panel.yneg = true;
			break;
		case 37:// left arrow key
			panel.xneg = true;
			break;
		case 39:// right arrow key
			panel.xpos = true;
			break;
    case 87:// w
      panel.moveYNeg = true;
      break;
		case 83:// s
      panel.moveYPos = true;
      break;
    case 65:// a
      panel.moveXNeg = true;
      break;
    case 68:// d
      panel.moveXPos = true;
      break;
    }
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		switch (ke.getKeyCode()) {
		case 44:// , key
			panel.zneg = false;
			break;
		case 46:// . key
			panel.zpos = false;
			break;
		case 38:// up arrow key
			panel.ypos = false;
			break;
		case 40:// down arrow key
			panel.yneg = false;
			break;
		case 37:// left key
			panel.xneg = false;
			break;
		case 39:// right key
			panel.xpos = false;
			break;
    case 87:// w
      panel.moveYNeg = false;
      break;
		case 83:// s
      panel.moveYPos = false;
      break;
    case 65:// a
      panel.moveXNeg = false;
      break;
    case 68:// d
      panel.moveXPos = false;
      break;
		}
	}
  
  @Override
  public void actionPerformed(ActionEvent e)
  {
    partOb p1 = new partOb();
    ArrayList<String> inputArr = new ArrayList<String>();
    int i = 0;
    while(true){
      String input = JOptionPane.showInputDialog(null, "Enter coordinate({0,0,0} form): ");
      int exit = JOptionPane.showConfirmDialog(null, "Enter Another Cooridinate?", "Enter", JOptionPane.YES_NO_OPTION);
      if (exit == 1)
        break;
      inputArr.add(input);
      i++;
    }
    String input = JOptionPane.showInputDialog(null, "Enter color({0,0,0} form): ");
  }

  public JButton createB;
  
	public class Draw extends JPanel{
		public Draw() {
			z = 0;
			y = 0;
			x = 0;

			zpos = false;
			zneg = false;
			ypos = false;
			yneg = false;
			xpos = false;
			xneg = false;
      
      moveXPos = false;
      moveYPos = false;
      moveXNeg = false;
      moveYNeg = false;
      
      moveX = 0;
      moveY = 0;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			setRotation();
			setDisplayCoordinates();
			setCamera();

			g.setColor(Color.RED);
			g.drawLine(centerX + xX, centerY + xY, centerX - xX, centerY - xY);

			g.setColor(Color.BLUE);
			g.drawLine(centerX + yX, centerY + yY, centerX - yX, centerY - yY);

			g.setColor(Color.BLACK);
			g.drawLine(centerX + zX, centerY + zY, centerX - zX, centerY - zY);

			drawAll(g);

			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
			g.drawString("X Deg: " + x, 10, winHeight - 50);
			g.drawString("Y Deg: " + y, 100, winHeight - 50);
			g.drawString("Z Deg: " + z, 190, winHeight - 50);
      g.drawString("X Pos: " + moveX, 280, winHeight - 50);
			g.drawString("Y Pos: " + moveY, 370, winHeight - 50);
		}

		public void drawAll(Graphics g) {
			for (int i = 0; i < partList.length; i++) {
				rotMatrix(i, partList[i]);
			}
			setOrder();
			for (int i = 0; i < partList.length; i++) {
				setPoints(copyPartList[i], g,
						copyPartList[i][copyPartList[i].length - 1][0],
						copyPartList[i][copyPartList[i].length - 1][1],
						copyPartList[i][copyPartList[i].length - 1][2]);
			}
		}

		public void rotMatrix(int index, int part[][]) {
			int copyPart[][] = new int[part.length][part[0].length];
//			 double cosY = Math.cos(Math.toRadians(y));
//			 double cosX = Math.cos(Math.toRadians(x));
//			 double cosZ = Math.cos(Math.toRadians(z));
//			 double sinY = Math.sin(Math.toRadians(y));
//			 double sinX = Math.sin(Math.toRadians(x));
//			 double sinZ = Math.sin(Math.toRadians(z));
			for (int i = 0; i < copyPart.length - 1; i++) {
				//doesn't work
				// copyPart[i][0] = (int)
				// (cosZ*cosY*part[i][0]+(-sinZ)*part[i][1]+sinY*part[i][2]);
				// copyPart[i][1] = (int)
				// (sinZ*part[i][0]+cosZ*cosX*part[i][1]+(-sinX)*part[i][2]);
				// copyPart[i][2] = (int)
				// ((-sinY)*part[i][0]+sinX*part[i][1]+cosX*cosY*part[i][2]);
				//new
//				copyPart[i][0] = (int) (cosY*cosZ*part[i][0]+cosY*cosZ*part[i][1]+(-sinY)*part[i][2]);
//				copyPart[i][1] = (int) ((cosZ*sinX*sinY-cosX*sinZ)*part[i][0]+(cosX*cosZ+sinX*sinY*sinZ)*part[i][1]+cosY*sinX*part[i][2]);
//				copyPart[i][2] = (int) ((cosX*cosZ*sinY+sinX*sinZ)*part[i][0]+((-cosZ)*sinX+cosX*sinY*sinZ)*part[i][1]+cosX*cosY*part[i][2]);
				//original
				copyPart[i][0] = (int) (Math.cos(Math.toRadians(y))* part[i][0] + Math.sin(Math.toRadians(y))* Math.sin(Math.toRadians(x)) * part[i][1] - Math.sin(Math.toRadians(y))* Math.cos(Math.toRadians(x))* part[i][2])+moveX;
				copyPart[i][1] = (int) (Math.cos(Math.toRadians(x))* part[i][1] + Math.sin(Math.toRadians(x)) * part[i][2]+moveY);
				copyPart[i][2] = (int) (Math.sin(Math.toRadians(y))* part[i][0] + Math.cos(Math.toRadians(y))* (-Math.sin(Math.toRadians(x)) * part[i][1]) + Math.cos(Math.toRadians(y))* Math.cos(Math.toRadians(x))* part[i][2]);
			}
			copyPart[copyPart.length - 1][0] = part[part.length - 1][0];
			copyPart[copyPart.length - 1][1] = part[part.length - 1][1];
			copyPart[copyPart.length - 1][2] = part[part.length - 1][2];
			copyPartList[index] = copyPart;
		}

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

		public void setPoints(int part[][], Graphics g, int red, int green,
				int blue) {
			int xArray[] = new int[part.length - 1];
			for (int i = 0; i < part.length - 1; i++) {
				xArray[i] = part[i][0];
			}
			int yArray[] = new int[part.length - 1];
			for (int i = 0; i < part.length - 1; i++) {
				yArray[i] = part[i][1];
			}
			int zArray[] = new int[part.length - 1];
			for (int i = 0; i < part.length - 1; i++) {
				zArray[i] = part[i][2];
			}
			int xPoints[] = new int[part.length - 1];
			int yPoints[] = new int[part.length - 1];
			for (int i = 0; i < part.length - 1; i++) {
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
//			for (int i = 0; i < xPoints.length; i++) {
//				g.drawString("" + i + "", xPoints[i], yPoints[i]);
//			}
		}

		public void setCamera() {
			curCamera[0] = (int) (camera[1] * Math.sin(Math.toRadians(coZ)
					* Math.cos(Math.toRadians(coX))));
			curCamera[1] = (int) (camera[1] * Math.sin(Math.toRadians(coZ)
					* Math.cos(Math.toRadians(coX))));
			curCamera[2] = (int) (camera[1] * Math.sin(Math.toRadians(coX)));
		}

		public int averageCo(int part[][], int coI) {
			int ave = 0;
			for (int i = 0; i < part.length - 1; i++) {
				ave += part[i][coI];
			}
			return ave / (part.length - 1);
		}

		public double camDist(int part[][]) {
			return Math
					.sqrt(((averageCo(part, 0) - curCamera[0]) * (averageCo(
							part, 0) - curCamera[0]))
							+ ((averageCo(part, 1) - curCamera[1]) * (averageCo(
									part, 1) - curCamera[1]))
							+ ((averageCo(part, 2) - curCamera[2]) * (averageCo(
									part, 2) - curCamera[2])));
		}

		public void setOrder() {
			for (int i = 0; i < copyPartList.length; i++) {
				for (int ii = 0; ii < copyPartList.length - 1; ii++) {
					if (camDist(copyPartList[ii]) < camDist(copyPartList[ii + 1])) {
						int tempArr[][] = copyPartList[ii];
						copyPartList[ii] = copyPartList[ii + 1];
						copyPartList[ii + 1] = tempArr;
					}
				}
			}
		}

		public int map(int x, int in_min, int in_max, int out_min, int out_max) {
			return (x - in_min) * (out_max - out_min) / (in_max - in_min)
					+ out_min;
		}

		public void setRotation() {
			if (zneg) {
				z--;
				if (z < 0) {
					z += 360;
				}
			}
			if (zpos) {
				z++;
				if (z > 360) {
					z -= 360;
				}
			}
			if (yneg) {
				y--;
				if (y < 0) {
					y += 360;
				}
			}
			if (ypos) {
				y++;
				if (y > 360) {
					y -= 360;
				}
			}
			if (xneg) {
				x--;
				if (x < 0) {
					x += 360;
				}
			}
			if (xpos) {
				x++;
				if (x > 360) {
					x -= 360;
				}
			}
      if (moveXPos){
        moveX +=2;
        moveY -=2;
      }
      if (moveYPos){
        moveY +=4;
        moveX +=4;
      }
      if (moveXNeg){
        moveX -=2;
        moveY +=2;
      }
      if (moveYNeg){
        moveY -=4;
        moveX -=4;
      }
		}

		int z;
		int y;
		int x;
		boolean zpos;
		boolean zneg;
		boolean ypos;
		boolean yneg;
		boolean xpos;
		boolean xneg;
    
    boolean moveXPos;
    boolean moveYPos;
    boolean moveXNeg;
    boolean moveYNeg;
    int moveX;
    int moveY;

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

		int part1[][] = { 
			{ -75, -75, -75 }, 
			{ -75, 75, -75 }, 
			{ 75, 75, -75 },
			{ 75, -75, -75 }, 
			{ 255, 0, 0 } 
		};
		
		int part4[][] = { 
			{ -75, -75, 75 }, 
			{ -75, 75, 75 }, 
			{ 75, 75, 75 },
			{ 75, -75, 75 }, 
			{ 255, 255, 0 } 
		};

		int part2[][] = {
			{ -75, -75, -75 }, 
			{ -75, 75, -75 }, 
			{ -75, 75, 75 },
			{ -75, -75, 75 }, 
			{ 0, 255, 0 } 
		};
		
		int part5[][] = {
			{ 75, -75, -75 }, 
			{ 75, 75, -75 }, 
			{ 75, 75, 75 },
			{ 75, -75, 75 }, 
			{ 0, 255, 255 } 
		};

		int part3[][] = { 
			{ -75, -75, -75 }, 
			{ 75, -75, -75 },
			{ 75, -75, 75 },
			{ -75, -75, 75 }, 
			{ 0, 0, 255 } 
		};

		int part6[][] = { 
			{ -75, 75, -75 }, 
			{ 75, 75, -75 },
			{ 75, 75, 75 },
			{ -75, 75, 75 }, 
			{ 255, 0, 255 } 
		};
		
		int partList[][][] = { 
			part1, 
			part2, 
			part3,
			part4,
			part5
		};

		int copyPartList[][][] = partList.clone();
		int camera[] = { 0, 1000, 0 };
		int curCamera[] = { 0, 1000, 0 };
	}
  
  public class partOb{
    int coordinates[][] = {};
    int color[] = {};
    
    public void setProperties(int props[][]){
      for (int i = 0; i < props.length-1; i++){
        coordinates[i] = props[i];
      }
      for (int i = 0; i < 3; i++){
        color[i] = props[props.length-1][i];
      }
    }
    
    public int[][] getCoordinates(){
      return coordinates;
    }
    
    public int[] getColor(){
      return color;
    }
    
  }
  
	Draw panel;
	int winWidth = 500;
	int winHeight = 500;
}
