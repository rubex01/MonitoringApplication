package Functionality.Algorithm.Algorithm;

import Functionality.Server;

import java.util.ArrayList;

public class ServerConfiguration {

    private ArrayList<Server> servers;

    private double price, uptime;

    public ServerConfiguration(ArrayList<Server> servers, double price, double uptime) {
        this.servers = servers;
        this.price = price;
        this.uptime = uptime;
    }

    public double getPrice() {
        return price;
    }

    public double getUptime() {
        return uptime;
    }

    public int getServerCount() {
        return servers.size();
    }

    public ArrayList<Server> getServers() {
        return servers;
    }
}
