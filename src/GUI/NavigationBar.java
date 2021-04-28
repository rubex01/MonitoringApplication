package GUI;

import Assets.Variables;
import Functionality.Blueprint.Blueprint;
import Functionality.SaveController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBar extends JMenuBar implements ActionListener {

    private JMenuItem jmNew, jmSave, jmOpen, jmSaveOnline, jmOpenOnline;

    private JMenu jmbMenu, jmbOptimalisation;

    private Frame parent;

    public NavigationBar(Frame parent) {
        this.parent = parent;
        setBorder(new EmptyBorder(6, 3, 6, 3));
        setBackground(Variables.background);
        drawOptions();
    }

    public void drawOptions() {
        jmbMenu = new JMenu("Bestand");
        jmbMenu.setIcon(new ImageIcon(Variables.getImage("folder")));
        jmbMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jmbMenu.setFocusPainted(false);
        jmbMenu.setBorderPainted(false);
        jmbMenu.setContentAreaFilled(false);

        jmbOptimalisation = new JMenu("Optimalisatie");
        jmbOptimalisation.setIcon(new ImageIcon(Variables.getImage("star")));
        jmbOptimalisation.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jmbOptimalisation.setFocusPainted(false);
        jmbOptimalisation.setBorderPainted(false);
        jmbOptimalisation.setContentAreaFilled(false);
        jmbOptimalisation.addActionListener(this);

        jmNew = new JMenuItem("Nieuw", new ImageIcon(Variables.getImage("new_file")));
        jmNew.addActionListener(this);
        jmSave = new JMenuItem("Opslaan", new ImageIcon(Variables.getImage("save")));
        jmSave.addActionListener(this);
        jmOpen = new JMenuItem("Openen", new ImageIcon(Variables.getImage("folder")));
        jmOpen.addActionListener(this);
        jmSaveOnline = new JMenuItem("Online opslaan", new ImageIcon(Variables.getImage("save_online")));
        jmSaveOnline.addActionListener(this);
        jmOpenOnline = new JMenuItem("Online openen", new ImageIcon(Variables.getImage("open_online")));
        jmOpenOnline.addActionListener(this);

        jmbMenu.add(jmNew);
        jmbMenu.add(jmSave);
        jmbMenu.add(jmOpen);
        jmbMenu.add(jmSaveOnline);
        jmbMenu.add(jmOpenOnline);

        add(jmbMenu);
        add(jmbOptimalisation);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jmNew) {
            Blueprint blueprint = new Blueprint();
            parent.getTabsBar().addTab(blueprint);
            parent.getTabsBar().changeFocus(blueprint);
        }
        else if (e.getSource() == jmOpen) {
            SaveController.openBlueprint();
        }
        else if (e.getSource() == jmSave) {
            try {
                Blueprint blueprint = (Blueprint) parent.getTabsBar().getCurrentFocus();
                SaveController.saveBlueprint(blueprint);
            }
            catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
        else if (e.getSource() == jmbOptimalisation) {
            // TODO: Open dialog etc..
            System.out.println("todo");
        }
        else if (e.getSource() == jmOpenOnline) {
            // TODO: ...
            System.out.println("todo");
        }
        else if (e.getSource() == jmSaveOnline) {
            try {
                Blueprint blueprint = (Blueprint) parent.getTabsBar().getCurrentFocus();
                SaveController.saveBlueprintOnline(blueprint);
            }
            catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
