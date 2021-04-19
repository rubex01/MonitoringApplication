package Functionality.Blueprint;

import Assets.Variables;
import Functionality.Server;
import Functionality.ServerParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerListPanel extends JPanel implements ActionListener {

    private JPanel serverPanel;

    private JButton button;

    public ServerListPanel() {
        setBackground(Variables.backgroundLighter);
        setLayout(new BorderLayout());

        JPanel togglePanel = new JPanel();
        button = new JButton("Servers");
        button.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        button.setHorizontalAlignment(SwingConstants.RIGHT);
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.setBackground(Variables.background);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMargin(new Insets(2, 14, 0, 14));
        button.addActionListener(this);
        button.setIcon(new ImageIcon(Variables.getImage("arrow_down")));
        togglePanel.add(button);
        togglePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        togglePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        togglePanel.setBackground(Variables.backgroundLighter);
        add(togglePanel, BorderLayout.NORTH);

        serverPanel = new JPanel();
        serverPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        serverPanel.setBackground(Variables.background);
        for (Server server : ServerParser.serverList) {
            serverPanel.add(new ServerListItemPanel(server));
        }

        add(serverPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        serverPanel.setVisible(!serverPanel.isVisible());
        if (serverPanel.isVisible()) {
            button.setIcon(new ImageIcon(Variables.getImage("arrow_down")));
        }
        else {
            button.setIcon(new ImageIcon(Variables.getImage("arrow_up")));
        }

    }
}
