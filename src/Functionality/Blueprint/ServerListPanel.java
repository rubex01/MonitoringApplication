package Functionality.Blueprint;

import Assets.Variables;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerListPanel extends JPanel implements ActionListener {

    private JPanel serverPanel;

    private JButton toggleButton;

    private Blueprint parent;

    public ServerListPanel(Blueprint parent) {
        this.parent = parent;
        setBackground(Variables.backgroundLighter);
        setLayout(new BorderLayout());
        drawPanels();
    }

    private void drawPanels() {
        JPanel togglePanel = new JPanel();
        toggleButton = new JButton("Servers");
        toggleButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        toggleButton.setHorizontalAlignment(SwingConstants.RIGHT);
        toggleButton.setHorizontalTextPosition(SwingConstants.LEFT);
        toggleButton.setBackground(Variables.background);
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setMargin(new Insets(2, 14, 0, 14));
        toggleButton.addActionListener(this);
        toggleButton.setIcon(new ImageIcon(Variables.getImage("arrow_down")));
        togglePanel.add(toggleButton);
        togglePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        togglePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        togglePanel.setBackground(Variables.backgroundLighter);
        add(togglePanel, BorderLayout.NORTH);

        serverPanel = new JPanel();
        serverPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        serverPanel.setBackground(Variables.background);
        for (Server server : parent.getCurrentBlueprintServerList()) {
            serverPanel.add(new ServerListItemPanel(server));
        }

        add(serverPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toggleButton) {
            serverPanel.setVisible(!serverPanel.isVisible());
            toggleButton.setIcon(new ImageIcon(Variables.getImage((serverPanel.isVisible() ? "arrow_down" : "arrow_up"))));
        }
    }
}
