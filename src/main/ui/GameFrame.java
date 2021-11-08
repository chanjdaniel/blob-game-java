package ui;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame(String title) {
        super(title);
        setPreferredSize(new Dimension(Screen.WIDTH, Screen.HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
    }

    public void nextScreen(Screen screen) {
        setContentPane(screen);
        pack();
        setVisible(true);
    }
}
