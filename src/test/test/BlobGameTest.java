package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Screen;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static model.BlobGame.*;
import static org.junit.jupiter.api.Assertions.*;

public class BlobGameTest {
    private final int centreX = Screen.CENTRE_WIDTH - BlobGame.PLAYER_INITIAL_SIZE / 2;
    private final int centreY = Screen.CENTRE_HEIGHT - BlobGame.PLAYER_INITIAL_SIZE / 2;
    private BlobGame testBlobGame;
    private Blob testPlayerBlob;
    private Abilities testAbilities;
    private Blobs testEnemyBlobs;
    private Blob testEnemyBlob1;
    private Blob testEnemyBlob2;

    @BeforeEach
    void runBefore() {
        testEnemyBlob1 = new Blob(
                "testEnemyBlob1",
                15, 0,
                centreX,
                centreY,
                Color.CYAN
        );

        testEnemyBlob2 = new Blob(
                "testEnemyBlob2",
                15,
                0,
                centreX,
                centreY,
                Color.CYAN
        );

        testPlayerBlob = new Blob(
                "testPlayer",
                BlobGame.PLAYER_INITIAL_SIZE,
                BlobGame.PLAYER_INITIAL_SPEED,
                centreX,
                centreY,
                Color.CYAN
        );
        testAbilities = new Abilities();
        testEnemyBlobs = new Blobs();

        testBlobGame = new BlobGame(testPlayerBlob, testAbilities, testEnemyBlobs);
    }

    @Test
    void testRandIntBetweenValues() {
        for (int i = 0; i < 100; i++) {
            int randInt = randIntBetweenValues(0, 100);
            assertTrue(randInt >= 0);
            assertTrue(randInt <= 100);
        }
    }

    @Test
    void testConstructor() {
        String testName = "testPlayer";
        Color testColor = Color.CYAN;
        Blob testPlayerBlob = testBlobGame.getPlayerBlob();

        assertEquals(testName, testPlayerBlob.getName());
        assertEquals(BlobGame.PLAYER_INITIAL_SIZE, testPlayerBlob.getSize());
        assertEquals(BlobGame.PLAYER_INITIAL_SPEED, testPlayerBlob.getSpeed());
        assertEquals(centreX, testPlayerBlob.getPositionX());
        assertEquals(0, testPlayerBlob.getMovementX());
        assertEquals(centreY, testPlayerBlob.getPositionY());
        assertEquals(0, testPlayerBlob.getMovementY());
        assertEquals(testColor, testPlayerBlob.getColor());
        assertEquals(0, testBlobGame.getAbilities().size());
        assertEquals(0, testBlobGame.getEnemyBlobs().size());
    }

