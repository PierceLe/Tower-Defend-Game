package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PImage;

import static org.junit.jupiter.api.Assertions.*;

class AppTest extends App
{
    private App p = new App();
    @Test
    void Test1()
    {
        assertNotNull(p);
    }
    @Test
    void Test2()
    {
        PImage img = new PImage(20, 40);
        p.rotateImageByDegrees(img, 90);
    }




}