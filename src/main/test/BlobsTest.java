package test;

import model.Blob;
import model.Blobs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static java.awt.Color.red;
import static org.junit.jupiter.api.Assertions.*;

public class BlobsTest {
    private Blobs testBlobs;
    private Blob testBlob1;
    private Blob testBlob2;
    private Color blue = Color.blue;
    private Color red = Color.red;

    @BeforeEach
    void runBefore() {
        testBlobs = new Blobs();
        testBlob1 = new Blob("test_blob1", 20, blue);
        testBlob2 = new Blob("test_blob2", 10, red);
    }

    @Test
    void testConstructor() {

        assertEquals(0, testBlobs.getBlobs().size());
    }

    @Test
    void testRemoveBlobSingle() {

        testBlobs.addBlob(testBlob1);
        assertEquals(1, testBlobs.getBlobs().size());
        testBlobs.removeBlob(testBlob1);
        assertEquals(0, testBlobs.getBlobs().size());
    }

    @Test
    void testRemoveBlobMultiple() {

        testBlobs.addBlob(testBlob1);
        testBlobs.addBlob(testBlob2);
        assertEquals(2, testBlobs.getBlobs().size());
        testBlobs.removeBlob(testBlob1);
        testBlobs.removeBlob(testBlob2);
        assertEquals(0, testBlobs.getBlobs().size());
    }

    @Test
    void testGetByName() {

        testBlobs.addBlob(testBlob1);
        testBlobs.addBlob(testBlob2);
        assertEquals(testBlob1, testBlobs.getByName(testBlob1.getName()));
        assertEquals(testBlob2, testBlobs.getByName(testBlob2.getName()));
    }

    @Test
    void testGetNamesEmpty() {

        assertEquals(0, testBlobs.getNames().size());
    }

    @Test
    void testGetNamesMultiple() {

        testBlobs.addBlob(testBlob1);
        testBlobs.addBlob(testBlob2);
        assertEquals(2, testBlobs.getNames().size());
        assertEquals(testBlob1.getName(), testBlobs.getNames().get(0));
    }

    @Test
    void testGetLowerCaseNamesEmpty() {

        assertEquals(0, testBlobs.getLowerCaseNames().size());
    }

    @Test
    void testGetLowerCaseNamesMultiple() {

        testBlobs.addBlob(testBlob1);
        testBlobs.addBlob(testBlob2);
        assertEquals(2, testBlobs.getLowerCaseNames().size());
        assertEquals(testBlob1.getName().toLowerCase(), testBlobs.getLowerCaseNames().get(0));
    }

    @Test
    void testMakeBlobs() {

        ArrayList<String> testNames = new ArrayList<>(Arrays.asList(
                "Michael", "James", "Sam", "Tiffany", "Gordon", "Aaron", "Peter", "Hannah", "Jane", "Gary"));
        ArrayList<String> testNamesReversed = new ArrayList<>(Arrays.asList(
                "Michael", "James", "Sam", "Tiffany", "Gordon", "Aaron", "Peter", "Hannah", "Jane", "Gary"));
        Collections.reverse(testNamesReversed);
        testBlobs.makeBlobs(10, testNames);
        assertEquals(10, testBlobs.getBlobs().size());
        for (Blob next : testBlobs.getBlobs()) {
            assertEquals(testNamesReversed.get(testBlobs.getBlobs().indexOf(next)), next.getName());
            assertTrue(next.getSize() >= 1 && next.getSize() <= 50);
            assertEquals(0, next.getVictims().size());
            assertEquals(0, next.getAbilities().size());
        }
    }
}
