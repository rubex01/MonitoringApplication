package Functionality.Algorithm.Algorithm;

import Functionality.Server;

import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm {

    private ArrayList<Server> databases, webservers, firewalls;

    private ArrayList<Server[]> serverBlocks;

    private double wantedUptime;

    private ServerConfiguration currentBestSolution;

    private Server[] lowestUptimeServers;

    private int maxOptimalOverlayDebt;

    private final static double doubleThreshold = 0.000001;

    private final static int overhead = 2;

    public Algorithm(ArrayList<Server> servers, double wantedUptime) {
        this.wantedUptime = wantedUptime;
        this.databases = new ArrayList<>();
        this.webservers = new ArrayList<>();
        this.firewalls = new ArrayList<>();
        this.serverBlocks = new ArrayList<>();
        this.lowestUptimeServers = new Server[3];

        for (Server server : servers) {
            if (lowestUptimeServers[server.getType()] == null || lowestUptimeServers[server.getType()].getUptime() > server.getUptime()) lowestUptimeServers[server.getType()] = server;
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

        if (databases.size() > webservers.size() && databases.size() > firewalls.size()) maxOptimalOverlayDebt = databases.size()-1;
        else if (webservers.size() > firewalls.size()) maxOptimalOverlayDebt = webservers.size()-1;
        else maxOptimalOverlayDebt = firewalls.size()-1;

        generateBlocks();
        calculate(new ArrayList<>(), 0, null);
    }

    private void generateBlocks() {
        for (Server dbServer : databases) {
            for (Server wbServer : webservers) {
                for (Server fwServer : firewalls) {
                    Server[] serverList = {dbServer, wbServer, fwServer};
                    int[] blockSize = new int[3];

                    for (int i=0;i<3;i++) {
                        double uptime = 1;
                        while (1-uptime < wantedUptime) {
                            uptime = uptime*(1-(serverList[i].getUptime()/100));
                            blockSize[serverList[i].getType()]++;
                        }
                        blockSize[serverList[i].getType()] += overhead;
                    }

                    for (int x=0;x<blockSize[0];x++) {
                        for (int y=0;y<blockSize[1];y++) {
                            for (int z=0;z<blockSize[2];z++) {
                                if (x == 0 && y == 0 & z == 0) continue;
                                int size = x+y+z;
                                int[] countList = {x, y, z};
                                serverBlocks.add(createBlock(serverList, countList, size));
                            }
                        }
                    }

                }
            }
        }

        ArrayList<Server[]> newServerBlockList = new ArrayList<>();
        parentLoop : for (Server[] serverBlock : serverBlocks) {
            for (Server[] newBlockList : newServerBlockList) {
                if (Arrays.equals(newBlockList, serverBlock)) continue parentLoop;
            }
            newServerBlockList.add(serverBlock);
        }
        serverBlocks = newServerBlockList;
    }

    private Server[] createBlock(Server[] serverList, int[] countList, int totalSize) {
        Server[] newBlock = new Server[totalSize];

        int count = 0;
        for (int i=0;i<serverList.length;i++) {
            if (countList[i] == 0) continue;
            for (int x=0;x<countList[i];x++) {
                newBlock[count] = serverList[i];
                count++;
            }
        }
        return newBlock;
    }

    private void calculate(ArrayList<Server> runningConfigurations, int depth, double[] oldConfig) {
        double[] configInfo = calculateInfo(runningConfigurations);

        if (
                (currentBestSolution != null && configInfo[0] > currentBestSolution.getPrice()) ||
                        (oldConfig != null && configInfo[1]/configInfo[0] > oldConfig[1]/oldConfig[0]) ||
                        (oldConfig != null && (configInfo[1] - oldConfig[1]) < doubleThreshold)
        ) return;

        if (configInfo[1] >= wantedUptime) {
            if (currentBestSolution == null) {
                currentBestSolution = new ServerConfiguration(runningConfigurations, configInfo[0], configInfo[1]);
                return;
            }
            else if (
                    (configInfo[0] < currentBestSolution.getPrice()) ||
                            (configInfo[0] == currentBestSolution.getPrice() && (
                                    runningConfigurations.size() < currentBestSolution.getServerCount()) ||
                                    configInfo[1] > currentBestSolution.getUptime()
                            )
            ) {
                currentBestSolution = new ServerConfiguration(runningConfigurations, configInfo[0], configInfo[1]);
                return;
            }
            return;
        }

        if (depth > maxOptimalOverlayDebt) return;

        parent: for (Server[] serverBlock : serverBlocks) {
            for (Server server : runningConfigurations) {
                for (Server compareServer : serverBlock) if (server == compareServer) continue parent;
            }
            ArrayList<Server> newConfiguration = new ArrayList<>(runningConfigurations);
            newConfiguration.addAll(Arrays.asList(serverBlock));
            calculate(newConfiguration, depth+1, configInfo);
        }
    }

    private double[] calculateInfo(ArrayList<Server> config) {
        double totalPrice = 0;
        double[] uptimePerType = {1, 1, 1};
        for (Server server : config) {
            uptimePerType[server.getType()] *= (1-(server.getUptime()/100));
            totalPrice += server.getPrice();
        }
        for (double val : uptimePerType) if (val == 1) return new double[] {totalPrice, 0};
        return new double[] {totalPrice, (1-uptimePerType[0]) * (1-uptimePerType[1]) * (1-uptimePerType[2])};
    }

    public ServerConfiguration getCurrentBestSolution() {
        return currentBestSolution;
    }
}
