package Assets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ContextMenu extends JPopupMenu {

    public ContextMenu() {
        setBorder(new EmptyBorder(8, 5, 5, 8));
        setBackground(Variables.transparent);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2 = (Graphics2D) g;

        graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 13, 13);
        graphics2.setColor(Variables.nonFocus);
        graphics2.fill(roundedRectangle);

        setBackground(Variables.transparent);
    }

}
