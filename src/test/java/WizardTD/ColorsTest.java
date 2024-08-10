package WizardTD;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorsTest
{
    private Colors colors;

    @Test
    void Test1()
    {
        for(Colors color : colors.values())
        {
            System.out.println(color);
        }
    }


}