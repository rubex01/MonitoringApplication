package Functionality.BlueprintSaves;
import Assets.DefaultButton;
import Functionality.DatabaseConnection;
import GUI.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


    public class DeleteOnlineDialog extends JDialog implements ActionListener, KeyListener {

        private JTextField jtFilename;

        private DefaultButton jbOk;

        private boolean okPressed = false, overwrite = false;

        private JLabel warning;
        private String filetitle;

        public DeleteOnlineDialog(String filetitle) {
            super(Frame.defaultFrame, true);
            this.filetitle = filetitle;

            setTitle("Bestand verwijderen");
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setSize(600,100);
            setLocationRelativeTo(Frame.defaultFrame);

            drawItems();

            setVisible(true);
        }

        public boolean getOkPressed(){
            return okPressed;
        }

        public String getFileName(){
            return jtFilename.getText();
        }


        private void drawItems() {
            jtFilename = new JTextField(16);
            jtFilename.addKeyListener(this);
            if (filetitle != null) jtFilename.setText(filetitle);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            jbOk = new DefaultButton("Verwijderen");
            jbOk.addActionListener(this);
            DefaultButton jbCancel = new DefaultButton("Annuleren");
            jbCancel.addActionListener(this);

            warning = new JLabel("Weet je zeker dat "+ filetitle +" verwijderd moet worden?");

            add(warning);
            buttonPanel.add(jbOk);
            buttonPanel.add(jbCancel);
            add(buttonPanel);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbOk) okPressed = true;
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

        }
    }

