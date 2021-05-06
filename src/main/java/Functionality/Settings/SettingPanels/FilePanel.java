package Functionality.Settings.SettingPanels;

import Assets.DefaultButton;
import Functionality.Settings.SettingItem;
import GUI.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FilePanel extends SettingPanel implements ActionListener {

    private JLabel currentLocation;

    private DefaultButton openExplorer;

    public FilePanel(SettingItem parent) {
        super(parent);
    }

    @Override
    protected void drawItems() {
        JPanel items = new JPanel();
        items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));

        openExplorer = new DefaultButton("Verander locatie");
        openExplorer.addActionListener(this);

        currentLocation = new JLabel();
        reconsiderValueDisplay();

        items.add(currentLocation);
        items.add(openExplorer);
        add(items);
    }

    @Override
    public void reconsiderValueDisplay() {
        currentLocation.setText((parent.getNewValue() == null) ? value : parent.getNewValue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openExplorer) {
            JFileChooser j = new JFileChooser();
            j.setSelectedFile(new File(value));
            int values = j.showSaveDialog(Frame.defaultFrame);
            if (j.getSelectedFile() == null || values == JFileChooser.CANCEL_OPTION) return;

            String outputFilePath = j.getSelectedFile().getPath();
            value = outputFilePath;
            parent.setNewValue(outputFilePath);
            reconsiderValueDisplay();
            checkResetVisibility();
        }
        else if (e.getSource() == reset) {
            resetValue();
        }
    }

}
