package Functionality.Monitoring.QuickStatusPanel;

import Assets.Variables;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class QuickStatusPanel extends JPanel {

    private JLabel infoLabel;

    public static final int UPTIME = 0, DOWNTIME = 1, OUTAGES = 2;

    private int type, status;

    private Color typeColor;

    private JLabel infoLabelAddon, extraInfoLabel;

    private String info, extraInfo, imageName;

    public QuickStatusPanel(int type, int status) {
        this.type = type;
        this.status = status;
        setLayout(new GridBagLayout());

        checkType();
        decideTextBasedOnTime(status);
        drawItems();
    }

    private void checkType() {
        switch (type) {
            case QuickStatusPanel.UPTIME:
                typeColor = Variables.uptimeColor;
                imageName = "clock_shadow";
                break;
            case QuickStatusPanel.DOWNTIME:
                typeColor = Variables.downtimeColor;
                imageName = "clock_shadow";
                break;
            case QuickStatusPanel.OUTAGES:
                info = "servers offline";
                extraInfo = "Aantal servers offline op dit moment";
                typeColor = Variables.outagesColor;
                imageName = "offline_shadow";
                break;
        }
    }

    private void decideTextBasedOnTime(int status) {
        String time = "";
        int newStatus = status;
        if (type != QuickStatusPanel.OUTAGES) {
            if (status < 60) {
                time = "seconden";
                newStatus = status;
            }
            else if ((status/60) < 59) {
                time = "minuten";
                newStatus = Integer.valueOf(status/60);
            }
            else {
                time = "uren";
                newStatus = Integer.valueOf(status/60/60);
            }
        }
        switch (type) {
            case QuickStatusPanel.DOWNTIME:
                info = time + " downtime";
                extraInfo = "Aantal " + time + " downtime sinds start servers";
                break;
            case QuickStatusPanel.UPTIME:
                info = time + " online";
                extraInfo = "Aantal " + time + " online sinds laatste downtime";
                break;
        }
        this.status = newStatus;
    }

    private void drawItems() {
        GridBagConstraints gbc = new GridBagConstraints();

        add(new QuickStatusPanelIcon(typeColor, imageName), gbc);
        JPanel quickStatusInfo = new JPanel();
        quickStatusInfo.setBackground(Variables.white);
        quickStatusInfo.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Variables.white);

        infoLabel = new JLabel(String.valueOf(status));
        infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.BOLD, 21));
        infoLabel.setForeground(typeColor);
        infoLabel.setBorder(new EmptyBorder(-6, 0, -6, 0));
        topPanel.add(infoLabel);

        infoLabelAddon = new JLabel(info);
        infoLabelAddon.setBorder(new EmptyBorder(0, 0, -3, 0));
        topPanel.add(infoLabelAddon);

        quickStatusInfo.add(topPanel);
        extraInfoLabel = new JLabel(extraInfo);
        extraInfoLabel.setFont(new Font(extraInfoLabel.getFont().getName(), Font.PLAIN, 12));
        extraInfoLabel.setForeground(Variables.subText);
        extraInfoLabel.setBorder(new EmptyBorder(-3, 6, 0, 0));
        quickStatusInfo.add(extraInfoLabel);

        add(quickStatusInfo, gbc);
    }

    public void updateTrigger(int newValue) {
        decideTextBasedOnTime(newValue);
        infoLabel.setText(String.valueOf(status));
        infoLabelAddon.setText(info);
        extraInfoLabel.setText(extraInfo);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);

        Graphics2D graphics2 = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2.setRenderingHints(rh);

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16);
        graphics2.fill(roundedRectangle);
    }
}
