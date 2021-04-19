package GUI;

import Assets.Variables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private int defaultWidth = 500, defaultHeight = 500;

    private Tabs tabsBar;

    public Frame() {
        setTitle("Monitoring Applicatie");
        setLayout(new BorderLayout());
        setSize(defaultWidth, defaultHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Variables.white);

        drawLogo();
        drawNavigation();
        drawTabs();

        setVisible(true);
    }

    private void drawLogo() {
        try {
            Image logo = ImageIO.read(getClass().getResource("../Assets/logo.png"));
            setIconImage(logo);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
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
