package Functionality.Monitoring;

import Assets.Variables;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class ServerResultContextMenu  extends JPopupMenu implements ActionListener {

    private JButton closeDialogs, copyIp, copyName;

    private ServerResult parent;

    public ServerResultContextMenu(ServerResult parent) {
        this.parent = parent;

        setBorder(new EmptyBorder(8, 5, 5, 8));
        setBackground(Variables.transparent);

        addItems();
    }

    private void addItems() {
        closeDialogs = new JButton("Bijbehorende dialogen sluiten");
        closeDialogs.setBackground(Variables.transparent);
        closeDialogs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeDialogs.setFocusPainted(false);
        closeDialogs.setBorderPainted(false);
        closeDialogs.setContentAreaFilled(false);
        closeDialogs.addActionListener(this);
        add(closeDialogs);

        copyIp = new JButton("IP-adres kopiëren");
        copyIp.setBackground(Variables.transparent);
        copyIp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyIp.setFocusPainted(false);
        copyIp.setBorderPainted(false);
        copyIp.setContentAreaFilled(false);
        copyIp.addActionListener(this);
        add(copyIp);

        copyName = new JButton("Servernaam kopiëren");
        copyName.setBackground(Variables.transparent);
        copyName.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyName.setFocusPainted(false);
        copyName.setBorderPainted(false);
        copyName.setContentAreaFilled(false);
        copyName.addActionListener(this);
        add(copyName);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2 = (Graphics2D) g;

        graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 13, 13);
        graphics2.setColor(Variables.nonFocus);
        graphics2.fill(roundedRectangle);

        setBackground(Variables.transparent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeDialogs) {
            parent.removeAllDialogs();
        }
        else if (e.getSource() == copyIp) {
            StringSelection stringSelection = new StringSelection(parent.getIpAddress());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
        else if (e.getSource() == copyName) {
            StringSelection stringSelection = new StringSelection(parent.getServerName());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
        setVisible(false);
    }
}