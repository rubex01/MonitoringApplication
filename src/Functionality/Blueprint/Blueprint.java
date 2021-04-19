package Functionality.Blueprint;

import Functionality.Server;
import Functionality.ServerParser;
import GUI.TabModel;

import javax.swing.*;
import java.awt.*;

public class Blueprint extends TabModel {

    private JPanel panel;

    private ServerListPanel serverListPanel;

    public Blueprint() {
        super("Nieuw ontwerp");

        for (Server test : ServerParser.serverList) {
            System.out.println(test);
        }
    }

    @Override
    public JPanel getPanel() {
        panel = new JPanel();
        serverListPanel = new ServerListPanel();
        panel.setLayout(new BorderLayout());
        panel.add(serverListPanel, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public boolean closeCheck() {
        return true;
    }
}
