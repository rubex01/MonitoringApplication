package Assets;

import GUI.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OkDialog extends JDialog implements ActionListener {

    private String message, buttonText;

    private int type;

    public final static int INFO = 0, WARNING = 1, ERROR = 2;

    public OkDialog(String title, String message, String buttonText, int type) {
        super(Frame.defaultFrame, true);

        setTitle(title);
        setLayout(new GridLayout(2, 1));
        setSize(Math.max(message.length() * 10, 300), 140);
        setLocationRelativeTo(Frame.defaultFrame);

        this.message = message;
        this.type = type;
        this.buttonText = buttonText;

        drawItems();

        setVisible(true);
    }

    public OkDialog(String title, String message, String buttonText) {
        this(title, message, buttonText, INFO);
    }

    public OkDialog(String title, String message) {
        this(title, message, "Oke", INFO);
    }

    private Image getImage() {
        if (type == INFO) return Variables.getImage("info_popup");
        else if (type == WARNING) return Variables.getImage("warning_popup");
        return Variables.getImage("error_popup");
    }

    private void drawItems() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.setBorder(new EmptyBorder(20, 20, 5, 20));

        JLabel messageLabel = new JLabel(this.message);
        messageLabel.setIcon(new ImageIcon(getImage()));
        infoPanel.add(messageLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(5, 20, 20, 20));

        DefaultButton okButton = new DefaultButton(buttonText);
        okButton.addActionListener(this);
        buttonPanel.add(okButton);

        add(infoPanel);
        add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }
}
