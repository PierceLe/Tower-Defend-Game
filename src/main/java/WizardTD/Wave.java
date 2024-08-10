package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.*;

public class Wave
{
    private PApplet pApplet;
    private int duration;

    private float preWavePause;
    private ArrayList<Monster> monsters;

    private ArrayList<Integer> monsterQuantity;

    private ArrayList<Monster> monstersBorn;

    private int genID = 0;

    private int numFrameScape;
    private int numFrameWait;
    private int numFrameDuration;

    private boolean isPause = false;

    private boolean isDoubleSpeed = false;

    private HashMap<Integer, ArrayList<PVector>> pathToWizard;

    public HashMap<Integer, ArrayList<PVector>> getPathToWizard() {
        return pathToWizard;
    }

    public void setPathToWizard(HashMap<Integer, ArrayList<PVector>> pathToWizard) {
        this.pathToWizard = pathToWizard;
    }

    public Wave(PApplet pApplet, int duration, float preWavePause, ArrayList<Monster> monsters, ArrayList<Integer> monsterQuantity) {
        this.pApplet = pApplet;
        this.duration = duration;
        this.preWavePause = preWavePause;
        this.monsters = monsters;
        this.monsterQuantity = monsterQuantity;

        int sumMonster =  0;
        for (int value : monsterQuantity)
            sumMonster+=value;


        this.numFrameScape  = (int) (60 * duration)/sumMonster;
        this.monstersBorn = new ArrayList<>();
        this.pathToWizard = new HashMap<>();
        this.numFrameDuration = duration * 60;
        this.numFrameWait = (int) preWavePause * 60;
        this.genID = 0;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Monster> getMonstersBorn() {
        return monstersBorn;
    }

    public void setMonstersBorn(ArrayList<Monster> monstersBorn) {
        this.monstersBorn = monstersBorn;
    }

    /**
     * This method use to get the duration of waiting in second
     * @return waiting time in second
     */
    public int getSecondWaveWait()
    {
        return (int) this.numFrameWait / 60;
    }

    public boolean isDoubleSpeed() {
        return isDoubleSpeed;
    }

    public void setDoubleSpeed(boolean doubleSpeed) {
        isDoubleSpeed = doubleSpeed;
    }

    /**
     * This method check if all monster death of not
     * @return True if all monster died, else return False
     */
    public boolean checkAllDied()
    {
        if (monstersBorn.size() == 0)
        {
            return true;
        }
        return false;
    }
    /**
     * This method check if all monster are born or not
     * @return True if all monster are born, else return False
     */
    public boolean checkAllBorn()
    {
        if (numFrameDuration == 0)
        {
            return true;
        }
        return false;
    }




    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Wave{" +
                "duration=" + duration +
                ", pre_wave_pause=" + preWavePause +
                ", monsters=" + monsters +
                ", monsters_quantity=" + monsterQuantity+
                ", num_frame_scape=" +numFrameScape+
                '}';
    }

    /**
     * This method spawn monster in the wave to game
     */
    public void born()
    {
        if (monsterQuantity.size() != 0)
        {
            Random rand = new Random();
            int index = rand.nextInt(monsterQuantity.size());
            System.out.println(monsterQuantity.size());
            int quantity = monsterQuantity.get(index);
            quantity -= 1;
            monsterQuantity.set(index, quantity);

            Monster monsterPrototype = monsters.get(index);
            Monster monster = new Monster(pApplet,
                    monsterPrototype.getType(),
                    monsterPrototype.getInitialHP(),
                    monsterPrototype.getSpeed(),
                    monsterPrototype.getArmour(),
                    monsterPrototype.getMana_gain_on_kill());

            monster.setImgDie((ArrayList<PImage>)monsterPrototype.getImgDie().clone());
            monster.born(pathToWizard.get(rand.nextInt(pathToWizard.size())));
            monster.setID(genID);
            monstersBorn.add(monster);
            genID += 1;

            if (quantity == 0) {
                monsters.remove(index);
                monsterQuantity.remove(index);
            }
        }
    }

    /**
     * This method display everything in the wave to game
     * @return the mana change after a frame
     */
    public PVector display()
    {
        PVector manaGained = new PVector(0, 0);
        if (numFrameWait > 0)
        {
            if (!isPause)
            {
                numFrameWait --;
                if (isDoubleSpeed)
                {
                    numFrameWait--;
                }
            }
        }
        if (numFrameWait == 0)
        {
            if (numFrameDuration > 0)
            {
                if (!isPause)
                {
                    numFrameDuration--;
                    if(isDoubleSpeed)
                    {
                        numFrameDuration--;
                    }
                }

            }
            for(Monster monster : monstersBorn)
            {
                if(isPause)
                {
                    monster.downSpeed();
                }
                else if(isDoubleSpeed)
                {
                    monster.upSpeed();
                }
                else
                {
                    monster.resetSpeed();
                }
            }
            if (monstersBorn.size() != 0)
            {
                for(int i = 0; i < monstersBorn.size(); i++)
                {
                    Monster monster = monstersBorn.get(i);
                    int state = monster.update();
                    if (state == 1)
                    {
                        monster.display();
                    }
                    if(state == 2)
                    {
                        state = monster.displayDied();
                        manaGained.x += monster.getManaGained();
                    }
                    if(state == 3)
                    {
                        monstersBorn.remove(i);
                        if(monster.isManaGained())
                        {
                            manaGained.y -= monster.getCurrentHP();
                        }
                    }

                }
            }
            for(Monster monster: monstersBorn)
            {
                if (monster.checkDied() == 1)
                {
                    monster.displayHealthBar();
                }

            }
            if (numFrameDuration % numFrameScape == 0 && isPause == false)
            {
                born();
            }
        }
        return manaGained;
    }


}