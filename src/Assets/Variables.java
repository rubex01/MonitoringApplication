package Assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Variables {

    public final static Color background = new Color(214, 213, 213);

    public final static Color backgroundLighter = new Color(242, 242, 242);

    public final static Color white = Color.WHITE;

    public final static Color focus = new Color(242, 242, 242);

    public final static Color nonFocus = new Color(214, 213, 213);

    public final static Color transparent = new Color(0, 0, 0, 0);

    private final static String[] images = {"star", "folder", "logo", "new_file", "close"};

    public static Image getImage(String type) {
        try {
            if (Arrays.stream(images).toList().contains(type)) {
                Image close = ImageIO.read(Variables.class.getResource("./"+type+".png"));
                return close;
            }
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

}
