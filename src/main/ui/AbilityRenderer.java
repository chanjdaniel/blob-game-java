package ui;

import model.Ability;

import java.awt.*;
import java.awt.geom.Rectangle2D;


// Renderer for abilities
public class AbilityRenderer {

    public AbilityRenderer() {
    }

    // MODIFIES: g
    // EFFECTS: draws ability square onto g
    public void renderAbility(Graphics g, Ability ability) {
        int size = 10;
        double x = ability.getPositionX() - size / 2.0;
        double y = ability.getPositionY() - size / 2.0;
        Color color = Color.RED;
        renderAbility(g, size, x, y, color);
    }

    // MODIFIES: g
    // EFFECTS: draws ability outline onto g
    private void renderAbility(Graphics g, int size, double x, double y, Color color) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D.Double square = new Rectangle2D.Double(x,y,size,size);
        float stroke = (float) ((Math.sqrt(size)) * 0.5);

        g2.setColor(color);
        g2.fill(square);
        g2.setStroke(new BasicStroke(stroke));
        g2.setColor(Color.BLACK);
        g2.draw(square);
    }
}
