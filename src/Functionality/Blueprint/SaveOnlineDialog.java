package Functionality.Blueprint;

import Functionality.DatabaseConnection;
import GUI.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SaveOnlineDialog extends JDialog implements ActionListener, KeyListener {

    private JTextField jtFilename;

    private JButton jbOk, jbCancel;

    private boolean okPressed = false, overwrite = false;

    private JLabel warning;

    private String filetitle;

    public SaveOnlineDialog(String filetitle) {
        super(Frame.defaultFrame, true);
        this.filetitle = filetitle;

        setTitle("Bestand online opslaan");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(300,150);

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
        jtFilename = new JTextField(15);
        jtFilename.addKeyListener(this);
        if (filetitle != null) jtFilename.setText(filetitle);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        jbOk = new JButton("Opslaan");
        jbOk.addActionListener(this);
        jbCancel = new JButton("Annuleren");
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
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select count('ID') as 'exists' from blueprints where Filename = '" + getFileName() + "'");
            int restult = 0;
            while(rs.next()) restult = rs.getInt(1);
            System.out.println(getFileName());
            if (restult > 0) {
                warning.setText("<html><div style='text-align: center;'>Als u dit bestand met deze naam opslaat<br>  wordt het oude bestand overschreven</div></html>");
                overwrite = true;
            }
            else {
                warning.setText("");
                overwrite = false;
            }
            DatabaseConnection.closeConnection();
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
