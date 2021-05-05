package Functionality.Blueprint;

import Assets.Variables;
import Functionality.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

public class ServerVisualizationItem extends JPanel implements Serializable, ActionListener {

    private ServerCollection collection;

    private Blueprint parent;

    private JLabel imageLabel;

    private JButton plus, min;

    public ServerVisualizationItem(ServerCollection collection, Blueprint parent) {
        this.collection = collection;
        this.parent = parent;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 35, 10, 55));
        setBackground(Variables.backgroundLighter);

        drawPanel();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2 = (Graphics2D) g;

        graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        graphics2.setColor(Variables.onlineColor);
        RoundRectangle2D contentRectangle = new RoundRectangle2D.Float(10, (imageLabel.getY()+20), 22, 22, 5, 5);
        graphics2.fill(contentRectangle);
        graphics2.setColor(Variables.onlineColor.darker());
        graphics2.draw(contentRectangle);

        graphics2.setColor(Variables.black);
        graphics2.setFont(new Font(imageLabel.getFont().getName(), Font.BOLD, 11));
        graphics2.drawString(collection.getAmount()+"x", 15, (imageLabel.getY()+35));

        boolean foundWeb = false;
        boolean foundDB = false;
        for (Server server : parent.getServers()) {
            if (server.getType() == Server.DATABASE) foundDB = true;
            else if (server.getType() == Server.WEBSERVER) foundWeb = true;
        }

        if (
                (collection.getServer().getType() == Server.FIREWALL && foundWeb == true) ||
                (collection.getServer().getType() == Server.WEBSERVER && foundDB == true)
        ) {
            int circleSize = 6;
            int circleY = (getHeight()/2+(circleSize/2));
            int lineY = circleY+circleSize/2;
            int circle1X = getWidth() - 50;
            int circle2X = getWidth() - 10;

            graphics2.setColor(Variables.nonFocus);
            Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{2}, 0);
            graphics2.setStroke(dashed);
            graphics2.drawLine(circle1X+3, lineY, circle2X, lineY);

            graphics2.fillOval(circle1X, circleY, circleSize, circleSize);
            graphics2.fillOval(circle2X, circleY, circleSize, circleSize);
        }
    }

    private void drawPanel() {
        imageLabel = new JLabel("");
        imageLabel.setIcon(new ImageIcon(collection.getServer().getImage()));
        add(imageLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Variables.backgroundLighter);
        infoPanel.add(new JLabel(collection.getServer().getName()));
        JLabel infoLabel = new JLabel("€" + collection.getServer().getPrice() + " " + collection.getServer().getUptime() + "%");
        infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, 11));
        infoPanel.add(infoLabel);

        add(infoPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());
        actionPanel.setBackground(Variables.backgroundLighter);

        min = new JButton("- 1");
        min.setBackground(Variables.transparent);
        min.setCursor(new Cursor(Cursor.HAND_CURSOR));
        min.setBorderPainted(false);
        min.setFocusPainted(false);
        min.setContentAreaFilled(false);
        min.setMargin(new Insets(0, 3, 0, 3));
        min.addActionListener(this);
        actionPanel.add(min);

        plus = new JButton("+ 1");
        plus.setBackground(Variables.transparent);
        plus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        plus.setBorderPainted(false);
        plus.setFocusPainted(false);
        plus.setContentAreaFilled(false);
        plus.addActionListener(this);
        plus.setMargin(new Insets(0, 3, 0, 3));
        actionPanel.add(plus);

        add(actionPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plus) {
            parent.addServer(collection.getServer());
        }
        else if (e.getSource() == min) {
            parent.removeServer(collection.getServer());
        }
    }
}
