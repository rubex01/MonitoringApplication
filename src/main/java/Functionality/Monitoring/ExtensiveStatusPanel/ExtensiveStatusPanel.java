package Functionality.Monitoring.ExtensiveStatusPanel;

import Assets.Variables;
import Functionality.Monitoring.PoolResult;
import Functionality.Monitoring.ServerResult;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class ExtensiveStatusPanel extends JPanel {

    private int type;

    private PoolResult pool;

    private JPanel serverPanel, poolInfoPanel;

    private JLabel uptimeLabel, downtimeLabel, downloadLabel, uploadLabel;

    public ExtensiveStatusPanel(int type){
        this.type = type;

        setBorder(new EmptyBorder(0, 21, 21, 21));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        drawItems();
    }

    private String getTitleText() {
        switch (type) {
            case Server.FIREWALL:
                return "Firewalls";
            case Server.WEBSERVER:
                return  "Webservers";
            case Server.DATABASE:
                return "Databases";
        }
        return null;
    }

    private String getImageName() {
        switch (type) {
            case Server.FIREWALL:
                return "firewall_white";
            case Server.WEBSERVER:
                return  "webserver_white";
            case Server.DATABASE:
                return "database_white";
        }
        return null;
    }

    private void drawItems() {
        JLabel title = new JLabel(getTitleText());
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 15));
        title.setForeground(new Color(68, 68, 68));
        title.setBorder(new EmptyBorder(3, 30, 10, 1050));
        add(title);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 0, 0, 0));
        contentPanel.setLayout(new BorderLayout());

        poolInfoPanel = new JPanel();
        poolInfoPanel.setLayout(new GridLayout(2, 2, 10, 3));
        poolInfoPanel.setBackground(new Color(0, 0, 0, 0));
        poolInfoPanel.setBorder(new EmptyBorder(0, 0, 13, 0));
        uptimeLabel = new JLabel("uptime:");
        downtimeLabel = new JLabel("Downtime:");
        uploadLabel = new JLabel("Upload:");
        downloadLabel = new JLabel("Download:");
        poolInfoPanel.add(uptimeLabel);
        poolInfoPanel.add(uploadLabel);
        poolInfoPanel.add(downtimeLabel);
        poolInfoPanel.add(downloadLabel);
        contentPanel.add(poolInfoPanel, BorderLayout.NORTH);

        serverPanel = new JPanel();
        serverPanel.setBackground(Variables.white);
        contentPanel.add(serverPanel, BorderLayout.CENTER);

        add(contentPanel);
    }

    private String decideTextBasedOnTime(int time) {
        if (time < 60) {
            return time + " seconden";
        }
        else if ((time/60) < 59) {
            return (time/60) + " minuten";
        }
        else {
            return (time/60/60) + " uren";
        }
    }

    private String decideTextBasedOnSize(int size) {
        if (size < 1000) {
            return size + "B";
        }
        else if (size < 1000000) {
            return (size/1000) + "KB";
        }
        else {
            return (size/100000) + "MB";
        }
    }

    public void updateTrigger(ArrayList<ServerResult> serverResults, PoolResult poolResult) {
        pool = poolResult;
        if (poolResult != null) {
            uptimeLabel.setText("Uptime: " + decideTextBasedOnTime(poolResult.getUptime()));
            downtimeLabel.setText("Downtime: " + decideTextBasedOnTime(poolResult.getDowntime()));
            downloadLabel.setText("Upload: " + decideTextBasedOnSize(poolResult.getBytesIn()));
            uploadLabel.setText("Download: " + decideTextBasedOnSize(poolResult.getBytesOut()));
        }
        else {
            uptimeLabel.setText("Uptime: " + decideTextBasedOnTime(serverResults.get(1).getUptime()));
            downtimeLabel.setText("Downtime: " + decideTextBasedOnTime(serverResults.get(1).getDowntime()));
            downloadLabel.setText("Upload: " + decideTextBasedOnSize(serverResults.get(1).getBytesIn()));
            uploadLabel.setText("Download: " + decideTextBasedOnSize(serverResults.get(1).getBytesOut()));
        }
        serverPanel.removeAll();
        serverPanel.setLayout(new GridLayout(serverResults.size(), 0, 0, 10));
        for (ServerResult serverResult : serverResults) {
            if (serverResult.getType() == Server.FIREWALL && serverResult.getServerName().equals("FRONTEND")) continue;
            serverPanel.add(serverResult.paintSelf());
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);
        Graphics2D graphics2 = (Graphics2D) g;

        graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        RoundRectangle2D contentRectangle = new RoundRectangle2D.Float(0, 19, getWidth(), getHeight()-19, 16, 16);
        graphics2.fill(contentRectangle);

        RoundRectangle2D titleRectangle = new RoundRectangle2D.Float(29, 5, 140, 26, 25, 25);
        graphics2.fill(titleRectangle);
        graphics2.setColor(Variables.backgroundLighter);
        graphics2.draw(titleRectangle);

        if (pool == null || pool.isOnline()) {
            graphics2.setColor(Variables.onlineColor);
        }
        else {
            graphics2.setColor(Variables.offlineColor);
        }
        graphics2.fillOval(149, 11, 14, 14);

        graphics2.drawImage(Variables.getImage("shadow"), 8, 16, null);

        graphics2.setColor(Variables.serverPool);
        graphics2.fillOval(10, 0, 38, 38);

        graphics2.drawImage(Variables.getImage(getImageName()), 16, 6, 26, 26, null);
    }

}
