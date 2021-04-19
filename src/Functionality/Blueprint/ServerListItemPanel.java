package Functionality.Blueprint;

import Assets.Variables;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ServerListItemPanel extends JPanel {

    private Server server;

    public ServerListItemPanel(Server server) {
        this.server = server;
        setLayout(new BorderLayout());
        setBackground(Variables.white);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        drawItems();
    }

    private void drawItems() {
        JLabel imageLabel = new JLabel("");
        imageLabel.setIcon(new ImageIcon(getRightImage()));
        add(imageLabel, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setLayout(new BorderLayout());
        info.setBackground(Variables.white);

        JLabel serverName = new JLabel(server.getName());
        JLabel serverUptime = new JLabel("Uptime: " + server.getUptime());
        JLabel serverPrice = new JLabel("Prijs: " + server.getPrice());
        info.add(serverName, BorderLayout.NORTH);
        info.add(serverUptime, BorderLayout.CENTER);
        info.add(serverPrice, BorderLayout.SOUTH);

        add(info, BorderLayout.CENTER);
    }

    private Image getRightImage() {
        Image image = null;
        switch (server.getType()) {
            case 0:
                image = Variables.getImage("database");
                break;
            case 1:
                image = Variables.getImage("webserver");
                break;
            case 2:
                image = Variables.getImage("firewall");
                break;
        }
        return image;
    }
}
