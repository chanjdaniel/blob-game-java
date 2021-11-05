package ui;

import java.awt.*;

public class StartScreen extends Screen {

    // Constructs a start panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public StartScreen() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawMenu(g);
    }

    // Draws the game
    // modifies: g
    // effects:  draws the game onto g
    private void drawMenu(Graphics g) {
        drawButtons(g);
        drawTitle(g);
        drawTitleArt(g);
    }

    private void drawButtons(Graphics g) {

    }
}
