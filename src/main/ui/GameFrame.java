package ui;

import ui.Screen;

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
//        Container pane = getContentPane();
//        pane.removeAll();
//        pane.add(screen);
//        pack();
//        pane.revalidate();
//        setVisible(true);

        setContentPane(screen);
        pack();
        setVisible(true);
    }
}
