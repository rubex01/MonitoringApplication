package Functionality.Monitoring;

import Functionality.Settings.SettingsController;
import GUI.TabModel;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;

public class Monitoring extends TabModel implements Serializable {

    private APIController apiController;

    private MonitoringPanel monitoringPanel;

    public Monitoring() {
        super("Monitoring", false);

        startApiController();
    }

    private void startApiController() {
        apiController = new APIController(this);

        Thread thread = new Thread(){
            public void run(){
                Timer timer = new Timer();
                timer.schedule(apiController, 500, (Integer.valueOf(SettingsController.getSetting("check_interval"))*1000));
            }
        };

        thread.start();
    }

    public void startUpdateCycle(ArrayList<ServerResult> serverResults, ArrayList<PoolResult> poolResults) {
        monitoringPanel.statusUpdateHandler(serverResults, poolResults);
        monitoringPanel.revalidate();
        monitoringPanel.repaint();
    }

    @Override
    public JPanel getPanel() {
        if (monitoringPanel == null) monitoringPanel = new MonitoringPanel();
        return monitoringPanel;
    }

    @Override
    public boolean closeCheck() {
        return false;
    }
}
