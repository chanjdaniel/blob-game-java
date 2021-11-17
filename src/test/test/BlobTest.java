package test;

import model.Abilities;
import model.Ability;
import model.Blob;
import model.Blobs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Screen;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlobTest {
    private Blob testBlob;

    @BeforeEach
    void runBefore() {
        String name = "testName";
        int size = 15;
        int speed = 1;
        double positionX = GameScreen.RIGHT_WIDTH / 2.0;
        double positionY = GamePanel.HEIGHT / 2.0;
        Color color = Color.CYAN;

        testBlob = new Blob(name, size, speed, positionX, positionY, color);
    }

    @Test
    void testConstructor() {

        assertEquals("testName", testBlob.getName());
        assertEquals(15, testBlob.getSize());
        assertEquals(1, testBlob.getSpeed());
        assertEquals(GameScreen.RIGHT_WIDTH / 2.0, testBlob.getPositionX());
        assertEquals(GamePanel.HEIGHT / 2.0, testBlob.getPositionY());
        assertEquals(Color.CYAN, testBlob.getColor());
        assertEquals(0, testBlob.getAbilities().size());
        assertEquals(0, testBlob.getVictims().size());
    }

    @Test
    void testLoadConstructor() {

        Blob testBlob2 = new Blob(
                testBlob.getName(),
                testBlob.getSize(),
                testBlob.getSpeed(),
                testBlob.getPositionX(),
                testBlob.getMovementX(),
                testBlob.getPositionY(),
                testBlob.getMovementY(),
                testBlob.getColor(),
                new Abilities(testBlob.getAbilities()),
                new Blobs(testBlob.getVictims())
        );

        assertEquals(testBlob.getName(), testBlob2.getName());
        assertEquals(testBlob.getSize(), testBlob2.getSize());
        assertEquals(testBlob.getSpeed(), testBlob2.getSpeed());
        assertEquals(testBlob.getPositionX(), testBlob2.getPositionX());
        assertEquals(testBlob.getMovementX(), testBlob2.getMovementX());
        assertEquals(testBlob.getPositionY(), testBlob2.getPositionY());
        assertEquals(testBlob.getMovementY(), testBlob2.getMovementY());
        assertEquals(testBlob.getColor(), testBlob2.getColor());
        assertEquals(testBlob.getAbilities().size(), testBlob2.getAbilities().size());
        assertEquals(testBlob.getVictims().size(), testBlob2.getVictims().size());
    }

    @Test
    void testAddAbilitySingle() {
        Ability testAbility1 = new Ability(
                "testName1",
                "testDescription1",
                "testStat1",
                1,
                0,
                0);

        assertEquals(0, testBlob.getAbilities().size());
        testBlob.addAbility(testAbility1);
        assertEquals(1, testBlob.getAbilities().size());
        assertEquals("testName1", testBlob.getAbilities().get(0).getName());
    }

    @Test
    void testAddAbilityMultiple() {
        Ability testAbility1 = new Ability(
                "testName1",
                "testDescription1",
                "testStat1",
                1,
                0,
                0);

        Ability testAbility2 = new Ability(
                "testName2",
                "testDescription2",
                "testStat2",
                1,
                0,
                0);

        assertEquals(0, testBlob.getAbilities().size());
        testBlob.addAbility(testAbility1);
        assertEquals(1, testBlob.getAbilities().size());
        assertEquals("testName1", testBlob.getAbilities().get(0).getName());
        testBlob.addAbility(testAbility2);
        assertEquals(2, testBlob.getAbilities().size());
        assertEquals("testName2", testBlob.getAbilities().get(1).getName());
    }

    @Test
    void testEatBlobSingle() {
        Blob foodBlob1 = new Blob(
                "testName1",
                10,
                1,
                0,
                0,
                Color.CYAN);

        int initialSize = testBlob.getSize();

        assertEquals(0, testBlob.getVictims().size());
        testBlob.eatBlob(foodBlob1);
        assertEquals(1, testBlob.getVictims().size());
        assertEquals("testName1", testBlob.getVictims().get(0).getName());
        assertEquals(initialSize + foodBlob1.getSize() / 2, testBlob.getSize());
    }

    @Test
    void testEatBlobMultiple() {
        Blob foodBlob1 = new Blob(
                "testName1",
                10,
                1,
                0,
                0,
                Color.CYAN);
        Blob foodBlob2 = new Blob(
                "testName2",
                12,
                1,
                0,
                0,
                Color.CYAN);

        int initialSize = testBlob.getSize();

        assertEquals(0, testBlob.getVictims().size());
        testBlob.eatBlob(foodBlob1);
        assertEquals(1, testBlob.getVictims().size());
        assertEquals("testName1", testBlob.getVictims().get(0).getName());
        assertEquals(initialSize + foodBlob1.getSize() / 2, testBlob.getSize());
        initialSize = testBlob.getSize();
        testBlob.eatBlob(foodBlob2);
        assertEquals(2, testBlob.getVictims().size());
        assertEquals("testName2", testBlob.getVictims().get(1).getName());
        assertEquals(initialSize + foodBlob2.getSize() / 2, testBlob.getSize());
    }

    @Test
    void testMoveUp() {
        double initialPosY = testBlob.getPositionY();
        testBlob.setMovementY(1);
        testBlob.move();
        assertEquals(initialPosY - testBlob.getMovementY(), testBlob.getPositionY());
    }

    @Test
    void testMoveDown() {
        double initialPosY = testBlob.getPositionY();
        testBlob.setMovementY(-1);
        testBlob.move();
        assertEquals(initialPosY - testBlob.getMovementY(), testBlob.getPositionY());
    }

    @Test
    void testMoveLeft() {
        double initialPosX = testBlob.getPositionX();
        testBlob.setMovementX(-1);
        testBlob.move();
        assertEquals(initialPosX + testBlob.getMovementX(), testBlob.getPositionX());
    }

    @Test
    void testMoveRight() {
        double initialPosX = testBlob.getPositionX();
        testBlob.setMovementX(1);
        testBlob.move();
        assertEquals(initialPosX + testBlob.getMovementX(), testBlob.getPositionX());
    }

    @Test
    void testMoveUpLeft() {
        double initialPosX = testBlob.getPositionX();
        double initialPosY = testBlob.getPositionY();
        testBlob.setMovementX(-1);
        testBlob.setMovementY(1);
        testBlob.move();
        assertEquals(initialPosX + testBlob.getMovementX(), testBlob.getPositionX());
        assertEquals(initialPosY - testBlob.getMovementY(), testBlob.getPositionY());
    }

    @Test
    void testMoveUpRight() {
        double initialPosX = testBlob.getPositionX();
        double initialPosY = testBlob.getPositionY();
        testBlob.setMovementX(1);
        testBlob.setMovementY(1);
        testBlob.move();
        assertEquals(initialPosX + testBlob.getMovementX(), testBlob.getPositionX());
        assertEquals(initialPosY - testBlob.getMovementY(), testBlob.getPositionY());
    }

    @Test
    void testMoveDownLeft() {
        double initialPosX = testBlob.getPositionX();
        double initialPosY = testBlob.getPositionY();
        testBlob.setMovementX(-1);
        testBlob.setMovementY(-1);
        testBlob.move();
        assertEquals(initialPosX + testBlob.getMovementX(), testBlob.getPositionX());
        assertEquals(initialPosY - testBlob.getMovementY(), testBlob.getPositionY());
    }

    @Test
    void testMoveDownRight() {
        double initialPosX = testBlob.getPositionX();
        double initialPosY = testBlob.getPositionY();
        testBlob.setMovementX(1);
        testBlob.setMovementY(-1);
        testBlob.move();
        assertEquals(initialPosX + testBlob.getMovementX(), testBlob.getPositionX());
        assertEquals(initialPosY - testBlob.getMovementY(), testBlob.getPositionY());
    }

    @Test
    void testMoveUpBoundary() {
        testBlob.setPositionY(0);
        double initialPosY = testBlob.getPositionY();
        testBlob.setMovementY(1);
        testBlob.move();
        assertEquals(initialPosY, testBlob.getPositionY());
    }

    @Test
    void testMoveDownBoundary() {
        testBlob.setPositionY(GamePanel.HEIGHT - 30);
        double initialPosY = testBlob.getPositionY();
        testBlob.setMovementY(-1);
        testBlob.move();
        assertEquals(initialPosY, testBlob.getPositionY());
    }

    @Test
    void testMoveLeftBoundary() {
        testBlob.setPositionX(0);
        double initialPosX = testBlob.getPositionX();
        testBlob.setMovementX(-1);
        testBlob.move();
        assertEquals(initialPosX, testBlob.getPositionX());
    }

    @Test
    void testMoveRightBoundary() {
        testBlob.setPositionX(GameScreen.RIGHT_WIDTH);
        double initialPosX = testBlob.getPositionX();
        testBlob.setMovementX(1);
        testBlob.move();
        assertEquals(initialPosX, testBlob.getPositionX());
    }
}
