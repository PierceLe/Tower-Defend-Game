package WizardTD;

import processing.core.PApplet;
import processing.core.PVector;

public class UpdateCost
{
    public PApplet pApplet;

    private float x = 645;

    private float y = 530;

    private float width = 85;

    private float height = 40;

    private PVector eachUpdate;

    private boolean chooseRange = false;

    private boolean chooseSpeed = false;

    private boolean chooseDamage = false;
    public UpdateCost(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.eachUpdate = new PVector(0, 0, 0);
    }

    /**
     * This method set the state of button
     * @param chooseRange the state of upgrade range button
     * @param chooseSpeed the state of upgrade speed button
     * @param chooseDamage the state of upgrade damage button
     */
    public void setChoose(boolean chooseRange, boolean chooseSpeed, boolean chooseDamage)
    {
        this.chooseRange = chooseRange;
        this.chooseSpeed = chooseSpeed;
        this.chooseDamage = chooseDamage;
    }

    public void setEachUpdate(PVector eachUpdate) {
        this.eachUpdate = eachUpdate;
    }

    /**
     * This method display the tooltip next to the button showing cost
     * @param posX coordinate of button in horizontal
     * @param posY coordinate of button in vertical
     * @param cost the cost of upgrading or building
     */
    public void Tooltip(float posX, float posY, float cost) {
        posX -= 80;

        int[] color = Colors.WHITE.color.getRGB();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.strokeWeight(1);
        pApplet.stroke(1);
        pApplet.rect(posX, posY, 65, 20);
        pApplet.fill(0);
        pApplet.textSize(13);
        String costRange = String.format("Cost:%4.0f", cost);
        pApplet.text(costRange, posX + 32, posY + 15);
        pApplet.noStroke();

    }

    /**
     * This method display the small box that showing the upgrade cost in the corner of game
     */
    public void display()
    {
        int[] color = Colors.WHITE.color.getRGB();
        float addHeight = 0;

        if (chooseRange)
        {
            addHeight += 15;
        }
        if (chooseSpeed)
        {
            addHeight += 15;
        }
        if (chooseDamage)
        {
            addHeight += 15;
        }

        float totalHeight =  height + addHeight;
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.strokeWeight(2);
        pApplet.stroke(1);
        pApplet.rect(x, y,width,totalHeight );
        pApplet.rect(x, y,width,15 );
        pApplet.rect(x, y+totalHeight-17,width,17 );
        pApplet.noStroke();
        pApplet.fill(0);


        pApplet.textAlign(pApplet.LEFT);
        pApplet.textSize(13);

        addHeight = 0;
        if(chooseRange)
        {
            addHeight += 15;
            String costRange = String.format("range: %5.0f", eachUpdate.x);
            pApplet.text(costRange, x+3, y + addHeight + 13);
        }
        if(chooseSpeed)
        {
            addHeight += 15;
            String costSpeed = String.format("speed: %5.0f", eachUpdate.y);
            pApplet.text(costSpeed, x+3, y+ addHeight + 13);
        }
        if(chooseDamage)
        {
            addHeight += 15;
            String costDamage = String.format("damage: %3.0f", eachUpdate.z);
            pApplet.text(costDamage, x+3, y + addHeight + 13);
        }

        pApplet.text("Update cost", x+3, y + 13);

        String totalCost = String.format("Total: %7.0f", eachUpdate.x + eachUpdate.y + eachUpdate.z);
        pApplet.text(totalCost, x+3, y + totalHeight - 5);
    }
}
