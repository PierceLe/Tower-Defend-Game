package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.*;

class ManaTest extends Mana
{
    PApplet pApplet = new PApplet();


    Mana mana = new Mana(pApplet, 100, 100, 100);


    @Test
    public void Test1()
    {
        assertEquals(100, mana.getCurrentMana());
        assertEquals(100, mana.getInitialManaCap());
        mana.setDoubleSpeed(true);
        mana.setCurrentMana(100);
        mana.setPause(true);
        mana.setInitialManaCap(100);
        mana.setPause(true);
        mana.setInitialManaGainedPerSecond(100);
        mana.setGainMultiplier(100);
    }

    @Test
    public void Test2()
    {
        mana.increaseManaPerSecond();
    }

    @Test
    public void Test3()
    {
        mana.updateMana(new PVector(1, 1));
        assertEquals(101, mana.getCurrentMana());
    }


}