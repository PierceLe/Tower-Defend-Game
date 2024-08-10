package WizardTD;

public enum Colors
{
    BROWN(new Color(132, 114, 74)),
    WHITE(new Color(255, 255, 255)),
    LIGHT_BLUE(new Color(0, 214, 214)),
    GREY(new Color(206, 206, 206)),
    BLUE(new Color(135, 206, 230)),
    GREEN(new Color(42, 255, 74)),
    YELLOW(new Color(239, 222, 66)),
    MAGENTA(new Color(255, 0, 255)),
    RED(new Color(255, 0 ,0)),
    VIOLET(new Color(196, 150, 240));
    public final Color color;

    private Colors(Color color)
    {
        this.color = color;
    }

}
