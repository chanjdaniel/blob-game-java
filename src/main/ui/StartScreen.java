package ui;

import model.Blob;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents the start screen
public class StartScreen extends Screen {
    private BlobEatBlob beb;

    // Constructs a start screen
    public StartScreen(BlobEatBlob beb) {
        super();
        this.beb = beb;
        drawScreen();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        addTitleArt(g);
    }

    // MODIFIES: this
    // EFFECTS:  adds title art
    private void addTitleArt(Graphics g) {
        int size = 200;
        int centreX = CENTRE_WIDTH - size / 2;
        int centreY = CENTRE_HEIGHT - size / 2;
        Color blobColor = Color.CYAN;
        Blob blob = new Blob("", size, centreX, centreY, blobColor);
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }

    // Draws the screen
    // MODIFIES: this
    // EFFECTS:  adds the components
    private void drawScreen() {
        addButtons();
        addTitle();
    }

    // MODIFIES: this
    // EFFECTS:  adds buttons
    private void addButtons() {
        int width = 150;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetX = 150;
        int offSetY = 200;
        JButton newGame = new JButton(new NewGameAction());
        newGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newGame.setBounds(centreX - offSetX,centreY + offSetY,width,height);
        JButton loadGame = new JButton(new LoadGameAction());
        loadGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loadGame.setBounds(centreX + offSetX,centreY + offSetY,width,height);

        add(newGame);
        add(loadGame);
    }

    // Represents action taken when user clicks on the "New Game" button
    // replaces current screen with NewGameScreen
    private class NewGameAction extends AbstractAction {

        NewGameAction() {
            super("New Game");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            beb.nextScreen(new NewGameScreen(beb));
        }
    }

    // Represents action taken when user clicks on the "Load Game" button
    // replaces current screen with LoadGameScreen
    private class LoadGameAction extends AbstractAction {

        LoadGameAction() {
            super("Load Game");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            beb.nextScreen(new LoadGameScreen(beb));
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds title label
    private void addTitle() {
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
}
