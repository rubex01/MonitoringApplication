import Functionality.Example;
import GUI.Frame;
import GUI.Tabs;

public class Main {

    public static void main(String[] args) {
        GUI.Frame gui = new Frame();
        Tabs tabs = gui.getTabsBar();

        Example tab1 = new Example("Example file", true);
        Example tab2 = new Example("Not closeable", true);
        Example tab3 = new Example("Example file23", false);
        tabs.addTab(tab1);
        tabs.addTab(tab2);
        tabs.addTab(tab3);

    }

}