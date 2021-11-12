package test;

import model.Blob;
import model.Blobs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BlobsTest {
    private Blobs testBlobs;
    private Blob testBlob1;
    private Blob testBlob2;
    private Color blue = Color.blue;
    private Color red = Color.red;

    private ArrayList<String> names;

    @BeforeEach
    void runBefore() {
        testBlobs = new Blobs();
        testBlob1 = new Blob("test_blob1", 20, 0, 0, 0, blue);
        testBlob2 = new Blob("test_blob2", 10, 0, 0, 0, red);
    }

    @Test
    void testConstructor() {

        assertEquals(0, testBlobs.getBlobs().size());
        assertTrue(testBlobs.getJsonNames().length() != 0);
    }

    @Test
    void testAddRemoveBlobSingle() {

        testBlobs.addBlob(testBlob1);
        assertEquals(1, testBlobs.getBlobs().size());
        testBlobs.removeBlob(testBlob1);
        assertEquals(0, testBlobs.getBlobs().size());
    }

    @Test
    void testAddRemoveBlobMultiple() {

        testBlobs.addBlob(testBlob1);
        assertEquals(1, testBlobs.getBlobs().size());
        testBlobs.addBlob(testBlob2);
        assertEquals(2, testBlobs.getBlobs().size());
        testBlobs.removeBlob(testBlob2);
        assertEquals(1, testBlobs.getBlobs().size());
        testBlobs.removeBlob(testBlob1);
        assertEquals(0, testBlobs.getBlobs().size());
    }

    @Test
    void testAddRandomBlob() {
        double maxX = GameScreen.RIGHT_WIDTH;
        double maxY = GamePanel.HEIGHT;

        for (int i = 0; i < 10; i++) {
            testBlobs.addRandomBlob();
            assertEquals(i + 1, testBlobs.getBlobs().size());
        }

        for (Blob blob : testBlobs.getBlobs()) {
            assertTrue(blob.getSize() >= 10);
            assertTrue(blob.getSize() <= 50);
            assertTrue(blob.getSpeed() >= 1);
            assertTrue(blob.getSpeed() <= 5);
            assertEquals(0, blob.getMovementX());
            assertEquals(0, blob.getMovementY());

            double positionX = blob.getPositionX();
            double positionY = blob.getPositionY();
            assertTrue(
                    ((0 <= positionX && positionX <= maxX) && (0 == positionY))
                            || ((positionX == maxX) && (0 <= positionY && positionY <= maxY))
                            || ((0 <= positionX && positionX <= maxX) && (positionY == maxY))
                            || ((0 == positionX) && (0 <= positionY && positionY <= maxY))
            );

            assertEquals(0, blob.getAbilities().size());
            assertEquals(0, blob.getVictims().size());
        }
    }
}
