package Functionality.Blueprint;

import Assets.DefaultScrollPane;
import Assets.Variables;
import Assets.YesNoDialog;
import Functionality.BlueprintSaves.SaveController;
import Functionality.Server;
import Functionality.ServerParser;
import GUI.TabModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.Serializable;
import java.util.ArrayList;

public class Blueprint extends TabModel implements Serializable {

    private JPanel panel;

    private ServerListPanel serverListPanel;

    private ArrayList<Server> currentBlueprintServerList;

    private ArrayList<Server> selectedServers;

    private String savePath;

    private boolean onlineSaved = false;

    private ServerVisualizationPanel serverVisualizerPanel;

    private int currentState = 0, savedState = 0;

    public Blueprint(String title) {
        super(title);
        if (currentBlueprintServerList == null) currentBlueprintServerList = ServerParser.parseServers();
        selectedServers = new ArrayList<>();
    }

    public Blueprint() {
        this("Nieuw ontwerp");
    }

    public ArrayList<Server> getCurrentBlueprintServerList() {
        return currentBlueprintServerList;
    }

    public void setOnlineSaved(boolean onlineSaved) {
        this.onlineSaved = onlineSaved;
    }

    @Override
    public JPanel getPanel() {
        if (panel != null) return panel;

        panel = new JPanel();
        panel.setBackground(Variables.backgroundLighter);
        panel.setLayout(new BorderLayout());

        serverListPanel = new ServerListPanel(this);
        panel.add(serverListPanel, BorderLayout.SOUTH);

        serverVisualizerPanel = new ServerVisualizationPanel(this);
        panel.add(serverVisualizerPanel, BorderLayout.CENTER);
        serverVisualizerPanel.drawServers();
        calculatePriceAndUptime();

        DefaultScrollPane scrollPane = new DefaultScrollPane(serverVisualizerPanel);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        panel.add(scrollPane);

        MouseAdapter ma = new BlueprintDrag(this);
        serverVisualizerPanel.addMouseListener(ma);
        serverVisualizerPanel.addMouseMotionListener(ma);

        return panel;
    }

    public ServerVisualizationPanel getServerVisualizerPanel() {
        return serverVisualizerPanel;
    }

    public void setSavePath(String path) {
        savePath = path;
    }

    public String getSavePath() {
        return savePath;
    }

    public void addBulk(ArrayList<Server> serverList) {
        for (Server server : serverList) selectedServers.add(server);
        serverVisualizerPanel.drawServers();
        calculatePriceAndUptime();
    }

    public void addServer(Server server){
        selectedServers.add(server);
        serverVisualizerPanel.drawServers();
        updateState();
        calculatePriceAndUptime();
    }

    public void removeServer(Server server) {
        selectedServers.remove(server);
        serverVisualizerPanel.drawServers();
        updateState();
        calculatePriceAndUptime();
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

    private void calculatePriceAndUptime() {
        double webserver = 0;
        double database = 0;
        double firewall = 0;
        int totalPrice = 0;
        for (Server server : selectedServers) {
            switch (server.getType()) {
                case Server.DATABASE:
                    webserver = (webserver == 0) ? (1-(server.getUptime()/100)) :  (webserver  * (1-(server.getUptime()/100)));
                    break;
                case Server.WEBSERVER:
                    database = (database == 0) ? (1-(server.getUptime()/100)) :  (database  * (1-(server.getUptime()/100)));
                    break;
                case Server.FIREWALL:
                    firewall = (firewall == 0) ? (1-(server.getUptime()/100)) :  (firewall  * (1-(server.getUptime()/100)));
                    break;
            }
            totalPrice += server.getPrice();
        }
        double totalUptime = (1-webserver) * (1-database) * (1-firewall);
        serverListPanel.updateInfoLabel(totalUptime, totalPrice, selectedServers.size());
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
            YesNoDialog saveDialog = new YesNoDialog("Let op", "Wilt u de wijzigingen in \"" + getFileTitle() + "\" opslaan?");
            if(saveDialog.getCloseMethod() == YesNoDialog.YES_OPTION){
                if (onlineSaved == false) {
                    boolean saveResult = SaveController.saveBlueprint(this);
                    return saveResult;
                }
                boolean saveResult = SaveController.saveBlueprintOnline(this);
                return saveResult;
            }
            else if (saveDialog.getCloseMethod() == YesNoDialog.CLOSED_OPTION) {
                return false;
            }
        }
        return true;
    }
}
