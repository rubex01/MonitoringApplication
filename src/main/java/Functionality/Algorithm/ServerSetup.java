package Functionality.Algorithm;
import java.util.ArrayList;

public class ServerSetup {

    private ArrayList<Server> serverSetup;

    private Double uptimePercentage;

    public ServerSetup (ArrayList<Server> serverSetup, Double uptimePercentage) {
        this.serverSetup = serverSetup;
        this.uptimePercentage = uptimePercentage;
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;

        for (Server singleServer : this.serverSetup) {
            totalPrice += singleServer.getPrice();
        }

        return totalPrice;
    }

    public String toString() {
        ArrayList returnString = new ArrayList();
        returnString.add(this.serverSetup);
        returnString.add(this.uptimePercentage);
        returnString.add(Integer.toString(this.calculateTotalPrice()));
        return returnString.toString();
    }

    public Double getUptimePercentage() {
        return uptimePercentage;
    }

}
