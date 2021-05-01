package Functionality.Blueprint;

import Functionality.Server;

import javax.swing.*;
import java.io.Serializable;

public class ServerCollection implements Serializable {

    private Server server;

    private int amount = 1;

    private Blueprint parent;

    public ServerCollection(Server server, Blueprint parent) {
        this.server = server;
        this.parent = parent;
    }

    public void incrementOne() {
        amount++;
    }

    public Server getServer() {
        return server;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Server: " + server.getName() + " amount: " + amount;
    }

    public JPanel getPanel() {
        return new ServerVisualizationItem(this, parent);
    }
}
