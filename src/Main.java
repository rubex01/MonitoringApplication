import Functionality.Example;
import GUI.Frame;
import GUI.Tabs;

public class Main {

    public static void main(String[] args) {
        GUI.Frame gui = new Frame();
        Tabs tabs = gui.getTabsBar();

        Example tab1 = new Example("Monitoring", false);
        Example tab2 = new Example("Ontwerp 1", true);
        Example tab3 = new Example("Ontwerp 2", true);
        Example tab4 = new Example("Ontwerp 3", true);
        tabs.addTab(tab1);
        tabs.addTab(tab2);
        tabs.addTab(tab3);
        tabs.addTab(tab4);

    }

}