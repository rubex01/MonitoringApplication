package GUI;

import Assets.Variables;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private int defaultWidth = 700, defaultHeight = 600;

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

        setVisible(true);
    }

    private void drawNavigation() {
        NavigationBar navigationBar = new NavigationBar(this);
        setJMenuBar(navigationBar);
    }

    private void drawTabs() {
        tabsBar = new Tabs();
        add(tabsBar, BorderLayout.CENTER);
    }

    public Tabs getTabsBar() {
        return tabsBar;
    }
}
