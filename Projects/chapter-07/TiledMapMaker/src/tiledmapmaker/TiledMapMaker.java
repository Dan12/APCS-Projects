package tiledmapmaker;

import javax.swing.JFrame;

public class TiledMapMaker {
    
    public static int screenWidth = 800;
    public static int screenHeight = 600;
    public static int screenWidthPlus = 1100;
    public static int screenHeightPlus = 700;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Map Maker");
        frame.setContentPane(new Window());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(10, 10);
        frame.setVisible(true);
        frame.pack();
    }
    
}
