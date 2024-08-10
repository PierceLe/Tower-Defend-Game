package WizardTD;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;

    public static final int FPS = 60;

    public String configPath;

    public Random random = new Random();

    // Feel free to add any additional methods or attributes you want. Please put classes in different files.
    public static Game game;


    public App()
    {
        game = new Game(this);
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings()
    {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);
        HashMap<String, PImage> Image = new HashMap<String, PImage>();
        PImage GrassImage = loadImage("src/main/resources/WizardTD/grass.png");
        PImage ShrubImage = loadImage("src/main/resources/WizardTD/shrub.png");
        PImage Path0Image = loadImage("src/main/resources/WizardTD/path0.png");
        PImage Path1Image = loadImage("src/main/resources/WizardTD/path1.png");
        PImage Path2Image = loadImage("src/main/resources/WizardTD/path2.png");
        PImage Path3Image = loadImage("src/main/resources/WizardTD/path3.png");
        PImage WizardImage = loadImage("src/main/resources/WizardTD/wizard_house.png");
        PImage imgGremlin = loadImage("src/main/resources/WizardTD/gremlin.png");
        PImage imgGremLin1 = loadImage("src/main/resources/WizardTD/gremlin1.png");
        PImage imgGremLin2 = loadImage("src/main/resources/WizardTD/gremlin2.png");
        PImage imgGremLin3 = loadImage("src/main/resources/WizardTD/gremlin3.png");
        PImage imgGremLin4 = loadImage("src/main/resources/WizardTD/gremlin4.png");
        PImage imgGremLin5 = loadImage("src/main/resources/WizardTD/gremlin5.png");
        PImage imgBeetle = loadImage("src/main/resources/WizardTD/beetle.png");
        PImage Tower0Image = loadImage("src/main/resources/WizardTD/tower0.png");
        PImage Tower1Image = loadImage("src/main/resources/WizardTD/tower1.png");
        PImage Tower2Image = loadImage("src/main/resources/WizardTD/tower2.png");
        PImage Tower3Image = loadImage("src/main/resources/WizardTD/tower3.png");
        PImage fireballImage = loadImage("src/main/resources/WizardTD/fireball.png");
        PImage fireball1Image = loadImage("src/main/resources/WizardTD/fireball1.png");
        Image.put("GrassImage", GrassImage);
        Image.put("ShrubImage", ShrubImage);
        Image.put("PathImage10", Path0Image);
        Image.put("PathImage11", rotateImageByDegrees(Path0Image, 90));
        Image.put("PathImage12", rotateImageByDegrees(Path1Image, 90));
        Image.put("PathImage13", Path1Image);
        Image.put("PathImage14", rotateImageByDegrees(Path1Image, 270));
        Image.put("PathImage15", rotateImageByDegrees(Path1Image, 180));
        Image.put("PathImage16", rotateImageByDegrees(Path2Image, 180));
        Image.put("PathImage17", rotateImageByDegrees(Path2Image, 90));
        Image.put("PathImage18", Path2Image);
        Image.put("PathImage19", rotateImageByDegrees(Path2Image, 270));
        Image.put("PathImage20", Path3Image);
        Image.put("Path3Image", Path3Image);
        Image.put("WizardImage", WizardImage);
        Image.put("gremlin", imgGremlin);
        Image.put("gremlin1", imgGremLin1);
        Image.put("gremlin2", imgGremLin2);
        Image.put("gremlin3", imgGremLin3);
        Image.put("gremlin4", imgGremLin4);
        Image.put("gremlin5", imgGremLin5);
        Image.put("beetle", imgBeetle);
        Image.put("Tower0Image", Tower0Image);
        Image.put("Tower1Image", Tower1Image);
        Image.put("Tower2Image", Tower2Image);
        Image.put("Tower3Image", Tower3Image);
        Image.put("Fireball", fireballImage);
        Image.put("Fireball1",fireball1Image);


        game.setup(Image, this.configPath);
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(){

    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased(){

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /*@Override
    public void mouseDragged(MouseEvent e) {

    }*/

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw()
    {
        if (game.status())
        {
            game = new Game(this);
            setup();
        }
        game.display();
    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, RGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i , j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}