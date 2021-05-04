package Functionality.Monitoring;

import java.io.Serializable;

public class PoolResult implements Serializable {

    private int type, downtime, uptime, bytesIn, bytesOut;

    private String poolName;

    private boolean online;

    public PoolResult(String poolName, boolean online, int downtime, int type, int uptime, int bytesIn, int bytesOut) {
        this.poolName = poolName;
        this.online = online;
        this.downtime = downtime;
        this.type = type;
        this.uptime = uptime;
        this.bytesIn = bytesIn;
        this.bytesOut = bytesOut;
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

    public int getBytesIn() {
        return bytesIn;
    }

    public int getBytesOut() {
        return bytesOut;
    }

    public void updateStatus(boolean online, int downtime, int uptime, int bytesIn, int bytesOut) {
        this.online = online;
        this.downtime = downtime;
        this.uptime = uptime;
        this.bytesIn = bytesIn;
        this.bytesOut = bytesOut;
    }

}
