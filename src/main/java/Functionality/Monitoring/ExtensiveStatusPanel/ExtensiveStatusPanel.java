package Functionality.Monitoring.ExtensiveStatusPanel;

import Assets.Variables;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ExtensiveStatusPanel extends JPanel {

    private int type;

    public ExtensiveStatusPanel(int type){
        this.type = type;

        setBorder(new EmptyBorder(0, 21, 21, 21));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        drawItems();
    }

    private String getTitleText() {
        switch (type) {
            case Server.FIREWALL:
                return "Firewalls";
            case Server.WEBSERVER:
                return  "Webservers";
            case Server.DATABASE:
                return "Databases";
        }
        return null;
    }

    private String getImageName() {
        switch (type) {
            case Server.FIREWALL:
                return "firewall_white";
            case Server.WEBSERVER:
                return  "webserver_white";
            case Server.DATABASE:
                return "database_white";
        }
        return null;
    }

    private void drawItems() {
        JLabel title = new JLabel(getTitleText());
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 15));
        title.setForeground(new Color(68, 68, 68));
        title.setBorder(new EmptyBorder(3, 30, 0, 0));
        add(title);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);
        Graphics2D graphics2 = (Graphics2D) g;

        graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        RoundRectangle2D contentRectangle = new RoundRectangle2D.Float(0, 19, getWidth(), getHeight()-19, 16, 16);
        graphics2.fill(contentRectangle);

        RoundRectangle2D titleRectangle = new RoundRectangle2D.Float(29, 5, 130, 26, 25, 25);
        graphics2.fill(titleRectangle);
        graphics2.setColor(Variables.backgroundLighter);
        graphics2.draw(titleRectangle);

        graphics2.drawImage(Variables.getImage("shadow"), 8, 16, null);

        graphics2.setColor(Variables.serverPool);
        graphics2.fillOval(10, 0, 38, 38);

        graphics2.drawImage(Variables.getImage(getImageName()), 16, 6, 26, 26, null);
    }

}
