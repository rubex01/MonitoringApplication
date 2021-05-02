package Functionality.Monitoring.ExtensiveStatusPanel;

import Assets.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ExtensiveStatusPanel extends JPanel {

    public ExtensiveStatusPanel(){

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);
        Graphics2D graphics2 = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2.setRenderingHints(rh);

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 21, getWidth(), getHeight()-21, 8, 8);
        graphics2.fill(roundedRectangle);

        graphics2.drawImage(Variables.getImage("shadow"), 21, 19, null);

        graphics2.setColor(Variables.uptimeColor);
        graphics2.fillOval(21, 0, 42, 42);

        graphics2.drawImage(Variables.getImage("firewall_white"), 29, 8, 30, 30, null);
    }

}
