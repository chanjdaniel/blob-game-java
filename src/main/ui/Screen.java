package ui;

import javax.swing.*;
import java.awt.*;

// Represents the default screen
public class Screen extends JPanel {
    public static final Color BACKGROUND_COLOR = Color.WHITE;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final int CENTRE_WIDTH = WIDTH / 2;
    public static final int CENTRE_HEIGHT = HEIGHT / 2;

    public Screen() {
        setBounds(CENTRE_WIDTH - WIDTH / 2,CENTRE_HEIGHT - HEIGHT / 2,WIDTH,HEIGHT);
        setBackground(BACKGROUND_COLOR);
        setLayout(null);
        setVisible(true);
    }
}
