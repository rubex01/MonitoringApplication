package Assets;

import GUI.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YesNoDialog extends JDialog implements ActionListener {

    private String message;

    private DefaultButton okButton;

    public static final int YES_OPTION = 0, NO_OPTION = 1, CLOSED_OPTION = 2;

    private int closeMethod;

    public YesNoDialog(String title, String message) {
        super(Frame.defaultFrame, true);

        setTitle(title);
        setLayout(new GridLayout(2, 1));
        setSize(Math.max(message.length() * 10, 300), 140);
        setLocationRelativeTo(Frame.defaultFrame);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeMethod = CLOSED_OPTION;
            }
        });

        this.message = message;

        drawItems();

        setVisible(true);
    }

    private void drawItems() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.setBorder(new EmptyBorder(20, 20, 5, 20));

        JLabel messageLabel = new JLabel(this.message);
        messageLabel.setIcon(new ImageIcon(Variables.getImage("questionmark_popup")));
        infoPanel.add(messageLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(5, 20, 20, 20));

        okButton = new DefaultButton("Ja");
        okButton.addActionListener(this);
        DefaultButton cancelButton = new DefaultButton("Nee");
        cancelButton.addActionListener(this);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(infoPanel);
        add(buttonPanel);
    }

    public int getCloseMethod() {
        return closeMethod;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            closeMethod = YES_OPTION;
        }
        else {
            closeMethod = NO_OPTION;
        }
        setVisible(false);
    }

}
