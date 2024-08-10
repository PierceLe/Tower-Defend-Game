package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TowerTest extends App
{

    PImage pImage1 = new PImage(20, 40);
    PImage pImage2 = new PImage(20, 40);
    PImage pImage3 = new PImage(20, 40);
    ArrayList<PImage> img = new ArrayList();

    Tower tower = new Tower(this, img, 100, 100, 100, 100);


    @Test
    void Test1()
    {
        img.add(pImage1);
        img.add(pImage2);
        img.add(pImage3);
        assertEquals(100, tower.getTotalCost());
        assertEquals(100, tower.getTowerRange());
        assertEquals(100, tower.getTowerFiringSpeed());
        assertEquals(100, tower.getTowerDamage());
        assertEquals(0, tower.getX());
        assertEquals(0, tower.getY());
        assertNotNull(tower.getImgTowers());
        assertNotNull(tower.getPosition());
        assertNotNull(tower.getCenterPosition());
        assertNotNull(tower.getpApplet());
        tower.setImgTowers(img);
        tower.setX(20);
        tower.setY(20);
        tower.setpApplet(this);
    }

    @Test
    void Test2()
    {
        Tower t = new Tower(this, img);
        assertNotNull(t);
    }

    @Test
    void Test3()
    {
        assertEquals(20, tower.costUpdate(0));
        assertEquals(30, tower.costUpdate(1));
        assertEquals(40, tower.costUpdate(2));
    }

    @Test
    void Test4()
    {
        tower.updateSpeed();
        tower.updateRange();
        tower.updateDamage();
    }

    @Test
    void Test5()
    {
        tower.manageLevel();
    }
    @Test
    void Test6()
    {
        tower.whenUpdate(true, true, true);
    }
    @Test
    void Test7()
    {
        PVector p = tower.infoUpdate(true, true, true);
        assertEquals(new PVector(20, 20, 20), p);
    }

    @Test
    void Test8()
    {
        tower.followMouse(20, 20);
        assertEquals(20, tower.getX());
    }
    @Test
    void Test9()
    {
        tower.followMouse(0, 0);
        assertEquals(true, tower.checkOverTower(20, 20));
        assertEquals(false, tower.checkOverTower(80, 80));
    }
    @Test
    void Test10()
    {
        Fireball f1 = tower.shoot(-1, new PVector(0, 0));
        assertEquals(null, f1);
    }


}