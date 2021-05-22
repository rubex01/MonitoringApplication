package Functionality.Monitoring;

import Assets.Variables;
import Functionality.Monitoring.ExtensiveStatusPanel.ExtraServerInfoDialog;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class ServerResult implements Serializable, MouseListener {

    private int type, downtime, sid, uptime, bytesIn, bytesOut, weight;

    private String serverName, checkStatus, checkDesc, ipAddress;

    private JLabel statusInfo, checkDescLabel;

    private JPanel statusPanel, panel;

    private boolean online;

    private ArrayList<ExtraServerInfoDialog> linkedDialogs;

    public ServerResult(String serverName, String checkStatus, String checkDesc, String ipAddress, boolean online, int downtime, int type, int sid, int uptime, int bytesIn, int bytesOut, int weight) {
        this.serverName = serverName;
        this.checkStatus = checkStatus;
        this.checkDesc = checkDesc;
        this.ipAddress = ipAddress;
        this.online = online;
        this.downtime = downtime;
        this.type = type;
        this.sid = sid;
        this.uptime = uptime;
        this.bytesIn = bytesIn;
        this.bytesOut = bytesOut;
        this.weight = weight;
        this.linkedDialogs = new ArrayList<>();
    }

    public int getUptime() {
        return uptime;
    }

    public int getType() {
        return type;
    }

    public int getDowntime() {
        return downtime;
    }

    public int getSid() {
        return sid;
    }

    public boolean isOnline() {
        return online;
    }

    public String getServerName() {
        return serverName;
    }

    public double getCpuUsage() {
        return (8000 * Math.log((100/(double) weight))/461);
    }

    public void updateStatus(String checkStatus, String checkDesc, boolean online, int downtime, int uptime, int bytesIn, int bytesOut, int weight) {
        this.checkStatus = checkStatus;
        this.checkDesc = checkDesc;
        this.online = online;
        this.downtime = downtime;
        this.uptime = uptime;
        this.bytesIn = bytesIn;
        this.bytesOut = bytesOut;
        this.weight = weight;
    }

    private String getcheckDescForDisplay() {
        return (checkDesc.equals("")) ? "Checks passed" : checkDesc;
    }

    public int getBytesOut() {
        return bytesOut;
    }

    public int getBytesIn() {
        return bytesIn;
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

    public void removeLinkedDialog(ExtraServerInfoDialog dialog) {
        linkedDialogs.remove(dialog);
    }

    public JPanel paintSelf() {
        for (ExtraServerInfoDialog dialog : linkedDialogs) dialog.updateCycle();
        if (panel != null) {
            statusInfo.setText(decideTextBasedOnTime(uptime) + " online | " + decideTextBasedOnTime(downtime) + " offline");
            statusPanel.repaint();
            checkDescLabel.setText(getcheckDescForDisplay());
            return panel;
        }
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D graphics2 = (Graphics2D) g;

                graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                graphics2.setColor(Variables.backgroundLighter);
                RoundRectangle2D background = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
                graphics2.fill(background);

                int offsetLeft = 255;
                int offsetTop = 10;
                int size = 40;

                if (type == Server.FIREWALL) return;
                int degree = (int) (getCpuUsage()*1000/360);

                Color circleColor = Variables.onlineColor;
                if (getCpuUsage() > 20) {
                    circleColor = Variables.warningColor;
                }
                else if (getCpuUsage() > 50) {
                    circleColor = Variables.offlineColor;
                }

                graphics2.setColor(Variables.nonFocus.brighter());
                graphics2.fillOval(offsetLeft, offsetTop, size, size);
                graphics2.setColor(circleColor);
                graphics2.fillArc(offsetLeft, offsetTop, size, size, 90, -degree);
                graphics2.setColor(Variables.backgroundLighter);
                graphics2.fillOval(offsetLeft+5, offsetTop+5, 30, 30);

                graphics2.setColor(Variables.black);
                graphics2.setFont(new Font(getFont().getName(), Font.BOLD, 11));
                graphics2.drawString(String.format("%.1f", getCpuUsage()) + "%", offsetLeft+11, offsetTop+25);
            }
        };
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());
        panel.setBackground(Variables.transparent);
        panel.addMouseListener(this);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Variables.transparent);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(new JLabel(serverName + " (" + sid + ")"));
        JLabel ipLabel = new JLabel(ipAddress);
        ipLabel.setForeground(Variables.nonFocus);
        ipLabel.setFont(new Font(ipLabel.getFont().getName(), Font.PLAIN, 12));
        headerPanel.add(ipLabel);

        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(Variables.transparent);
        statusInfo = new JLabel(decideTextBasedOnTime(uptime) + " online | " + decideTextBasedOnTime(downtime) + " offline");
        statusInfo.setFont(new Font(statusInfo.getFont().getName(), Font.PLAIN, 12));
        statusInfo.setForeground(Variables.subText);
        infoPanel.add(statusInfo);

        panel.add(infoPanel, BorderLayout.CENTER);

        statusPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D graphics2 = (Graphics2D) g;

                graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                graphics2.setColor(Variables.nonFocus);
                RoundRectangle2D border = new RoundRectangle2D.Float(0, 0, 250, 26, 30, 30);
                graphics2.fill(border);

                graphics2.setColor(Variables.white);
                RoundRectangle2D content = new RoundRectangle2D.Float(2, 2, 246, 22, 25, 25);
                graphics2.fill(content);

                graphics2.setFont(new Font(statusInfo.getFont().getName(), Font.BOLD, 12));
                String status = "";
                if (isOnline()) {
                    graphics2.setColor(Variables.onlineColor);
                    status = "Online";
                }
                else {
                    graphics2.setColor(Variables.offlineColor);
                    status = "Offline";
                }
                graphics2.drawString(status, 205, 17);
            }
        };
        statusPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(Variables.white);
        checkDescLabel = new JLabel(getcheckDescForDisplay());
        checkDescLabel.setBorder(new EmptyBorder(0, 5, 0, 150));
        statusPanel.add(checkDescLabel);

        panel.add(statusPanel, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == panel && type != Server.FIREWALL) {
            linkedDialogs.add(new ExtraServerInfoDialog(this));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
