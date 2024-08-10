package WizardTD;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest
{
    private Color color = new Color(0, 0, 0);

    @Test
    void Test1()
    {
        assertNotNull(color);
    }
    @Test
    void Test2()
    {
        int[] rgb = color.getRGB();
        assertNotNull(rgb);
    }

}