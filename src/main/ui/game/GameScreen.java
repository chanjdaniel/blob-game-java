package ui.game;

import model.Abilities;
import model.Blob;
import model.BlobGame;
import ui.BlobEatBlob;
import ui.BlobRenderer;
import ui.Screen;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;

public class GameScreen extends Screen {
    public static final int RIGHT_WIDTH = 800;
    public static final int LEFT_WIDTH = Screen.WIDTH - RIGHT_WIDTH;

    BlobEatBlob beb;
    BlobGame bg;

    // Constructs a game screen
    public GameScreen(BlobEatBlob beb) {
        super();
        setLayout(new BorderLayout());
        this.beb = beb;
        this.bg = beb.getBlobGame();
        drawGame();
    }

    private void drawGame() {
        addMainInfoPanel();
        addMainGamePanel();
    }

    private void addMainInfoPanel() {
        JPanel mainInfoPanel = new JPanel();
        mainInfoPanel.setPreferredSize(new Dimension(LEFT_WIDTH, Screen.HEIGHT));
        mainInfoPanel.setBackground(BACKGROUND_COLOR);
        mainInfoPanel.setLayout(new BoxLayout(mainInfoPanel, BoxLayout.PAGE_AXIS));
        mainInfoPanel.setVisible(true);
        add(mainInfoPanel, BorderLayout.WEST);

        PlayerInfoPanel pip = new PlayerInfoPanel(bg);
        pip.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainInfoPanel.add(pip);

        AbilitiesPanel ap = new AbilitiesPanel(bg);
        ap.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainInfoPanel.add(ap);
    }

    private void addMainGamePanel() {
        JPanel mainGamePanel = new JPanel();
        mainGamePanel.setPreferredSize(new Dimension(RIGHT_WIDTH, Screen.HEIGHT));
        mainGamePanel.setBackground(BACKGROUND_COLOR);
        mainGamePanel.setLayout(new BoxLayout(mainGamePanel, BoxLayout.PAGE_AXIS));
        mainGamePanel.setVisible(true);
        add(mainGamePanel, BorderLayout.EAST);

        InfoBar ib = new InfoBar(bg);
        ib.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainGamePanel.add(ib);

        GamePanel gp = new GamePanel(bg);
        gp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainGamePanel.add(gp);
    }
}
