package Functionality;

import Functionality.Settings.SettingsController;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ServerParser {

    public static ArrayList<Server> parseServers() {
        ArrayList<Server> serverList = new ArrayList<>();
        try {
            String path = SettingsController.getSetting("server_file_location");
            URI fileUri = null;
            if (path.equals("standaard")) {
                fileUri = ServerParser.class.getResource("servers.txt").toURI();
            }
            else {
                fileUri = new File(path).toURI();
            }
            Stream<String> rows = Files.lines(Path.of(fileUri));

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
            exception.printStackTrace();
        }
        return serverList;
    }
}
