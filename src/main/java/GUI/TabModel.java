package GUI;

import javax.swing.*;
import java.io.Serializable;

public abstract class TabModel implements Serializable {

    private String title;

    private boolean closable;

    private TabButton tabButton;

    private Tabs tabsParent;

    public TabModel(String title, boolean closeable) {
        this.title = title;
        this.closable = closeable;
        this.tabButton = new TabButton(this);
    }

    public TabModel(String title) {
        this(title, true);
    }

    public TabButton getTabButton() {
        return tabButton;
    }

    public String getTitle() {
        return title;
    }

    public boolean isClosable() {
        return closable;
    }

    public abstract JPanel getPanel();

    public void setTabsParent(Tabs tabsParent) {
        this.tabsParent = tabsParent;
    }

    public void setFocus() {
        tabsParent.changeFocus(this);
    }

    public void close() {
        if (closeCheck()) tabsParent.closeTab(this);
    }

    public abstract boolean closeCheck();

    public void setTitle(String title) {
        this.title = title;
        tabButton.updateTitle();
        tabButton.repaint();
    }

    public Tabs getTabsParent() {
        return tabsParent;
    }
}
