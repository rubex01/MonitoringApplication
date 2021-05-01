package Functionality.Monitoring;

import java.io.Serializable;

public class PoolResult implements Serializable {

    private int type, downtime, uptime;

    private String poolName;

    private boolean online;

    public PoolResult(String poolName, boolean online, int downtime, int type, int uptime) {
        this.poolName = poolName;
        this.online = online;
        this.downtime = downtime;
        this.type = type;
        this.uptime = uptime;

        testingPurposes();
    }

    public int getUptime() {
        return uptime;
    }

    public int getType() {
        return type;
    }

    public int getDowntime() {
        return downtime;
    }

    public boolean isOnline() {
        return online;
    }

    public String getPoolName() {
        return poolName;
    }

    public void updateStatus(boolean online, int downtime, int uptime) {
        this.online = online;
        this.downtime = downtime;
        this.uptime = uptime;

        testingPurposes();
    }

    private void testingPurposes() {
        System.out.printf("Server " + poolName + " is currently ");
        if (!online) System.out.printf("offline");
        else System.out.printf("online");
        System.out.println();
    }

}
