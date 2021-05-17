package Functionality.Algorithm.Algorithm;

import Functionality.Server;

import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm {

    private ArrayList<Server> databases, webservers, firewalls;

    private ArrayList<Server[]> serverBlocks;

    private double wantedUptime;

    private ServerConfiguration currentBestSolution;

    private final static int overhead = 2;

    public Algorithm(ArrayList<Server> servers, double wantedUptime) {
        this.wantedUptime = wantedUptime;
        this.databases = new ArrayList<>();
        this.webservers = new ArrayList<>();
        this.firewalls = new ArrayList<>();
        this.serverBlocks = new ArrayList<>();

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

        generateBlocks();
        calculate();
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
                                Server[] newBlock = createBlock(serverList, countList, size);
                                boolean shouldAdd = true;
                                for (Server[] serverBlock : serverBlocks) if (Arrays.equals(serverBlock, newBlock)){
                                    shouldAdd = false;
                                    break;
                                }
                                if (shouldAdd) serverBlocks.add(newBlock);
                            }
                        }
                    }
                }
            }
        }
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

    private void calculate() {
        for (Server[] firstBlock : serverBlocks) {
            parent : for (Server[] secondBlock : serverBlocks) {
                for (Server server : firstBlock) {
                    for (Server compareServer : secondBlock) if (server == compareServer) continue parent;
                }

                Server[] config = new Server[firstBlock.length + secondBlock.length];
                for (int i=0;i< firstBlock.length;i++) config[i] = firstBlock[i];
                for (int i=firstBlock.length;i<config.length;i++) config[i] = secondBlock[i- firstBlock.length];

                double[] configInfo = calculateInfo(config);

                if (configInfo[1] >= wantedUptime) {
                    if (currentBestSolution == null) {
                        currentBestSolution = new ServerConfiguration(config, configInfo[0], configInfo[1]);
                    }
                    else if (
                        (configInfo[0] < currentBestSolution.getPrice()) ||
                        (configInfo[0] == currentBestSolution.getPrice() && (configInfo[1] > currentBestSolution.getUptime() || config.length < currentBestSolution.getServerCount()))
                    ) {
                        currentBestSolution = new ServerConfiguration(config, configInfo[0], configInfo[1]);
                    }
                }
            }
        }
    }

    private double[] calculateInfo(Server[] config) {
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
