package ui.game;

import model.Ability;
import model.Blob;
import model.BlobGame;
import model.Blobs;
import ui.AbilityRenderer;
import ui.BlobRenderer;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Represents a game panel where the game is played
public class GamePanel extends JPanel {
    private static final int WIDTH = GameScreen.RIGHT_WIDTH;
    public static final int HEIGHT = Screen.HEIGHT;

    BlobGame bg;

    // Constructs a game panel
    public GamePanel(BlobGame bg) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        setVisible(true);
        this.bg = bg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        addPlayerBlob(g);
        addEnemyBlobs(g);
        addAbilities(g);
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

    // MODIFIES: this
    // EFFECTS:  adds abilities
    private void addAbilities(Graphics g) {
        ArrayList<Ability> abilities = bg.getAbilities();
        AbilityRenderer renderer = new AbilityRenderer();

        for (Ability next : abilities) {
            renderer.renderAbility(g, next);
        }
    }
}
