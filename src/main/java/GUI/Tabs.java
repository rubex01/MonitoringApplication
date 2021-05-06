package GUI;

import Assets.DefaultScrollPane;
import Assets.Variables;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Tabs extends JPanel {

    private JPanel tabBar;

    private ArrayList<TabModel> tabs;

    private TabModel currentFocus;

    public Tabs() {
        setBackground(Variables.backgroundLighter);
        setLayout(new BorderLayout());
        tabs = new ArrayList<>();

        createTabBar();
    }

    public void createTabBar() {
        tabBar = new JPanel();
        tabBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabBar.setBorder(new EmptyBorder(4, 5, 0, 5));
        tabBar.setBackground(Variables.white);

        DefaultScrollPane scrollPane = new DefaultScrollPane(tabBar);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(scrollPane, BorderLayout.NORTH);
    }

    public void addTab(TabModel tab) {
        tabs.add(tab);
        tab.setTabsParent(this);
        tabBar.add(tab.getTabButton());
        tabBar.revalidate();
        if (tabs.size() == 1) changeFocus(tab);
    }

    public void changeFocus(TabModel tab) {
        if (currentFocus != null) remove(1);
        currentFocus = tab;
        tab.getTabButton().setFocusedButton();
        add(tab.getPanel(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void closeTab(TabModel tab) {
        tabBar.remove(tabs.indexOf(tab));
        tabs.remove(tab);
        if (currentFocus == tab) changeFocus(tabs.get(0));
        tabBar.revalidate();
        tabBar.repaint();
        revalidate();
        repaint();
    }

    public TabModel getCurrentFocus() {
        return currentFocus;
    }

}
