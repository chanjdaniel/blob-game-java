package ui;

import model.Blob;
import model.BlobGame;

import java.awt.*;

/*
 * Represents the main window in which the blob game is played
 */

public class BlobEatBlob {

    public static final String TITLE = "A Blob Eat Blob World";
    private static final int INTERVAL = 10;

    private BlobGame bg;
    private GameFrame gameFrame;

    // Constructs main window
    // effects: sets up window in which Space Invaders game will be played
    public BlobEatBlob() {
        gameFrame = new GameFrame(TITLE);
        centreOnScreen();
        run();
    }

    public void setBlobGame(BlobGame bg) {
        this.bg = bg;
    }

    public BlobGame getBlobGame() {
        return bg;
    }

    public Blob getPlayerBlob() {
        return bg.getPlayer();
    }

    private void run() {
        StartScreen startScreen = new StartScreen(this);
        nextScreen(startScreen);
    }

    public void nextScreen(Screen screen) {
        gameFrame.nextScreen(screen);
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        gameFrame.setLocation((screen.width - Screen.WIDTH) / 2, (screen.height - Screen.HEIGHT) / 2);
    }
}