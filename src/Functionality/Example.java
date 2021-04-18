package Functionality;

import GUI.TabModel;

import javax.swing.*;
import java.awt.*;

public class Example extends TabModel {

    private JPanel panel;

    public Example(String title, boolean closeable) {
        super(title, closeable);
        generatePanel();
    }

    private void generatePanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Titel van dit tablad: " + getTitle()));
        panel.add(new JLabel("Voorbeeld Tablad in het programma.. Zie src/Functionality/Example"));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public boolean closeCheck() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(panel, "De wijzigingen aan" + getTitle() + " zijn niet opgeslagen. Wil je het bestand toch sluiten?", "Monitorings Applicatie", dialogButton);
        if(dialogResult == 0) {
            return true;
        } else {
            return false;
        }
    }

}
