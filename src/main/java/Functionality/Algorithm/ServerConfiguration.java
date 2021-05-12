package Functionality.Algorithm;

import Functionality.Server;

import java.util.LinkedList;

public class ServerConfiguration {

    private LinkedList<Server> serverConfig;

    private double price;

    public ServerConfiguration(LinkedList<Server> serverConfig, double price) {
        this.serverConfig = serverConfig;
        this.price = price;
    }


    public double getPrice() {
        return price;
    }

    public int getServerCount() {
        return serverConfig.size();
    }

    public LinkedList<Server> getAllServers() {
        return serverConfig;
    }

}
