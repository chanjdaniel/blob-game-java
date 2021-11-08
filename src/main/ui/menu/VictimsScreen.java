package ui.menu;

import ui.BlobEatBlob;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents a victims screen
public class VictimsScreen extends Screen {
    private BlobEatBlob beb;

    // Constructs a victims screen
    public VictimsScreen(BlobEatBlob beb) {
        super();
        this.beb = beb;
        setLayout(null);
        drawScreen();
    }

    private void drawScreen() {
        addTitleLabel();
        addBackButton();
        addVictimsPanel(beb);
    }

    // MODIFIES: this
    // EFFECTS:  adds the title label
    private void addTitleLabel() {
        int width = WIDTH;
        int height = 100;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        int offSetY = 250;
        JLabel text = new JLabel("Look at what you've done...", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 60);
        text.setFont(font);
        text.setBounds(centreX,centreY - offSetY,width,height);
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
            beb.nextScreen(new MainMenuScreen(beb));
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds the victims panel
    private void addVictimsPanel(BlobEatBlob beb) {
        VictimsPanel vp = new VictimsPanel(beb);
        int width = 400;
        int height = 400;
        int centreX = CENTRE_WIDTH - width / 2;
        int centreY = CENTRE_HEIGHT - height / 2;
        JScrollPane jsp = new JScrollPane(vp);
        jsp.setLocation(centreX, centreY);
        jsp.setSize(width, height);
        jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(jsp);
    }
}
