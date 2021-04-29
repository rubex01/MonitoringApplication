package Functionality.Settings.SettingPanels;

import Functionality.Settings.SettingItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BooleanPanel extends SettingPanel implements ActionListener {

    private JButton jbToggle;

    public BooleanPanel(SettingItem parent) {
        super(parent);
    }

    @Override
    protected void drawItems() {
        jbToggle = new JButton();
        reconsiderValueDisplay();
        jbToggle.addActionListener(this);

        add(jbToggle);
    }

    @Override
    public void reconsiderValueDisplay() {
        jbToggle.setText((value.equals("false") ? "Ja" : "Nee"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbToggle) {
            if (value.equals("false")) {
                parent.setNewValue("true");
                value = "true";
            }
            else {
                parent.setNewValue("false");
                value = "false";
            }
            reconsiderValueDisplay();
            checkResetVisibility();
        }
        else if (e.getSource() == reset) {
            resetValue();
        }
    }
}
