package Functionality.Monitoring.ExtensiveStatusPanel;

import Assets.Variables;
import Functionality.Monitoring.ServerResult;
import GUI.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ExtraServerInfoDialog extends JDialog {

    private ServerResult serverResult;

    private JLabel lastUpload, lastDownload, processorUsage;

    private int lastUploadVal, lastDownloadVal;

    private StatusGraph networkGraph, cpuGraph;

    public ExtraServerInfoDialog(ServerResult serverResult) {
        super(Frame.defaultFrame, false);
        this.serverResult = serverResult;
        this.lastUploadVal = serverResult.getBytesOut();
        this.lastDownloadVal = serverResult.getBytesIn();

        setTitle("Uitgebreide informatie " + serverResult.getServerName());
        setSize(500, 600);
        setLocationRelativeTo(Frame.defaultFrame);
        setLayout(new GridLayout(2, 1));

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                close();
            }
        });

        drawNetworkElements();
        drawCPUElements();

        setVisible(true);
    }

    private void close() {
        serverResult.removeLinkedDialog(this);
    }

    private void drawCPUElements() {
        JPanel cpuPanel = new JPanel();
        cpuPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        cpuPanel.setLayout(new BorderLayout());
        cpuPanel.setBackground(Variables.transparent);

        JLabel cpuTitle = new JLabel("CPU");
        cpuTitle.setFont(new Font(cpuTitle.getFont().getName(), Font.BOLD, 15));
        cpuPanel.add(cpuTitle, BorderLayout.NORTH);

        cpuGraph = new StatusGraph(new Color(23, 147, 223), "%");
        cpuPanel.add(cpuGraph, BorderLayout.CENTER);

        JPanel currentUsagePanel = new JPanel();
        currentUsagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        currentUsagePanel.setBackground(Variables.transparent);

        processorUsage = new JLabel(String.format("%.2f procent", serverResult.getCpuUsage()));
        processorUsage.setIcon(new ImageIcon(Variables.getImage("cpu")));

        currentUsagePanel.add(processorUsage);

        cpuPanel.add(currentUsagePanel, BorderLayout.SOUTH);

        add(cpuPanel);
    }

    private void drawNetworkElements() {
        JPanel networkPanel = new JPanel();
        networkPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        networkPanel.setLayout(new BorderLayout());
        networkPanel.setBackground(Variables.transparent);

        JLabel networkTitle = new JLabel("Netwerk");
        networkTitle.setFont(new Font(networkTitle.getFont().getName(), Font.BOLD, 15));
        networkPanel.add(networkTitle, BorderLayout.NORTH);

        networkGraph = new StatusGraph(new Color(223, 143, 23), "kb");
        networkPanel.add(networkGraph, BorderLayout.CENTER);

        JPanel currentThroughPutPanel = new JPanel();
        currentThroughPutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        currentThroughPutPanel.setBackground(Variables.transparent);

        lastUpload = new JLabel("0kb");
        lastUpload.setIcon(new ImageIcon(Variables.getImage("upload")));
        lastDownload = new JLabel("0kb");
        lastDownload.setIcon(new ImageIcon(Variables.getImage("download")));

        currentThroughPutPanel.add(lastUpload);
        currentThroughPutPanel.add(lastDownload);

        networkPanel.add(currentThroughPutPanel, BorderLayout.SOUTH);

        add(networkPanel);
    }

    public void updateCycle() {
        processorUsage.setText(String.format("%.2f procent", serverResult.getCpuUsage()));
        lastUpload.setText((serverResult.getBytesOut() - lastUploadVal)/1000 + " kb");
        lastDownload.setText((serverResult.getBytesIn() - lastDownloadVal)/1000 + " kb");

        double total = (double) ((serverResult.getBytesOut() - lastUploadVal)/1000) + ((serverResult.getBytesIn() - lastDownloadVal)/1000);

        networkGraph.addValue((total < 1) ? -1 : total);
        cpuGraph.addValue(serverResult.getCpuUsage());

        lastUploadVal = serverResult.getBytesOut();
        lastDownloadVal = serverResult.getBytesIn();

        repaint();
    }
}
