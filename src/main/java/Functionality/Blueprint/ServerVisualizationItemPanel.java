package Functionality.Blueprint;

import Assets.Variables;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ServerVisualizationItemPanel extends JPanel implements Serializable {

    private ArrayList<ServerCollection> serverCollections;

    private int type;

    public ServerVisualizationItemPanel(int type) {
        this.type = type;
        serverCollections = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Variables.backgroundLighter);
        drawToPanels();
        setVisible(false);
    }

    public void drawToPanels() {
        if (serverCollections.size() == 0) {
            setVisible(false);
            return;
        }
        setVisible(true);
        removeAll();
        for (ServerCollection collection : serverCollections) {
            if (collection.getServer().getType() == type) {
                add(collection.getPanel());
            }
        }
        revalidate();
        repaint();
    }

    public void addToServerCollection(ServerCollection serverCollection) {
        serverCollections.add(serverCollection);
    }

    public void clearServerCollection() {
        serverCollections.clear();
    }

}
