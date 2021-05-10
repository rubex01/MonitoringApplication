package Functionality.Settings;

import Assets.Variables;
import Functionality.Settings.SettingPanels.BooleanPanel;
import Functionality.Settings.SettingPanels.FilePanel;
import Functionality.Settings.SettingPanels.PasswordPanel;
import Functionality.Settings.SettingPanels.TextPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SettingItem {

    public static final int BOOLEAN = 0, FILE = 1, TEXT = 2, PASSWORD = 3, SPACE = 4;

    private String currentValue, settingsTitle, configKey, newValue;

    private int type;

    private boolean restartToTakeEffect;

    public SettingItem(String configKey, String settingTitle, int settingType, boolean restartToTakeEffect) {
        this.restartToTakeEffect = restartToTakeEffect;
        this.configKey = configKey;
        this.settingsTitle = settingTitle;
        this.type = settingType;
        this.currentValue = SettingsController.getSetting(configKey);
    }

    public int getType() {
        return type;
    }

    public SettingItem(String configKey, String settingTitle, int settingType) {
        this(configKey, settingTitle, settingType, false);
    }

    public JPanel drawSelf() {
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (type != SPACE) return;

                Graphics2D g2 = (Graphics2D) g;

                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHints(rh);

                RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(3, 45, settingsTitle.length()*9, 3, 3, 3);

                g2.setColor(Variables.highlightColor);
                g2.fill(roundedRectangle);

            }
        };

        if (type == SPACE) {
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.setBorder(new EmptyBorder(20, 0, 10, 0));
            JLabel title = new JLabel(settingsTitle);
            title.setFont(new Font(title.getFont().getName(), Font.BOLD, 15));
            panel.add(title);
            return panel;
        }

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(new EmptyBorder(0, 4, 0, 4));

        panel.add(new JLabel(settingsTitle));
        panel.add(Box.createHorizontalGlue());

        switch (type) {
            case BOOLEAN:
                panel.add(new BooleanPanel(this));
                break;
            case FILE:
                panel.add(new FilePanel(this));
                break;
            case TEXT:
                panel.add(new TextPanel(this));
                break;
            case PASSWORD:
                panel.add(new PasswordPanel(this));
                break;
        }

        return panel;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public void addSaveToQueue() {
        if (type == SPACE || newValue == null) return;
        SettingsController.addToQueue(configKey, newValue);
        if (restartToTakeEffect) SettingsController.setShowRestartOption(true);
    }

    public String getNewValue() {
        return newValue;
    }

    public String getConfigKey() {
        return configKey;
    }

}
