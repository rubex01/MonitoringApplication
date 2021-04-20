package GUI;

import Assets.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TabButton extends JPanel implements MouseListener {

    private TabModel child;

    public static TabButton focusedButton;

    private JLabel title;

    public TabButton(TabModel child) {
        this.child = child;
        addMouseListener(this);
        setBackground(Variables.nonFocus);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createMatteBorder(3, 0, -3, 5, Variables.white));

        drawElements();
    }

    public void updateTitle() {
        title.setText(child.getTitle());
    }

    private void drawElements() {
        title = new JLabel(child.getTitle());
        add(title);

        if (child.isClosable()) {
            JButton jbClose = new JButton();
            jbClose = new JButton("");
            jbClose.setBackground(Variables.transparent);
            jbClose.setBorderPainted(false);
            jbClose.setFocusPainted(false);
            jbClose.setContentAreaFilled(false);
            jbClose.addMouseListener(this);
            jbClose.setMargin(new Insets(2, 0, 2, 0));
            jbClose.setIcon(new ImageIcon(Variables.getImage("close")));
            add(jbClose);
        }
    }

    public void setFocusedButton() {
        if (focusedButton != null) {
            focusedButton.setBackground(Variables.nonFocus);
            focusedButton.setBorder(BorderFactory.createMatteBorder(2, 0, -2, 5, Variables.white));
        }
        focusedButton = this;
        focusedButton.setBackground(Variables.focus);
        focusedButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Variables.white));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == this) {
            child.setFocus();
        }
        else if (e.getSource() instanceof JButton) {
            child.close();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
