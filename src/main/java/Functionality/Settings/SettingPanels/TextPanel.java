package Functionality.Settings.SettingPanels;

import Functionality.Settings.SettingItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextPanel extends SettingPanel implements KeyListener {

    private JTextField textField;

    public TextPanel(SettingItem parent) {
        super(parent);
    }

    @Override
    protected void drawItems() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        textField = new JTextField(12);
        textField.setText(value);
        textField.addKeyListener(this);

        panel.add(textField);
        add(panel);
    }

    @Override
    public void reconsiderValueDisplay() {
        textField.setText(value);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        value = textField.getText();
        parent.setNewValue(textField.getText());
        checkResetVisibility();
    }
}
