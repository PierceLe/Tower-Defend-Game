package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PImage;
import processing.core.*;

import static org.junit.jupiter.api.Assertions.*;

class TowerIceTest extends App
{
    PImage pImage3 = new PImage(20, 40);
    TowerIce towerIce = new TowerIce(this, pImage3, 100, 100, 100, 100);
    @Test
    void Test1()
    {
        assertEquals(100, towerIce.getTotalCost());
        assertEquals(100, towerIce.getTowerRange());
        assertEquals(100, towerIce.getTowerFiringSpeed());
        assertEquals(100, towerIce.getTowerDamage());
        assertEquals(pImage3, towerIce.getImgTower());
        assertEquals(new PVector(15, 15), towerIce.getCenterPosition());
    }
    @Test
    void Test2()
    {
        TowerIce t = new TowerIce(this, pImage3, 100, 100, 100, 100);
        assertNotNull(t);
    }

    @Test
    void Test3()
    {
        assertEquals(new PVector(15, 15), towerIce.getCenterPosition());
    }

    @Test
    void Test8()
    {
        towerIce.followMouse(20, 20);
        assertEquals(20, towerIce.getX());
    }
    @Test
    void Test9()
    {
        towerIce.followMouse(0, 0);
        assertEquals(true, towerIce.checkOverTower(20, 20));
        assertEquals(false, towerIce.checkOverTower(80, 80));
    }
    @Test
    void Test10()
    {
        Fireball f1 = towerIce.shoot(-1, new PVector(0, 0));
        assertEquals(null, f1);
    }

    @Test
    void Test11()
    {
        towerIce.setX(20);
    }

}