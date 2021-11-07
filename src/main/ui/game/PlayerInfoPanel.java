package ui.game;

import model.BlobGame;
import ui.Screen;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {
    private static final int WIDTH = GameScreen.LEFT_WIDTH;
    private static final int HEIGHT = Screen.HEIGHT / 2;

    BlobGame bg;

    public PlayerInfoPanel(BlobGame bg) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.PINK);
        setVisible(true);
        this.bg = bg;
    }
}
