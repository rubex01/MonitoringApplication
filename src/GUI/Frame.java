package GUI;

import Assets.Variables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private int defaultWidth = 700, defaultHeight = 600;

    private Tabs tabsBar;

    public Frame() {
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
        add(navigationBar, BorderLayout.NORTH);
    }

    private void drawTabs() {
        tabsBar = new Tabs();
        add(tabsBar, BorderLayout.CENTER);
    }

    public Tabs getTabsBar() {
        return tabsBar;
    }
}
