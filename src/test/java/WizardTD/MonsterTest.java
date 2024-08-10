package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest
{
    PApplet pApplet = new PApplet();
    PImage pImage = new PImage(20, 40);
    Monster monster = new Monster(pApplet, pImage, 100, 10, 100, 10);
//
//    @Test
//    void Test1()
//    {
//        ArrayList<PVector> path = new ArrayList<>();
//        path.add(new PVector(1, 1));
//        path.add(new PVector(2, 2));
//        monster.setPath(path);
//        int state = monster.checkDied();
//        assertEquals(1, state);
//    }

}