package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest extends App
{
    private PApplet pApplet = new PApplet();
    public PImage rotateImageByDegrees(PImage pimg, double angle)
    {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.pApplet.createImage(newWidth, newHeight, pApplet.RGB);
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

    @Test
    void Test1()
    {
        Board b = new Board(new PApplet());
        assertNotNull(b);
    }
    @Test
    void Test2()
    {
        Board b = new Board(new PApplet());
        assertEquals(b.x_wizardhouse, 0);
    }
    @Test
    void Test3()
    {
        Board b = new Board(new PApplet());
        PVector pos = b.checkValidBuild(32, 72);
        assertEquals(pos, new PVector(b.getPixelX(1), b.getPixelY(1)));
    }

    @Test
    void Test4()
    {
        Board b = new Board(new PApplet());
        b.setTower(32, 72);
        assertEquals(b.map[1][1], 2);
    }
    @Test
    void Test5()
    {
        Board b = new Board(new PApplet());
        b.loadMap("level1.txt");
        assertEquals(b.map[1][1], 0);
    }
    @Test
    void Test6()
    {
        Board b = new Board(new PApplet());
        float x = b.getPixelX(1);
        float y = b.getPixelY(1);
        assertEquals(x, 32);
        assertEquals(y, 72);
    }

    @Test
    void Test7()
    {
        Board b = new Board(new PApplet());
        b.setLayout("level1.txt");
        String layout = b.getLayout();
        assertEquals(layout, "level1.txt");
    }

//    @Test
//    void Test8()
//    {
//
//        Board b = new Board(new PApplet());
//        b.loadMap("level1.txt");
//        b.display();
//    }
}