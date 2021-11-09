package ui.game;

import model.Blob;
import model.BlobGame;
import ui.BlobRenderer;
import ui.Screen;

import javax.swing.*;
import java.awt.*;

// Represents the player info panel
public class PlayerInfoPanel extends JPanel {
    private static final int WIDTH = GameScreen.LEFT_WIDTH;
    private static final int HEIGHT = Screen.HEIGHT / 2;

    JLabel sizeLabel;
    JLabel killsLabel;

    BlobGame bg;
    Blob playerBlob;

    // Constructs a player info panel
    public PlayerInfoPanel(BlobGame bg) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        this.bg = bg;
        this.playerBlob = bg.getPlayerBlob();
        drawScreen();
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        addBlob(g);
        // updateInfo();
    }

    // MODIFIES: this
    // EFFECTS:  adds blob art
    private void addBlob(Graphics g) {
        int size = 100;
        int offSetY = 50;
        Color blobColor = playerBlob.getColor();
        Blob blob = new Blob("", size, 0, WIDTH / 2.0,
                HEIGHT / 2.0 - offSetY, blobColor);
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }

    // MODIFIES: this
    // EFFECTS:  draws the screen
    private void drawScreen() {
        addNameLabel();
        addKillsLabel();
        addSizeLabel();
    }

    // MODIFIES: this
    // EFFECTS:  adds name label
    private void addNameLabel() {
        String name = playerBlob.getName();
        int width = 200;
        int height = 50;
        int centreX = WIDTH / 2 - width / 2;
        int centreY = HEIGHT / 2 - height / 2;
        int offSetY = 125;
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        label.setFont(font);
        label.setBounds(centreX, centreY - offSetY, width, height);
        add(label);
    }

    // MODIFIES: this
    // EFFECTS:  adds size label
    private void addSizeLabel() {
        String text = "Size: " + playerBlob.getSize();
        int width = 200;
        int height = 50;
        int centreX = WIDTH / 2 - width / 2;
        int centreY = HEIGHT / 2 - height / 2;
        int offSetX = 55;
        int offSetY = 50;
        sizeLabel = new JLabel(text, SwingConstants.LEFT);
        Font font = new Font(Font.SERIF, Font.BOLD, 25);
        sizeLabel.setFont(font);
        sizeLabel.setBounds(centreX + offSetX, centreY + offSetY, width, height);
        add(sizeLabel);
    }

    // MODIFIES: this
    // EFFECTS:  adds kills label
    private void addKillsLabel() {
        String text = "Kills: " + playerBlob.getVictims().size();
        int width = 200;
        int height = 50;
        int centreX = WIDTH / 2 - width / 2;
        int centreY = WIDTH / 2 - height / 2;
        int offSetX = 55;
        int offSetY = 150;
        killsLabel = new JLabel(text, SwingConstants.LEFT);
        Font font = new Font(Font.SERIF, Font.BOLD, 25);
        killsLabel.setFont(font);
        killsLabel.setBounds(centreX + offSetX, centreY + offSetY, width, height);
        add(killsLabel);
    }

    // Updates the info panel
    // MODIFIES: this
    // EFFECTS:  updates size and kills labels
    public void update() {
        sizeLabel.setText("Size: " + playerBlob.getSize());
        killsLabel.setText("Kills: " + playerBlob.getVictims().size());
        repaint();
    }
}
