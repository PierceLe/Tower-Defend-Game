package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PImage;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.*;

class FireballTest extends App
{
    PImage img = new PImage(20, 40);
    Fireball f = new Fireball(100, new PVector(10, 10), new PVector(10, 10),2, false);
    @Test
    void Test0()
    {   Fireball b = new Fireball(this, img, 100);
        assertNotNull(b);
    }
    @Test
    void Test1()
    {
        f.setup(this, img);
        assertEquals(img, f.getImg());
        assertEquals(2, f.getIDTarget());
        assertEquals(100, f.getDamage());
    }

    @Test
    void Test2()
    {
        f.move();
    }

    @Test
    void Test3()
    {
        assertEquals(true, f.checkOnTarget());
    }

    @Test
    void Test4()
    {
        f.upSpeed();
        f.downSpeed();
        f.resetSpeed();
    }

    @Test
    void Test5()
    {
        assertEquals(false, f.isFreeze());
    }


}