package Functionality.Algorithm;

import Functionality.Server;

import java.util.ArrayList;
import java.util.LinkedList;

public class Algorithm {

    private ServerConfiguration currentBestSolution;

    private ArrayList<Server> databases = new ArrayList<>(), webservers = new ArrayList<>(), firewalls = new ArrayList<>();

    private double wantedUptime, skipMargin;

    private int depth;

    private AlgorithmDialog parent;

    public Algorithm(ArrayList<Server> servers, double wantedUptime, AlgorithmDialog parent) {
        this.parent = parent;
        this.wantedUptime = wantedUptime;
        databases.add(null);
        webservers.add(null);
        firewalls.add(null);
        smartDecideValues();

        for (Server server : servers) {
            switch (server.getType()) {
                case Server.DATABASE:
                    databases.add(server);
                    break;
                case Server.WEBSERVER:
                    webservers.add(server);
                    break;
                case Server.FIREWALL:
                    firewalls.add(server);
                    break;
            }
        }

        calculate(new ServerConfiguration(new LinkedList<>(), new LinkedList<>(), new LinkedList<>()), null, 0);
    }

    private void smartDecideValues() {
        int marginLength = String.valueOf(wantedUptime).length()-2;
        String skipMarginStr = "0.";
        for (int i=2;i<marginLength;i++) skipMarginStr += "0";
        skipMarginStr += "1";
        skipMargin = Double.valueOf(skipMarginStr);
        depth = (marginLength > 5) ? 5 : marginLength;
    }

    private void calculate(ServerConfiguration runningConfig, ServerConfiguration oldConfig, int currentDepth) {
        if (
                (currentBestSolution != null && runningConfig.getTotalPrice() > currentBestSolution.getTotalPrice()) ||
                        (oldConfig != null && (runningConfig.getTotalUptime()-oldConfig.getTotalUptime()) < skipMargin) ||
                        (oldConfig != null && (runningConfig.getTotalUptime()/runningConfig.getTotalPrice()) > (oldConfig.getTotalUptime()/oldConfig.getTotalPrice()))
        ) return;
        if (runningConfig.getTotalUptime() >= wantedUptime) {
            if (currentBestSolution == null ) {
                currentBestSolution = runningConfig;
                return;
            }
            else if ((runningConfig.getTotalPrice() < currentBestSolution.getTotalPrice()) || (runningConfig.getTotalPrice() == currentBestSolution.getTotalPrice() && runningConfig.serverCount() < currentBestSolution.serverCount())) {
                currentBestSolution = runningConfig;
                return;
            }
        }
        if (currentDepth > depth) return;
        for (Server fwServer : firewalls) {
            for (Server wbServer : webservers) {
                for (Server dbServer : databases) {
                    calculate(runningConfig.newInstance(dbServer, wbServer, fwServer), runningConfig, (currentDepth + 1));
                }
            }
        }
    }

    public ServerConfiguration getBestSolution() {
        return currentBestSolution;
    }
}
