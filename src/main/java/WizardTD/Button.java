package WizardTD;
import processing.core.PApplet;
public class Button
{
    private PApplet pApplet;
    private float x;
    private float y;

    private float w;
    private float h;

    private String symbol;

    private String description;

    private boolean pressed = false;

    public Button(PApplet pApplet, float x, float y, String symbol, String description)
    {
        this.pApplet = pApplet;
        this.x = x;
        this.y = y;
        this.w = 45;
        this.h = 45;
        this.symbol = symbol;
        this.description = description;
    }

    /**
     * This method change the press state of button
     */
    public void press()
    {
        pressed = !pressed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * This method check if the mouse position is over the button
     * @return True if it mouse over the button, else return False
     */
    public boolean checkHover()
    {
        if (x <= pApplet.mouseX &&  pApplet.mouseX <= x + w && y <= pApplet.mouseY && pApplet.mouseY <= y + h)
        {

            return true;
        }
        return false;
    }

    /**
     * This method display the button to screen
     */
    public void displayButton()
    {
        int[] color;
        if (pressed)
        {
            color = Colors.YELLOW.color.getRGB();
        }
        else {
            color = Colors.BROWN.color.getRGB();
        }

        if(checkHover() && !pressed)
        {
            color = Colors.GREY.color.getRGB();
        }
        pApplet.fill(color[0], color[1], color[2]);
        pApplet.strokeWeight(3);
        pApplet.stroke(0);
        pApplet.rect(x, y, 45, 45);
        pApplet.noStroke();


        pApplet.fill(0);
        pApplet.textSize(24);
        pApplet.textAlign(pApplet.CENTER, pApplet.CENTER);
        pApplet.text(symbol, x, y, 45, 45);

        pApplet.fill(0);
        pApplet.textSize(12);
        pApplet.textAlign(pApplet.LEFT);
        pApplet.text(description, x + 50, y + 2, 65, 45);
    }

    public boolean isPressed()
    {
        return pressed;
    }

    /**
     * This method execute the click
     */
    public void clickOn()
    {
        if (checkHover() && pApplet.mousePressed)
        {
            press();
            pApplet.mousePressed = false;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
