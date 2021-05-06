package Assets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class DefaultButton extends JButton {

    public DefaultButton(String title) {
        super(title);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        init();
    }

    public DefaultButton() {
        super();
        init();
    }

    private void init() {
        setBackground(new Color(37, 39, 53));
        setBorder(new EmptyBorder(5, 14, 5, 14));
        setFocusable(false);
    }

    public void paint(Graphics g) {
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);

        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paint(g);

        g2d.setColor(Variables.defaultButtonColor);
        g2d.fillRoundRect(0,0,getWidth(),getHeight(),5,5);

        FontRenderContext frc = new FontRenderContext(null, false, false);
        Rectangle2D r = getFont().getStringBounds(getText(), frc);

        float xMargin = (float)(getWidth()-r.getWidth())/2;
        float yMargin = (float)(getHeight()-getFont().getSize())/2;

        g2d.setColor(Variables.white);

        g2d.drawString(getText(),xMargin,((float)getFont().getSize()+yMargin-1));
    }
}