package ui;

import exceptions.InvalidInputException;
import model.Blob;
import model.BlobGame;

import javax.swing.*;
import java.awt.*;

public class NewGameScreen extends Screen {

    private static final int X_OFFSET = 350;

    private BlobEatBlob beb;
    private Blob blob = new Blob("",15,Color.WHITE); // default blob
    private String playerName;
    private Color playerColor;

    // Constructs a new game screen
    // effects: sets size and background colour of panel
    public NewGameScreen(BlobEatBlob beb) {
        super();
        this.beb = beb;
        drawScreen();
    }

    private void drawScreen() {
        drawNamer();
        drawColorPicker();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBlobRenderer(g);
    }

    private void drawNamer() {
        drawNameText();
        drawTextField();
    }

    private void drawNameText() {
        int width = 300;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 250;
        JLabel text = new JLabel("Name your blob!", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        text.setFont(font);
        text.setBounds(centreX - X_OFFSET,centreY - offSetY,width,height);
        add(text);
    }

    private void drawTextField() {
        int width = 200;
        int height = 30;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 200;
        JTextField field = new JTextField(20);
        field.setBounds(centreX - X_OFFSET,centreY - offSetY,width,height);
        field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        field.setHorizontalAlignment(JTextField.CENTER);
        add(field);
        playerName = field.getText();
    }

    private void drawColorPicker() {
        drawColorText();
        drawPicker();
    }

    private void drawColorText() {
        int width = 300;
        int height = 50;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 75;
        JLabel text = new JLabel("Pick a colour!", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        text.setFont(font);
        text.setBounds(centreX - X_OFFSET,centreY - offSetY,width,height);
        add(text);
    }

    private void drawPicker() {
        int width = 300;
        int height = 300;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 100;
        JColorChooser picker = new JColorChooser();
        picker.setBounds(centreX - X_OFFSET,centreY + offSetY,width,height);
        add(picker);
        playerColor = picker.getColor();
    }

    private void blobMaker() {
        BlobGame bg = null;
        try {
            bg = new BlobGame(playerName,playerColor);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        beb.setBlobGame(bg);
    }

    private void drawBlobRenderer(Graphics g) {
        drawBlobText();
        drawBlob(g);
       // drawBlobOutline(g);
    }

    private void drawBlobText() {
    }

    private void drawBlob(Graphics g) {
        int width = 200;
        int height = 200;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 100;
        g.setColor(this.blob.getColor());
        g.fillRect(centreX,centreY,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(centreX,centreY,width,height); // make thicker
    }
}
