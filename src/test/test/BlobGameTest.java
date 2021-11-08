package test;

import exceptions.InvalidInputException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.awt.Color.blue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlobGameTest {
    private int playerInitialSize;
    private final ArrayList<String> abilityNames = new ArrayList<>(Arrays.asList(
            "Super Speed", "Physical Resistance", "Regeneration", "Sticky", "Heat Resistance",
            "Acid", "Digest", "Grow", "Jump", "Dash"));
    private int initialEnemies;
    private final ArrayList<String> enemyBlobNames = new ArrayList<>(Arrays.asList(
            "Michael", "James", "Sam", "Tiffany", "Gordon",
            "Aaron", "Peter", "Hannah", "Jane", "Gary"));

    private BlobGame testBlobGame;
    private Blob testPlayer;
    private Blob testBlob1;
    private ArrayList<Ability> testAbilities;
    private Ability testAbility1;
    private Blobs testEnemyBlobs;


    @BeforeEach
    void runBefore() {
        testAbility1 = new Ability("Test_Ability1", "description", "stat", 0);
        initialEnemies = enemyBlobNames.size();
        playerInitialSize = 15;
        try {
            testBlobGame = new BlobGame("testBlob", Color.cyan);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        testPlayer = testBlobGame.getPlayerBlob();
        testBlob1 = new Blob("test_blob1", 20, 0, 0, 0, blue);
        testAbilities = testBlobGame.getAbilities();
        testEnemyBlobs = testBlobGame.getEnemyBlobs();
    }

    @Test
    void testConstructor() {

        assertEquals("testBlob", testPlayer.getName());
        assertEquals(playerInitialSize, testPlayer.getSize());
        assertEquals(playerInitialSize, testPlayer.getSize());
        assertEquals(Color.cyan, testPlayer.getColor());
        assertEquals(abilityNames.size(), testAbilities.size());
        assertEquals(initialEnemies, testEnemyBlobs.getBlobs().size());
    }

//    @Test
//    void testMakePlayerBlob(){
//        Blob expect = new Blob(testPlayer.getName(), playerInitialSize, Color.cyan);
//        assertEquals(expect.getName(), testPlayer.getName());
//        assertEquals(expect.getSize(), testPlayer.getSize());
//        assertEquals(expect.getColor(), testPlayer.getColor());
//    }
}
