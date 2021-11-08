package ui.menu;

import model.Blob;
import persistence.JsonWriter;
import ui.BlobEatBlob;
import ui.BlobRenderer;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;

import static javax.swing.SwingConstants.*;

// Represents the save game screen
public class SaveGameScreen extends Screen {
    private BlobEatBlob beb;
    private String status;
    private JTextField textField;
    private JLabel label;

    // Constructs a save screen
    public SaveGameScreen(BlobEatBlob beb, String status) {
        super();
        this.beb = beb;
        this.status = status;
        drawScreen();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Blob art
        addBlobArt(g);
        addBlobSpeech(g);
    }

    // MODIFIES: this
    // EFFECTS:  adds the blob art
    private void addBlobArt(Graphics g) {
        int size = 100;
        Color blobColor = Color.CYAN;
        Blob blob = new Blob("", size, 0, CENTRE_WIDTH, CENTRE_HEIGHT, blobColor);
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }

    // MODIFIES: this
    // EFFECTS:  adds blob speech art
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
    // EFFECTS:  draws the components
    private void drawScreen() {
        addTitleLabel();
        addSaveNameLabel();
        addSaveNameField();
        addEnterButton();
    }

    // MODIFIES: this
    // EFFECTS:  adds title label
    private void addTitleLabel() {
        String message = status.equals("quit") ? (message = "See you soon!") : (message = "Save your game!");
        int width = WIDTH;
        int height = 100;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 150;
        JLabel text = new JLabel(message, CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 60);
        text.setFont(font);
        text.setBounds(centreX, centreY - offSetY, width, height);
        add(text);
    }

    // MODIFIES: this
    // EFFECTS:  adds save name label
    private void addSaveNameLabel() {
        int width = WIDTH;
        int height = 100;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 170;
        label = new JLabel("Enter a save name...", CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 20);
        label.setFont(font);
        label.setBounds(centreX, centreY + offSetY, width, height);
        add(label);
    }

    // MODIFIES: this
    // EFFECTS:  adds save name field
    private void addSaveNameField() {
        int width = 300;
        int height = 30;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 200;
        textField = new JTextField(20);
        textField.setBounds(centreX, centreY + offSetY, width, height);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textField.setHorizontalAlignment(JTextField.CENTER);
        add(textField);
    }

    // MODIFIES: this
    // EFFECTS:  adds enter button
    private void addEnterButton() {
        int width = 100;
        int height = 25;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetX = 225;
        int offSetY = 200;
        JButton enterButton = new JButton(new EnterAction());
        enterButton.setBounds(centreX + offSetX, centreY + offSetY, width, height);

        add(enterButton);
    }

    // Represents action taken when user clicks on the "Enter" button
    // attempts to save to file with entered save name;
    // exits program if status is "quit", else replaces current screen with MainGameScreen
    private class EnterAction extends AbstractAction {

        EnterAction() {
            super("Enter");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String jsonStore = generateJsonStore(textField.getText());
            try {
                saveBlobGame(jsonStore);
                if (status.equals("quit")) {
                    System.exit(0);
                } else {
                    beb.nextScreen(new MainMenuScreen(beb));
                }
            } catch (FileNotFoundException ex) {
                label.setText("Unable to save...");
            }
        }
    }

    // EFFECTS: returns a file path for a JSONStore from jsonStoreName
    private String generateJsonStore(String jsonStoreName) {
        return "./data/saves/" + jsonStoreName + ".json";
    }

    // EFFECTS: saves the blob game to file with jsonStore
    private void saveBlobGame(String jsonStore) throws FileNotFoundException {
        JsonWriter jsonWriter = new JsonWriter(jsonStore);
        jsonWriter.open();
        jsonWriter.write(beb.getBlobGame());
        jsonWriter.close();
    }
}
