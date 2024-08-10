package WizardTD;
import processing.core.PApplet;
import org.json.simple.*;
import org.json.simple.parser.*;
import processing.core.PImage;
import processing.core.PVector;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
public class Game
{
    private PApplet pApplet;
    private Board board;
    private Element element;

    private ArrayList<Wave> waves;
    private Wave wave;
    private int waveNum;

    private ArrayList<Monster> monsters;

    private ArrayList<Tower> towers;
    private Tower tower; //current tower follow mouse
    private Tower towerPrototype;

    private ArrayList<TowerIce> towersIce;
    private TowerIce towerIce; //current tower follow mouse
    private TowerIce towerIcePrototype;


    private Fireball fireballPrototype;
    private Fireball fireballIcePrototype;
    private ArrayList<Fireball> fireballs;

    private boolean checkPause = false;
    private boolean checkDoubleSpeed = false;

    private UpdateCost updateInfo;

    private Mana mana;
    private ManaPoolSpell manaPoolSpell;

    private Result result;
    private int numOfCast = 1;

    public Game(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.board = new Board(pApplet);
        this.element = new Element(pApplet);
        this.waves = new ArrayList<Wave>();
        this.monsters = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.towersIce = new ArrayList<>();
        this.fireballs = new ArrayList<>();
        this.updateInfo = new UpdateCost(pApplet);
        this.result = new Result(pApplet);
    }


    /**
     * This method preparing and loading the needed data to create instances
     * @param images images of elements in the game
     * @param srcJson the path to the config file
     */
    public void setup(HashMap<String,PImage> images, String srcJson)
    {
        loadJSON(srcJson, images);
        fireballPrototype = new Fireball(this.pApplet, images.get("Fireball"), 0);
        fireballIcePrototype = new Fireball(this.pApplet, images.get("Fireball1"), 0);
        board.loadMap(board.getLayout());
        board.setupMap(images);
        for(Wave aWave : waves)
        {
            aWave.setPathToWizard(board.getPathToWizard());
        }
        wave = waves.get(waveNum);

    }

    /**
     * This method display anythings in the wave to the screen
     */
    public void displayWave()
    {
        if (waveNum < waves.size())
        {
            PVector gainedMana = wave.display();
            mana.updateMana(gainedMana);

            if(wave.checkAllBorn() && wave.checkAllDied())
            {
                waveNum += 1;
                if(waveNum >= waves.size()) {
                    return;
                }
                wave = waves.get(waveNum);
            }
        }
        if (towers.size() != 0)
        {
            for(Tower eachTower: towers)
            {
                int idMonsterInRange = -1;
                for(Monster monster: wave.getMonstersBorn())
                {
                    if(monster.getCountframedie() > 0)
                    {
                        continue;
                    }
                    float distance = eachTower.getPosition().dist(monster.getPosition());
                    if (distance < eachTower.getTowerRange())
                    {
                        idMonsterInRange = monster.getID();
                        break;
                    }
                }

                PVector posMonster = new PVector(0, 0);
                if (idMonsterInRange != -1)
                {
                    for(Monster monster: wave.getMonstersBorn())
                    {
                        if (monster.getID() == idMonsterInRange)
                        {
                            posMonster = monster.getCentrePosition();
                            break;
                        }
                    }
                }

                Fireball fireball = eachTower.shoot(idMonsterInRange, posMonster);
                if(fireball != null)
                {
                    fireball.setup(pApplet, fireballPrototype.getImg());
                    fireballs.add(fireball);
                }
                eachTower.display();
            }
        }
        if(towersIce.size() != 0)
        {
            for(TowerIce eachTowerIce: towersIce)
            {
                int idMonsterInRange = -1;
                for(Monster monster: wave.getMonstersBorn())
                {
                    if(monster.getCountframedie() > 0)
                    {
                        continue;
                    }
                    float distance = eachTowerIce.getCenterPosition().dist(monster.getPosition());
                    if (distance < eachTowerIce.getTowerRange())
                    {
                        idMonsterInRange = monster.getID();
                        // get last id monster
                    }
                }

                PVector posMonster = new PVector(0, 0);
                if (idMonsterInRange != -1)
                {
                    for(Monster monster: wave.getMonstersBorn())
                    {
                        if (monster.getID() == idMonsterInRange)
                        {
                            posMonster = monster.getCentrePosition();
                            break;
                        }
                    }
                }

                Fireball fireball = eachTowerIce.shoot(idMonsterInRange, posMonster);
                if(fireball != null)
                {
                    fireball.setup(pApplet, fireballIcePrototype.getImg());
                    fireballs.add(fireball);
                }

                eachTowerIce.display();
            }
        }




        if (fireballs.size() != 0)
        {
            for(int i = 0; i < fireballs.size(); i++)
            {
                Fireball fireball = fireballs.get(i);
                if (checkPause)
                {
                    fireball.downSpeed();
                }
                else if(checkDoubleSpeed) {
                    fireball.upSpeed();
                }
                else
                {
                    fireball.resetSpeed();
                }


            }

            for(int i = 0; i < fireballs.size(); i++)
            {
                Fireball fireball = fireballs.get(i);
                fireball.display();
                if (fireball.checkOnTarget())
                {
                    int idTarget = fireball.getIDTarget();

                    for(Monster monster: wave.getMonstersBorn())
                    {
                        if(monster.getID() == idTarget)
                        {
                            monster.updateHP(fireball.getDamage(), fireball.isFreeze());

                        }
                    }
                    fireballs.remove(i);
                }
            }
        }

    }

