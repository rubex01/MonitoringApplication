package Functionality.Monitoring;

public class ServerResult {

    private int type, downtime, sid;

    private String serverName, checkStatus, checkDesc, ipAddress;

    private boolean online;

    public ServerResult(String serverName, String checkStatus, String checkDesc, String ipAddress, boolean online, int downtime, int type, int sid) {
        this.serverName = serverName;
        this.checkStatus = checkStatus;
        this.checkDesc = checkDesc;
        this.ipAddress = ipAddress;
        this.online = online;
        this.downtime = downtime;
        this.type = type;
        this.sid = sid;

        testingPurposes();
    }

    public int getSid() {
        return sid;
    }

    public boolean isOnline() {
        return online;
    }

    public String getServerName() {
        return serverName;
    }

    public void updateStatus(String checkStatus, String checkDesc, boolean online, int downtime) {
        this.checkStatus = checkStatus;
        this.checkDesc = checkDesc;
        this.online = online;
        this.downtime = downtime;

        testingPurposes();
    }

    private void testingPurposes() {
        System.out.printf("Server " + serverName + " is currently ");
        if (!online) System.out.printf("offline");
        else System.out.printf("online");
        System.out.println();
    }
}
