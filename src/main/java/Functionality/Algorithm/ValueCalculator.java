package Functionality.Algorithm;

import java.util.ArrayList;
import java.util.Collections;

public class ValueCalculator {

    public ArrayList<ServerSetup> outcomes = new ArrayList<>();

    private ArrayList<Server> options;

    private Double targetPercentage;

    public ValueCalculator(ArrayList<Server> options, Double targetPercentage) {
        this.options = options;
        this.targetPercentage = targetPercentage;

        ArrayList<Server> initRunningOptions = new ArrayList<>();
        this.calculateOptions(this.options, initRunningOptions);
    }

    private void calculateOptions(ArrayList<Server> options, ArrayList<Server> currentRunningOption) {

        for (Server option : options) {

            // Create an updated version of the current running option since it just joined a new option
            ArrayList<Server> newCurrentRunningOption = new ArrayList<Server>(currentRunningOption);
            newCurrentRunningOption.add(option);

            // Calculate downtime percentage per option in current running option arraylist
            ArrayList<Double> optionValue = new ArrayList<>();
            newCurrentRunningOption.forEach(optionToCalculate -> {
                Double calculatedOption = 1 - optionToCalculate.getPercentage();
                optionValue.add(calculatedOption);
            });

            // Set current option uptime percentage to the value of the first item in the array then remove it
            Double currentOptionUptimePercentage = optionValue.get(0);
            optionValue.remove(0);

            // Multiply by each value in the arraylist
            for ( Double optionToCalculate : optionValue)
            {
                currentOptionUptimePercentage = currentOptionUptimePercentage * optionToCalculate;
            }

            // Calculate final percentage value of current running option
            currentOptionUptimePercentage = 1 - currentOptionUptimePercentage;

            // Check if the current running option satisfies the target percentage
            if (currentOptionUptimePercentage >= this.targetPercentage) {

                // Create new arraylist with the current running option in it
                ServerSetup possibleOption = new ServerSetup(newCurrentRunningOption, currentOptionUptimePercentage);

                // Add current possible option to all the outcomes
                this.outcomes.add(possibleOption);
            }
            else {
                // Call itself again so it can add another instance to reach the target percentage
                this.calculateOptions(options, newCurrentRunningOption);
            }

        }

    }

    public ArrayList<ServerSetup> getOutcomes() {
        return outcomes;
    }

    public ServerSetup getBestOutcome() {
        // Create array list of all outcomes
        ArrayList<ServerSetup> possibleWinners = this.outcomes;

        // Create array list with all prices
        ArrayList<Integer> allPrices = new ArrayList<>();

        // Put all total prices into array list
        for (ServerSetup possibleWinner : possibleWinners) {
            allPrices.add(possibleWinner.calculateTotalPrice());
        }

        // Get index of the lowest price
        int lowestPriceIndex = allPrices.indexOf(Collections.min(allPrices));

        return possibleWinners.get(lowestPriceIndex);
    }

}
