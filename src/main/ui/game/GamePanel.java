package ui.game;

import javafx.beans.value.WritableDoubleValue;
import model.Blob;
import model.BlobGame;
import model.Blobs;
import ui.BlobEatBlob;
import ui.BlobRenderer;
import ui.Screen;

import javax.swing.*;
import java.awt.*;

import static ui.Screen.CENTRE_HEIGHT;
import static ui.Screen.CENTRE_WIDTH;

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

        // Blob
        addBlob(g);
    }

    // MODIFIES: this
    // EFFECTS:  adds the blob preview
    private void addBlob(Graphics g) {
        int size = 15;
        int centreX = WIDTH / 2;
        int centreY = HEIGHT / 2;
        Color blobColor = Color.CYAN;
        Blob blob = new Blob("", size, centreX, centreY, blobColor);
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }
}
