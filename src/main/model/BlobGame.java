package model;

import exceptions.InvalidInputException;
import org.json.JSONObject;
import persistence.Writable;
import ui.Screen;
import ui.game.GameScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static ui.game.GameScreen.INTERVAL;

// Represents the game state of the blob game
public class BlobGame implements Writable {
    public static final int PLAYER_INITIAL_SIZE = 15;
    public static final int PLAYER_INITIAL_SPEED = 2;
    public static final int MAX_ENEMIES = 10;
    public static final int NEW_ENEMY_RATE = 1000;
    public static final int MAX_ABILITIES = 3;
    public static final int NEW_ABILITY_RATE = 10000;
    private int newEnemyCounter = 0;
    private int newAbilityCounter = 0;
    private boolean isGameOver = false;
    private boolean isWin = false;

    private final Blob playerBlob;
    private Abilities abilities;
    private Blobs enemyBlobs;

    // Creates an initial blob game with player with name and color;
    public BlobGame(String playerName, Color playerColor) throws InvalidInputException {

        // Creates player with name and color
        playerBlob = makePlayerBlob(playerName, playerColor);

        // Creates empty array list of ability
        abilities = new Abilities();

        // Creates empty blobs
        enemyBlobs = new Blobs();
    }

    // Creates a blob game with all fields given as parameters; for loading a saved game
    public BlobGame(Blob player, Abilities abilities, Blobs enemyBlobs) {

        this.playerBlob = player;
        this.enemyBlobs = enemyBlobs;
        this.abilities = abilities;
    }

