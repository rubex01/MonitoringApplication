package Functionality.Algorithm;

import Functionality.Server;
import Functionality.Settings.SettingsController;

import java.util.ArrayList;
import java.util.LinkedList;

public class Algorithm {

    private ServerConfiguration currentBestSolution;

    private ArrayList<Server> databases, webservers, firewalls;

    private double wantedUptime;

    private final static double doubleThreshold = 0.000001;

    private double skipThreshold;

    private int maxDepth;

    private boolean autoOptimize = false;

    public Algorithm(ArrayList<Server> servers, double wantedUptime) {
        this.wantedUptime = wantedUptime;
        this.databases = new ArrayList<>();
        this.webservers = new ArrayList<>();
        this.firewalls = new ArrayList<>();

        databases.add(null);
        webservers.add(null);
        firewalls.add(null);

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

        if (SettingsController.getSetting("algorithm_autooptimalisation").equals("yes")) {
            calculateSkippingMargins();
            autoOptimize = true;
        }

        calculate(new LinkedList<>(), null, 0);
    }

    private void calculateSkippingMargins() {
        int uptimeLength = String.valueOf(wantedUptime).length()-2;
        String skipThresholdStr = "0.";
        for (int i=2;i<uptimeLength;i++) skipThresholdStr += "0";
        skipThresholdStr += "1";
        skipThreshold = Double.parseDouble(skipThresholdStr);

        double[] uptimeValues = new double[] {0, 0, 0};
        int key = 2;

        for (Server server : databases) if (server != null) uptimeValues[0] += server.getUptime();
        for (Server server : webservers) if (server != null) uptimeValues[1] = uptimeValues[1] + server.getUptime();
        for (Server server : firewalls) if (server != null) uptimeValues[2] = uptimeValues[2] + server.getUptime();

        if (uptimeValues[0] < uptimeValues[1] && uptimeValues[0] < uptimeValues[2]) key = 0;
        else if (uptimeValues[1] < uptimeValues[2]) key = 1;

        if (uptimeValues[key] > 90) maxDepth = 5;
        else if (uptimeValues[key] > 70) maxDepth = 8;
        else if (uptimeValues[key] > 50) maxDepth = 14;
        else if (uptimeValues[key] > 30) maxDepth = 20;
        else maxDepth = 40;
    }

    private void calculate(LinkedList<Server> runningConfig, double[] oldConfig, int currentDepth) {
        double[] configInfo = calculateConfigInfo(runningConfig);
        if (
            currentBestSolution != null && configInfo[0] > currentBestSolution.getPrice() ||
            (oldConfig != null && autoOptimize && (configInfo[1]-oldConfig[1]) < skipThreshold) ||
            (oldConfig != null && configInfo[1]/configInfo[0] > oldConfig[1]/oldConfig[0]) ||
            (oldConfig != null && (configInfo[1] - oldConfig[1]) < doubleThreshold)
        ) return;
        if (configInfo[1] >= wantedUptime) {
            if (currentBestSolution == null ) {
                currentBestSolution = new ServerConfiguration(runningConfig, configInfo[0]);
                return;
            }
            else if ((configInfo[0] < currentBestSolution.getPrice()) || (configInfo[0] == currentBestSolution.getPrice() && runningConfig.size() < currentBestSolution.getServerCount())) {
                currentBestSolution = new ServerConfiguration(runningConfig, configInfo[0]);
                return;
            }
        }
        if (autoOptimize && currentDepth > maxDepth) return;
        for (Server firewall : firewalls) {
            for (Server webserver : webservers) {
                for (Server database : databases) {
                    LinkedList<Server> newConfig = new LinkedList<>(runningConfig);
                    if (database != null) newConfig.add(database);
                    if (webserver != null) newConfig.add(webserver);
                    if (firewall != null) newConfig.add(firewall);
                    calculate(newConfig, configInfo, (currentDepth+1));
                }
            }
        }
    }

    private double[] calculateConfigInfo(LinkedList<Server> config) {
        double totalPrice = 0;
        double[] uptimePerType = {0, 0, 0};
        for (Server server : config) {
            uptimePerType[server.getType()] = (uptimePerType[server.getType()] == 0) ? (1-(server.getUptime()/100)) : (uptimePerType[server.getType()] * (1-(server.getUptime()/100)));
            totalPrice += server.getPrice();
        }
        if (uptimePerType[0] == 0 || uptimePerType[1] == 0 || uptimePerType[2] == 0) return new double[] {totalPrice, 0};
        double totalUptime = (1-uptimePerType[0]) * (1-uptimePerType[1]) * (1-uptimePerType[2]);
        return new double[] {totalPrice, totalUptime};
    }

    public ServerConfiguration getBestSolution() {
        return currentBestSolution;
    }
}
