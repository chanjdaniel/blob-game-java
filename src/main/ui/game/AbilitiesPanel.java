package ui.game;

import model.Ability;
import model.BlobGame;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AbilitiesPanel extends JPanel {
    private static final int WIDTH = GameScreen.LEFT_WIDTH;
    private static final int HEIGHT = Screen.HEIGHT / 2;

    BlobGame bg;
    ArrayList<Ability> abilities;
    JPanel abilityViewer;

    public AbilitiesPanel(BlobGame bg) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setVisible(true);
        this.bg = bg;
        this.abilities = bg.getPlayerBlob().getAbilities();
        drawPanel();
    }

    private void drawPanel() {
        addTitleLabel();
        addInfoLabel();
        addAbilityViewer();
    }

    private void addTitleLabel() {
        int width = 200;
        int height = 50;
        int centreX = WIDTH / 2 - width / 2;
        int centreY = HEIGHT / 2 - height / 2;
        int offSetY = 125;
        JLabel label = new JLabel("Abilities", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        label.setFont(font);
        label.setBounds(centreX, centreY - offSetY, width, height);
        add(label);
    }

    private void addInfoLabel() {
        int width = 200;
        int height = 50;
        int centreX = WIDTH / 2 - width / 2;
        int centreY = HEIGHT / 2 - height / 2;
        int offSetY = 100;
        JLabel label = new JLabel("press space to use next ability", SwingConstants.CENTER);
        Font font = new Font(Font.SERIF, Font.BOLD, 15);
        label.setFont(font);
        label.setBounds(centreX, centreY - offSetY, width, height);
        add(label);
    }

    private void addAbilityViewer() {
        int width = WIDTH - 10;
        int height = 225;
        int centreX = WIDTH / 2 - width / 2;
        abilityViewer = new JPanel();
        abilityViewer.setSize(new Dimension(width, height));
        abilityViewer.setLocation(centreX, HEIGHT - height);
        abilityViewer.setBackground(Color.WHITE);
        abilityViewer.setLayout(new BoxLayout(abilityViewer,BoxLayout.PAGE_AXIS));
        addAbilityLabels();
        abilityViewer.setVisible(true);
        add(abilityViewer);
    }

    private void addAbilityLabels() {
        for (Ability next : abilities) {
            JLabel abilityNameLabel = new JLabel(next.getName(), SwingConstants.CENTER);
            Font abilityNameFont = new Font(Font.SERIF, Font.BOLD, 20);
            abilityNameLabel.setFont(abilityNameFont);
            abilityViewer.add(abilityNameLabel);

            JLabel abilityDescLabel = new JLabel(next.getDescription(), SwingConstants.CENTER);
            Font abilityDescFont = new Font(Font.SERIF, Font.BOLD, 12);
            abilityDescLabel.setFont(abilityDescFont);
            abilityViewer.add(abilityDescLabel);

            JLabel blank = new JLabel(" ", SwingConstants.CENTER);
            Font blankFont = new Font(Font.SERIF, Font.BOLD, 8);
            blank.setFont(blankFont);
            abilityViewer.add(blank);
        }
    }

    public void update() {
        abilityViewer.removeAll();
        abilityViewer.revalidate();
        addAbilityLabels();
    }
}
