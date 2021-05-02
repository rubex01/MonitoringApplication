package Functionality.Monitoring;

import Functionality.Monitoring.ExtensiveStatusPanel.ExtensiveStatusPanel;
import Functionality.Monitoring.QuickStatusPanel.QuickStatusPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class MonitoringPanel extends JPanel {

    private QuickStatusPanel quickStatusPanel1, quickStatusPanel2, quickStatusPanel3;

    private ExtensiveStatusPanel extensiveStatusPanel1, extensiveStatusPanel2, extensiveStatusPanel3;

    public MonitoringPanel(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10,10,10,10));

        drawQuickStatus();
        drawExtensiveStatus();
    }

    private void drawQuickStatus() {
        JPanel quickStatusPanel = new JPanel();
        quickStatusPanel.setLayout(new GridLayout(1, 3, 10, 10));
        quickStatusPanel.setPreferredSize(new Dimension(500, 80));
        quickStatusPanel.setBorder(new EmptyBorder(0,0,10,0));

        quickStatusPanel1 = new QuickStatusPanel(QuickStatusPanel.UPTIME, 0);
        quickStatusPanel2 = new QuickStatusPanel(QuickStatusPanel.DOWNTIME, 0);
        quickStatusPanel3 = new QuickStatusPanel(QuickStatusPanel.OUTAGES, 0);

        quickStatusPanel.add(quickStatusPanel1);
        quickStatusPanel.add(quickStatusPanel2);
        quickStatusPanel.add(quickStatusPanel3);

        add(quickStatusPanel, BorderLayout.NORTH);
    }

    private void drawExtensiveStatus(){
        JPanel extensiveStatusPanel = new JPanel();
        extensiveStatusPanel.setLayout(new GridLayout(1, 3, 10, 10));

        extensiveStatusPanel1 = new ExtensiveStatusPanel();
        extensiveStatusPanel2 = new ExtensiveStatusPanel();
        extensiveStatusPanel3 = new ExtensiveStatusPanel();

        extensiveStatusPanel.add(extensiveStatusPanel1);
        extensiveStatusPanel.add(extensiveStatusPanel2);
        extensiveStatusPanel.add(extensiveStatusPanel3);

        add(extensiveStatusPanel, BorderLayout.CENTER);
    }

    public void statusUpdateHandler(ArrayList<ServerResult> serverResults, ArrayList<PoolResult> poolResults) {
        int currentOut = 0;
        int uptime = -1;
        int downtime = 0;
        boolean poolDown = false;
        for (PoolResult poolStatus : poolResults) {
            downtime += poolStatus.getDowntime();
            if (!poolStatus.isOnline()) poolDown = true;
            if (poolStatus.getUptime() < uptime || uptime == -1) uptime = poolStatus.getUptime();
        }
        for (ServerResult status : serverResults) {
            if (!status.isOnline()) currentOut++;
        }
        quickStatusPanel1.updateTrigger(poolDown ? 0 : uptime);
        quickStatusPanel2.updateTrigger(downtime);
        quickStatusPanel3.updateTrigger(currentOut);
    }

}
