package WizardTD;
import processing.core.PApplet;
public class ManaPoolSpell
{
    private float manaPoolSpellInitialCost;

    private float manaPoolSpellCurrentCost;

    private float manaPoolSpellCostIncreasePerUse;

    private float manaPoolSpellCapMultiplier;

    private float manaPoolSpellManaGainedMultiplier;

    public ManaPoolSpell(float manaPoolSpellInitialCost, float manaPoolSpellCostIncreasePerUse, float manaPoolSpellCapMultiplier, float manaPoolSpellManaGainedMultiplier) {
        this.manaPoolSpellInitialCost = manaPoolSpellInitialCost;
        this.manaPoolSpellCurrentCost = manaPoolSpellInitialCost;
        this.manaPoolSpellCostIncreasePerUse = manaPoolSpellCostIncreasePerUse;
        this.manaPoolSpellCapMultiplier = manaPoolSpellCapMultiplier;
        this.manaPoolSpellManaGainedMultiplier = manaPoolSpellManaGainedMultiplier;
    }

    /**
     * This method responsible for update the value of mana pool speel after a press/click from user
     */
    public void update()
    {
        manaPoolSpellCurrentCost += manaPoolSpellCostIncreasePerUse;
        manaPoolSpellManaGainedMultiplier += 0.1;
    }

    public float getManaPoolSpellCurrentCost() {
        return manaPoolSpellCurrentCost;
    }

    public float getManaPoolSpellCapMultiplier() {
        return manaPoolSpellCapMultiplier;
    }

    public float getManaPoolSpellManaGainedMultiplier() {
        return manaPoolSpellManaGainedMultiplier;
    }


}
