package Functionality;

import Functionality.Settings.SettingsController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ServerParser {

    public static ArrayList<Server> parseServers() {
        ArrayList<Server> serverList = new ArrayList<>();
        try {
            String path = SettingsController.getSetting("server_file_location");
            if (path.equals("null")) path = "src/servers.txt";
            Stream<String> rows = Files.lines(Paths.get(path));

            rows.map(x -> x.split(",")).forEach(x -> {
                int type = 0;
                switch (x[2]) {
                    case "database":
                        type = Server.DATABASE;
                        break;
                    case "webserver":
                        type = Server.WEBSERVER;
                        break;
                    case "firewall":
                        type = Server.FIREWALL;
                        break;
                }
                serverList.add(new Server(x[0], Integer.valueOf(x[1]), type, Double.valueOf(x[3])));
            });
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return serverList;
    }
}
