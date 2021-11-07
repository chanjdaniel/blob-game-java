package ui;

import model.Blob;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import static ui.Screen.CENTRE_HEIGHT;
import static ui.Screen.CENTRE_WIDTH;

public class BlobRenderer {

    public BlobRenderer() {
    }

    public void renderBlob(Graphics g, Blob blob) {
        int size = blob.getSize();
        double x = blob.getPositionX();
        double y = blob.getPositionY();
        Color color = blob.getColor();
        renderBlobBody(g, size, x, y, color);
        renderBlobMouth(g, size, x, y);
        renderBlobEyes(g, size, x, y);
    }

    private void renderBlobBody(Graphics g, int size, double x, double y, Color color) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D.Double body = new Rectangle2D.Double(x,y,size,size);
        float stroke = (float) ((Math.sqrt(size)) * 0.5);

        g2.setColor(color);
        g2.fill(body);
        g2.setStroke(new BasicStroke(stroke));
        g2.setColor(Color.BLACK);
        g2.draw(body);
    }

    private void renderBlobMouth(Graphics g, int size, double x, double y) {
        double width = size / 3.0;
        double height = width / 2.0;
        double posX = (x + size / 2.0) - (width / 2.0);
        double posY = y + size / 1.8;
        float stroke = (float) ((Math.sqrt(size)) * 0.5);
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D.Double mouth = new Rectangle2D.Double(posX,posY,width,height);

        g2.setStroke(new BasicStroke(stroke));
        g2.setColor(Color.BLACK);
        g2.draw(mouth);
    }

    private void renderBlobEyes(Graphics g, int size, double x, double y) {
        float eyeLevel = (float) (y + (size / (float) 4));
        float blobCentreX = (float) (x + size / (float) 2);
        float eyeOffSetX = size / (float) 4;
        float leftEyeCentreX = blobCentreX - eyeOffSetX;
        float rightEyeCentreX = blobCentreX + eyeOffSetX;
        float eyeLength = size / (float) 4;
        float stroke = (float) ((Math.sqrt(size)) * 0.5);
        Graphics2D g2 = (Graphics2D) g;
        Line2D leftEye = new Line2D.Float(
                leftEyeCentreX - eyeLength / 2, eyeLevel,
                leftEyeCentreX + eyeLength / 2, eyeLevel);
        Line2D rightEye = new Line2D.Float(
                rightEyeCentreX - eyeLength / 2, eyeLevel,
                rightEyeCentreX + eyeLength / 2, eyeLevel);

        g2.setStroke(new BasicStroke(stroke));
        g2.setColor(Color.BLACK);
        g2.draw(leftEye);
        g2.draw(rightEye);
    }
}
