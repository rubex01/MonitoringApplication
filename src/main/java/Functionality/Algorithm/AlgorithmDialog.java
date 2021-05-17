package Functionality.Algorithm;

import Assets.DefaultButton;
import Functionality.Algorithm.Algorithm.Algorithm;
import Functionality.Algorithm.Algorithm.ServerConfiguration;
import Functionality.Blueprint.Blueprint;
import Functionality.Server;
import Functionality.ServerParser;
import Functionality.Settings.SettingsController;
import GUI.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class AlgorithmDialog extends JDialog implements ActionListener, KeyListener {

    private JTextField JTuptime;

    private DefaultButton okButton;

    private ArrayList<Server> servers;

    private ServerConfiguration lastCalculatedQuickSolution;

    public AlgorithmDialog() {
        super(Frame.defaultFrame, true);

        this.servers = ServerParser.parseServers();

        resetTitle();
        setSize(300, 150);
        setLayout(new FlowLayout());
        drawItems();
        setLocationRelativeTo(Frame.defaultFrame);

        setVisible(true);
    }

    private void resetTitle() {
        setTitle("Bereken optimale samenstelling");
    }

    private void drawItems() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout());
        formPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JLabel label = new JLabel("BeschikbaarheidsPercentage:");
        JTuptime = new JTextField(4);
        JTuptime.addKeyListener(this);

        okButton = new DefaultButton("Berekenen");
        okButton.addActionListener(this);
        DefaultButton cancelButton = new DefaultButton("Annuleren");
        cancelButton.addActionListener(this);

        formPanel.add(label);
        formPanel.add(JTuptime);
        add(formPanel);
        add(okButton);
        add(cancelButton);
    }

    private double parseInput() {
        String input = JTuptime.getText();
        input = input.replace(",", "");
        input = input.replace(".", "");
        input = "0." + input;
        return Double.parseDouble(input);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            try {
                setTitle("Berekenen...");

                double uptimeInput = parseInput();

                ServerConfiguration optimalSolution = (lastCalculatedQuickSolution == null)
                        ? new Algorithm(servers, uptimeInput).getCurrentBestSolution()
                        : lastCalculatedQuickSolution;

                Blueprint optimalBlueprint = new Blueprint("Optimaal ontwerp");

                Frame.defaultFrame.getTabsBar().addTab(optimalBlueprint);
                Frame.defaultFrame.getTabsBar().changeFocus(optimalBlueprint);

                optimalBlueprint.addBulk(optimalSolution.getServers());
            }
            catch (Exception exception) {
                exception.printStackTrace();
                // TODO: nice error!!!
            }
        }
        setVisible(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        resetTitle();
        if (JTuptime.getText().charAt(0) == '0') JTuptime.setText("");
        if (JTuptime.getText().length() > 2) {
            if (JTuptime.getText().charAt(2) != '.' && JTuptime.getText().charAt(2) != ',') {
                JTuptime.setText(JTuptime.getText().substring(0, 2) + "." + JTuptime.getText().substring(2));
            }
        }
        if (JTuptime.getText().length() <= 5) {
            double inputUptime = parseInput();
            Algorithm quickCalculate = new Algorithm(servers, inputUptime);
            lastCalculatedQuickSolution = quickCalculate.getCurrentBestSolution();
            setTitle(lastCalculatedQuickSolution.getServers().length + " servers, €" + lastCalculatedQuickSolution.getPrice());
        }
        else if (SettingsController.getSetting("algorithm_allowlong").equals("no")) {
            JTuptime.setText(JTuptime.getText().substring(0, 7));
        }
        else {
            lastCalculatedQuickSolution = null;
        }
    }
}
