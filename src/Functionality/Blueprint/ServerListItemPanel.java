package Functionality.Blueprint;

import Assets.Variables;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ServerListItemPanel extends JPanel implements MouseListener {

    private Server server;

    private JPanel infoPanel;

    public ServerListItemPanel(Server server) {
        this.server = server;
        addMouseListener(this);
        setLayout(new BorderLayout());
        setBackground(Variables.white);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        drawItems();
    }

    private void drawItems() {
        JLabel imageLabel = new JLabel("");
        imageLabel.setIcon(new ImageIcon(server.getImage()));
        add(imageLabel, BorderLayout.NORTH);

        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(Variables.white);

        JLabel serverName = new JLabel(server.getName());
        JLabel serverUptime = new JLabel("Uptime: " + server.getUptime() + "%");
        serverUptime.setFont(new Font(serverUptime.getFont().getName(), Font.PLAIN, 11));
        JLabel serverPrice = new JLabel("Prijs: €" + server.getPrice());
        serverPrice.setFont(new Font(serverPrice.getFont().getName(), Font.PLAIN, 11));
        infoPanel.add(serverName, BorderLayout.NORTH);
        infoPanel.add(serverUptime, BorderLayout.CENTER);
        infoPanel.add(serverPrice, BorderLayout.SOUTH);

        add(infoPanel, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO: add current item to design
        System.out.println("Add server " + server.getName() + " to infrastructure blueprint");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(Variables.focus);
        infoPanel.setBackground(Variables.focus);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(Variables.white);
        infoPanel.setBackground(Variables.white);
    }
}
