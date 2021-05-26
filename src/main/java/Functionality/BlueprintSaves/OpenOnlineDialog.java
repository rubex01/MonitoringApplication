package Functionality.BlueprintSaves;

import Assets.DefaultButton;
import Assets.DefaultScrollPane;
import Assets.Variables;
import Functionality.DatabaseConnection;
import GUI.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OpenOnlineDialog extends JDialog implements ActionListener, KeyListener {

    private JTextField jtfSearch;

    private DefaultButton jbOpen;

    private Button jbDel;

    private JList serversPanel;

    private DefaultListModel blueprints;

    private boolean openPressed;

    private boolean delPress;

    private ArrayList<String> allBlueprints;

    public OpenOnlineDialog() {
        super(Frame.defaultFrame, true);

        setTitle("Bestand online openen");
        setLayout(new BorderLayout());
        setBackground(Variables.backgroundLighter);
        setSize(500, 400);
        setLocationRelativeTo(Frame.defaultFrame);

        updateOnlineBlueprints();
        drawItems();

        setVisible(true);
    }

    private void drawItems(){
        JLabel imageLabel = new JLabel(new ImageIcon(Variables.getImage("search")));
        jtfSearch = new JTextField(15);
        jtfSearch.addKeyListener(this);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Variables.backgroundLighter);
        searchPanel.add(imageLabel);
        searchPanel.add(jtfSearch);

        blueprints = new DefaultListModel();
        for (String blueprint : allBlueprints) blueprints.addElement(blueprint);

        serversPanel = new JList(blueprints);
        serversPanel.setBackground(Variables.backgroundLighter);
        serversPanel.setFixedCellHeight(30);
        serversPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        DefaultScrollPane scrollPane = new DefaultScrollPane(serversPanel);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Variables.nonFocus));

        jbOpen = new DefaultButton("Openen");
        jbOpen.addActionListener(this);
        DefaultButton jbCancel = new DefaultButton("Annuleren");
        jbCancel.addActionListener(this);
        jbDel = new Button("Verwijderen");
        jbDel.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Variables.backgroundLighter);
        buttonPanel.add(jbOpen);
        buttonPanel.add(jbCancel);
        buttonPanel.add(jbDel);


        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateOnlineBlueprints() {
        try {
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement("select Filename from blueprints");
            ResultSet result = statement.executeQuery();
            ArrayList<String> blueprints = new ArrayList<>();
            while(result.next()) blueprints.add(result.getString(1));
            DatabaseConnection.closeConnection();
            allBlueprints = blueprints;
        }
        catch (Exception exception) {
//            TODO: give nice error
            exception.printStackTrace();
        }
    }

    public String getSelectedValue(){
        return serversPanel.getSelectedValue().toString();
    }
    
    public boolean getOpenPressed(){
        return openPressed;
    }
    public boolean getDelPressed(){ return delPress; }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbOpen) openPressed = true;
        setVisible(false);

        if (e.getSource() == jbDel) delPress = true;
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
        blueprints.removeAllElements();
        if (jtfSearch.getText().equals("")) {
            for (String blueprint : allBlueprints) blueprints.addElement(blueprint);
            return;
        }
        for (String blueprint : allBlueprints) if (blueprint.toLowerCase().contains(jtfSearch.getText().toLowerCase())) blueprints.addElement(blueprint);
    }
}
