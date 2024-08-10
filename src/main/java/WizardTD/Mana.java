package WizardTD;

import java.util.*;
import processing.core.*;
public class Mana
{
    private PApplet pApplet;

    private int initialMana;

    private int currentMana;

    private int initialManaCap;

    private int initialManaGainedPerSecond;

    private float gainMultiplier = 0;

    private boolean isPause = false;

    private boolean isDoubleSpeed = false;

    public void setDoubleSpeed(boolean doubleSpeed) {
        isDoubleSpeed = doubleSpeed;
    }

    public Mana(PApplet pApplet, int initialMana, int initialManaCap, int initialManaGainedPerSecond) {
        this.pApplet = pApplet;
        this.initialMana = initialMana;
        this.initialManaCap = initialManaCap;
        this.initialManaGainedPerSecond = initialManaGainedPerSecond;
        this.currentMana = initialMana;
        this.gainMultiplier = 0;
    }

    public Mana()
    {

    }

    /**
     * This method increase the mana after a second
     */
    public void increaseManaPerSecond()
    {
        if (pApplet.frameCount % 60 == 0)
        {
            currentMana += Math.min(initialManaGainedPerSecond, initialManaCap - currentMana);
        }
    }

    /**
     * This method responsible for update the value of mana
     * @param gainMana
     */
    public void updateMana(PVector gainMana)
    {
        gainMana.x = gainMana.x + gainMana.x * gainMultiplier;
        currentMana = Math.min(currentMana + (int)(gainMana.x), initialManaCap);//upper bound
        currentMana = Math.max(currentMana + (int) gainMana.y, 0);
    }

    /**
     * This method display the mana bar to screen
     */
    public void display()
    {
        int[] color;
        if(!isPause)
        {
            increaseManaPerSecond();
            if(isDoubleSpeed)
            {
                increaseManaPerSecond();
            }
        }


        //txt mana
        pApplet.textSize(20);
        pApplet.fill(0);
        pApplet.text("MANA:", 316, 10, 70, 64);
        //bar mana
        pApplet.fill(255);
        pApplet.stroke(0);
        pApplet.strokeWeight(3);
        pApplet.rect(390, 10, 300, 20);
        pApplet.noStroke();

        float pixelCurrentMana = (float) (300.0/initialManaCap) * currentMana;
        color = Colors.LIGHT_BLUE.color.getRGB();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.strokeWeight(2);
        pApplet.stroke(0);
        pApplet.rect(390, 11, pixelCurrentMana, 18);
        pApplet.noStroke();
        pApplet.fill(0);
        pApplet.textSize(18);
        pApplet.textAlign(pApplet.CENTER);
        String displayMana = String.format("%d/%d", currentMana, initialManaCap);
        pApplet.text(displayMana, 390, 10, 300, 64);

    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getInitialManaCap() {
        return initialManaCap;
    }

    public void setInitialManaCap(int initialManaCap) {
        this.initialManaCap = initialManaCap;
    }

    public void setGainMultiplier(float gainMultiplier) {
        this.gainMultiplier = gainMultiplier;
    }

    public void setInitialManaGainedPerSecond(int initialManaGainedPerSecond) {
        this.initialManaGainedPerSecond = initialManaGainedPerSecond;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }


}
