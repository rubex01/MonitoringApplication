package Functionality.Monitoring;

import Functionality.Server;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.stream.Stream;

public class APIController extends TimerTask implements Serializable {

    private ArrayList<ServerResult> serverResults;

    private ArrayList<PoolResult> poolResults;

    private static final String webServerPoolName = "Webservers_ipvANY", databaseServerPoolName = "Databases_ipvANY";

    private static final String statsCommand = "echo \"show stat\" | socat stdio /tmp/haproxy.socket";

    private Monitoring parent;

    private int reconnectionTries = 0;

    public APIController(Monitoring parent) {
        this.parent = parent;
        serverResults = new ArrayList<>();
        poolResults = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            Channel channel= SSHClient.getSession().openChannel("exec");
            ((ChannelExec)channel).setCommand(statsCommand);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();
            channel.connect();

            String currentStatus = "";
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    currentStatus += new String(tmp, 0, i);
                }
                if(channel.isClosed()){
                    break;
                }
            }
            channel.disconnect();

            parseResult(currentStatus);
        }
        catch (Exception exception) {
//            TODO: give error
            System.out.println("OOPS, could not send request to the api.");
            if (reconnectionTries < 5) {
                SSHClient.closeConnection();
                SSHClient.getSession();
                reconnectionTries++;
            }
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
            valueArrays.remove(valueArrays.size()-1);

            for (ArrayList<String> stringlist : valueArrays) {
                if (!stringlist.get(28).equals("0") || stringlist.get(0).equals("HAProxyLocalStats")) {
                    boolean alreadyExists = false;
                    for (ServerResult existingResult : serverResults) {
                        if (existingResult.getSid() == Integer.valueOf(stringlist.get(28)) && existingResult.getServerName().equals(stringlist.get(1))) {
                            alreadyExists = true;
                            existingResult.updateStatus(
                                    stringlist.get(36),
                                    stringlist.get(65),
                                    (stringlist.get(17).equals("UP") || stringlist.get(17).equals("OPEN")),
                                    (stringlist.get(24).equals("") ? 0 : Integer.valueOf(stringlist.get(24))),
                                    (stringlist.get(23).equals("") ? 0 : Integer.valueOf(stringlist.get(23))),
                                    (stringlist.get(8).equals("") ? 0 : Integer.valueOf(stringlist.get(8))),
                                    (stringlist.get(9).equals("") ? 0 : Integer.valueOf(stringlist.get(9)))
                            );
                        }
                    }
                    if (alreadyExists) continue;

                    serverResults.add(new ServerResult(
                            stringlist.get(1),
                            stringlist.get(36),
                            stringlist.get(65),
                            stringlist.get(73),
                            (stringlist.get(17).equals("UP") || stringlist.get(17).equals("OPEN")),
                            (stringlist.get(24).equals("") ? 0 : Integer.valueOf(stringlist.get(24))),
                            (
                                    stringlist.get(0).equals(APIController.webServerPoolName) ?
                                            Server.WEBSERVER :
                                            (stringlist.get(0).equals(APIController.databaseServerPoolName) ?
                                                    Server.DATABASE :
                                                    Server.FIREWALL
                                            )
                            ),
                            Integer.valueOf(stringlist.get(28)),
                            (stringlist.get(23).equals("") ? 0 : Integer.valueOf(stringlist.get(23))),
                            (stringlist.get(8).equals("") ? 0 : Integer.valueOf(stringlist.get(8))),
                            (stringlist.get(9).equals("") ? 0 : Integer.valueOf(stringlist.get(9)))
                    ));
                }
                else if (APIController.databaseServerPoolName.equals(stringlist.get(0)) || APIController.webServerPoolName.equals(stringlist.get(0)) && (stringlist.get(1).equals("BACKEND"))) {
                    boolean alreadyExists = false;
                    int type = (stringlist.get(0).equals(APIController.databaseServerPoolName) ? Server.DATABASE : Server.WEBSERVER);
                    for (PoolResult existingResult : poolResults) {
                        if (existingResult.getType() == type) {
                            alreadyExists = true;
                            existingResult.updateStatus(
                                    (stringlist.get(17).equals("UP")),
                                    Integer.valueOf(stringlist.get(24)),
                                    Integer.valueOf(stringlist.get(23)),
                                    Integer.valueOf(stringlist.get(8)),
                                    Integer.valueOf(stringlist.get(9))
                            );
                        }
                    }
                    if (alreadyExists) continue;

                    poolResults.add(new PoolResult(
                            stringlist.get(0),
                            (stringlist.get(17).equals("UP")),
                            Integer.valueOf(stringlist.get(24)),
                            type,
                            Integer.valueOf(stringlist.get(23)),
                            Integer.valueOf(stringlist.get(8)),
                            Integer.valueOf(stringlist.get(9))
                    ));
                }
            }
            startUpdateCycle();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Oops, the api sent back an invalid response.");
//            TODO: give right error
        }
    }

    private void startUpdateCycle() {
        parent.startUpdateCycle(serverResults, poolResults);
    }

    public ArrayList<ServerResult> getServerResults() {
        return serverResults;
    }
}
