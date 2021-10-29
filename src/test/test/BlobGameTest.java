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
import static org.junit.jupiter.api.Assertions.fail;

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
    private Abilities testAbilities;
    private Blobs testEnemyBlobs;


    @BeforeEach
    void runBefore() {
        initialEnemies = enemyBlobNames.size();
        playerInitialSize = 15;
        testBlobGame = new BlobGame("testBlob", Color.cyan);
        testPlayer = testBlobGame.getPlayer();
        testBlob1 = new Blob("test_blob1", 20, blue);
        testAbilities = testBlobGame.getAbilities();
        testEnemyBlobs = testBlobGame.getEnemyBlobs();
    }

    @Test
    void testConstructor() {

        assertEquals("testBlob", testPlayer.getName());
        assertEquals(playerInitialSize, testPlayer.getSize());
        assertEquals(Color.cyan, testPlayer.getColor());
        assertEquals(abilityNames.size(), testAbilities.getAbilities().size());
        assertEquals(initialEnemies, testEnemyBlobs.getBlobs().size());
    }

    @Test
    void testGetAbilityByName(){
        String expect = abilityNames.get(0);
        String actual = testBlobGame.getAbilityByName(abilityNames.get(0)).getName();
        assertEquals(expect, actual);
    }

    @Test
    void testMakeBlobs(){
        Blobs actual = null;
        actual = testBlobGame.makeBlobs();
        assertEquals(initialEnemies, actual.getBlobs().size());
    }

    @Test
    void testMakeAbilities(){
        Abilities actual = testBlobGame.makeAbilities();
        assertEquals(abilityNames.size(), actual.getAbilities().size());
    }

    @Test
    void testMakePlayerBlob(){
        Blob expect = new Blob(testPlayer.getName(), playerInitialSize, Color.cyan);
        assertEquals(expect.getName(), testPlayer.getName());
        assertEquals(expect.getSize(), testPlayer.getSize());
        assertEquals(expect.getColor(), testPlayer.getColor());
    }
}
