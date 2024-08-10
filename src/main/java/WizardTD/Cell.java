package WizardTD;
import processing.core.PImage;
public class Cell
{
    private String type;
    private int x;
    private int y;

    private PImage img;

    public Cell(String type, PImage img, int x, int y) {
        this.type = type;
        this.img = img;
        this.x = x;

    }

    public String getType() {
        return type;
    }

    public PImage getImg() {
        return img;
    }

}
