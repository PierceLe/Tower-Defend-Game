package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Fireball implements WaveSpeed
{
    private PApplet pApplet;
    private PImage img;
    private PVector currentPos;
    private PVector targetPos;
    private float speed = 5;

    private boolean freeze = false;
    private float constSpeed = 5;
    private float damage;

    private int IDTarget;


    public PImage getImg() {
        return img;
    }

    public int getIDTarget() {
        return IDTarget;
    }

    public float getDamage() {
        return damage;
    }


    public Fireball(PApplet pApplet, PImage img, float damage)
    {
        this.pApplet = pApplet;
        this.img = img;
        this.damage = damage;
    }

    public Fireball(float damage, PVector currentPos, PVector targetPos, int IDTarget, boolean freeze)
    {
        this.damage = damage;
        this.currentPos = currentPos;
        this.targetPos = targetPos;
        this.IDTarget = IDTarget;
        this.freeze = freeze;
    }

    public void setup(PApplet pApplet, PImage img)
    {
        this.pApplet = pApplet;
        this.img = img;
    }

    /**
     * This method make the fireball to move to monster
     */
    public void move()
    {
        PVector direction = PVector.sub(targetPos, currentPos);
        if (direction.mag() > 0)
        {
            float newSpeed = Math.min(speed, direction.mag()); //change speed caculation
            direction.normalize();
            direction.mult(newSpeed);
            currentPos = currentPos.add(direction);
        }
    }

    /**
     * This method display the fireball to screen
     */

    public void display()
    {
        pApplet.image(img, currentPos.x, currentPos.y, 5, 5);
        move();
    }

    /**
     * This method check the fireball is get to the monster or not
     * @return True if it get to the monster, else return False
     */
    public boolean checkOnTarget()
    {
        PVector dir = PVector.sub(targetPos, currentPos);
        return dir.mag() == 0;
    }

    /**
     * This method set the speed of fireball to 0
     */
    public void downSpeed()
    {
        speed = 0;
    }

    /**
     * This method multiples the speed of fireball by two
     */
    public void upSpeed()
    {
        speed = constSpeed*2;
    }

    /**
     * This method make the speed come back to its initial speed
     */
    public void resetSpeed()
    {
        speed = constSpeed;
    }

    public boolean isFreeze() {
        return freeze;
    }

}