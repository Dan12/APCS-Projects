package survive;

import java.awt.Color;
import java.awt.Graphics;

public class LineCheck {
    private int[] xPoints;
    private int[] yPoints;
    private int[] xPoints2;
    private int[] yPoints2;
    private int side;
    
    //Constructor, initializes variables
    public LineCheck(){
        xPoints = new int[4];
        yPoints = new int[4];
        xPoints2 = new int[4];
        yPoints2 = new int[4];
        side = -1;
    }
    
    //Draw the two lines
    public void drawLine(Graphics g){
        if(side != -1){
            g.setColor(new Color(0,255,0,100));
            g.fillPolygon(xPoints, yPoints, 4);
            g.fillPolygon(xPoints2, yPoints2, 4);
        }
    }
    
    /*Set the two lines based on the x and y position of the cursor
    **and the side of the square that the cursor is on*/
    public void onSquare(int x, int y, int s, Map map, Square sq){
    	//Change screen coordinates to map coordinates
        int xStart = x+map.getOffsetX();
        int yStart = y+map.getOffsetY();
        int xStart2 = xStart;
        int yStart2 = yStart;
        int lineB = 0;
        side = s;
        switch(side){
            //top
            case 0:{
            	//normalizing y on top
                yStart = sq.getY()+map.getOffsetY();
                yStart2 = yStart;
                
                //line 1
                /*Both lines are lines with a slope of 1 represented as 
                **y = x + b, therefore b = y-x. However, due to the y plane
                **being inverted in java, we invert the y, resulting in b = -y-x*/
                lineB = -yStart-xStart;
                //check if this line will hit the left boundary before the top boundary
                if(yStart > map.getWidth()-xStart){
                	//solve for y if x = left boundary(map width) (y = x+b) and invert y back to java plane
                    int yEnd = -(lineB+map.getWidth());
                    //convert map coordinates to screen coordinates
                    yEnd-=map.getOffsetY();
                    yStart-=map.getOffsetY();
                    xStart-=map.getOffsetX();
                    //set x and y points of polygon in screen coordinates
                    yPoints = new int[]{yStart,yStart,yEnd+Main.PLAYER_DIM/2,yEnd-Main.PLAYER_DIM/2};
                    xPoints = new int[]{xStart-Main.PLAYER_DIM/2,xStart+Main.PLAYER_DIM/2,map.getWidth()-map.getOffsetX(),map.getWidth()-map.getOffsetX()};
                }
                else{
                    //line will hit top boundary, solve for x when y = 0 (x = -b)
                    int xEnd = -lineB;
                    //convert map coordinates to screen coordinates
                    xEnd-=map.getOffsetX();
                    yStart-=map.getOffsetY();
                    xStart-=map.getOffsetX();
                    //set x and y points of polygon in screen coordinates
                    yPoints = new int[]{yStart,yStart,-map.getOffsetY(),-map.getOffsetY()};
                    xPoints = new int[]{xStart-Main.PLAYER_DIM/2,xStart+Main.PLAYER_DIM/2,xEnd+Main.PLAYER_DIM/2,xEnd-Main.PLAYER_DIM/2};
                }
                //the same process is repeated seven more times for different parameters
                
                //line 2
                lineB = -yStart2+xStart2;
                if(yStart2 > xStart2){
                    int yEnd = -lineB;
                    yEnd-=map.getOffsetY();
                    yStart2-=map.getOffsetY();
                    xStart2-=map.getOffsetX();
                    yPoints2 = new int[]{yStart2,yStart2,yEnd+Main.PLAYER_DIM/2,yEnd-Main.PLAYER_DIM/2};
                    xPoints2 = new int[]{xStart2+Main.PLAYER_DIM/2,xStart2-Main.PLAYER_DIM/2,-map.getOffsetX(),-map.getOffsetX()};
                }
                else{
                    int xEnd = lineB;
                    xEnd-=map.getOffsetX();
                    yStart2-=map.getOffsetY();
                    xStart2-=map.getOffsetX();
                    yPoints2 = new int[]{yStart2,yStart2,-map.getOffsetY(),-map.getOffsetY()};
                    xPoints2 = new int[]{xStart2-Main.PLAYER_DIM/2,xStart2+Main.PLAYER_DIM/2,xEnd+Main.PLAYER_DIM/2,xEnd-Main.PLAYER_DIM/2};
                }
                break;
            }
            //right
            case 1:{
            	//normalizing x on right
                xStart = sq.getX()+sq.getWidth()+map.getOffsetX();
                xStart2 = xStart;
                
                //line 1
                lineB = -yStart-xStart;
                if(yStart > map.getWidth()-xStart){
                    int yEnd = -(lineB+map.getWidth());
                    yEnd-=map.getOffsetY();
                    yStart-=map.getOffsetY();
                    xStart-=map.getOffsetX();
                    yPoints = new int[]{yStart-Main.PLAYER_DIM/2,yStart+Main.PLAYER_DIM/2,yEnd+Main.PLAYER_DIM/2,yEnd-Main.PLAYER_DIM/2};
                    xPoints = new int[]{xStart,xStart,map.getWidth()-map.getOffsetX(),map.getWidth()-map.getOffsetX()};
                }
                else{
                    int xEnd = -lineB;
                    xEnd-=map.getOffsetX();
                    yStart-=map.getOffsetY();
                    xStart-=map.getOffsetX();
                    yPoints = new int[]{yStart-Main.PLAYER_DIM/2,yStart+Main.PLAYER_DIM/2,-map.getOffsetY(),-map.getOffsetY()};
                    xPoints = new int[]{xStart,xStart,xEnd+Main.PLAYER_DIM/2,xEnd-Main.PLAYER_DIM/2};
                }
                
                //line 2
                lineB = -yStart2+xStart2;
                if(map.getHeight()-yStart2 > map.getWidth()-xStart2){
                    int yEnd = -(lineB-map.getWidth());
                    yEnd-=map.getOffsetY();
                    yStart2-=map.getOffsetY();
                    xStart2-=map.getOffsetX();
                    yPoints2 = new int[]{yStart2+Main.PLAYER_DIM/2,yStart2-Main.PLAYER_DIM/2,yEnd-Main.PLAYER_DIM/2,yEnd+Main.PLAYER_DIM/2};
                    xPoints2 = new int[]{xStart2,xStart2,map.getWidth()-map.getOffsetX(),map.getWidth()-map.getOffsetX()};
                }
                else{
                    int xEnd = (lineB+map.getHeight());
                    xEnd-=map.getOffsetX();
                    yStart2-=map.getOffsetY();
                    xStart2-=map.getOffsetX();
                    yPoints2 = new int[]{yStart2-Main.PLAYER_DIM/2,yStart2+Main.PLAYER_DIM/2,map.getHeight()-map.getOffsetY(),map.getHeight()-map.getOffsetY()};
                    xPoints2 = new int[]{xStart2,xStart2,xEnd-Main.PLAYER_DIM/2,xEnd+Main.PLAYER_DIM/2};
                }
                break;
            }
            //bottom
            case 2:{
            	//normalizing y on bottom
                yStart = sq.getY()+sq.getHeight()+map.getOffsetY();
                yStart2 = yStart;
                
                //line 1
                lineB = -yStart-xStart;
                if(map.getHeight()-yStart > xStart){
                    int yEnd = -(lineB);
                    yEnd-=map.getOffsetY();
                    yStart-=map.getOffsetY();
                    xStart-=map.getOffsetX();
                    yPoints = new int[]{yStart,yStart,yEnd+Main.PLAYER_DIM/2,yEnd-Main.PLAYER_DIM/2};
                    xPoints = new int[]{xStart-Main.PLAYER_DIM/2,xStart+Main.PLAYER_DIM/2,-map.getOffsetX(),-map.getOffsetX()};
                }
                else{
                    int xEnd = (-map.getHeight()-lineB);
                    xEnd-=map.getOffsetX();
                    yStart-=map.getOffsetY();
                    xStart-=map.getOffsetX();
                    yPoints = new int[]{yStart,yStart,map.getHeight()-map.getOffsetY(),map.getHeight()-map.getOffsetY()};
                    xPoints = new int[]{xStart-Main.PLAYER_DIM/2,xStart+Main.PLAYER_DIM/2,xEnd+Main.PLAYER_DIM/2,xEnd-Main.PLAYER_DIM/2};
                }
                
                //line 2
                lineB = -yStart2+xStart2;
                if(map.getHeight()-yStart2 > map.getWidth()-xStart2){
                    int yEnd = -(lineB-map.getWidth());
                    yEnd-=map.getOffsetY();
                    yStart2-=map.getOffsetY();
                    xStart2-=map.getOffsetX();
                    yPoints2 = new int[]{yStart2,yStart2,yEnd+Main.PLAYER_DIM/2,yEnd-Main.PLAYER_DIM/2};
                    xPoints2 = new int[]{xStart2+Main.PLAYER_DIM/2,xStart2-Main.PLAYER_DIM/2,map.getWidth()-map.getOffsetX(),map.getWidth()-map.getOffsetX()};
                }
                else{
                    int xEnd = (lineB+map.getHeight());
                    xEnd-=map.getOffsetX();
                    yStart2-=map.getOffsetY();
                    xStart2-=map.getOffsetX();
                    yPoints2 = new int[]{yStart2,yStart2,map.getHeight()-map.getOffsetY(),map.getHeight()-map.getOffsetY()};
                    xPoints2 = new int[]{xStart2-Main.PLAYER_DIM/2,xStart2+Main.PLAYER_DIM/2,xEnd+Main.PLAYER_DIM/2,xEnd-Main.PLAYER_DIM/2};
                }
                break;
            }
            //left
            case 3:{
            	//normalizing x on left
                xStart = sq.getX()+map.getOffsetX();
                xStart2 = xStart;
                
                //line 1
                lineB = -yStart-xStart;
                if(map.getHeight()-yStart > xStart){
                    int yEnd = -(lineB);
                    yEnd-=map.getOffsetY();
                    yStart-=map.getOffsetY();
                    xStart-=map.getOffsetX();
                    yPoints = new int[]{yStart-Main.PLAYER_DIM/2,yStart+Main.PLAYER_DIM/2,yEnd+Main.PLAYER_DIM/2,yEnd-Main.PLAYER_DIM/2};
                    xPoints = new int[]{xStart,xStart,-map.getOffsetX(),-map.getOffsetX()};
                }
                else{
                    int xEnd = (-map.getHeight()-lineB);
                    xEnd-=map.getOffsetX();
                    yStart-=map.getOffsetY();
                    xStart-=map.getOffsetX();
                    yPoints = new int[]{yStart-Main.PLAYER_DIM/2,yStart+Main.PLAYER_DIM/2,map.getHeight()-map.getOffsetY(),map.getHeight()-map.getOffsetY()};
                    xPoints = new int[]{xStart,xStart,xEnd+Main.PLAYER_DIM/2,xEnd-Main.PLAYER_DIM/2};
                }
                
                //line 2
                lineB = -yStart2+xStart2;
                if(yStart2 > xStart2){
                    int yEnd = -lineB;
                    yEnd-=map.getOffsetY();
                    yStart2-=map.getOffsetY();
                    xStart2-=map.getOffsetX();
                    yPoints2 = new int[]{yStart2-Main.PLAYER_DIM/2,yStart2+Main.PLAYER_DIM/2,yEnd+Main.PLAYER_DIM/2,yEnd-Main.PLAYER_DIM/2};
                    xPoints2 = new int[]{xStart2,xStart2,-map.getOffsetX(),-map.getOffsetX()};
                }
                else{
                    int xEnd = lineB;
                    xEnd-=map.getOffsetX();
                    yStart2-=map.getOffsetY();
                    xStart2-=map.getOffsetX();
                    yPoints2 = new int[]{yStart2-Main.PLAYER_DIM/2,yStart2+Main.PLAYER_DIM/2,-map.getOffsetY(),-map.getOffsetY()};
                    xPoints2 = new int[]{xStart2,xStart2,xEnd-Main.PLAYER_DIM/2,xEnd+Main.PLAYER_DIM/2};
                }
                break;
            }
        }
    }
    
    //Line check is not on a square, won't draw line check
    public void offSquare(){
        side = -1;
    }
    
    public boolean isOff(){
        return side == -1;
    }
    
    public int[] getX1Array(){
        return xPoints;
    }
    
    public int[] getY1Array(){
        return yPoints;
    }
    
    public int[] getX2Array(){
        return xPoints2;
    }
    
    public int[] getY2Array(){
        return yPoints2;
    }
}
