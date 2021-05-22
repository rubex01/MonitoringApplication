package GUI;

import Assets.Variables;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

public class TabButton extends JPanel implements MouseListener, Serializable, ActionListener {

    private TabModel child;

    public static TabButton focusedButton;

    private JLabel title;

    private Timer animationTimer;

    private int animationCount;

    public TabButton(TabModel child) {
        this.child = child;
        this.animationTimer = new Timer(35, this);

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
            JButton jbClose = new JButton("");
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

    public void unfocus() {
        setBackground(Variables.nonFocus);
        animationCount = 0;
        animationTimer.start();
    }

    public void focus() {
        setBackground(Variables.focus);
        animationCount = -3;
        animationTimer.start();
    }

    public void setFocusedButton() {
        if (focusedButton != null) {
            focusedButton.unfocus();
        }
        focusedButton = this;
        focus();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getModifiersEx() == InputEvent.ALT_DOWN_MASK) {
            child.close();
        }
        else if (e.getSource() == this) {
            child.setFocus();
        }
        else if (e.getSource() instanceof JButton) {
            child.close();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == animationTimer) {
            if (this == focusedButton) {
                animationCount++;
                setBorder(BorderFactory.createMatteBorder(animationCount, 0, animationCount, 5, Variables.white));
                if (animationCount == 0) animationTimer.stop();
            }
            else {
                animationCount--;
                setBorder(BorderFactory.createMatteBorder(-animationCount, 0, animationCount, 5, Variables.white));
                if (animationCount == -3) animationTimer.stop();
            }
        }
    }
}
