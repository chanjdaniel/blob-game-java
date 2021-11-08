package ui.game;

import model.BlobGame;
import ui.BlobEatBlob;
import ui.Screen;
import ui.menu.MainMenuScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Represents the game screen
public class GameScreen extends Screen {
    public static final int RIGHT_WIDTH = 800;
    public static final int LEFT_WIDTH = Screen.WIDTH - RIGHT_WIDTH;
    public static final int INTERVAL = 10;

    BlobEatBlob beb;
    BlobGame bg;
    JPanel mainInfoPanel;
    JPanel mainGamePanel;
    PlayerInfoPanel pip;
    Timer timer;

    // Constructs a game screen
    public GameScreen(BlobEatBlob beb) {
        super();
        setLayout(new BorderLayout());
        this.beb = beb;
        this.bg = beb.getBlobGame();
        mainInfoPanel = new JPanel();
        mainGamePanel = new JPanel();
        addKeyListener(new KeyHandler());
        drawGame();
        addTimer();
    }

    // MODIFIES: this
    // EFFECTS:  draws the game
    private void drawGame() {
        addMainInfoPanel();
        addMainGamePanel();
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(INTERVAL, ae -> {
            bg.update();
            pip.update();
            mainGamePanel.repaint();
            mainInfoPanel.repaint();

            if (bg.isGameOver()) {
                timer.stop();
                beb.nextScreen(new GameOverScreen(beb));
            }

            if (bg.isWin()) {
                timer.stop();
                beb.nextScreen(new VictoryScreen(beb));
            }
        });

        timer.start();
    }

    // MODIFIES: this
    // EFFECTS:  adds main info panel
    private void addMainInfoPanel() {
        mainInfoPanel.setPreferredSize(new Dimension(LEFT_WIDTH, Screen.HEIGHT));
        mainInfoPanel.setBackground(BACKGROUND_COLOR);
        mainInfoPanel.setLayout(new BoxLayout(mainInfoPanel, BoxLayout.PAGE_AXIS));
        mainInfoPanel.setVisible(true);
        add(mainInfoPanel, BorderLayout.WEST);

        pip = new PlayerInfoPanel(bg);
        pip.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainInfoPanel.add(pip);

        AbilitiesPanel ap = new AbilitiesPanel(bg);
        ap.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainInfoPanel.add(ap);
    }

    // MODIFIES: this
    // EFFECTS:  adds main game panel
    private void addMainGamePanel() {
        mainGamePanel.setPreferredSize(new Dimension(RIGHT_WIDTH, Screen.HEIGHT));
        mainGamePanel.setBackground(BACKGROUND_COLOR);
        mainGamePanel.setLayout(new BoxLayout(mainGamePanel, BoxLayout.PAGE_AXIS));
        mainGamePanel.setVisible(true);
        add(mainGamePanel, BorderLayout.EAST);

        GamePanel gp = new GamePanel(bg);
        gp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainGamePanel.add(gp);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_SPACE) {
                // useAbility();
            } else if (keyCode == KeyEvent.VK_M) {
                timer.stop();
                beb.nextScreen(new MainMenuScreen(beb));
            } else {
                bg.blobControl(keyCode);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            bg.blobStop(e.getKeyCode());
        }
    }
}
