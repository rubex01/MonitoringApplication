package Functionality;

import Assets.Variables;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public class Server implements Serializable {

    public static final int DATABASE = 0;

    public static final int WEBSERVER = 1;

    public static final int FIREWALL = 2;

    private int type, price;

    private String name;

    private double uptime;

    private String typeName;

    public Server(String name, int price, int type, double uptime) {
        this.name = name;
        this.uptime = uptime;
        this.type = type;
        this.price = price;
        switch (type) {
            case DATABASE:
                typeName = "database";
                break;
            case WEBSERVER:
                typeName = "webserver";
                break;
            case FIREWALL:
                typeName = "firewall";
                break;
        }
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

    public Image getImage() {
        return Variables.getImage(typeName);
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return "[name: " + name + ", price: " + price + ", type: " + getTypeName() + ", uptime: " + uptime + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(name, server.name) && Objects.equals(type, server.type) && Objects.equals(price, server.price) && Objects.equals(uptime, server.uptime);
    }
}
