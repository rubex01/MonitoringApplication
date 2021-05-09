package Functionality.Algorithm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculation extends JDialog implements ActionListener {

    public Calculation() {

        ArrayList<Server> options = new ArrayList<>();

        options.add(new Server(0.80, 2200));
        options.add(new Server(0.90, 3200));
        options.add(new Server(0.95, 5100));

        ArrayList<Server> optionsDb = new ArrayList<>();

        optionsDb.add(new Server(0.90, 5100));
        optionsDb.add(new Server(0.95, 7700));
        optionsDb.add(new Server(0.98, 12200));

        ArrayList<Server> optionsFW = new ArrayList<>();

        optionsFW.add(new Server(0.99998, 4000));

        ArrayList<TotalSetup> total = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            for (int x = 0; x < 10; x++) {
                for (int s = 0; s < 10; s++) {
                    ValueCalculator calculateDb = new ValueCalculator(optionsDb, Double.valueOf(String.valueOf("0.9999") + String.valueOf(i)));
                    ValueCalculator calculate = new ValueCalculator(options, Double.valueOf(String.valueOf("0.9999") + String.valueOf(x)));
                    ValueCalculator calculateFW = new ValueCalculator(optionsFW, Double.valueOf(String.valueOf("0.9999") + String.valueOf(s)));
                    double check = calculate.getBestOutcome().getUptimePercentage() * calculateDb.getBestOutcome().getUptimePercentage() * calculateFW.getBestOutcome().getUptimePercentage();
                    if (check > 0.9999) {
                        TotalSetup res = new TotalSetup();
                        res.addServerSetup(calculate.getBestOutcome());
                        res.addServerSetup(calculateDb.getBestOutcome());
                        res.addServerSetup(calculateFW.getBestOutcome());
                        total.add(res);
                    }
                }
            }
        }

        int lowest = -1;
        for (TotalSetup totalSetup : total) {
            if (totalSetup.getPrice() < lowest) {
                lowest = totalSetup.getPrice();
            } else if (lowest == -1) {
                lowest = totalSetup.getPrice();
            }
        }
        JOptionPane.showMessageDialog(null, "De laagste prijs op moment is : \u20AC"+lowest);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
