package Functionality.Settings.SettingPanels;

import Functionality.Settings.SettingItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PasswordPanel extends SettingPanel implements KeyListener {

    private JPasswordField passwordField;

    public PasswordPanel(SettingItem parent) {
        super(parent);
    }

    @Override
    protected void drawItems() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        passwordField = new JPasswordField(12);
        passwordField.setText(value);
        passwordField.addKeyListener(this);

        panel.add(passwordField);
        add(panel);
    }

    @Override
    public void reconsiderValueDisplay() {
        passwordField.setText(value);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        value = String.valueOf(passwordField.getPassword());
        parent.setNewValue(String.valueOf(passwordField.getPassword()));
        checkResetVisibility();
    }
}
