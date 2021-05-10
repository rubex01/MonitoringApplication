package Functionality.Blueprint;

import Assets.DefaultScrollPane;
import Assets.Variables;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.text.DecimalFormat;

public class ServerListPanel extends JPanel implements ActionListener, Serializable, KeyListener {

    private JPanel serverPanel;

    private DefaultScrollPane scrollPane;

    private JButton toggleButton;

    private JTextField searchField;

    private JLabel totalInfoLabel, imageLabel;

    private Blueprint parent;

    private Timer animationTimer;

    private int animationOffset;

    private boolean visible = true;

    public ServerListPanel(Blueprint parent) {
        this.parent = parent;
        setBackground(Variables.backgroundLighter);
        setLayout(new BorderLayout());
        animationTimer = new Timer(10, this);
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

        imageLabel = new JLabel(new ImageIcon(Variables.getImage("search")));
        togglePanel.add(imageLabel);

        searchField = new JTextField(8);
        searchField.addKeyListener(this);
        searchField.setBackground(Variables.white);
        searchField.setBorder(new LineBorder(Variables.nonFocus, 1, false));
        togglePanel.add(searchField);

        totalInfoLabel = new JLabel();
        togglePanel.add(totalInfoLabel);

        togglePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        togglePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        togglePanel.setBackground(Variables.backgroundLighter);
        add(togglePanel, BorderLayout.NORTH);

        serverPanel = new JPanel();
        serverPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        serverPanel.setBackground(Variables.background);
        for (Server server : parent.getCurrentBlueprintServerList()) {
            serverPanel.add(new ServerListItemPanel(server, parent));
        }

        add(serverPanel, BorderLayout.CENTER);

        scrollPane = new DefaultScrollPane(serverPanel);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        add(scrollPane);
    }

    public void updateInfoLabel(double uptime, int price, int amount) {
        DecimalFormat df = new DecimalFormat("##.###");
        if (amount > 0) totalInfoLabel.setText("Uptime: " + (df.format(uptime*100)) + "%  Prijs: €" + price + "  Servers: " + amount);
        else totalInfoLabel.setText("");
    }

    private void searchForServer() {
        serverPanel.removeAll();
        for (Server server : parent.getCurrentBlueprintServerList()) {
            try {
                if (
                        server.getName().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                        server.getTypeName().toLowerCase().contains(searchField.getText().toLowerCase())
                ) {
                    serverPanel.add(new ServerListItemPanel(server, parent));
                    continue;
                } else if (
                        searchField.getText().contains("%") &&
                        server.getUptime() >= Double.valueOf(searchField.getText().substring(0, (searchField.getText().length() - 1)))
                ) {
                    serverPanel.add(new ServerListItemPanel(server, parent));
                    continue;
                } else if (
                        searchField.getText().contains("€") &&
                        server.getPrice() <= Integer.valueOf(searchField.getText().substring(1))
                ) {
                    serverPanel.add(new ServerListItemPanel(server, parent));
                    continue;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (serverPanel.getComponents().length == 0) {
            JLabel notFound = new JLabel("Geen servers gevonden");
            notFound.setBorder(new EmptyBorder(50, 5, 50, 0));
            serverPanel.add(notFound);
        }
    }

    private void downAnimation() {
        setBorder(new EmptyBorder(0, 0, animationOffset, 0));
        animationOffset = animationOffset - 5;
        if (animationOffset <= -131) {
            animationOffset = -131;
            animationTimer.stop();
            setBorder(new EmptyBorder(0, 0, 0, 0));
            setVisibility(false);
            toggleButton.setIcon(new ImageIcon(Variables.getImage("arrow_up")));
            visible = false;
        }
    }

    private void setVisibility(boolean value) {
        serverPanel.setVisible(value);
        scrollPane.setVisible(value);
        searchField.setVisible(value);
        imageLabel.setVisible(value);
    }

    private void upAnimation() {
        setVisibility(true);
        setBorder(new EmptyBorder(0, 0, animationOffset, 0));
        animationOffset = animationOffset + 5;
        if (animationOffset >= 0) {
            animationOffset = 0;
            animationTimer.stop();
            setBorder(new EmptyBorder(0, 0, 0, 0));
            toggleButton.setIcon(new ImageIcon(Variables.getImage("arrow_down")));
            visible = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            if (visible) downAnimation();
            else upAnimation();
        }
        else if (e.getSource() == toggleButton) {
            animationTimer.start();
        }
        else if (e.getSource() == searchField) {
            searchForServer();
        }
        revalidate();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        searchForServer();
        revalidate();
        repaint();
    }
}
