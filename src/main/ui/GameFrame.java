package ui;

import javax.swing.*;
import java.awt.*;

// Represents the frame the game will be contained in
public class GameFrame extends JFrame {

    public GameFrame(String title) {
        super(title);
        setPreferredSize(new Dimension(Screen.WIDTH, Screen.HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
    }

    // MODIFIES: this
    // EFFECTS: sets the content pane to screen
    public void nextScreen(Screen screen) {
        setContentPane(screen);
        pack();
        setVisible(true);
    }
}
