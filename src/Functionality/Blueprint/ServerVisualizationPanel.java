package Functionality.Blueprint;

import Functionality.Server;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ServerVisualizationPanel extends JPanel implements Serializable {

    private Blueprint parent;

    private JPanel firewallPanel, databasePanel, webserverPanel;

    public ServerVisualizationPanel(Blueprint parent) {
        this.parent = parent;
        setLayout(new FlowLayout());
        drawServerBoxes();
        decideVisibility();
    }

    private void drawServerBoxes() {
//        JPanel firewallPanel = new JPanel();
//        firewallPanel.setBackground(Color.ORANGE);
//        firewallPanel.setVisible(false);
//
//        JPanel databasePanel = new JPanel();
//        databasePanel.setBackground(Color.BLACK);
//        databasePanel.setVisible(false);
//
//        JPanel webserverPanel = new JPanel();
//        webserverPanel.setBackground(Color.GREEN);
//        webserverPanel.setVisible(false);
//
//        add(firewallPanel);
//        add(databasePanel);
//        add(webserverPanel);
    }

    private void decideVisibility() {
//        ArrayList<Server> servers = parent.getServers();
//        for (Server server : servers) {
//            if (server.getType() == 0) firewallPanel.setVisible(true);
//            if (server.getType() == 1) databasePanel.setVisible(true);
//            if (server.getType() == 2) webserverPanel.setVisible(true);
//        }
    }

}
