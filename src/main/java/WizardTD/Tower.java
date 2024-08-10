package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class Tower {
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
    private int totalLevel = 0;
    private int rangeLevel = 0;
    private int speedLevel = 0;
    private int damageLevel = 0;

    private int frameShoot;

    private ArrayList<PImage> imgTowers;

    private int indexImg = 0;

    public Tower(PApplet pApplet, ArrayList<PImage> imgTowers,  float initialTowerRange,
                 float initialFiringSpeed,
                 float initialTowerDamage,
                 float totalCost)
    {
        this.pApplet = pApplet;
        this.imgTowers = imgTowers;
        this.initialTowerRange = initialTowerRange;
        this.initialTowerDamage = initialTowerDamage;
        this.initialFiringSpeed = initialFiringSpeed;
        this.totalCost = totalCost;

        this.towerDamage = initialTowerRange;
        this.towerFiringSpeed = initialFiringSpeed;
        this.towerRange = initialTowerRange;

        this.frameShoot = (int) (60 / this.initialFiringSpeed);
    }

    public Tower(PApplet pApplet, ArrayList<PImage> imgTowers)
    {
        this.pApplet = pApplet;
        this.imgTowers = imgTowers;
    }

    /**
     * This method return the cost related to level
     * @param currentLevel the level to update
     * @return
     */
    public int costUpdate(int currentLevel)
    {
        int cost = 0;
        if(currentLevel == 0)
        {
            cost = 20;
        }
        else if(currentLevel == 1)
        {
            cost = 30;
        }
        else if(currentLevel == 2)
        {
            cost = 40;
        }
        return cost;
    }

    /**
     * This method update the range of shootings of towers
     */
    public void updateRange()
    {
        if(rangeLevel + indexImg < 3)
        {
            rangeLevel += 1;
            towerRange += 32;
        }
    }

    /**
     * This method update the speed of shooting of towers
     */
    public void updateSpeed()
    {
        if(speedLevel + indexImg < 3)
        {
            speedLevel += 1;
            towerFiringSpeed += 0.5;
            frameShoot = (int) (60 / towerFiringSpeed);
        }
    }

    /**
     * This method update the damage given by the towers
     */
    public void updateDamage()
    {
        if (damageLevel + indexImg < 3)
        {
            damageLevel += 1;
            towerDamage += (0.5) * initialTowerDamage;
        }
    }

    /**
     * This method responsible for modify attribute related to level
     */
    public void manageLevel()
    {
        if(indexImg == 0 && speedLevel >= 1 && damageLevel >= 1 && rangeLevel >= 1)
        {
            indexImg += 1;
            speedLevel -= 1;
            damageLevel -= 1;
            rangeLevel -= 1;
        }
        if(indexImg == 1 && speedLevel >= 1 && damageLevel >= 1 && rangeLevel >= 1)
        {
            indexImg += 1;
            speedLevel -= 1;
            damageLevel -= 1;
            rangeLevel -= 1;
        }
    }

    /**
     * This method perform an update depend on the state of buttons
     * @param upRange the state of update range button
     * @param upSpeed the state of update speed button
     * @param upDamage the state of update damage button
     */
    public void whenUpdate(boolean upRange, boolean upSpeed, boolean upDamage) //update when click tower
    {
        if(upRange)
        {
            updateRange();
        }
        if(upSpeed)
        {
            updateSpeed();
        }
        if(upDamage)
        {
            updateDamage();
        }
    }

    /**
     *This method update the cost of upgrade tower associated with the button state
     * @param upRange
     * @param upSpeed
     * @param upDamage
     * @return
     */
    public PVector infoUpdate(boolean upRange, boolean upSpeed, boolean upDamage)
    {
        PVector eachUpdate = new PVector(0, 0, 0);

        if (upRange)
        {
            eachUpdate.x = costUpdate(rangeLevel + indexImg);
        }
        if(upSpeed)
        {
            eachUpdate.y = costUpdate(speedLevel + indexImg);
        }
        if(upDamage)
        {
            eachUpdate.z = costUpdate(damageLevel + indexImg);
        }
        return eachUpdate;
    }

    public PApplet getpApplet() {
        return pApplet;
    }

    public void setpApplet(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public ArrayList<PImage> getImgTowers() {
        return imgTowers;
    }

    public void setImgTowers(ArrayList<PImage> imgTowers) {
        this.imgTowers = imgTowers;
    }

    public PVector getPosition()
    {
        return new PVector(x, y);
    }

    public PVector getCenterPosition() {return new PVector(x+15, y+15);};

    public void followMouse(float x, float y)
    {
        this.x = x;
        this.y = y;
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

    /**
     * This method display the tower's upgrade symbols
     */
    public void displayRangeLevel()
    {
        String txtLevel = "";

        if (rangeLevel == 1)
        {
            txtLevel = "O";
        }
        else if(rangeLevel == 2)
        {
            txtLevel = "OO";
        }
        else if(rangeLevel == 3)
        {
            txtLevel = "OOO";
        }
        int[] color = Colors.MAGENTA.color.getRGB();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.textSize(8);
        pApplet.textAlign(pApplet.LEFT);
        pApplet.text(txtLevel, x, y - 2, 20, 20);
    }

    /**
     * This method display the tower's damage symbols
     */

    public void displayDamageLevel()
    {
        String txtLevel = "";

        if (damageLevel == 1)
        {
            txtLevel = "X";
        }
        else if(damageLevel == 2)
        {
            txtLevel = "XX";
        }
        else if(damageLevel == 3)
        {
            txtLevel = "XXX";
        }
        int[] color = Colors.MAGENTA.color.getRGB();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.textSize(8);
        pApplet.textAlign(pApplet.LEFT);
        pApplet.text(txtLevel, x, y + 23, 20, 20);

    }

    /**
     * This method display the tower's speed symbols
     */
    public void displaySpeedLevel()
    {
        if (speedLevel == 0)
        {
            return;
        }
        float stroke = 0;
        if (speedLevel == 1)
        {
            stroke = 2f;
        }
        else if (speedLevel == 2)
        {
            stroke = 2.5f;
        }
        else if (speedLevel == 3)
        {
            stroke = 4f;
        }

        int[] color = Colors.BLUE.color.getRGB();
        pApplet.fill(0, 0);
        pApplet.strokeWeight(stroke);
        pApplet.stroke(color[0], color[1], color[2]);
        pApplet.rect(x + 5, y + 5, 20, 20);
        pApplet.noStroke();

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

    /**
     * This method display the tower to the screen
     */
    public void display()
    {
        manageLevel();
        pApplet.image(imgTowers.get(indexImg), x, y, 32, 32);
//        System.out.println("range" + rangeLevel + "speed" + speedLevel + "damage" + damageLevel);
        displayRangeLevel();
        displayDamageLevel();
        displaySpeedLevel();
    }

    /**
     * This method perform the shooting fireballs
     * @param idMonsterInRange the ID of monster that in the shooting range of tower
     * @param posMonster position of the monster
     * @return a fireball instance if there is existed a monster in range, else return null
     */

    public Fireball shoot(int idMonsterInRange, PVector posMonster)
    {
        if (idMonsterInRange == -1)
        {
            return null;
        }
        if(pApplet.frameCount % frameShoot == 0)
        {
            return new Fireball(towerDamage, getCenterPosition(), posMonster, idMonsterInRange, false);
        }
        return null;
    }

}