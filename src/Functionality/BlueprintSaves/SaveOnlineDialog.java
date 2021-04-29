package Functionality.BlueprintSaves;

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

public class SaveOnlineDialog extends JDialog implements ActionListener, KeyListener {

    private JTextField jtFilename;

    private JButton jbOk;

    private boolean okPressed = false, overwrite = false;

    private JLabel warning;

    private String filetitle;

    public SaveOnlineDialog(String filetitle) {
        super(Frame.defaultFrame, true);
        this.filetitle = filetitle;

        setTitle("Bestand online opslaan");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(300,150);
        setLocationRelativeTo(Frame.defaultFrame);

        drawItems();

        checkForOverwrite();

        setVisible(true);
    }

    public boolean getOkPressed(){
        return okPressed;
    }

    public String getFileName(){
        return jtFilename.getText();
    }

    public boolean hasToOverwrite() {
        return overwrite;
    }

    private void drawItems() {
        jtFilename = new JTextField(16);
        jtFilename.addKeyListener(this);
        if (filetitle != null) jtFilename.setText(filetitle);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        jbOk = new JButton("Opslaan");
        jbOk.addActionListener(this);
        JButton jbCancel = new JButton("Annuleren");
        jbCancel.addActionListener(this);

        warning = new JLabel();

        add(jtFilename);
        add(warning);
        buttonPanel.add(jbOk);
        buttonPanel.add(jbCancel);
        add(buttonPanel);
    }

    private void checkForOverwrite() {
        try{
            jbOk.setEnabled(false);
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("select count('ID') as 'exists' from blueprints where Filename = ?");
            preparedStatement.setString(1, getFileName());
            ResultSet rs = preparedStatement.executeQuery();
            int restult = 0;
            while(rs.next()) restult = rs.getInt(1);
            if (restult > 0) {
                warning.setText("<html><div style='text-align: center;'>Als u dit bestand met deze naam opslaat<br>  wordt het oude bestand overschreven</div></html>");
                overwrite = true;
            }
            else {
                warning.setText("");
                overwrite = false;
            }
            DatabaseConnection.closeConnection();
            jbOk.setEnabled(true);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
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
        checkForOverwrite();
    }
}
