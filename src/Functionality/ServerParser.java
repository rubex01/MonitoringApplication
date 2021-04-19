package Functionality;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ServerParser {

    public static String serverFileLocation = "src/servers.txt";

    public static ArrayList<Server> serverList = new ArrayList<>();

    public ServerParser(String filePath) {
        serverFileLocation = filePath;
        parseServers();
    }

    public ServerParser() {
        this(serverFileLocation);
    }

    private void parseServers() {
        try {
            Stream<String> rows1 = Files.lines(Paths.get(serverFileLocation));

            rows1.map(x -> x.split(",")).forEach(x -> {
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
    }
}
