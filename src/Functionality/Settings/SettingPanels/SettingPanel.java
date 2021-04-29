package Functionality.Settings.SettingPanels;

import Assets.Variables;
import Functionality.Settings.SettingItem;
import Functionality.Settings.SettingsController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SettingPanel extends JPanel implements ActionListener {

    protected SettingItem parent;

    protected String value;

    protected JButton reset;

    public SettingPanel(SettingItem parent) {
        this.parent = parent;
        this.value = parent.getCurrentValue();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        drawItems();
        drawResetButton();
    }

    private void drawResetButton() {
        reset = new JButton("");
        reset.addActionListener(this);
        reset.setIcon(new ImageIcon(Variables.getImage("reset")));
        reset.setFocusPainted(false);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        checkResetVisibility();

        add(reset);
    }

    protected abstract void drawItems();

    public abstract void reconsiderValueDisplay();

    protected void resetValue() {
        value = SettingsController.getDefault(parent.getConfigKey());
        parent.setNewValue(value);
        reconsiderValueDisplay();
        checkResetVisibility();
    }

    public void checkResetVisibility() {
        if (SettingsController.isDefault(parent.getConfigKey(), value)) {
            reset.setVisible(false);
            return;
        }
        reset.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != reset) return;
        resetValue();
    }
}
