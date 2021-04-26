package Functionality.Monitoring.QuickStatusPanel;

import Assets.Variables;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QuickStatusPanel extends JPanel {

    private JLabel infoLabel;

    public static final int UPTIME = 0, DOWNTIME = 1, OUTAGES = 2;

    private int type;

    private Color typeColor;

    private String info, extraInfo, status;

    public QuickStatusPanel(int type, String status) {
        this.type = type;
        this.status = status;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Variables.white);

        checkType();
        drawItems();
    }

    private void checkType() {
        switch (type) {
            case QuickStatusPanel.UPTIME:
                info = "uren online";
                extraInfo = "Aantal uren online sinds laatste downtime";
                typeColor = Variables.uptimeColor;
                break;
            case QuickStatusPanel.DOWNTIME:
                info = "minuten downtime";
                extraInfo = "Aantal minuten downtime in de laatste 24 uur";
                typeColor = Variables.downtimeColor;
                break;
            case QuickStatusPanel.OUTAGES:
                info = "servers offline";
                extraInfo = "Aantal servers offline op dit moment";
                typeColor = Variables.outagesColor;
                break;
        }
    }

    private void drawItems() {
        add(new QuickStatusPanelIcon(typeColor));
        JPanel quickStatusInfo = new JPanel();
        quickStatusInfo.setBackground(Variables.white);
        quickStatusInfo.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Variables.white);

        infoLabel = new JLabel(status);
        infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.BOLD, 21));
        infoLabel.setForeground(typeColor);
        infoLabel.setBorder(new EmptyBorder(-6, 0, -6, 0));
        topPanel.add(infoLabel);

        JLabel infoLabel = new JLabel(info);
        infoLabel.setBorder(new EmptyBorder(0, 0, -3, 0));
        topPanel.add(infoLabel);

        quickStatusInfo.add(topPanel);
        JLabel extraInfoLable = new JLabel(extraInfo);
        extraInfoLable.setFont(new Font(extraInfoLable.getFont().getName(), Font.PLAIN, 12));
        extraInfoLable.setForeground(Variables.subText);
        extraInfoLable.setBorder(new EmptyBorder(-3, 6, 0, 0));
        quickStatusInfo.add(extraInfoLable);

        add(quickStatusInfo);
    }

}