    public Blob getPlayerBlob() {
        return playerBlob;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities.getAbilities();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public ArrayList<Blob> getEnemyBlobs() {
        return enemyBlobs.getBlobs();
    }

    public void setNewEnemyCounter(int newEnemyCounter) {
        this.newEnemyCounter = newEnemyCounter;
    }

    public void setNewAbilityCounter(int newAbilityCounter) {
        this.newAbilityCounter = newAbilityCounter;
    }

    // EFFECTS: returns a random int between the values min and max (inclusive)
    public static int randIntBetweenValues(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    // MODIFIES: this
    // EFFECTS: makes player blob with name playerName, size as playerInitialSize,
    // and color playerColor at centre of screen
    public static Blob makePlayerBlob(String playerName, Color playerColor) {
        int centreX = Screen.CENTRE_WIDTH - PLAYER_INITIAL_SIZE / 2;
        int centreY = Screen.CENTRE_HEIGHT - PLAYER_INITIAL_SIZE / 2;

        return new Blob(playerName, PLAYER_INITIAL_SIZE, PLAYER_INITIAL_SPEED, centreX, centreY, playerColor);
    }

    // Updates the game on clock tick
    // MODIFIES: this
    // EFFECTS:  updates playerBlob, enemyBlobs
    public void update() {
        moveEnemyBlobs();
        playerBlob.move();

        addEnemyBlob();
        addRandomAbility();
        checkEnemyBlobsEat();
        checkPlayerEat();
        checkPlayerGainAbility();
        checkGameOver();
        checkWin();
    }

    // Controls the blob
    // REQUIRES: keyCode is a valid keyCode for WASD or arrow keys
    // MODIFIES: this
    // EFFECTS: moves the blob in response to key code
    public void blobControl(int keyCode) {
        int speed = playerBlob.getSpeed();
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            playerBlob.setMovementY(speed);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            playerBlob.setMovementX(speed * -1);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            playerBlob.setMovementY(speed * -1);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            playerBlob.setMovementX(speed);
        }
    }

    // Stops the blob
    // REQUIRES: keyCode is a valid keyCode for WASD or arrow keys
    // MODIFIES: this
    // EFFECTS: stops the blob in response to key code
    public void blobStop(int keyCode) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            playerBlob.setMovementY(0);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            playerBlob.setMovementX(0);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            playerBlob.setMovementY(0);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            playerBlob.setMovementX(0);
        }
    }

    public void useAbility() {
        ArrayList<Ability> playerAbilities = playerBlob.getAbilities();
        if (playerAbilities.size() > 0) {
            Ability ability = playerAbilities.get(0);
            int stat = 0;

            if (ability.getStat().equals("size")) {
                int newSize = playerBlob.getSize() * ability.getValue();
                playerBlob.setSize(newSize);
                stat = playerBlob.getSize();
            }

            if (ability.getStat().equals("speed")) {
                int newSpeed = playerBlob.getSpeed() + ability.getValue();
                playerBlob.setSpeed(newSpeed);
                stat = playerBlob.getSpeed();
            }
            EventLog.getInstance().logEvent(
                    new Event(
                            playerBlob.getName() + " used " + ability.getName() + " - " + ability.getStat()
                    + ": " + stat));
            playerAbilities.remove(ability);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets movement of each enemy blob to a random value between min and max, moves blobs
    private void moveEnemyBlobs() {
        for (Blob next : enemyBlobs.getBlobs()) {
            int speed = next.getSpeed();
            int randX = randIntBetweenValues(speed * -1, speed);
            int randY = randIntBetweenValues(speed * -1, speed);
            next.setMovementX(randX);
            next.setMovementY(randY);
            next.move();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a random ability every NEW_ABILITY_RATE if number of abilities is less than MAX_ABILITIES
    public void addRandomAbility() {
        if (newAbilityCounter >= NEW_ABILITY_RATE) {
            if (abilities.getAbilities().size() < MAX_ABILITIES) {
                abilities.addRandomAbility();
            }
            newAbilityCounter = 0;
        }
        newAbilityCounter += INTERVAL;
    }

    // MODIFIES: this
    // EFFECTS: generates a new random blob and add it to enemyBlobs
    // every NEW_ENEMY_RATE if number of enemy blobs is less than MAX_ENEMIES
    public void addEnemyBlob() {
        if (newEnemyCounter >= NEW_ENEMY_RATE) {
            if (enemyBlobs.getBlobs().size() < MAX_ENEMIES) {
                enemyBlobs.addRandomBlob();
            }
            newEnemyCounter = 0;
        }
        newEnemyCounter += INTERVAL;
    }

    private boolean checkBlobCollision(Blob predator, Blob prey) {
        double predatorHalfSize = predator.getSize() / 2.0;
        double predatorBlobLeftBound = predator.getPositionX() - predatorHalfSize;
        double predatorBlobRightBound = predator.getPositionX() + predatorHalfSize;
        double predatorBlobUpperBound = predator.getPositionY() - predatorHalfSize;
        double predatorBlobLowerBound = predator.getPositionY() + predatorHalfSize;

        return (prey.getPositionX() > predatorBlobLeftBound && prey.getPositionX() < predatorBlobRightBound
                && prey.getPositionY() > predatorBlobUpperBound && prey.getPositionY() < predatorBlobLowerBound);
    }

    private void checkEnemyBlobsEat() {
        Blobs tempEnemyBlobs = new Blobs();
        for (Blob next : enemyBlobs.getBlobs()) {
            tempEnemyBlobs.addBlob(next);
        }
        for (Blob predator : tempEnemyBlobs.getBlobs()) {
            for (Blob prey : tempEnemyBlobs.getBlobs()) {
                if (!predator.equals(prey)) {
                    if (checkBlobCollision(predator, prey) && predator.getSize() > prey.getSize()) {
                        predator.eatBlob(prey);
                        enemyBlobs.removeBlob(prey);
                    }
                }
            }
        }
    }

    private void checkPlayerEat() {
        Blobs tempEnemyBlobs = new Blobs();
        tempEnemyBlobs.setBlobs(new ArrayList<>(enemyBlobs.getBlobs()));
        for (Blob next : tempEnemyBlobs.getBlobs()) {
            if (checkBlobCollision(playerBlob, next) && playerBlob.getSize() > next.getSize()) {
                playerBlob.eatBlob(next);
                EventLog.getInstance().logEvent(new Event(
                        playerBlob.getName() + " ate " + next.getName() + " - size: " + playerBlob.getSize()));
                enemyBlobs.removeBlob(next);
            }
        }
    }

    private boolean checkAbilityCollision(Blob player, Ability ability) {
        double playerHalfSize = player.getSize() / 2.0;
        double playerBlobLeftBound = player.getPositionX() - playerHalfSize;
        double playerBlobRightBound = player.getPositionX() + playerHalfSize;
        double playerBlobUpperBound = player.getPositionY() - playerHalfSize;
        double playerBlobLowerBound = player.getPositionY() + playerHalfSize;

        return (ability.getPositionX() > playerBlobLeftBound && ability.getPositionX() < playerBlobRightBound
                && ability.getPositionY() > playerBlobUpperBound && ability.getPositionY() < playerBlobLowerBound);
    }

    private void checkPlayerGainAbility() {
        Abilities tempAbilities = new Abilities(abilities.getAbilities());
        for (Ability next : tempAbilities.getAbilities()) {
            if (checkAbilityCollision(playerBlob, next) && playerBlob.getAbilities().size() < 5) {
                EventLog.getInstance().logEvent(
                        new Event(playerBlob.getName() + " gained " + next.getName()));
                playerBlob.addAbility(next);
                abilities.removeAbility(next);
            }
        }
    }

    private void checkGameOver() {
        for (Blob next : enemyBlobs.getBlobs()) {
            if (checkBlobCollision(next, playerBlob) && next.getSize() > playerBlob.getSize()) {
                EventLog.getInstance().logEvent(new Event(next.getName() + " ate " + playerBlob.getName()));
                isGameOver = true;
            }
        }
    }

    private void checkWin() {
        if (playerBlob.getSize() >= GameScreen.RIGHT_WIDTH) {
            EventLog.getInstance().logEvent(
                    new Event(playerBlob.getName() + " won!"));
            isWin = true;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerBlob", playerBlob.toJson());
        json.put("abilities", abilities.toJson());
        json.put("enemyBlobs", enemyBlobs.toJson());
        return json;
    }
}
