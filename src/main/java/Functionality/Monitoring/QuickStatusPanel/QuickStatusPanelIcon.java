package Functionality.Monitoring.QuickStatusPanel;

import Assets.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QuickStatusPanelIcon extends JPanel {

    private Color typeColor;

    private String imageName;

    public QuickStatusPanelIcon(Color typeColor, String imageName) {
        this.typeColor = typeColor;
        this.imageName = imageName;

        setBackground(Variables.white);
        setPreferredSize(new Dimension(50, 50));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2 = (Graphics2D) g;

        graphics2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        BufferedImage image = (BufferedImage) Variables.getImage("icon_template");
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (image.getRGB(i, j) != 0) {
                    Color imageColor = new Color(image.getRGB(i, j));
                    Color finalColor = blend(imageColor, typeColor, 0.7f);
                    image.setRGB(i, j, finalColor.getRGB());
                }
            }
        }

        graphics2.drawImage(image, 5, 5, 40, 40, null);
        graphics2.drawImage(Variables.getImage(imageName), 11, 11, 30, 30, null);
    }

    private static Color blend(Color c1, Color c2, float ratio) {
        if ( ratio > 1f ) ratio = 1f;
        else if ( ratio < 0f ) ratio = 0f;
        float iRatio = 1.0f - ratio;

        int i1 = c1.getRGB();
        int i2 = c2.getRGB();

        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);

        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);

        int a = (int)((a1 * iRatio) + (a2 * ratio));
        int r = (int)((r1 * iRatio) + (r2 * ratio));
        int g = (int)((g1 * iRatio) + (g2 * ratio));
        int b = (int)((b1 * iRatio) + (b2 * ratio));

        return new Color( a << 24 | r << 16 | g << 8 | b );
    }
}
