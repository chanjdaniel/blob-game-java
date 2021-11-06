package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartScreen extends Screen {
    private BlobEatBlob beb;

    // Constructs a start screen
    // effects: sets size and background colour of panel
    public StartScreen(BlobEatBlob beb) {
        super();
        this.beb = beb;
        drawScreen();
    }

    // Draws the screen
    // modifies: this, gameFrame
    // effects:  draws the components
    private void drawScreen() {
        drawButtons();
        drawTitle();
        drawTitleArt();
    }

    private void drawButtons() {
        int width = 150;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetX = 150;
        int offSetY = 200;
        JButton newGame = new JButton(new NewGameAction());
        newGame.setBounds(centreX - offSetX,centreY + offSetY,width,height);
        JButton loadGame = new JButton(new LoadGameAction());
        loadGame.setBounds(centreX + offSetX,centreY + offSetY,width,height);

        add(newGame);
        add(loadGame);
    }

    private class NewGameAction extends AbstractAction {

        NewGameAction() {
            super("New Game");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            NewGameScreen ngs = new NewGameScreen(beb);
            beb.nextScreen(ngs);
        }
    }

    private class LoadGameAction extends AbstractAction {

        LoadGameAction() {
            super("Load Game");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LoadGameScreen lgs = new LoadGameScreen(beb);
            beb.nextScreen(lgs);
        }
    }

    private void drawTitle() {
        int width = WIDTH;
        int height = 100;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 200;
        JLabel text = new JLabel("BLOB   eat   BLOB", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 60);
        text.setFont(font);
        text.setBounds(centreX,centreY - offSetY,width,height);
        add(text);
    }

    private void drawTitleArt() {
    }
}
