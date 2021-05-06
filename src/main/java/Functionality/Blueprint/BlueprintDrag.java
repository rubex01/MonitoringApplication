package Functionality.Blueprint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class BlueprintDrag extends MouseAdapter implements Serializable {

    private Point origin;

    private Blueprint parent;

    public BlueprintDrag(Blueprint parent) {
        this.parent = parent;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        origin = new Point(e.getPoint());
        parent.getServerVisualizerPanel().setCursor(new Cursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        parent.getServerVisualizerPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (origin != null) {
            JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, parent.getServerVisualizerPanel());
            if (viewPort != null) {
                int deltaX = origin.x - e.getX();
                int deltaY = origin.y - e.getY();

                Rectangle view = viewPort.getViewRect();
                view.x += deltaX;
                view.y += deltaY;

                parent.getServerVisualizerPanel().scrollRectToVisible(view);
            }
        }
    }
}
