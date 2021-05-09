package Functionality.Algorithm;

import java.util.ArrayList;

public class TotalSetup {

    private ArrayList<ServerSetup> serverSetups;

    public TotalSetup() {
        serverSetups = new ArrayList<>();
    }

    public void addServerSetup(ServerSetup setup) {
        serverSetups.add(setup);
    }

    public int getPrice() {
        int total = 0;
        for (ServerSetup setup : serverSetups) {
            total += setup.calculateTotalPrice();
        }
        return total;
    }

    public int count() {
        return serverSetups.size();
    }

}
