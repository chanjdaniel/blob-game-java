package ui;

import model.BGame;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    private static final Color MENU_COLOR = Color.GRAY;

    // Constructs a start panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public StartPanel() {
        setPreferredSize(new Dimension(BGame.WIDTH, BGame.HEIGHT));
        setBackground(MENU_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isOver()) {
            gameOver(g);
        }
    }

    // Draws the game
    // modifies: g
    // effects:  draws the game onto g
    private void drawMenu(Graphics g) {
        drawButtons(g);
        drawEnemyBlobs(g);
        drawPowerUps(g);
    }

    // Draws the "game over" message and replay instructions
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color( 0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, SIGame.HEIGHT / 2);
        centreString(REPLAY, g, fm, SIGame.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (SIGame.WIDTH - width) / 2, yPos);
    }
}
