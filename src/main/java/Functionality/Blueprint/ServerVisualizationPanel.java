package Functionality.Blueprint;

import Assets.Variables;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ServerVisualizationPanel extends JPanel implements Serializable {

    private Blueprint parent;

    private ServerVisualizationItemPanel fwPanel, wbPanel, dbPanel;

    public ServerVisualizationPanel(Blueprint parent) {
        this.parent = parent;
        setBackground(Variables.backgroundLighter);

        drawPanels();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBorder(new EmptyBorder(0, 0, 0, 0));

        int totalHeight = getHeight();
        int contentHeight = 0;

        if(fwPanel.getHeight() > wbPanel.getHeight() && fwPanel.getHeight() > dbPanel.getHeight()) contentHeight = fwPanel.getHeight();
        else if (wbPanel.getHeight() > dbPanel.getHeight()) contentHeight = wbPanel.getHeight();
        else contentHeight = dbPanel.getHeight();

        int paddingTop = (totalHeight - contentHeight)/2;
        setBorder(new EmptyBorder(paddingTop, 0, 0, 0));

    }

    public void drawPanels() {
        fwPanel = new ServerVisualizationItemPanel(Server.FIREWALL);
        wbPanel = new ServerVisualizationItemPanel(Server.WEBSERVER);
        dbPanel = new ServerVisualizationItemPanel(Server.DATABASE);
        add(fwPanel);
        add(wbPanel);
        add(dbPanel);
    }

    public void drawServers() {
        fwPanel.clearServerCollection();
        wbPanel.clearServerCollection();
        dbPanel.clearServerCollection();
        ArrayList<ServerCollection> serverCollections = new ArrayList<>();
        for (Server server : parent.getServers()) {
            boolean found = false;
            for (ServerCollection collection : serverCollections) {
                if (collection.getServer().equals(server)) {
                    collection.incrementOne();
                    found = true;
                }
            }
            if (!found) serverCollections.add(new ServerCollection(server, parent));
        }
        for (ServerCollection collection : serverCollections) {
            switch (collection.getServer().getType()) {
                case Server.DATABASE:
                    dbPanel.addToServerCollection(collection);
                    break;
                case Server.WEBSERVER:
                    wbPanel.addToServerCollection(collection);
                    break;
                case Server.FIREWALL:
                    fwPanel.addToServerCollection(collection);
                    break;
            }
        }
        fwPanel.drawToPanels();
        wbPanel.drawToPanels();
        dbPanel.drawToPanels();
    }

}