    /**
     * This method display the elements of the game to screen
     */
    public void displayElement()
    {
        element.setNumWave(waveNum + 1);
        element.setWaitWave(wave.getSecondWaveWait());
        element.displayButtons();
        if (waveNum < waves.size() - 1)
        {
            element.displayWave();
        }
    }

    /**
     * This method read the json file and create instance from the information in the json file
     * @param src the path to the config file
     * @param images images used in the game
     */
    public void loadJSON(String src, HashMap<String,PImage> images)
    {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(src));
            JSONObject jsonObject = (JSONObject)obj;

            String layout = (String)jsonObject.get("layout");
            board.setLayout(layout);
            // towers
            ArrayList<PImage> imgTowers = new ArrayList<>();
            imgTowers.add(images.get("Tower0Image"));
            imgTowers.add(images.get("Tower1Image"));
            imgTowers.add(images.get("Tower2Image"));
            PImage imgTowerIce = images.get("Tower3Image");


            //died
            ArrayList<PImage> imgDied = new ArrayList<>();
            imgDied.add(images.get("gremlin1"));
            imgDied.add(images.get("gremlin2"));
            imgDied.add(images.get("gremlin3"));
            imgDied.add(images.get("gremlin4"));
            imgDied.add(images.get("gremlin5"));

            towerPrototype = new Tower(pApplet, imgTowers,
                    Float.parseFloat(jsonObject.get("initial_tower_range").toString()),
                    Float.parseFloat(jsonObject.get("initial_tower_firing_speed").toString()),
                    Float.parseFloat(jsonObject.get("initial_tower_damage").toString()),
                    Float.parseFloat(jsonObject.get("tower_cost").toString())
            );

            towerIcePrototype = new TowerIce(pApplet, imgTowerIce,Float.parseFloat(jsonObject.get("initial_tower_range").toString()),
                    Float.parseFloat(jsonObject.get("initial_tower_firing_speed").toString()),
                    Float.parseFloat(jsonObject.get("initial_tower_damage").toString()),
                    Float.parseFloat(jsonObject.get("tower_cost").toString()) );



            mana = new Mana(pApplet,
                    Integer.parseInt(jsonObject.get("initial_mana").toString()),
                    Integer.parseInt(jsonObject.get("initial_mana_cap").toString()),
                    Integer.parseInt(jsonObject.get("initial_mana_gained_per_second").toString())
            );

            manaPoolSpell = new ManaPoolSpell(
                    Integer.parseInt(jsonObject.get("mana_pool_spell_initial_cost").toString()),
                    Integer.parseInt(jsonObject.get("mana_pool_spell_cost_increase_per_use").toString()),
                    Float.parseFloat(jsonObject.get("mana_pool_spell_cap_multiplier").toString()),
                    Float.parseFloat(jsonObject.get("mana_pool_spell_mana_gained_multiplier").toString())
            );


