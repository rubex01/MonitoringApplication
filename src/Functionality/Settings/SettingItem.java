package Functionality.Settings;

import Functionality.Settings.SettingPanels.BooleanPanel;
import Functionality.Settings.SettingPanels.FilePanel;
import Functionality.Settings.SettingPanels.PasswordPanel;
import Functionality.Settings.SettingPanels.TextPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

    public SettingItem(String configKey, String settingTitle, int settingType) {
        this(configKey, settingTitle, settingType, false);
    }

    public JPanel drawSelf() {
        JPanel panel = new JPanel();

        if (type == SPACE) {
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.setBorder(new EmptyBorder(6, 0, 6, 0));
            JLabel title = new JLabel(settingsTitle);
            title.setFont(new Font(title.getFont().getName(), Font.BOLD, 15));
            panel.add(title);
            return panel;
        }

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

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
