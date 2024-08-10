package WizardTD;

import processing.core.PApplet;

public class Result
{
    private PApplet pApplet;

    private boolean isWin = true;

    private boolean pressR = false;

    public Result(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public void setPressR(boolean pressR) {
        this.pressR = pressR;
    }

    /**
     * This method check if the user win the game or not
     * @return True for win, else False
     */
    public boolean isWin()
    {
        if (isWin == false & pressR == true)
        {
            pressR = false;
            return true;
        }
        return false;
    }


    /**
     * This method display the win announcement to the screen
     */
    public void displayWin()
    {
        int[] color = Colors.VIOLET.color.getRGB();
        pApplet.textSize(30);
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.text("YOU WIN", 300, 300);
    }

    /**
     * This method display the lose announcement to the screen
     */
    public void displayLost()
    {
        isWin = false;
        int[] color = Colors.GREEN.color.getRGB();
        pApplet.textSize(30);
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.text("YOU LOSS", 300, 300);
        pApplet.textSize(20);
        pApplet.text("(press 'r' to restart)", 300, 320);
    }
}
