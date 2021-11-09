package model;

import exceptions.InvalidInputException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.Writable;
import ui.Screen;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static ui.game.GameScreen.INTERVAL;

// Represents the game state of the blob game
public class BlobGame implements Writable {
    private static final int PLAYER_INITIAL_SIZE = 15;
    private static final int PLAYER_INITIAL_SPEED = 2;
    private static final int MAX_ENEMIES = 10;
    private static final int NEW_ENEMY_RATE = 1000;
    private static final int MAX_ABILITIES = 3;
    private static final int NEW_ABILITY_RATE = 5000;
    private JSONArray jsonNames;
    private int newEnemyCounter;
    private int newAbilityCounter;
    private boolean isGameOver;
    private boolean isWin;

    private final Blob playerBlob;
    private ArrayList<Ability> abilities;
    private ArrayList<Ability> jsonAbilities;
    private Blobs enemyBlobs;

    // Creates an initial blob game with player with name and color;
    public BlobGame(String playerName, Color playerColor) throws InvalidInputException {

        // Creates player with name and color
        playerBlob = makePlayerBlob(playerName, playerColor);

        // Creates empty array list of ability
        abilities = new ArrayList<>();

        // Creates empty blobs
        enemyBlobs = new Blobs();

        // Reads abilities from file
        try {
            readAbilities();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reads blobNames from file
        try {
            readBlobNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Creates a blob game with all fields given as parameters; for loading a saved game
    public BlobGame(Blob player, ArrayList<Ability> abilities, Blobs enemyBlobs) {

        this.playerBlob = player;
        this.enemyBlobs = enemyBlobs;
        this.abilities = abilities;
        this.isGameOver = false;

        // Reads abilities from file
        try {
            readAbilities();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reads blobNames from file
        try {
            readBlobNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Blob getPlayerBlob() {
        return playerBlob;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public Blobs getEnemyBlobs() {
        return enemyBlobs;
    }


    // MODIFIES: this
    // EFFECTS: reads abilities from file and returns abilities
    private void readAbilities() throws IOException {
        String abilitiesSource = "./data/abilities.json";
        JsonReader reader = new JsonReader(abilitiesSource);
        String jsonData = reader.readFile(abilitiesSource);
        JSONObject jsonObject = new JSONObject(jsonData);

        jsonAbilities = reader.parseAbilities(jsonObject);
    }

    // MODIFIES: this
    // EFFECTS: reads blobNames from file and returns list of names
    private void readBlobNames() throws IOException {
        String nameSource = "./data/blobNames.json";
        JsonReader reader = new JsonReader(nameSource);
        String jsonData = reader.readFile(nameSource);

        jsonNames = new JSONArray(jsonData);
    }

    // MODIFIES: this
    // EFFECTS: makes player blob with name playerName, size as playerInitialSize,
    // and color playerColor at centre of screen
    public Blob makePlayerBlob(String playerName, Color playerColor) {
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
        addAbility();
        checkEnemyBlobsEat();
        checkPlayerEat();
        checkPlayerGainAbility();
        checkGameOver();
        checkWin();
    }

    // Controls the blob
    // MODIFIES: this
    // EFFECTS: moves the blob in response to key code
    public void blobControl(int keyCode) {
        int speed = playerBlob.getSpeed();
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_KP_UP) {
            playerBlob.setMovementY(speed);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_KP_LEFT) {
            playerBlob.setMovementX(speed * -1);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_KP_DOWN) {
            playerBlob.setMovementY(speed * -1);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_KP_RIGHT) {
            playerBlob.setMovementX(speed);
        }
    }

    // Stops the blob
    // MODIFIES: this
    // EFFECTS: stops the blob in response to key code
    public void blobStop(int keyCode) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_KP_UP) {
            playerBlob.setMovementY(0);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_KP_LEFT) {
            playerBlob.setMovementX(0);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_KP_DOWN) {
            playerBlob.setMovementY(0);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_KP_RIGHT) {
            playerBlob.setMovementX(0);
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

    // EFFECTS: returns a random int between the values min and max (inclusive)
    public int randIntBetweenValues(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    // MODIFIES: this
    // EFFECTS: generates a new random blob every NEW_ENEMY_RATE if number of enemy blobs is less than MAX_ENEMIES
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void addEnemyBlob() {
        if (newEnemyCounter >= NEW_ENEMY_RATE) {
            if (enemyBlobs.getBlobs().size() < MAX_ENEMIES) {
                String name = getRandomName();

                int minSize = 10;
                int maxSize = 50;
                int size = randIntBetweenValues(minSize, maxSize);

                int minSpeed = 1;
                int maxSpeed = 5;
                int speed = randIntBetweenValues(minSpeed, maxSpeed);

                ArrayList<Double> randomBoundaryXY = makeRandomBoundaryPositionXY();
                double positionX = randomBoundaryXY.get(0);
                double positionY = randomBoundaryXY.get(1);

                int minRGB = 0;
                int maxRGB = 255;
                int r = randIntBetweenValues(minRGB, maxRGB);
                int g = randIntBetweenValues(minRGB, maxRGB);
                int b = randIntBetweenValues(minRGB, maxRGB);
                Color color = new Color(r, g, b);

                Blob blob = new Blob(name, size, speed, positionX, positionY, color);
                enemyBlobs.addBlob(blob);
            }
            newEnemyCounter = 0;
        }
        newEnemyCounter += INTERVAL;
    }

    // MODIFIES: this
    // EFFECTS: adds a random ability every NEW_ABILITY_RATE if number of abilities is less than MAX_ABILITIES
    public void addAbility() {
        if (newAbilityCounter >= NEW_ABILITY_RATE) {
            if (abilities.size() < MAX_ABILITIES) {
                double randX = randIntBetweenValues(50, GameScreen.RIGHT_WIDTH - 50);
                double randY = randIntBetweenValues(50, GamePanel.HEIGHT - 50);
                int index = randIntBetweenValues(0, jsonAbilities.size() - 1);
                Ability jsonAbility = jsonAbilities.get(index);
                Ability newAbility = new Ability(
                        jsonAbility.getName(),
                        jsonAbility.getDescription(),
                        jsonAbility.getStat(),
                        jsonAbility.getValue(),
                        randX,
                        randY);

                abilities.add(newAbility);
            }
            newAbilityCounter = 0;
        }
        newAbilityCounter += INTERVAL;
    }

    private String getRandomName() {
        int index = randIntBetweenValues(0, jsonNames.length() - 1);
        return jsonNames.getString(index);
    }

    // EFFECTS: creates an ArrayList of Double with a random XY position on the boundary of the screen
    //          positionX at index 0; positionY at index 1
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"}) // long method due to switch
    private ArrayList<Double> makeRandomBoundaryPositionXY() {
        ArrayList<Double> positionXY = new ArrayList<>();
        double randX = randIntBetweenValues(0, GameScreen.RIGHT_WIDTH);
        double randY = randIntBetweenValues(0, GamePanel.HEIGHT);
        double positionX = 0.0;
        double positionY = 0.0;

        // (yes, this coin has 4 sides)
        int coinFlip = randIntBetweenValues(0, 3);
        switch (coinFlip) {
            case 0: // north
                positionX = randX;
                break;
            case 1: // east
                positionX = GameScreen.RIGHT_WIDTH;
                positionY = randY;
                break;
            case 2: // south
                positionX = randX;
                positionY = GamePanel.HEIGHT;
                break;
            case 3: // west
                positionY = randY;
                break;
        }

        positionXY.add(positionX);
        positionXY.add(positionY);
        return positionXY;
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
        for (Blob next : enemyBlobs.getBlobs()) {
            tempEnemyBlobs.addBlob(next);
        }
        for (Blob next : tempEnemyBlobs.getBlobs()) {
            if (checkBlobCollision(playerBlob, next) && playerBlob.getSize() > next.getSize()) {
                playerBlob.eatBlob(next);
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
        ArrayList<Ability> tempAbilities = new ArrayList<>();
        for (Ability next : abilities) {
            tempAbilities.add(next);
        }
        for (Ability next : tempAbilities) {
            if (checkAbilityCollision(playerBlob, next) && playerBlob.getAbilities().size() < 5) {
                playerBlob.addAbility(next);
                abilities.remove(next);
            }
        }
    }

    private void checkGameOver() {
        for (Blob next : enemyBlobs.getBlobs()) {
            if (checkBlobCollision(next, playerBlob) && next.getSize() > playerBlob.getSize()) {
                isGameOver = true;
            }
        }
    }

    private void checkWin() {
        if (playerBlob.getSize() >= GameScreen.RIGHT_WIDTH) {
            isWin = true;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerBlob", playerBlob.toJson());
        json.put("abilities", abilitiesToJson());
        json.put("enemyBlobs", enemyBlobs.toJson());
        return json;
    }

    // EFFECTS: returns abilities as a JSON array
    private JSONObject abilitiesToJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Ability a : abilities) {
            jsonArray.put(a.toJson());
        }

        jsonObject.put("abilities", jsonArray);
        return jsonObject;
    }
}
