package ui.game;

import model.BlobGame;
import ui.Screen;

import javax.swing.*;
import java.awt.*;

public class InfoBar extends JPanel {
    private static final int WIDTH = GameScreen.RIGHT_WIDTH;
    private static final int HEIGHT = Screen.HEIGHT - GamePanel.HEIGHT;

    BlobGame bg;

    public InfoBar(BlobGame bg) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.ORANGE);
        setVisible(true);
        this.bg = bg;
    }
}
