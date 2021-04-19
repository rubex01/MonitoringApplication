package Functionality;

public class Server {

    public static final int DATABASE = 0;

    public static final int WEBSERVER = 1;

    public static final int FIREWALL = 2;

    private int type, price;

    private String name;

    private double uptime;

    public Server(String name, int price, int type, double uptime) {
        this.name = name;
        this.uptime = uptime;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return "name: " + name + " type: " + type + " price: " + price;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public double getUptime() {
        return uptime;
    }
}
