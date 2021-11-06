package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoadGameScreen extends Screen {
    private BlobEatBlob beb;
    private String saveName;

    // Constructs a load screen
    // effects: sets size and background colour of panel
    public LoadGameScreen(BlobEatBlob beb) {
        super();
        this.beb = beb;
        drawScreen();
    }

    // Draws the screen
    // modifies: this, gameFrame
    // effects:  draws the components
    private void drawScreen() {
        drawArt();
        drawLoader();
    }

    private void drawArt() {
    }

    private void drawLoader() {
        drawText();
        drawSaveNameField();
        drawEnterButton();
    }

    private void drawText() {
        int width = WIDTH;
        int height = 100;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 200;
        JLabel text = new JLabel("Enter your save name!", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 60);
        text.setFont(font);
        text.setBounds(centreX,centreY - offSetY,width,height);
        add(text);
    }

    private void drawSaveNameField() {
        int width = 300;
        int height = 30;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 200;
        JTextField field = new JTextField(20);
        field.setBounds(centreX,centreY + offSetY,width,height);
        field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        field.setHorizontalAlignment(JTextField.CENTER);
        add(field);
        saveName = field.getText();
    }

    private void drawEnterButton() {
        int width = 100;
        int height = 25;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetX = 225;
        int offSetY = 200;
        JButton enterButton = new JButton(new EnterAction());
        enterButton.setBounds(centreX + offSetX,centreY + offSetY,width,height);

        add(enterButton);
    }

    private class EnterAction extends AbstractAction {

        EnterAction() {
            super("Enter");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //beb.setBGame(loadedGame);
        }
    }
}
