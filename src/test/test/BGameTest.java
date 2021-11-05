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

public class BGameTest {
    private int playerInitialSize;
    private final ArrayList<String> abilityNames = new ArrayList<>(Arrays.asList(
            "Super Speed", "Physical Resistance", "Regeneration", "Sticky", "Heat Resistance",
            "Acid", "Digest", "Grow", "Jump", "Dash"));
    private int initialEnemies;
    private final ArrayList<String> enemyBlobNames = new ArrayList<>(Arrays.asList(
            "Michael", "James", "Sam", "Tiffany", "Gordon",
            "Aaron", "Peter", "Hannah", "Jane", "Gary"));

    private BGame testBGame;
    private Blob testPlayer;
    private Blob testBlob1;
    private Abilities testAbilities;
    private Ability testAbility1;
    private Blobs testEnemyBlobs;


    @BeforeEach
    void runBefore() {
        testAbility1 = new Ability("Test_Ability1", "");
        initialEnemies = enemyBlobNames.size();
        playerInitialSize = 15;
        try {
            testBGame = new BGame("testBlob", Color.cyan);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        testPlayer = testBGame.getPlayer();
        testBlob1 = new Blob("test_blob1", 20, blue);
        testAbilities = testBGame.getAbilities();
        testEnemyBlobs = testBGame.getEnemyBlobs();
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
        String actual = testBGame.getAbilityByName(abilityNames.get(0)).getName();
        assertEquals(expect, actual);
    }

    @Test
    void testMakeBlobs(){
        Blobs actual = null;
        actual = testBGame.makeBlobs();
        assertEquals(initialEnemies, actual.getBlobs().size());
    }

    @Test
    void testMakeAbilities(){
        Abilities actual = null;
        actual = testBGame.makeAbilities();
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
