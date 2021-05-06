package Assets;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.Serializable;

public class ScrollbarUI extends BasicScrollBarUI implements Serializable {

    private final Dimension d = new Dimension();

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = Variables.scrollThumbColor;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

}
