package GUI;

import Assets.Variables;
import Functionality.Blueprint.Blueprint;
import Functionality.SaveController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBar extends JPanel implements ActionListener {

    private JButton jbNew, jbOpen, jbOptimalisation, jbSave;

    private Frame parent;

    public NavigationBar(Frame parent) {
        this.parent = parent;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(1200, 30);
        setBackground(Variables.background);
        drawOptions();
    }

    public void drawOptions() {
        jbNew = new JButton("Nieuw");
        jbNew.setBackground(Variables.transparent);
        jbNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jbNew.setBorderPainted(false);
        jbNew.setFocusPainted(false);
        jbNew.setContentAreaFilled(false);
        jbNew.addActionListener(this);
        jbNew.setMargin(new Insets(2, 3, 2, 3));
        jbNew.setIcon(new ImageIcon(Variables.getImage("new_file")));

        jbSave = new JButton("Opslaan");
        jbSave.setBackground(Variables.transparent);
        jbSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jbSave.setBorderPainted(false);
        jbSave.setFocusPainted(false);
        jbSave.setContentAreaFilled(false);
        jbSave.addActionListener(this);
        jbSave.setMargin(new Insets(2, 3, 2, 3));
        jbSave.setIcon(new ImageIcon(Variables.getImage("save")));

        jbOpen = new JButton("Open");
        jbOpen.setBackground(Variables.transparent);
        jbOpen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jbOpen.setBorderPainted(false);
        jbOpen.setFocusPainted(false);
        jbOpen.setContentAreaFilled(false);
        jbOpen.addActionListener(this);
        jbOpen.setMargin(new Insets(2, 3, 2, 3));
        jbOpen.setIcon(new ImageIcon(Variables.getImage("folder")));

        jbOptimalisation = new JButton("Optimalisation");
        jbOptimalisation.setBackground(Variables.transparent);
        jbOptimalisation.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jbOptimalisation.setBorderPainted(false);
        jbOptimalisation.setFocusPainted(false);
        jbOptimalisation.setContentAreaFilled(false);
        jbOptimalisation.addActionListener(this);
        jbOptimalisation.setMargin(new Insets(2, 3, 2, 3));
        jbOptimalisation.setIcon(new ImageIcon(Variables.getImage("star")));

        add(jbNew);
        add(jbSave);
        add(jbOpen);
        add(jbOptimalisation);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbNew) {
            Blueprint blueprint = new Blueprint();
            parent.getTabsBar().addTab(blueprint);
            parent.getTabsBar().changeFocus(blueprint);
        }
        else if (e.getSource() == jbOpen) {
            SaveController.openBlueprint();
        }
        else if (e.getSource() == jbSave) {
            try {
                Blueprint blueprint = (Blueprint) parent.getTabsBar().getCurrentFocus();
                SaveController.saveBlueprint(blueprint);
            }
            catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
        else if (e.getSource() == jbOptimalisation) {
            // TODO: Open dialog etc..
            System.out.println("todo");
        }
    }
}
