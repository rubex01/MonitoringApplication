import Functionality.Blueprint.Blueprint;
import GUI.Frame;
import GUI.Tabs;
import Migrations.RunMigrations;

public class Main {

    public static void main(String[] args) {
        GUI.Frame gui = new Frame();
        Tabs tabs = gui.getTabsBar();

        Blueprint ontwerp = new Blueprint();
        tabs.addTab(ontwerp);

//        RunMigrations.runAllMigrations(); // Uncomment to run migrations
    }

}