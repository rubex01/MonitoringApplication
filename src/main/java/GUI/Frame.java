package GUI;

import Assets.Variables;
import Functionality.Monitoring.Monitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;

public class Frame extends JFrame {

    private int defaultWidth = 1100, defaultHeight = 700;

    private Tabs tabsBar;

    private JPanel hoverDropPanel;

    public static Frame defaultFrame;

    public Frame() {
        defaultFrame = this;
        setTitle("Monitoring Applicatie");
        setLayout(new BorderLayout());
        setSize(defaultWidth, defaultHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Variables.white);
        setIconImage(Variables.getImage("logo"));

        DropBlueprintListener myDragDropListener = new DropBlueprintListener(this);
        new DropTarget(this, myDragDropListener);

        drawNavigation();
        drawTabs();
        createHoverDropPanel();
        addMonitoring();

        setVisible(true);
    }

    private void createHoverDropPanel() {
        hoverDropPanel = new JPanel();
        hoverDropPanel.setBackground(Variables.fileDropHover);
        hoverDropPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel imageLabel = new JLabel(new ImageIcon(Variables.getImage("open_file")));
        JLabel textLabel = new JLabel("Sleep hier bestanden om ze te openen");
        textLabel.setForeground(Variables.white);
        textLabel.setFont(new Font(textLabel.getFont().getName(), Font.BOLD, 19));
        hoverDropPanel.add(imageLabel, gbc);
        hoverDropPanel.add(textLabel, gbc);
    }

    private void drawNavigation() {
        NavigationBar navigationBar = new NavigationBar(this);
        setJMenuBar(navigationBar);
    }

    private void drawTabs() {
        tabsBar = new Tabs();
        add(tabsBar, BorderLayout.CENTER);
    }

    private void addMonitoring() {
        tabsBar.addTab(new Monitoring());
    }

    public Tabs getTabsBar() {
        return tabsBar;
    }

    public void setDropHover(boolean state) {
        if (state) {
            remove(tabsBar);
            add(hoverDropPanel, BorderLayout.CENTER);
        }
        else {
            remove(hoverDropPanel);
            add(tabsBar, BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }
}
