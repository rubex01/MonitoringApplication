package Functionality.Monitoring;

import Assets.Variables;
import Functionality.Monitoring.ExtensiveStatusPanel.ExtensiveStatusPanel;
import Functionality.Monitoring.QuickStatusPanel.QuickStatusPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MonitoringPanel extends JPanel {

    public MonitoringPanel(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10,10,10,10));

        drawQuickStatus();
        drawExtensiveStatus();
    }

    private void drawQuickStatus() {
        JPanel quickStatusPanel = new JPanel();
        quickStatusPanel.setLayout(new GridLayout(1, 3, 10, 10));
        quickStatusPanel.setPreferredSize(new Dimension(500, 75));
        quickStatusPanel.setBorder(new EmptyBorder(0,0,10,0));

        QuickStatusPanel panel1 = new QuickStatusPanel(QuickStatusPanel.UPTIME, "103");
        QuickStatusPanel panel2 = new QuickStatusPanel(QuickStatusPanel.DOWNTIME, "10");
        QuickStatusPanel panel3 = new QuickStatusPanel(QuickStatusPanel.OUTAGES, "2");

        quickStatusPanel.add(panel1);
        quickStatusPanel.add(panel2);
        quickStatusPanel.add(panel3);

        add(quickStatusPanel, BorderLayout.NORTH);
    }

    private void drawExtensiveStatus(){
        JPanel extensiveStatusPanel = new JPanel();
        extensiveStatusPanel.setLayout(new GridLayout(1, 3, 10, 10));

        ExtensiveStatusPanel panel1 = new ExtensiveStatusPanel();
        panel1.setBackground(Variables.white);
        ExtensiveStatusPanel panel2 = new ExtensiveStatusPanel();
        panel2.setBackground(Variables.white);
        ExtensiveStatusPanel panel3 = new ExtensiveStatusPanel();
        panel3.setBackground(Variables.white);

        extensiveStatusPanel.add(panel1);
        extensiveStatusPanel.add(panel2);
        extensiveStatusPanel.add(panel3);

        add(extensiveStatusPanel, BorderLayout.CENTER);
    }

}
