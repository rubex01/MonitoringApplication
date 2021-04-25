package Functionality.Monitoring;

import Functionality.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.stream.Stream;

public class APIController extends TimerTask {

    private ArrayList<ServerResult> serverResults;

    private static final String webServerPoolName = "Webservers_ipvANY", databaseServerPoolName = "Databases_ipvANY", endpoint = "http://192.168.178.25:8800";

    public APIController() {
        serverResults = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            URL endpoint = new URL(APIController.endpoint);
            URLConnection yc = endpoint.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            String responseContent = "";
            while ((inputLine = in.readLine()) != null) responseContent += inputLine + "\n";
            in.close();

            parseResult(responseContent);
        }
        catch (Exception exception) {
//            TODO: give error
            System.out.println("OOPS, could not send request to the api.");
        }
    }

    private void parseResult(String content) {
        Stream<String> contentStream = content.lines();
        ArrayList<ArrayList<String>> valueArrays = new ArrayList<>();

        contentStream.map(x -> x.split(",")).forEach(x -> {
            ArrayList<String> valueArray = new ArrayList<>();
            for (String value : x) valueArray.add(value);
            valueArrays.add(valueArray);
        });

        try {
            valueArrays.remove(0);

            for (ArrayList<String> stringlist : valueArrays) {
                if (!stringlist.get(28).equals("0")) {
                    boolean alreadyExists = false;
                    for (ServerResult existingResult : serverResults) {
                        if (existingResult.getSid() == Integer.valueOf(stringlist.get(28))) {
                            alreadyExists = true;
                            existingResult.updateStatus(
                                    stringlist.get(36),
                                    stringlist.get(65),
                                    (stringlist.get(17).equals("UP")),
                                    Integer.valueOf(stringlist.get(24))
                            );
                        }
                    }
                    if (alreadyExists) continue;

                    serverResults.add(new ServerResult(
                            stringlist.get(1),
                            stringlist.get(36),
                            stringlist.get(65),
                            stringlist.get(73),
                            (stringlist.get(17).equals("UP")),
                            Integer.valueOf(stringlist.get(24)),
                            (stringlist.get(0).equals(APIController.webServerPoolName) ? Server.WEBSERVER : Server.DATABASE),
                            Integer.valueOf(stringlist.get(28))
                    ));
                }
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("Oops, the api sent back an invalid response.");
//            TODO: give right error
        }
    }

    public ArrayList<ServerResult> getServerResults() {
        return serverResults;
    }
}
