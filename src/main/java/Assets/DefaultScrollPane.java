package Assets;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class DefaultScrollPane extends JScrollPane implements Serializable {

    public DefaultScrollPane(Component component) {
        super(component);
        updateSettings();
    }

    private void updateSettings() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        getVerticalScrollBar().setUnitIncrement(16);
        getHorizontalScrollBar().setUnitIncrement(16);

        getHorizontalScrollBar().setBackground(Variables.backgroundLighter);
        getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, Variables.scrollBarWidth));
        getHorizontalScrollBar().setUI(new ScrollbarUI());

        getVerticalScrollBar().setBackground(Variables.backgroundLighter);
        getVerticalScrollBar().setPreferredSize(new Dimension(Variables.scrollBarWidth, Integer.MAX_VALUE));
        getVerticalScrollBar().setUI(new ScrollbarUI());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        getHorizontalScrollBar().setUI(new ScrollbarUI());
        getVerticalScrollBar().setUI(new ScrollbarUI());
    }

}
