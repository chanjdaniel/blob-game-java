package ui.menu;

import model.Blob;
import ui.BlobEatBlob;
import ui.BlobRenderer;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenuScreen extends Screen {
    private static final int MENU_ALIGN_X = 150;
    private static final int BLOB_ALIGN_X = 300;
    
    private BlobEatBlob beb;
    private Blob playerBlob;

    // Represents the main menu screen
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

    private void addBlob(Graphics g) {
        int size = 200;
        int centreX = CENTRE_WIDTH - size / 2;
        int centreY = CENTRE_HEIGHT - size / 2;
        int offSetY = 100;
        Color blobColor = playerBlob.getColor();
        Blob blob = new Blob("", size, centreX - BLOB_ALIGN_X, centreY - offSetY, blobColor);
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

    private void addButtonPanel() {
        int width = 300;
        int height = 400;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 0;

        JButton continueButton = new JButton(new ContinueAction());
        JButton saveButton = new JButton(new SaveAction());
        JButton helpButton = new JButton(new HelpAction());
        JButton quitButton = new JButton(new QuitAction());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,2));
        buttonPanel.add(continueButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(quitButton);
        buttonPanel.setBounds(centreX + MENU_ALIGN_X, centreY - offSetY, width, height);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        add(buttonPanel);
    }

    private class VictimsAction extends AbstractAction {

        VictimsAction() {
            super("Victims");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            //
        }
    }

    private class ContinueAction extends AbstractAction {

        ContinueAction() {
            super("Continue");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            //
        }
    }

    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            beb.nextScreen(new SaveGameScreen(beb, ""));
        }
    }

    private class HelpAction extends AbstractAction {

        HelpAction() {
            super("Help");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            //
        }
    }

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
