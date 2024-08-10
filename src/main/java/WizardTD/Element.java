package WizardTD;

import processing.core.PApplet;

public class Element
{
    private PApplet pApplet;

    Button FF, P, T, U1, U2, U3, M, H;

    private int numWave;
    private int waitWave;

    public void setNumWave(int numWave) {
        this.numWave = numWave;
    }

    public void setWaitWave(int waitWave) {
        this.waitWave = waitWave;
    }


    public Element(PApplet pApplet)
    {
        this.pApplet = pApplet;
        this.numWave = 3;
        this.waitWave = 2;
        FF = new Button(pApplet, 648, 50, "FF", "2x speed");
        P = new Button(pApplet, 648, 110, "P", "PAUSE");
        T = new Button(pApplet, 648, 170, "T", "Build tower");
        U1 = new Button(pApplet, 648, 230, "U1", "Upgrade range");
        U2 = new Button(pApplet, 648, 290, "U2", "Upgrade speed");
        U3 = new Button(pApplet, 648, 350, "U3", "Upgrade damage");
        M = new Button(pApplet, 648, 410, "M", "Mana pool cost: 100");
        H = new Button(pApplet, 648, 470, "H", "Build ice tower");
    }

    /**
     * This method display the button to screen
     */
    public void displayButtons()
    {
        //sidebar
        int[] color = Colors.BROWN.color.getRGB();
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.rect(640, 40, 120, 680);
        //header
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.rect(0, 0, 760, 40);

        FF.displayButton();
        P.displayButton();
        T.displayButton();
        U1.displayButton();
        U2.displayButton();
        U3.displayButton();
        M.displayButton();
        H.displayButton();
    }

    /**
     * This method display the text showing wave information
     */
    public void displayWave()
    {
        pApplet.textSize(20);
        pApplet.fill(0);
        pApplet.textAlign(pApplet.LEFT);
        String displayWaves = String.format("Wave %d start: %d", numWave, waitWave);
        pApplet.text(displayWaves, 16, 10, 250, 32);
    }


}
