package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {

    public GameFrame(String title) {
        super(title);
        setPreferredSize(new Dimension(Screen.WIDTH, Screen.HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
    }

    public void nextScreen(Screen screen) {
        Container pane = getContentPane();
        pane.removeAll();
        pane.add(screen);
        pack();
        setVisible(true);
        pane.revalidate();
    }
}
