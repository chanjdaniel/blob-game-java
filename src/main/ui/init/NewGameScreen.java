package ui.init;

import exceptions.InvalidInputException;
import model.Blob;
import model.BlobGame;
import model.Event;
import model.EventLog;
import ui.BlobEatBlob;
import ui.BlobRenderer;
import ui.Screen;
import ui.menu.MainMenuScreen;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents the new game screen
public class NewGameScreen extends Screen {
    private static final int X_OFFSET_LEFT = 200;
    private static final int X_OFFSET_RIGHT = 280;

    private BlobEatBlob beb;
    private final JTextField textField;
    private JLabel blobNameLabel;
    private final JColorChooser colorChooser;

    // Constructs a new game screen
    public NewGameScreen(BlobEatBlob beb) {
        super();
        this.beb = beb;
        DocumentHandler documentHandler = new DocumentHandler();
        textField = new JTextField("", 20);
        textField.getDocument().addDocumentListener(documentHandler);

        ChangeHandler changeHandler = new ChangeHandler();
        colorChooser = new JColorChooser(Color.CYAN);
        colorChooser.getSelectionModel().addChangeListener(changeHandler);
        drawScreen();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Blob preview
        addBlob(g);
        addBlobLabel();
    }

    // MODIFIES: this
    // EFFECTS:  adds the blob preview
    private void addBlob(Graphics g) {
        int size = 200;
        int offSetY = 50;
        Color blobColor = colorChooser.getColor();
        Blob blob = new Blob("", size, 0, CENTRE_WIDTH + X_OFFSET_RIGHT,
                CENTRE_HEIGHT - offSetY, blobColor);
        BlobRenderer renderer = new BlobRenderer();

        renderer.renderBlob(g, blob);
    }

    // MODIFIES: this
    // EFFECTS:  draws the components
    private void drawScreen() {
        // Name text field
        addNameFieldLabel();
        addTextField();

        // Color chooser
        addColorLabel();
        addColorChooser();

        // Confirm button
        addConfirmButton();
    }

    // MODIFIES: this
    // EFFECTS:  adds the name field label
    private void addNameFieldLabel() {
        int width = 300;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 250;
        JLabel text = new JLabel("Name your blob!", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        text.setFont(font);
        text.setBounds(centreX - X_OFFSET_LEFT, centreY - offSetY, width, height);
        add(text);
    }

    // MODIFIES: this
    // EFFECTS:  adds the text field
    private void addTextField() {
        int width = 200;
        int height = 30;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 200;
        textField.setBounds(centreX - X_OFFSET_LEFT, centreY - offSetY, width, height);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textField.setHorizontalAlignment(JTextField.CENTER);
        add(textField);
    }

    // MODIFIES: this
    // EFFECTS:  adds the color label
    private void addColorLabel() {
        int width = 300;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 75;
        JLabel text = new JLabel("Pick a colour!", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        text.setFont(font);
        text.setBounds(centreX - X_OFFSET_LEFT, centreY - offSetY, width, height);
        add(text);
    }

    // MODIFIES: this
    // EFFECTS:  adds the color chooser
    private void addColorChooser() {
        int width = 500;
        int height = 300;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 100;
        colorChooser.setBounds(centreX - X_OFFSET_LEFT, centreY + offSetY, width, height);
        add(colorChooser);
    }

    // MODIFIES: this
    // EFFECTS:  adds the blob name preview
    private void addBlobLabel() {
        if (blobNameLabel != null) {
            remove(blobNameLabel);
        }
        String name = textField.getText();
        int width = 300;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 200;
        blobNameLabel = new JLabel(name, SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        blobNameLabel.setFont(font);
        blobNameLabel.setBounds(centreX + X_OFFSET_RIGHT, centreY - offSetY, width, height);
        add(blobNameLabel);
    }

    // MODIFIES: this
    // EFFECTS:  adds the confirm button
    private void addConfirmButton() {
        int width = 125;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 175;
        JButton confirmButton = new JButton(new ConfirmAction());
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        confirmButton.setBounds(centreX + X_OFFSET_RIGHT, centreY + offSetY, width, height);

        add(confirmButton);
    }

    // Represents action taken when user clicks on the "Confirm" button
    // creates blobGame with textField.getText() and colorChooser.getColor()
    // replaces current screen with MenuScreen
    private class ConfirmAction extends AbstractAction {

        ConfirmAction() {
            super("Confirm");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            blobGameMaker();
            beb.nextScreen(new MainMenuScreen(beb));
        }
    }

    // MODIFIES: this
    // EFFECTS:  repaints the component
    private void redraw() {
        this.repaint();
    }

    // if detects change in picker, repaints the component
    private class ChangeHandler implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            redraw();
        }
    }

    // if detects change in text field, repaints the component
    private class DocumentHandler implements SimpleDocumentListener {
        @Override
        public void update(DocumentEvent e) {
            redraw();
        }
    }

    // simplify DocumentLister
    // source: https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
    public interface SimpleDocumentListener extends DocumentListener {
        void update(DocumentEvent e);

        @Override
        default void insertUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        default void removeUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        default void changedUpdate(DocumentEvent e) {
            update(e);
        }
    }

    // MODIFIES: beb
    // EFFECTS:  creates a new blob game using playerName and playColor as entered by user
    private void blobGameMaker() {
        String playerName = textField.getText();
        Color playerColor = colorChooser.getColor();

        BlobGame bg = null;
        try {
            bg = new BlobGame(playerName, playerColor);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }

        beb.setBlobGame(bg);
    }
}
