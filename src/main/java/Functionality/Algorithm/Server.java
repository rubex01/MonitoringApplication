package Functionality.Algorithm;

public class Server {

    private Double percentage;

    private int price;

    public Server (Double percentage, int price) {
        this.percentage = percentage;
        this.price = price;
    }

    public Double getPercentage() {
        return this.percentage;
    }

    public int getPrice() {
        return this.price;
    }

    public String toString() {
        return Integer.toString(this.price);
    }

}
