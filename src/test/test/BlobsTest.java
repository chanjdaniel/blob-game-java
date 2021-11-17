package test;

import model.Blob;
import model.Blobs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BlobsTest {
    private Blobs testBlobs1;
    private Blob testBlob1;
    private Blob testBlob2;
    private Color blue = Color.blue;
    private Color red = Color.red;

    private ArrayList<String> names;

    @BeforeEach
    void runBefore() throws IOException {
        testBlobs1 = new Blobs();
        testBlob1 = new Blob("test_blob1", 20, 0, 0, 0, blue);
        testBlob2 = new Blob("test_blob2", 10, 0, 0, 0, red);
    }

    @Test
    void testConstructor() {

        assertEquals(0, testBlobs1.getBlobs().size());
        assertNotNull(testBlobs1.getJsonNames());
    }

    @Test
    void testArrayListConstructor() {
        Blobs testBlobs2 = null;
        testBlobs2 = new Blobs(testBlobs1.getBlobs());
        assertEquals(0, testBlobs2.getBlobs().size());
        assertNotNull(testBlobs2.getJsonNames());
    }

    @Test
    void testAddRemoveBlobSingle() {

        testBlobs1.addBlob(testBlob1);
        assertEquals(1, testBlobs1.getBlobs().size());
        testBlobs1.removeBlob(testBlob1);
        assertEquals(0, testBlobs1.getBlobs().size());
    }

    @Test
    void testAddRemoveBlobMultiple() {

        testBlobs1.addBlob(testBlob1);
        assertEquals(1, testBlobs1.getBlobs().size());
        testBlobs1.addBlob(testBlob2);
        assertEquals(2, testBlobs1.getBlobs().size());
        testBlobs1.removeBlob(testBlob2);
        assertEquals(1, testBlobs1.getBlobs().size());
        testBlobs1.removeBlob(testBlob1);
        assertEquals(0, testBlobs1.getBlobs().size());
    }

    @Test
    void testAddRandomBlob() {
        double maxX = GameScreen.RIGHT_WIDTH;
        double maxY = GamePanel.HEIGHT;

        for (int i = 0; i < 100; i++) {
            testBlobs1.addRandomBlob();
            assertEquals(i + 1, testBlobs1.getBlobs().size());
        }

        for (Blob blob : testBlobs1.getBlobs()) {
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
