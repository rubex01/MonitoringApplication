package Functionality.Blueprint;

import Functionality.Server;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ServerVisualizationPanel extends JPanel implements Serializable {

    private Blueprint parent;

    private ServerVisualizationItemPanel fwPanel, wbPanel, dbPanel;

    public ServerVisualizationPanel(Blueprint parent) {
        this.parent = parent;
        drawPanels();
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
                if (server == collection.getServer()) {
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