    @Test
    void testUpdateMoveEnemyBlobs() {
        testEnemyBlob1.setSpeed(1);
        testEnemyBlob2.setSpeed(5);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob2);
        for (int i = 0; i < 100; i++) {
            double blob1x = testEnemyBlob1.getPositionX();
            double blob1y = testEnemyBlob1.getPositionY();
            double blob2x = testEnemyBlob2.getPositionX();
            double blob2y = testEnemyBlob2.getPositionY();
            testBlobGame.update();

            assertTrue(testEnemyBlob1.getSpeed() * -1 <= testEnemyBlob1.getMovementX() &&
                    testEnemyBlob1.getMovementX() <= testEnemyBlob1.getSpeed());
            assertTrue(blob1x - testEnemyBlob1.getSpeed() <= testEnemyBlob1.getPositionX() &&
                    testEnemyBlob1.getPositionX() <= blob1x + testEnemyBlob1.getSpeed());
            assertTrue(blob1y - testEnemyBlob1.getSpeed() <= testEnemyBlob1.getPositionY() &&
                    testEnemyBlob1.getPositionY() <= blob1y + testEnemyBlob1.getSpeed());
            assertTrue(testEnemyBlob2.getSpeed() * -1 <= testEnemyBlob2.getMovementX() &&
                    testEnemyBlob2.getMovementX() <= testEnemyBlob2.getSpeed());
            assertTrue(blob2x - testEnemyBlob2.getSpeed() <= testEnemyBlob2.getPositionX() &&
                    testEnemyBlob2.getPositionX() <= blob2x + testEnemyBlob2.getSpeed());
            assertTrue(blob2y - testEnemyBlob2.getSpeed() <= testEnemyBlob2.getPositionY() &&
                    testEnemyBlob2.getPositionY() <= blob2y + testEnemyBlob2.getSpeed());
        }
    }

    @Test
    void testAddEnemyBlobs() {
        testBlobGame.addEnemyBlob();
        assertEquals(0, testBlobGame.getEnemyBlobs().size());

        for (int i = 0; i < 100; i++) {
            testBlobGame.setNewEnemyCounter(BlobGame.NEW_ENEMY_RATE);
            testBlobGame.addEnemyBlob();

            double maxX = GameScreen.RIGHT_WIDTH;
            double maxY = GamePanel.HEIGHT;

            Blob blob =
                    testBlobGame.getEnemyBlobs().get(testBlobGame.getEnemyBlobs().size() - 1);
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
        assertEquals(BlobGame.MAX_ENEMIES, testBlobGame.getEnemyBlobs().size());
    }

    @Test
    void testAddRandomAbility() {
        ArrayList<Ability> jsonAbilities = testAbilities.getJsonAbilities();

        testBlobGame.addRandomAbility();
        assertEquals(0, testBlobGame.getAbilities().size());

        for (int i = 0; i < 100; i++) {
            testBlobGame.setNewAbilityCounter(NEW_ABILITY_RATE);
            testBlobGame.addRandomAbility();
        }

        assertEquals(MAX_ABILITIES, testBlobGame.getAbilities().size());

        for (Ability ab : testBlobGame.getAbilities()) {
            Ability speed = jsonAbilities.get(0);
            Ability size = jsonAbilities.get(1);
            assertTrue(ab.getName().equals(speed.getName())
                    || ab.getName().equals(size.getName()));
            assertTrue(ab.getDescription().equals(speed.getDescription())
                    || ab.getDescription().equals(size.getDescription()));
            assertTrue(ab.getStat().equals(speed.getStat())
                    || ab.getStat().equals(size.getStat()));
            assertTrue(ab.getValue() == speed.getValue()
                    || ab.getValue() == size.getValue());
            assertTrue(50 <= ab.getPositionX() && ab.getPositionX() <= GameScreen.RIGHT_WIDTH - 50);
            assertTrue(50 <= ab.getPositionY() && ab.getPositionY() <= GameScreen.HEIGHT - 50);
        }
    }

    @Test
    void testUpdateCheckEnemyBlobsEatEqual() {
        testEnemyBlob1.setSize(15);
        testEnemyBlob2.setSize(15);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob2);

        testBlobGame.update();

        assertEquals(2, testBlobGame.getEnemyBlobs().size());
        assertEquals(15, testEnemyBlob1.getSize());
        assertEquals(15, testEnemyBlob2.getSize());
    }

    @Test
    void testUpdateCheckEnemyBlobsEatNoCollision() {
        testEnemyBlob1.setSize(10);
        testEnemyBlob1.setPositionX(testEnemyBlob1.getPositionX() + 50);
        testEnemyBlob2.setSize(15);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob2);

        testBlobGame.update();

        assertEquals(2, testBlobGame.getEnemyBlobs().size());
        assertEquals(10, testEnemyBlob1.getSize());
        assertEquals(15, testEnemyBlob2.getSize());
    }

    @Test
    void testUpdateCheckEnemyBlobsEatLarger() {
        testEnemyBlob1.setSize(10);
        testEnemyBlob2.setSize(15);
        int prevSize2 = testEnemyBlob2.getSize();
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob2);

        testBlobGame.update();

        assertEquals(1, testBlobGame.getEnemyBlobs().size());
        assertEquals(10, testEnemyBlob1.getSize());
        assertEquals(prevSize2 + testEnemyBlob1.getSize() / 2, testEnemyBlob2.getSize());
    }

    @Test
    void testUpdateCheckPlayerEatSuccess() {
        testPlayerBlob.setSize(15);
        testEnemyBlob1.setSize(10);
        int prevSize = testPlayerBlob.getSize();
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);

        testBlobGame.update();

        assertEquals(0, testBlobGame.getEnemyBlobs().size());
        assertEquals(prevSize + testEnemyBlob1.getSize() / 2, testPlayerBlob.getSize());
    }

    @Test
    void testUpdateCheckPlayerEatFail() {
        testPlayerBlob.setSize(15);
        testEnemyBlob1.setSize(15);
        int prevSize = testPlayerBlob.getSize();
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);

        testBlobGame.update();

        assertEquals(1, testBlobGame.getEnemyBlobs().size());
        assertEquals(prevSize, testPlayerBlob.getSize());
    }

    @Test
    void testUpdateCheckPlayerEatNoCollision() {
        testPlayerBlob.setSize(15);
        testEnemyBlob1.setSize(10);
        testEnemyBlob1.setPositionX(testEnemyBlob1.getPositionX() + 50);
        int prevSize = testPlayerBlob.getSize();
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);

        testBlobGame.update();

        assertEquals(1, testBlobGame.getEnemyBlobs().size());
        assertEquals(prevSize, testPlayerBlob.getSize());
    }

    @Test
    void testUpdateCheckPlayerGainAbilityCollision() {
        testBlobGame.setNewAbilityCounter(NEW_ABILITY_RATE);
        testBlobGame.addRandomAbility();
        Ability ability = testBlobGame.getAbilities().get(0);
        testPlayerBlob.setPositionX(ability.getPositionX());
        testPlayerBlob.setPositionY(ability.getPositionY());

        assertEquals(0, testPlayerBlob.getAbilities().size());
        assertEquals(1, testBlobGame.getAbilities().size());

        testBlobGame.update();

        assertEquals(1, testPlayerBlob.getAbilities().size());
        assertEquals(0, testBlobGame.getAbilities().size());
    }

    @Test
    void testUpdateCheckPlayerGainAbilityNoCollision() {
        testBlobGame.setNewAbilityCounter(NEW_ABILITY_RATE);
        testBlobGame.addRandomAbility();
        Ability ability = testBlobGame.getAbilities().get(0);
        testPlayerBlob.setPositionX(ability.getPositionX() + 50);
        testPlayerBlob.setPositionY(ability.getPositionY() + 50);

        assertEquals(0, testPlayerBlob.getAbilities().size());
        assertEquals(1, testBlobGame.getAbilities().size());

        testBlobGame.update();

        assertEquals(0, testPlayerBlob.getAbilities().size());
        assertEquals(1, testBlobGame.getAbilities().size());
    }

    @Test
    void testUpdateCheckGameOverTrue() {
        testPlayerBlob.setSize(10);
        testEnemyBlob1.setSize(15);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);

        testBlobGame.update();

        assertTrue(testBlobGame.isGameOver());
    }

    @Test
    void testUpdateCheckGameOverFalse() {
        testPlayerBlob.setSize(15);
        testEnemyBlob1.setSize(15);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);

        testBlobGame.update();

        assertFalse(testBlobGame.isGameOver());
    }

    @Test
    void testUpdateCheckGameOverNoCollision() {
        testPlayerBlob.setSize(10);
        testEnemyBlob1.setSize(15);
        testEnemyBlob1.setPositionX(testPlayerBlob.getPositionX() + 50);
        testBlobGame.getEnemyBlobs().add(testEnemyBlob1);

        testBlobGame.update();

        assertFalse(testBlobGame.isGameOver());
    }

    @Test
    void testUpdateCheckWinTrue() {
        testPlayerBlob.setSize(GameScreen.RIGHT_WIDTH);
        testBlobGame.update();
        assertTrue(testBlobGame.isWin());
    }

    @Test
    void testUpdateCheckWinFalse() {
        testPlayerBlob.setSize(GameScreen.RIGHT_WIDTH - 1);
        testBlobGame.update();
        assertFalse(testBlobGame.isWin());
    }

    @Test
    void testBlobControlStopWASD() {
        testBlobGame.blobControl(KeyEvent.VK_W);
        assertEquals(testPlayerBlob.getSpeed(), testPlayerBlob.getMovementY());
        testBlobGame.blobControl(KeyEvent.VK_A);
        assertEquals(testPlayerBlob.getSpeed() * -1, testPlayerBlob.getMovementX());
        testBlobGame.blobControl(KeyEvent.VK_S);
        assertEquals(testPlayerBlob.getSpeed() * -1, testPlayerBlob.getMovementY());
        testBlobGame.blobControl(KeyEvent.VK_D);
        assertEquals(testPlayerBlob.getSpeed(), testPlayerBlob.getMovementX());
        testBlobGame.blobStop(KeyEvent.VK_W);
        assertEquals(0, testPlayerBlob.getMovementY());
        testBlobGame.blobStop(KeyEvent.VK_A);
        assertEquals(0, testPlayerBlob.getMovementX());
        testBlobGame.blobStop(KeyEvent.VK_S);
        assertEquals(0, testPlayerBlob.getMovementY());
        testBlobGame.blobStop(KeyEvent.VK_D);
        assertEquals(0, testPlayerBlob.getMovementX());
    }

    @Test
    void testBlobControlStopArrowKeys() {
        testBlobGame.blobControl(KeyEvent.VK_UP);
        assertEquals(testPlayerBlob.getSpeed(), testPlayerBlob.getMovementY());
        testBlobGame.blobControl(KeyEvent.VK_LEFT);
        assertEquals(testPlayerBlob.getSpeed() * -1, testPlayerBlob.getMovementX());
        testBlobGame.blobControl(KeyEvent.VK_DOWN);
        assertEquals(testPlayerBlob.getSpeed() * -1, testPlayerBlob.getMovementY());
        testBlobGame.blobControl(KeyEvent.VK_RIGHT);
        assertEquals(testPlayerBlob.getSpeed(), testPlayerBlob.getMovementX());
        testBlobGame.blobStop(KeyEvent.VK_UP);
        assertEquals(0, testPlayerBlob.getMovementY());
        testBlobGame.blobStop(KeyEvent.VK_LEFT);
        assertEquals(0, testPlayerBlob.getMovementX());
        testBlobGame.blobStop(KeyEvent.VK_DOWN);
        assertEquals(0, testPlayerBlob.getMovementY());
        testBlobGame.blobStop(KeyEvent.VK_RIGHT);
        assertEquals(0, testPlayerBlob.getMovementX());
    }

    @Test
    void testUseAbilityEmpty() {
        int prevSize = testPlayerBlob.getSize();
        int prevSpeed = testPlayerBlob.getSpeed();
        testBlobGame.useAbility();
        assertEquals(0, testPlayerBlob.getAbilities().size());
        assertEquals(prevSize, testPlayerBlob.getSize());
        assertEquals(prevSpeed, testPlayerBlob.getSpeed());
    }

    @Test
    void testUseAbilitySpeed() {
        int prevSize = testPlayerBlob.getSize();
        int prevSpeed = testPlayerBlob.getSpeed();
        ArrayList<Ability> jsonAbilities = testAbilities.getJsonAbilities();
        Ability jsonAbility = jsonAbilities.get(0);
        Ability newAbility = new Ability(
                jsonAbility.getName(),
                jsonAbility.getDescription(),
                jsonAbility.getStat(),
                jsonAbility.getValue(),
                0,
                0);

        testPlayerBlob.getAbilities().add(newAbility);
        testBlobGame.useAbility();
        assertEquals(0, testPlayerBlob.getAbilities().size());
        assertEquals(prevSize, testPlayerBlob.getSize());
        assertEquals(prevSpeed + newAbility.getValue(), testPlayerBlob.getSpeed());
    }

    @Test
    void testUseAbilitySize() {
        int prevSize = testPlayerBlob.getSize();
        int prevSpeed = testPlayerBlob.getSpeed();
        ArrayList<Ability> jsonAbilities = testAbilities.getJsonAbilities();
        Ability jsonAbility = jsonAbilities.get(1);
        Ability newAbility = new Ability(
                jsonAbility.getName(),
                jsonAbility.getDescription(),
                jsonAbility.getStat(),
                jsonAbility.getValue(),
                0,
                0);

        testPlayerBlob.getAbilities().add(newAbility);
        testBlobGame.useAbility();
        assertEquals(0, testPlayerBlob.getAbilities().size());
        assertEquals(prevSize * newAbility.getValue(), testPlayerBlob.getSize());
        assertEquals(prevSpeed, testPlayerBlob.getSpeed());
    }
}
