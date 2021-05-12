package Functionality.Algorithm;

import Assets.DefaultButton;
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

    public AlgorithmDialog() {
        super(Frame.defaultFrame, true);

        setTitle("Bereken optimale samenstelling");
        setSize(300, 150);
        setLayout(new FlowLayout());
        drawItems();
        setLocationRelativeTo(Frame.defaultFrame);

        setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            try {
                setTitle("Berekenen...");

                ArrayList<Server> servers = ServerParser.parseServers();

                String input = JTuptime.getText();
                input = input.replace(",", "");
                input = input.replace(".", "");
                input = "0." + input;
                double uptimeInput = Double.parseDouble(input);

                Algorithm calculate = new Algorithm(servers, uptimeInput);
                Blueprint optimalBlueprint = new Blueprint("Optimaal ontwerp");

                Frame.defaultFrame.getTabsBar().addTab(optimalBlueprint);
                Frame.defaultFrame.getTabsBar().changeFocus(optimalBlueprint);

                ArrayList<Server> optimalSolutionList = new ArrayList<>();
                for (Server server : calculate.getBestSolution().getAllServers()) optimalSolutionList.add(server);

                optimalBlueprint.addBulk(optimalSolutionList);
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
        if (JTuptime.getText().charAt(0) == '0') JTuptime.setText("");
        if (JTuptime.getText().length() > 2) {
            if (JTuptime.getText().charAt(2) != '.' && JTuptime.getText().charAt(2) != ',') {
                JTuptime.setText(JTuptime.getText().substring(0, 2) + "." + JTuptime.getText().substring(3));
            }
        }
        if (SettingsController.getSetting("algorithm_allowlong").equals("no") && JTuptime.getText().length() > 5) {
            JTuptime.setText(JTuptime.getText().substring(0, 5));
        }
    }
}
