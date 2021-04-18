package GUI;

import Assets.Variables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBar extends JPanel implements ActionListener {

    private JButton jbNew, jbOpen, jbOptimalisation;

    public NavigationBar() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(1200, 30);
        setBackground(Variables.background);
        drawOptions();
    }

    public void drawOptions() {
        jbNew = new JButton("Nieuw");
        jbNew.setBackground(Variables.transparent);
        jbNew.setBorderPainted(false);
        jbNew.setFocusPainted(false);
        jbNew.setContentAreaFilled(false);
        jbNew.addActionListener(this);
        jbNew.setMargin(new Insets(2, 3, 2, 3));
        jbNew.setIcon(new ImageIcon(Variables.getImage("new_file")));

        jbOpen = new JButton("Open");
        jbOpen.setBackground(Variables.transparent);
        jbOpen.setBorderPainted(false);
        jbOpen.setFocusPainted(false);
        jbOpen.setContentAreaFilled(false);
        jbOpen.addActionListener(this);
        jbOpen.setMargin(new Insets(2, 3, 2, 3));
        jbOpen.setIcon(new ImageIcon(Variables.getImage("folder")));

        jbOptimalisation = new JButton("Optimalisation");
        jbOptimalisation.setBackground(Variables.transparent);
        jbOptimalisation.setBorderPainted(false);
        jbOptimalisation.setFocusPainted(false);
        jbOptimalisation.setContentAreaFilled(false);
        jbOptimalisation.addActionListener(this);
        jbOptimalisation.setMargin(new Insets(2, 3, 2, 3));
        jbOptimalisation.setIcon(new ImageIcon(Variables.getImage("star")));

        add(jbNew);
        add(jbOpen);
        add(jbOptimalisation);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbNew) {
            // TODO: nieuwe samenstelling starten
            System.out.println("todo");
        }
        else if (e.getSource() == jbOpen) {
            // TODO: Open samenstelling van files
            System.out.println("todo");
        }
        else if (e.getSource() == jbOptimalisation) {
            // TODO: Open dialog etc..
            System.out.println("todo");
        }
    }
}
