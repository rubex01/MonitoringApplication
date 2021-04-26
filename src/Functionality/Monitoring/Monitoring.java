package Functionality.Monitoring;

import GUI.TabModel;

import javax.swing.*;
import java.util.Timer;

public class Monitoring extends TabModel {

    private APIController apiController;

    private MonitoringPanel monitoringPanel;

    public Monitoring() {
        super("Monitoring", false);

        startApiController();
    }

    private void startApiController() {
        apiController = new APIController();
        java.util.Timer timer = new Timer();
        timer.schedule(apiController, 0, 6000);
    }

    @Override
    public JPanel getPanel() {
        monitoringPanel = new MonitoringPanel();
        return monitoringPanel;
    }

    @Override
    public boolean closeCheck() {
        return false;
    }
}
