package Functionality.Algorithm.Algorithm;

import Functionality.Server;

public class ServerConfiguration {

    private Server[] servers;

    private double price, uptime;

    public ServerConfiguration(Server[] servers, double price, double uptime) {
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
        return servers.length;
    }

    public Server[] getServers() {
        return servers;
    }
}
