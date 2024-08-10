package WizardTD;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Monster implements WaveSpeed
{
    private PApplet pApplet;
    private PImage type;

    private int ID;
    private float x;
    private float y;

    private ArrayList<PImage> imgDie;
    private ArrayList<PVector> path;

    private int indexPath;

    private float initialHP;

    private float currentHP;

    private float speed;

    private float constantSpeed;

    private float armour;

    private float mana_gain_on_kill;

    private boolean manaGained = true;

    private int frameFreeze = 0;

    private int countframedie;

    public Monster(PApplet pApplet, PImage type, float initialHP, float speed, float armour, float mana_gain_on_kill) {
        this.pApplet = pApplet;
        this.type = type;
        this.initialHP = initialHP;
        this.currentHP = initialHP;

        this.speed = speed;
        this.constantSpeed = speed;
        this.armour = armour;
        this.mana_gain_on_kill = mana_gain_on_kill;
        this.indexPath = 0;
        this.countframedie = 0;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<PImage> getImgDie() {
        return imgDie;
    }

    public void setImgDie(ArrayList<PImage> imgDie) {
        this.imgDie = imgDie;
    }

    public PImage getType() {
        return type;
    }

    public float getInitialHP() {
        return initialHP;
    }


    public float getCurrentHP() {
        return currentHP;
    }


    public float getSpeed() {
        return speed;
    }

    public float getArmour() {
        return armour;
    }

    public float getMana_gain_on_kill() {
        return mana_gain_on_kill;
    }

    public PVector getPosition()
    {
        return new PVector(x, y);
    }

    public PVector getCentrePosition(){
        return new PVector(x + 12, y + 12);
    }

    /**
     * This method return the state of monster
     * @return 1 for live, 2 for death and 3 for had gone to the wizard
     */
    public int checkDied()
    {
        if (currentHP == 0)
        {
            return 2;
        }
        if (indexPath >= path.size())
        {
            return 3;
        }
        return 1;
    }

    /**
     * This method return the mana gained after killing a monster
     * @return
     */
    public float getManaGained()
    {
        if(manaGained == true)
        {
            manaGained = false;
            return mana_gain_on_kill;
        }
        return 0;
    }

    /**
     * This method use to display a monster to screen
     */
    public void display()
    {
        pApplet.image(type, x, y, 18, 18);
    }

    /**
     * This method display the bar showing the remaining heath of monsters
     */
    public void displayHealthBar()
    {
        int[] color = Colors.RED.color.getRGB();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.rect(x, y-4, 25f, 2);

        float pixelCurrentMana = (float) (25.0/initialHP) * currentHP;

        color = Colors.GREEN.color.getRGB();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.rect(x, y - 4, pixelCurrentMana, 2);
    }

    /**
     * This method display the monster death animation
     * @return
     */
    public int displayDied()
    {
        if (imgDie.size() != 0)
        {
            pApplet.image(imgDie.get(0), x, y, 25, 25);
            countframedie += 1;
            if(countframedie % 5 == 0)
            {
                imgDie.remove(0);
            }

        }

        if(imgDie.size() != 0)
        {
            return 2; //show animation
        }
        return 3;

    }

    public int getCountframedie() {
        return countframedie;
    }

    public float getPixelX(float x)
    {
        return x * 32;
    }

    public float getPixelY(float y)
    {
        return y * 32 + 40;
    }


    /**
     * This method spawn the monster to the game
     * @param path the path from destination to wizardHouse given for monsters
     */
    public void born(ArrayList<PVector> path)
    {
        this.path = path;
        PVector bornPosition = path.get(0);
        this.x = getPixelX(bornPosition.x);
        this.y = getPixelY(bornPosition.y);
    }

    /**
     * This method update the status of monster
     * @return 1 for live, 2 for died and 3 for had gone to wizardHouse
     */
    public int update()
    {
        if (currentHP == 0)
        {
            return 2;
        }
        if (indexPath >= path.size())
        {
            return 3;
        }


        PVector target = path.get(indexPath);
        move(target);

        if(getPixelX(target.x) == x && getPixelY(target.y) == y)
        {
            indexPath++;
        }
        return 1;
    }

    /**
     * This method make the monster move on the path
     * @param target the next step of monsters
     */
    public void move(PVector target)
    {

        float speed_move = speed;

        if (frameFreeze > 0) {
            frameFreeze -=1;
            speed_move = speed/5;
        }

        if (x < getPixelX(target.x))
        {
            x += Math.min(speed_move, Math.abs(x - getPixelX(target.x)));
        }
        else if (x > getPixelX(target.x))
        {
            x -= Math.min(speed_move, Math.abs(x - getPixelX(target.x)));
        }
        else if (y < getPixelY(target.y))
        {
            y += Math.min(speed_move, Math.abs(y - getPixelY(target.y)));
        }
        else if (y > getPixelY(target.y))
        {
            y -= Math.min(speed_move, Math.abs(y - getPixelY(target.y)));
        }
    }

    /**
     * This method update the health of monster
     * @param damage the damage given to monster
     */
    public void updateHP(float damage, boolean freeze)
    {
        currentHP = Math.max(0, currentHP - damage);
        if (freeze) {
            frameFreeze = 20;
        }
    }

    /**
     * This method set the speed of monster to 0 (freeze the monster)
     */
    @Override
    public void downSpeed()
    {
        speed = 0;
    }


    /**
     * This method multiply the speed of monster by 2
     */
    public void upSpeed()
    {
        speed = constantSpeed*2;
    }

    /**
     * This method reset the speed of monster to its original speed
     */
    @Override
    public void resetSpeed()
    {
        speed = constantSpeed;
    }

    public boolean isManaGained()
    {
        return manaGained;
    }

}