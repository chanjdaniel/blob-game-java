package ui.menu;

import model.Blob;
import ui.BlobEatBlob;
import ui.BlobRenderer;
import ui.Screen;
import ui.game.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents the main menu screen
public class MainMenuScreen extends Screen {
    private static final int MENU_ALIGN_X = 150;
    private static final int BLOB_ALIGN_X = 300;
    
    private BlobEatBlob beb;
    private Blob playerBlob;

    // Constructs a main menu screen
    public MainMenuScreen(BlobEatBlob beb) {
        super();
        this.beb = beb;
        playerBlob = beb.getPlayerBlob();
        drawScreen();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Blob render
        addBlob(g);
    }

    // MODIFIES: this
    // EFFECTS:  adds blob art
    private void addBlob(Graphics g) {
        int size = 200;
        int offSetY = 100;
        Color blobColor = playerBlob.getColor();
        Blob blob = new Blob("", size, 0, CENTRE_WIDTH - BLOB_ALIGN_X,
                CENTRE_HEIGHT - offSetY, blobColor);
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }

    // Draws the screen
    // MODIFIES: this
    // EFFECTS:  draws the components
    private void drawScreen() {
        addNameLabel();
        addSizeLabel();
        addKillsLabel();
        addMainMenuLabel();
        addVictimsButton();
        addButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS:  adds name label
    private void addNameLabel() {
        String name = playerBlob.getName();
        int width = 300;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 250;
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        label.setFont(font);
        label.setBounds(centreX - BLOB_ALIGN_X, centreY - offSetY, width, height);
        add(label);
    }

    // MODIFIES: this
    // EFFECTS:  adds size label
    private void addSizeLabel() {
        String text = "Size: " + playerBlob.getSize();
        int width = 200;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 50;
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        Font font = new Font(Font.SERIF, Font.BOLD, 25);
        label.setFont(font);
        label.setBounds(centreX - BLOB_ALIGN_X, centreY + offSetY, width, height);
        add(label);
    }

    // MODIFIES: this
    // EFFECTS:  adds kills label
    private void addKillsLabel() {
        String text = "Kills: " + playerBlob.getVictims().size();
        int width = 200;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 100;
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        Font font = new Font(Font.SERIF, Font.BOLD, 25);
        label.setFont(font);
        label.setBounds(centreX - BLOB_ALIGN_X, centreY + offSetY, width, height);
        add(label);
    }

    // MODIFIES: this
    // EFFECTS:  adds main menu label
    private void addMainMenuLabel() {
        int width = 300;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 250;
        JLabel label = new JLabel("Main Menu", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        label.setFont(font);
        label.setBounds(centreX + MENU_ALIGN_X, centreY - offSetY, width, height);
        add(label);
    }

    // MODIFIES: this
    // EFFECTS:  adds victims button
    private void addVictimsButton() {
        int width = 200;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 175;
        JButton victimsButton = new JButton(new VictimsAction());
        victimsButton.setBounds(centreX - BLOB_ALIGN_X, centreY + offSetY, width, height);
        victimsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(victimsButton);
    }

    // MODIFIES: this
    // EFFECTS:  adds button panel
    private void addButtonPanel() {
        int width = 300;
        int height = 400;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 0;

        JButton continueButton = new JButton(new ContinueAction());
        JButton saveButton = new JButton(new SaveAction());
        JButton quitButton = new JButton(new QuitAction());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,2));
        buttonPanel.add(continueButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);
        buttonPanel.setBounds(centreX + MENU_ALIGN_X, centreY - offSetY, width, height);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        add(buttonPanel);
    }

    // Represents action taken when user clicks on the "Victims" button
    // replaces current screen with VictimsScreen
    private class VictimsAction extends AbstractAction {

        VictimsAction() {
            super("Victims");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            beb.nextScreen(new VictimsScreen(beb));
        }
    }

    // Represents action taken when user clicks on the "Continue" button
    // replaces current screen with GameScreen
    private class ContinueAction extends AbstractAction {

        ContinueAction() {
            super("Continue");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            GameScreen game = new GameScreen(beb);
            beb.nextScreen(game);
            game.requestFocusInWindow();
        }
    }

    // Represents action taken when user clicks on the "Save" button
    // replaces current screen with SaveGameScreen
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            beb.nextScreen(new SaveGameScreen(beb, ""));
        }
    }

    // Represents action taken when user clicks on the "Quit" button
    // replaces current screen with QuitScreen
    private class QuitAction extends AbstractAction {

        QuitAction() {
            super("Quit");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            beb.nextScreen(new QuitScreen(beb));
        }
    }
}
