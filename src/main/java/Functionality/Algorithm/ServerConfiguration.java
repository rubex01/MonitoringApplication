package Functionality.Algorithm;

import Functionality.Server;

import java.util.LinkedList;

public class ServerConfiguration {

    private LinkedList<Server> databases, webservers, firewalls, allServers;

    public ServerConfiguration(LinkedList<Server> databases, LinkedList<Server> webservers, LinkedList<Server> firewalls) {
        this.databases = databases;
        this.webservers = webservers;
        this.firewalls = firewalls;
        allServers = new LinkedList<>();
        allServers.addAll(databases);
        allServers.addAll(webservers);
        allServers.addAll(firewalls);
    }

    public int getTotalPrice() {
        int total = 0;
        for (Server server : allServers) if (server != null) total += server.getPrice();
        return total;
    }

    public int serverCount() {
        return allServers.size();
    }

    public double getTotalUptime() {
        if (
                allServers.size() == 0 ||
                        (databases.get(0) == null || webservers.get(0) == null || firewalls.get(0) == null)
        ) return 0;
        double dbUptime, wbUptime, fwUptime; dbUptime = wbUptime = fwUptime = 0;
        for (Server dbServer : databases) if(dbServer != null) dbUptime = (dbUptime == 0) ? (1-(dbServer.getUptime()/100)) : (dbUptime * (1-(dbServer.getUptime()/100)));
        for (Server wbServer : webservers) if (wbServer != null) wbUptime = (wbUptime == 0) ? (1-(wbServer.getUptime()/100)) : (wbUptime * (1-(wbServer.getUptime()/100)));
        for (Server fwServer : firewalls) if (fwServer != null) fwUptime = (fwUptime == 0) ? (1-(fwServer.getUptime()/100)) : (fwUptime * (1-(fwServer.getUptime()/100)));
        return (1-dbUptime) * (1-wbUptime) * (1-fwUptime);
    }

    public ServerConfiguration newInstance(Server dbServer, Server webServer, Server fwServer) {
        LinkedList<Server> newDatabases = new LinkedList<>(databases);
        LinkedList<Server> newWebservers = new LinkedList<>(webservers);
        LinkedList<Server> newFirewalls = new LinkedList<>(firewalls);
        newDatabases.add(dbServer);
        newWebservers.add(webServer);
        newFirewalls.add(fwServer);
        return new ServerConfiguration(newDatabases, newWebservers, newFirewalls);
    }

    public LinkedList<Server> getAllServers() {
        return allServers;
    }
}