            // waves
            JSONArray array_waves = (JSONArray) jsonObject.get("waves");
            Iterator iterator_waves = array_waves.iterator();
            while (iterator_waves.hasNext()) {
                JSONObject json_wave = (JSONObject)iterator_waves.next();

                ArrayList<Monster> monsters = new ArrayList<Monster>();
                ArrayList<Integer> monsters_quantity = new ArrayList<Integer>();

                // monsters
                JSONArray array_monster = (JSONArray) json_wave.get("monsters");
                Iterator iterator_monsters = array_monster.iterator();
                // get a monsters
                while (iterator_monsters.hasNext()) {
                    JSONObject json_monster = (JSONObject) iterator_monsters.next();

                    Monster monster = new Monster(pApplet, images.get(json_monster.get("type")),
                            Float.parseFloat(json_monster.get("hp").toString()),
                            Float.parseFloat(json_monster.get("speed").toString()),
                            Float.parseFloat(json_monster.get("armour").toString()),
                            Float.parseFloat(json_monster.get("mana_gained_on_kill").toString()));




                    monster.setImgDie((ArrayList<PImage>) imgDied.clone());
                    monsters.add(monster);
                    monsters_quantity.add(Integer.parseInt(json_monster.get("quantity").toString()));
                }

                Wave wave = new Wave(pApplet,
                        Integer.parseInt(json_wave.get("duration").toString()),
                        Float.parseFloat(json_wave.get("pre_wave_pause").toString()),
                        monsters,
                        monsters_quantity);


                waves.add(wave);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This function responsible for pressing the button and execute the operation corresponding to that buttons
     */
    public void keyPressed()
    {

        if (pApplet.keyPressed)
        {
            if (pApplet.key == 't')
            {
                element.T.press();
            }
            else if (pApplet.key == '1')
            {
                element.U1.press();
            }
            else if (pApplet.key == '2')
            {
                element.U2.press();
            }
            else if (pApplet.key == '3')
            {
                element.U3.press();
            }
            else if(pApplet.key == 'm')
            {
                if (mana.getCurrentMana() >= manaPoolSpell.getManaPoolSpellCurrentCost())
                {
                    element.M.press();
                }

            }
            else if(pApplet.key == 'f')
            {
                element.FF.press();
            }
            else if(pApplet.key == 'p')
            {
                element.P.press();
            }
            else if(pApplet.key == 'r')
            {
                result.setPressR(true);
            }else if(pApplet.key == 'h') {
                element.H.press();
            }
        }
        pApplet.key = 'x';

        element.T.clickOn();
        element.U1.clickOn();
        element.U2.clickOn();
        element.U3.clickOn();
        element.FF.clickOn();
        element.H.clickOn();
        element.M.clickOn();
        element.P.clickOn();

        if(element.T.isPressed() && pApplet.mousePressed &&
                (0 < pApplet.mouseX && pApplet.mouseX < 640) &&
                (40 < pApplet.mouseY && pApplet.mouseY < 640 + 40))
        {
            tower = new Tower(pApplet,  towerPrototype.getImgTowers(),
                    towerPrototype.getTowerRange(),
                    towerPrototype.getTowerFiringSpeed(),
                    towerPrototype.getTowerDamage(),
                    towerPrototype.getTotalCost());
            PVector posTower = board.checkValidBuild(pApplet.mouseX, pApplet.mouseY);

            int costBuild = (int) tower.getTotalCost();
            PVector lvCost = tower.infoUpdate(element.U1.isPressed(),
                    element.U2.isPressed(),
                    element.U3.isPressed());

            costBuild += lvCost.x + lvCost.y + lvCost.z;

            if(!posTower.equals(new PVector(-1, -1)) && costBuild < mana.getCurrentMana())
            {
                tower.followMouse(posTower.x, posTower.y);
                tower.whenUpdate(element.U1.isPressed(),
                        element.U2.isPressed(),
                        element.U3.isPressed());
                board.setTower(pApplet.mouseX, pApplet.mouseY);
                towers.add(tower);
                mana.setCurrentMana(mana.getCurrentMana() - costBuild);
                pApplet.mousePressed = false;
            }
        }

        // ice tower
        if(!element.T.isPressed() && element.H.isPressed() && pApplet.mousePressed &&
                (0 < pApplet.mouseX && pApplet.mouseX < 640) &&
                (40 < pApplet.mouseY && pApplet.mouseY < 640 + 40))
        {
            tower = new Tower(pApplet,  towerPrototype.getImgTowers(),
                    towerPrototype.getTowerRange(),
                    towerPrototype.getTowerFiringSpeed(),
                    towerPrototype.getTowerDamage(),
                    towerPrototype.getTotalCost());
            towerIce = new TowerIce(pApplet, towerIcePrototype.getImgTower(),
                    towerIcePrototype.getTowerRange(),
                    towerIcePrototype.getTowerFiringSpeed(),
                    towerIcePrototype.getTowerDamage(),
                    towerIcePrototype.getTotalCost());

            PVector posTower = board.checkValidBuild(pApplet.mouseX, pApplet.mouseY);

            int costBuild = (int) towerIce.getTotalCost();

            if(!posTower.equals(new PVector(-1, -1)) && costBuild < mana.getCurrentMana())
            {
                towerIce.followMouse(posTower.x, posTower.y);
                board.setTower(pApplet.mouseX, pApplet.mouseY);
                System.out.println("Create tower ice");
                towersIce.add(towerIce);
                mana.setCurrentMana(mana.getCurrentMana() - costBuild);
                pApplet.mousePressed = false;
            }
            else
            {
                System.out.println("can't build");
            }
        }

        if(element.M.isPressed() && mana.getCurrentMana() >= manaPoolSpell.getManaPoolSpellCurrentCost())
        {
            manaPoolSpell.update();
            mana.setGainMultiplier(manaPoolSpell.getManaPoolSpellManaGainedMultiplier() - 1);
            int newManaCap = (int) (mana.getInitialManaCap() * manaPoolSpell.getManaPoolSpellCapMultiplier());
            mana.setInitialManaCap(newManaCap);
            String newDescription = String.format("Mana pool cost: %d", (int) manaPoolSpell.getManaPoolSpellCurrentCost());
            element.M.setDescription(newDescription);
            element.M.press();
        }


        if(element.P.isPressed())
        {
            checkPause = true;
            wave.setPause(true);
            mana.setPause(true);
        }
        else
        {
            checkPause = false;
            wave.setPause(false);
            mana.setPause(false);
        }

        if(element.FF.isPressed()) { // x2 speed
            checkDoubleSpeed =true;
            wave.setDoubleSpeed(true);
            mana.setDoubleSpeed(true);
        }else {
            checkDoubleSpeed = false;
            wave.setDoubleSpeed(false);
            mana.setDoubleSpeed(false);
        }
    }

    /**
     * This method display the range of tower when hovering
     */
    public void howerAndUpdate()
    {
        for(Tower tower: towers)
        {
            if(tower.checkOverTower(pApplet.mouseX, pApplet.mouseY))
            {
                tower.displayRange();
            }
        }

        for(TowerIce towerIce1: towersIce)
        {
            if(towerIce1.checkOverTower(pApplet.mouseX, pApplet.mouseY))
            {
                towerIce1.displayRange();
            }
        }
    }

    /**
     * This method display the cost of tower as little box in the corner of the game
     */
    public void displayInfoUpdate()
    {

        if (element.T.checkHover())
        {
            int costBuild = (int) (towerPrototype.getTotalCost());
            PVector lvCost;
            if (tower != null)
            {
                lvCost = tower.infoUpdate(element.U1.isPressed(),
                        element.U2.isPressed(),
                        element.U3.isPressed());
            }
            else
            {
                lvCost = towerPrototype.infoUpdate(element.U1.isPressed(),
                        element.U2.isPressed(),
                        element.U3.isPressed());
            }
            costBuild += (lvCost.x + lvCost.y + lvCost.z);
            updateInfo.Tooltip(element.T.getX(),
                    element.T.getY(),
                    costBuild);


        }

        if (element.M.checkHover())
        {
            updateInfo.Tooltip(element.M.getX(), element.M.getY(), manaPoolSpell.getManaPoolSpellCurrentCost());
        }

        if (element.H.checkHover())
        {
            updateInfo.Tooltip(element.H.getX(), element.H.getY(), towerIcePrototype.getTotalCost());
        }

        for (Tower tower: towers)
        {
            if(tower.checkOverTower(pApplet.mouseX, pApplet.mouseY))
            {
                if(element.U1.isPressed() || element.U2.isPressed() || element.U3.isPressed())
                {
                    updateInfo.setChoose(element.U1.isPressed(),
                            element.U2.isPressed(),
                            element.U3.isPressed());
                    updateInfo.setEachUpdate(tower.infoUpdate(element.U1.isPressed(),
                            element.U2.isPressed(),
                            element.U3.isPressed()));
                    updateInfo.display();

                    if(pApplet.mousePressed)
                    {
                        PVector lvUpdate = tower.infoUpdate(element.U1.isPressed(),
                                element.U2.isPressed(),
                                element.U3.isPressed());

                        float totalCost = (lvUpdate.x + lvUpdate.y + lvUpdate.z);

                        if (mana.getCurrentMana() > totalCost)
                        {
                            tower.whenUpdate(element.U1.isPressed(),
                                    element.U2.isPressed(),
                                    element.U3.isPressed());


                            if (mana.getCurrentMana() > totalCost) {
                                mana.setCurrentMana(mana.getCurrentMana() - (int) totalCost);
                            }
                        }
                        pApplet.mousePressed = false;
                    }
                }
            }
        }
    }

    /**
     * This method check the status of the game and perform operation related to its status
     */
    public void checkResult()
    {
        if(wave.checkAllDied() && waveNum == waves.size())
        {
            result.displayWin();
        }
        if(mana.getCurrentMana() == 0)
        {
            mana.setInitialManaGainedPerSecond(0);
            waveNum = waves.size() + 1;
            result.displayLost();
        }
    }

    /**
     * This method return the status of the game
     * @return True if win, else return False
     */
    public boolean status()
    {
        return result.isWin();
    }

    /**
     * This method responsible for displaying the entire game to the screen
     */
    public void display()
    {
        board.display();
        keyPressed();
        displayWave();
        board.displayWizard();
        howerAndUpdate();
        displayElement();
        mana.display();
        displayInfoUpdate();
        checkResult();
    }

}