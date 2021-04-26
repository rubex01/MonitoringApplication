package GUI;

import Assets.Variables;
import Functionality.Monitoring.Monitoring;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private int defaultWidth = 1100, defaultHeight = 700;

    private Tabs tabsBar;

    public static Frame defaultFrame;

    public Frame() {
        defaultFrame = this;
        setTitle("Monitoring Applicatie");
        setLayout(new BorderLayout());
        setSize(defaultWidth, defaultHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Variables.white);
        setIconImage(Variables.getImage("logo"));

        drawNavigation();
        drawTabs();
        addMonitoring();

        setVisible(true);
    }

    private void drawNavigation() {
        NavigationBar navigationBar = new NavigationBar(this);
        add(navigationBar, BorderLayout.NORTH);
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
}
