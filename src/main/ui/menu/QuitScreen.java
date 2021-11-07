package ui.menu;

import model.Blob;
import ui.BlobEatBlob;
import ui.BlobRenderer;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;

// Represents the quit screen
public class QuitScreen extends Screen {
    private BlobEatBlob beb;

    // Constructs a start screen
    public QuitScreen(BlobEatBlob beb) {
        super();
        this.beb = beb;
        drawScreen();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        addTitleArt(g);
        addBlobSpeech(g);
    }

    // MODIFIES: this
    // EFFECTS:  adds title art
    private void addTitleArt(Graphics g) {
        int size = 100;
        int centreX = CENTRE_WIDTH - size / 2;
        int centreY = CENTRE_HEIGHT - size / 2;
        Color blobColor = Color.CYAN;
        Blob blob = new Blob("", size, centreX, centreY, blobColor);
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }

    // MODIFIES: this
    // EFFECTS:  adds blob speech lines
    private void addBlobSpeech(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        float offSetX = 20;
        float upperY = CENTRE_HEIGHT - 100;
        float lowerY = CENTRE_HEIGHT - 75;
        float leftX = CENTRE_WIDTH - offSetX;
        float rightX = CENTRE_WIDTH + offSetX;

        Line2D left = new Line2D.Float(CENTRE_WIDTH, lowerY, leftX, upperY);
        Line2D right = new Line2D.Float(CENTRE_WIDTH, lowerY, rightX, upperY);

        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        g2.draw(left);
        g2.draw(right);
    }

    // Draws the screen
    // MODIFIES: this
    // EFFECTS:  adds the components
    private void drawScreen() {
        addTitleLabel();
        addButtons();
    }

    // MODIFIES: this
    // EFFECTS:  adds title label
    private void addTitleLabel() {
        int width = WIDTH;
        int height = 100;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 150;
        JLabel text = new JLabel("Save your game?", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 60);
        text.setFont(font);
        text.setBounds(centreX, centreY - offSetY, width, height);
        add(text);
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
        JButton yes = new JButton(new YesAction());
        yes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        yes.setBounds(centreX - offSetX,centreY + offSetY,width,height);
        JButton no = new JButton(new NoAction());
        no.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        no.setBounds(centreX + offSetX,centreY + offSetY,width,height);

        add(yes);
        add(no);
    }

    // Represents action taken when user clicks on the "Yes" button
    // replaces current screen with SaveGameScreen with "quit" status
    private class YesAction extends AbstractAction {

        YesAction() {
            super("Yes");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            beb.nextScreen(new SaveGameScreen(beb, "quit"));
        }
    }

    // Represents action taken when user clicks on the "No" button
    // exits the game
    private class NoAction extends AbstractAction {

        NoAction() {
            super("No");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
