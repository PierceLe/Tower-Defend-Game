package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.*;

class ButtonTest
{
    PApplet pApplet = new PApplet();
    Button b = new Button(pApplet, 2, 4, "dj", "dudududu");

    @Test
    void Test1()
    {
        assertNotNull(b);
    }

    @Test
    void Test2()
    {
        b.press();
    }

    @Test
    void Test3()
    {
        float x = b.getX();
        float y = b.getY();

        assertEquals(2.0, x);
        assertEquals(4.0, y);
    }

    @Test
    void Test4()
    {
        assertEquals(false, b.checkHover());
        b.checkHover();
    }

    @Test
    void Test5()
    {
        assertEquals(false, b.isPressed());
    }

    @Test
    void Test6()
    {
        b.setDescription("tjdkjdkjd");
    }

    @Test
    void Test7()
    {
        b.clickOn();
    }

}