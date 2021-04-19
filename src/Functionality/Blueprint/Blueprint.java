package Functionality.Blueprint;

import Assets.Variables;
import Functionality.Server;
import Functionality.ServerParser;
import GUI.TabModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Blueprint extends TabModel {

    private JPanel panel;

    private ServerListPanel serverListPanel;

    private ArrayList<Server> currentBlueprintServerList;

    public Blueprint() {
        super("Nieuw ontwerp");
        currentBlueprintServerList = ServerParser.parseServers();
    }

    public ArrayList<Server> getCurrentBlueprintServerList() {
        return currentBlueprintServerList;
    }

    @Override
    public JPanel getPanel() {
        panel = new JPanel();
        panel.setBackground(Variables.backgroundLighter);
        panel.setLayout(new BorderLayout());

        serverListPanel = new ServerListPanel(this);
        panel.add(serverListPanel, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public boolean closeCheck() {
        return true;
    }
}
