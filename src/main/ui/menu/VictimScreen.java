package ui.menu;

import model.Blob;
import ui.BlobEatBlob;
import ui.BlobRenderer;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents a victim screen
public class VictimScreen extends Screen {

    private BlobEatBlob beb;
    private Blob victim;

    // Constructs a victim screen for victim
    public VictimScreen(BlobEatBlob beb, Blob victim) {
        super();
        this.beb = beb;
        this.victim = victim;
        setLayout(null);
        drawScreen();
    }

    // MODIFIES: this
    // EFFECTS:  draws the screen
    private void drawScreen() {
        addVictimNameLabel();
        addBackButton();
        addVictimSizeLabel();
        addVictimKillsLabel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        addVictimArt(g);
    }

    // MODIFIES: this
    // EFFECTS:  adds title art
    private void addVictimArt(Graphics g) {
        int size = 200;
        Color blobColor = victim.getColor();
        Blob blob = new Blob("", size, 0, CENTRE_WIDTH, CENTRE_HEIGHT, blobColor);
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }

    // MODIFIES: this
    // EFFECTS:  adds victim name label
    private void addVictimNameLabel() {
        int width = WIDTH;
        int height = 100;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 150;
        JLabel text = new JLabel(victim.getName(), SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 60);
        text.setFont(font);
        text.setBounds(centreX, centreY - offSetY, width, height);
        add(text);
    }

    // MODIFIES: this
    // EFFECTS:  adds the confirm button
    private void addBackButton() {
        int width = 125;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 225;
        int offSetX = 400;
        JButton backButton = new JButton(new BackAction());
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backButton.setBounds(centreX - offSetX, centreY + offSetY, width, height);

        add(backButton);
    }

    // Represents action taken when user clicks on the "Back" button
    // replaces current screen with MenuScreen
    private class BackAction extends AbstractAction {

        BackAction() {
            super("Back");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            beb.nextScreen(new VictimsScreen(beb));
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds victim size label
    private void addVictimSizeLabel() {
        String text = "Size: " + victim.getSize();
        int width = 200;
        int height = 50;
        int centreX = WIDTH / 2 - width / 2;
        int centreY = HEIGHT / 2 - height / 2;
        int offSetY = 150;
        int offSetX = 50;
        JLabel sizeLabel = new JLabel(text, SwingConstants.LEFT);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        sizeLabel.setFont(font);
        sizeLabel.setBounds(centreX + offSetX, centreY + offSetY, width, height);
        add(sizeLabel);
    }

    // MODIFIES: this
    // EFFECTS:  adds victim kills label
    private void addVictimKillsLabel() {
        String text = "Kills: " + victim.getVictims().size();
        int width = 200;
        int height = 50;
        int centreX = WIDTH / 2 - width / 2;
        int centreY = WIDTH / 2 - height / 2;
        int offSetY = 0;
        int offSetX = 50;
        JLabel killsLabel = new JLabel(text, SwingConstants.LEFT);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        killsLabel.setFont(font);
        killsLabel.setBounds(centreX + offSetX, centreY + offSetY, width, height);
        add(killsLabel);
    }
}
