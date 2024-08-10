package WizardTD;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManaPoolSpellTest
{
    ManaPoolSpell mana = new ManaPoolSpell(100, 100, 1.5f, 1.5f);

    @Test
    void Test1()
    {
        float manaPoolSpellCurrentCost = mana.getManaPoolSpellCurrentCost();
        float manaPoolSpellCapMultiplier= mana.getManaPoolSpellCapMultiplier();
        float manaPoolSpellManaGainedMultiplier = mana.getManaPoolSpellManaGainedMultiplier();
        assertEquals(100, manaPoolSpellCurrentCost);
        assertEquals(1.5f, manaPoolSpellCapMultiplier);
        assertEquals(1.5f ,manaPoolSpellManaGainedMultiplier);
    }
    @Test
    void Test2()
    {
        mana.update();
        assertEquals(1.6f, mana.getManaPoolSpellManaGainedMultiplier());
    }
}
