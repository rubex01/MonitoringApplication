import Functionality.Blueprint.Blueprint;
import Functionality.ServerParser;
import GUI.Frame;
import GUI.Tabs;

public class Main {

    public static void main(String[] args) {

        ServerParser parser = new ServerParser();

        GUI.Frame gui = new Frame();
        Tabs tabs = gui.getTabsBar();

        Blueprint ontwerp = new Blueprint();
        tabs.addTab(ontwerp);
    }

}