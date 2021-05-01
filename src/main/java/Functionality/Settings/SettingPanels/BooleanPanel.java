package Functionality.Settings.SettingPanels;

import Assets.Variables;
import Functionality.Settings.SettingItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BooleanPanel extends SettingPanel implements ActionListener {

    private JButton jbToggle;

    private ImageIcon toggleOn, toggleOff;

    public BooleanPanel(SettingItem parent) {
        super(parent);
    }

    @Override
    protected void drawItems() {
        toggleOn = new ImageIcon(Variables.getImage("toggle_on"));
        toggleOff = new ImageIcon(Variables.getImage("toggle_off"));

        jbToggle = new JButton();
        reconsiderValueDisplay();
        jbToggle.addActionListener(this);
        jbToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jbToggle.setFocusPainted(false);
        jbToggle.setBorderPainted(false);
        jbToggle.setContentAreaFilled(false);

        add(jbToggle);
    }

    @Override
    public void reconsiderValueDisplay() {
        jbToggle.setIcon((value.equals("false") ? toggleOff : toggleOn));
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
