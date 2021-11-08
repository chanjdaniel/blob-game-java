package ui.menu;

import model.Blob;
import ui.BlobEatBlob;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents a victims panel
public class VictimsPanel extends JPanel {

    private BlobEatBlob beb;
    private Blob playerBlob;

    // Constructs a victims panel
    public VictimsPanel(BlobEatBlob beb) {
        this.beb = beb;
        this.playerBlob = beb.getBlobGame().getPlayerBlob();
        setLayout(new GridLayout(0,2));
        addVictimButtons();
    }

    // MODIFIES: this
    // EFFECTS:  adds victim buttons
    private void addVictimButtons() {
        for (Blob next : playerBlob.getVictims()) {
            addVictimButton(next);
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds a victim button for each victim
    private void addVictimButton(Blob next) {
        JButton button = new JButton(new ButtonAction(next));
        add(button);
    }

    // Represents action taken when user clicks on the victim name button
    // replaces current screen with VictimScreen
    private class ButtonAction extends AbstractAction {
        Blob victim;

        ButtonAction(Blob victim) {
            super(victim.getName());
            this.victim = victim;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            beb.nextScreen(new VictimScreen(beb, victim));
        }
    }
}
