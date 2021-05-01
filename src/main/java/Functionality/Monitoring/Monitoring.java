package Functionality.Monitoring;

import GUI.TabModel;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Monitoring extends TabModel implements Serializable {

    private APIController apiController;

    private MonitoringPanel monitoringPanel;

    public Monitoring() {
        super("Monitoring", false);

        startApiController();
    }

    private void startApiController() {
        apiController = new APIController(this);

        Timer timer = new Timer();
        timer.schedule(apiController, 0,6000);
    }

    public void startUpdateCycle(ArrayList<ServerResult> serverResults, ArrayList<PoolResult> poolResults) {
        monitoringPanel.statusUpdateHandler(serverResults, poolResults);
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
