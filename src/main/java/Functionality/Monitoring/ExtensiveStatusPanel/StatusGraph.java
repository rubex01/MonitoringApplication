package Functionality.Monitoring.ExtensiveStatusPanel;

import Assets.Variables;
import Functionality.Settings.SettingsController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatusGraph extends JPanel {

    private Color graphColor;

    private double[] values = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    private String type;

    public StatusGraph(Color graphColor, String type) {
        this.graphColor = graphColor;
        this.type = type;

        setBorder(new EmptyBorder(0, 0, 10, 0));
        setBackground(Variables.transparent);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2 = (Graphics2D) g;

        graphics2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2.setColor(new Color(graphColor.getRed(), graphColor.getGreen(), graphColor.getBlue(), 100));
        graphics2.fill(new Rectangle(0, 0, getWidth()-1, getHeight()-11));

        graphics2.setColor(graphColor.darker());
        graphics2.drawRect(0, 0, getWidth()-2, getHeight()-12);

        int stepSize = (getWidth()+3)/9;
        graphics2.setColor(new Color(graphColor.getRed(), graphColor.getGreen(), graphColor.getBlue(), 190));

        for (int i=1;i<10;i++) {
            graphics2.drawLine(stepSize*i, 1, stepSize*i, getHeight()-12);
        }

        graphics2.setColor(Variables.black);
        graphics2.drawString(Integer.parseInt(SettingsController.getSetting("check_interval")) * 10 + " seconden", 0, getHeight());
        graphics2.drawString("0", getWidth()-8, getHeight());

        drawGraph(graphics2);
    }

    private void drawGraph(Graphics2D graphics2) {
        double highestValue = -1;
        double lowestValue = -1;
        for (int i=0;i<values.length;i++) {
            if (values[i] > highestValue || highestValue == -1) highestValue = values[i];
            if (values[i] < lowestValue || lowestValue == -1) lowestValue = values[i];
        }
        if (highestValue == -1 && lowestValue == -1) return;

        int verticalSteps = (getHeight()-12) / ((int) (highestValue*1.7));
        int horizontalSteps = getWidth()/9;

        int[] horizontalCoordinates = new int[10];
        int[] verticalCoordinates = new int[10];

        for (int i=0;i<horizontalCoordinates.length;i++) {
            horizontalCoordinates[i] = i*horizontalSteps;
        }

        double[] newValues = new double[10];
        System.arraycopy(values, 0, newValues, 0, 10);

        int highestVerticalCoordinates = -1;
        for (int i=0;i<verticalCoordinates.length;i++) {
            verticalCoordinates[i] = (values[i] == -1) ? getHeight()-12 : (int) ((getHeight()-12) - (values[i]*verticalSteps));
            if (highestValue == -1 || verticalCoordinates[i] > highestVerticalCoordinates) highestVerticalCoordinates = verticalCoordinates[i];
        }

        for(int i = 0; i < newValues.length / 2; i++) {
            double temp = newValues[i];
            newValues[i] = newValues[newValues.length - i - 1];
            newValues[newValues.length - i - 1] = temp;
        }

        for(int i = 0; i < verticalCoordinates.length / 2; i++) {
            int temp = verticalCoordinates[i];
            verticalCoordinates[i] = verticalCoordinates[verticalCoordinates.length - i - 1];
            verticalCoordinates[verticalCoordinates.length - i - 1] = temp;
        }

        for (int i=1;i<horizontalCoordinates.length;i++) {
            graphics2.drawLine(horizontalCoordinates[i-1], verticalCoordinates[i-1], horizontalCoordinates[i], verticalCoordinates[i]);
        }

        double lastValue = -1;
        for (int i=0;i<horizontalCoordinates.length;i++) {
            if (verticalCoordinates[i] < getHeight()-12) {
                graphics2.setColor(graphColor.darker());
                graphics2.fillOval(horizontalCoordinates[i]-3, verticalCoordinates[i]-3, 8, 8);
                if (lastValue != newValues[i]) {
                    graphics2.setColor(Variables.black);
                    graphics2.drawString(String.format("%.2f ", newValues[i]) + type, horizontalCoordinates[i]+3, verticalCoordinates[i]-3);
                }
                lastValue = newValues[i];
            }
        }

    }

    public void addValue(double newVal) {
        for (int i=values.length-1;i>0;i--) {
            values[i] = values[i-1];
        }
        values[0] = newVal;
        repaint();
    }
}
