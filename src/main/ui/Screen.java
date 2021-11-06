package ui;

import javax.swing.*;
import java.awt.*;

import static ui.BlobEatBlob.TITLE;

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

    protected void nextScreen() {
        GameFrame gameFrame = new GameFrame(TITLE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        gameFrame.add(this);
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setLocation((screen.width - Screen.WIDTH) / 2, (screen.height - Screen.HEIGHT) / 2);
    }
}
