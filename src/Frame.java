import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame() {

        setTitle("Monitoring Applicatie");
        setLayout(new FlowLayout());
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }
}
