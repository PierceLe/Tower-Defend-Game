package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class TowerIce {

    private PApplet pApplet;
    private float x;
    private float y;
    private float totalCost;
    private float initialTowerRange;
    private float initialFiringSpeed;
    private float initialTowerDamage;
    private float towerRange;
    private float towerFiringSpeed;
    private float towerDamage;
    private int frameShoot;
    private PImage imgTower;

    public TowerIce(PApplet pApplet, PImage imgTower,  float initialTowerRange,
                    float initialFiringSpeed,
                    float initialTowerDamage,
                    float totalCost)
    {
        this.pApplet = pApplet;
        this.imgTower = imgTower;
        this.initialTowerRange = initialTowerRange;
        this.initialTowerDamage = initialTowerDamage;
        this.initialFiringSpeed = initialFiringSpeed;

        this.totalCost = totalCost;
        this.towerDamage = initialTowerRange;
        this.towerFiringSpeed = initialFiringSpeed;
        this.towerRange = initialTowerRange;

        this.frameShoot = (int) (60 / this.initialFiringSpeed);
    }

    /**
     * This method return the position of tower in the centre point
     * @return the new position which caculated by considering the centre point of tower
     */
    public PVector getCenterPosition() {return new PVector(x+15, y+15);};

    /**
     * This method perfrom the shooting ice fireball to monster
     * @param idMonsterInRange the ID of monster in the shooting range of tower
     * @param posMonster position of monster in the shooting range of tower
     * @return the new ice fireball if exist a monster in range, else return null
     */
    public Fireball shoot(int idMonsterInRange, PVector posMonster)
    {
        if (idMonsterInRange == -1)
        {
            return null;
        }
        if(pApplet.frameCount % frameShoot == 0)
        {
            return new Fireball(towerDamage, getCenterPosition(), posMonster, idMonsterInRange, true);
        }
        return null;
    }

    /**
     * This method check if the mouse position in the button size or not
     * @param MouseX the coordinate of mouse in horizontal
     * @param MouseY the coordinate of mouse in vertical
     * @return True if the mouse is in the button, else return False
     */
    public boolean checkOverTower(float MouseX, float MouseY)
    {
        if (MouseX >= x && MouseX <= x + 32 && MouseY >= y &&  MouseY <= y + 32)
        {
            return true;
        }
        return false;
    }

    public void followMouse(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    /**
     * This method display the tower to the screen
     */

    public void display()
    {
        pApplet.image(imgTower, x, y, 32, 32);
    }

    /**
     * This method display the tower's range symbols
     */
    public void displayRange()
    {
        int[] color = Colors.YELLOW.color.getRGB();
        pApplet.stroke(color[0], color[1], color[2]);
        pApplet.fill(0, 0);
        pApplet.ellipse(x + 16, y + 16, towerRange * 2, towerRange * 2);
        pApplet.noStroke();
    }

    public float getTotalCost() {
        return totalCost;
    }

    public float getTowerRange() {
        return towerRange;
    }

    public float getTowerFiringSpeed() {
        return towerFiringSpeed;
    }

    public float getTowerDamage() {
        return towerDamage;
    }

    public PImage getImgTower() {
        return imgTower;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }
}