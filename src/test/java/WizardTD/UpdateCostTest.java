package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.*;

class UpdateCostTest
{
    UpdateCost update = new UpdateCost(new PApplet());

    @Test
    void Test1()
    {
        update.setChoose(true, true, true);
        update.setEachUpdate(new PVector(1, 1, 1));

    }

}