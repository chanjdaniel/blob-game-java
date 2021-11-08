package ui.game;

import model.Blob;
import model.BlobGame;
import model.Blobs;
import ui.BlobRenderer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int WIDTH = GameScreen.RIGHT_WIDTH;
    public static final int HEIGHT = 575;

    BlobGame bg;

    public GamePanel(BlobGame bg) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.CYAN);
        setVisible(true);
        this.bg = bg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        addPlayerBlob(g);
        addEnemyBlobs(g);
    }

    // MODIFIES: this
    // EFFECTS:  adds the player blob
    private void addPlayerBlob(Graphics g) {
        Blob blob = bg.getPlayerBlob();
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }

    // MODIFIES: this
    // EFFECTS:  adds enemy blobs
    private void addEnemyBlobs(Graphics g) {
        Blobs blobs = bg.getEnemyBlobs();
        BlobRenderer renderer = new BlobRenderer();

        for (Blob next : blobs.getBlobs()) {
            renderer.renderBlob(g, next);
        }
    }
}
