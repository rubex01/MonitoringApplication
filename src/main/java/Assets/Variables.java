package Assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Variables {

    public final static Color background = new Color(214, 213, 213);

    public final static Color backgroundLighter = new Color(242, 242, 242);

    public final static Color white = Color.WHITE;

    public final static Color black = Color.BLACK;

    public final static Color focus = new Color(242, 242, 242);

    public final static Color nonFocus = new Color(214, 213, 213);

    public final static Color transparent = new Color(0, 0, 0, 0);

    public final static Color uptimeColor = new Color(29, 137, 241);

    public final static Color downtimeColor = new Color(252, 43, 63);

    public final static Color outagesColor = new Color(136, 63, 255);

    public final static Color subText = new Color(141, 141, 141);

    public final static Color fileDropHover = new Color(0, 150, 255, 70);

    public final static Color serverPool = new Color(123, 123, 123);

    public final static Color onlineColor = new Color(39, 255, 160);

    public final static Color offlineColor = new Color(255, 96, 96);

    public final static Color scrollThumbColor = new Color(99, 99, 99);

    public final static int scrollBarWidth = 5;

    public final static Color defaultButtonColor = new Color(37, 39, 53);

    public static Image getImage(String type) {
        try {
            Image image = ImageIO.read(Variables.class.getResource(type + ".png"));
            return image;
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
