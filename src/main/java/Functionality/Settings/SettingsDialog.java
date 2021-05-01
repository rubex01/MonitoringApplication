package Functionality.Settings;

import Assets.Variables;
import GUI.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SettingsDialog extends JDialog implements ActionListener {

    private JPanel settingsPanel;

    private ArrayList<SettingItem> settingItems;

    private JButton jbOk;

    public SettingsDialog() {
        super(Frame.defaultFrame, true);
        settingItems = new ArrayList<>();
        addSettingItems();

        setTitle("Instellingen");
        setLayout(new BorderLayout());
        setBackground(Variables.backgroundLighter);
        setSize(500, 430);
        setLocationRelativeTo(Frame.defaultFrame);

        drawItems();

        setVisible(true);
    }

    private void addSettingItems() {
        settingItems.add(new SettingItem("", "Servers configuratie", SettingItem.SPACE));
        settingItems.add(new SettingItem("server_file_location", "Server configuratie bestand", SettingItem.FILE));
        settingItems.add(new SettingItem("", "Database", SettingItem.SPACE));
        settingItems.add(new SettingItem("database_host", "Host", SettingItem.TEXT));
        settingItems.add(new SettingItem("database", "Database", SettingItem.TEXT));
        settingItems.add(new SettingItem("database_username", "Gebruikersnaam", SettingItem.TEXT));
        settingItems.add(new SettingItem("database_password", "Wachtwoord", SettingItem.PASSWORD));
        settingItems.add(new SettingItem("", "Status", SettingItem.SPACE));
        settingItems.add(new SettingItem("ssh_host", "Host", SettingItem.TEXT, true));
        settingItems.add(new SettingItem("ssh_user", "Gebruikersnaam", SettingItem.TEXT, true));
        settingItems.add(new SettingItem("ssh_password", "Wachtwoord", SettingItem.PASSWORD, true));
        // Add setting items here
    }

    private void drawItems() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(settingsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Variables.nonFocus));

        for (SettingItem item : settingItems) {
            settingsPanel.add(item.drawSelf());
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        jbOk = new JButton("Opslaan");
        jbOk.addActionListener(this);
        JButton jbCancel = new JButton("Annuleren");
        jbCancel.addActionListener(this);

        buttonPanel.add(jbOk);
        buttonPanel.add(jbCancel);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbOk) {
            for (SettingItem item : settingItems) {
                item.addSaveToQueue();
            }
            SettingsController.executeQueue();
        }
        setVisible(false);
        dispose();
    }
}