package Functionality.Blueprint;

import Assets.Variables;
import Functionality.SaveController;
import Functionality.Server;
import Functionality.ServerParser;
import GUI.TabModel;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Blueprint extends TabModel implements Serializable {

    private JPanel panel;

    private ServerListPanel serverListPanel;

    private ArrayList<Server> currentBlueprintServerList;

    private ArrayList<Server> selectedServers;

    private String savePath;

    private int currentState = 0, savedState = 0;

    public Blueprint() {
        super("Nieuw ontwerp");
        if (currentBlueprintServerList == null) currentBlueprintServerList = ServerParser.parseServers();
        selectedServers = new ArrayList<>();
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

        ServerVisualizationPanel serverVisualizerPanel = new ServerVisualizationPanel(this);
        panel.add(serverVisualizerPanel, BorderLayout.CENTER);

        return panel;
    }

    public void setSavePath(String path) {
        savePath = path;
    }

    public String getSavePath() {
        return savePath;
    }

    public void addServer(Server server){
        selectedServers.add(server);
        System.out.println(selectedServers); // TODO: remove this
        updateState();
    }

    private void updateState() {
        if (currentState == savedState) setTitle(getTitle() + " (niet opgeslagen)");
        currentState++;
    }

    public String getFileTitle() {
        if (getTitle().contains(" (niet opgeslagen)")) {
            return getTitle().substring(0, (getTitle().length()-18));
        }
        return getTitle();
    }

    public ArrayList<Server> getServers(){
        return selectedServers;
    }

    public void saved() {
        savedState = currentState;
    }

    @Override
    public boolean closeCheck() {
        if (savedState != currentState) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(panel, "Wilt u de wijzigingen in " + getTitle() + " opslaan?","Let op", dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                boolean saveResult = !SaveController.saveBlueprint(this);
                return saveResult;
            }
        }
        return true;
    }
}
