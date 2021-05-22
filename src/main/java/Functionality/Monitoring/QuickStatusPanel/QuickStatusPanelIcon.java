package Functionality.Monitoring.QuickStatusPanel;

import Assets.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
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
        graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gradient = new GradientPaint(0, 0, typeColor.brighter(), 40, 40, typeColor.darker());
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(5, 5, 40, 40, 16, 16);

        graphics2.setPaint(gradient);
        graphics2.fill(roundedRectangle);

        graphics2.drawImage(Variables.getImage(imageName), 11, 11, 30, 30, null);
    }
}
