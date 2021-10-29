package model;

import exceptions.InvalidInputException;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

// Represents the game state of the blob game
public class BlobGame implements Writable {
    private final int playerInitialSize = 15;
    private final ArrayList<String> abilityNames = new ArrayList<>(Arrays.asList(
            "Super Speed", "Physical Resistance", "Regeneration", "Sticky", "Heat Resistance",
            "Acid", "Digest", "Grow", "Jump", "Dash"));
    private int initialEnemies = 10;
    private final ArrayList<String> enemyBlobNames = new ArrayList<>(Arrays.asList(
            "Michael", "James", "Sam", "Tiffany", "Gordon",
            "Aaron", "Peter", "Hannah", "Jane", "Gary"));

    private Blob player;
    private Abilities abilities;
    private Blobs enemyBlobs;

    // Creates an initial blob game with player with name and color;
    public BlobGame(String playerName, Color playerColor) {

        // Creates player with name and color
        player = makePlayerBlob(playerName, playerColor);

        // Creates initial available abilities
        abilities = makeAbilities();

        // Creates initial enemy blobs
        enemyBlobs = makeBlobs();
    }

    // Creates a blob game with all fields given as parameters; for loading a saved game
    public BlobGame(Blob player, Abilities abilities, Blobs enemyBlobs) {

        this.player = player;
        this.enemyBlobs = enemyBlobs;
        this.abilities = abilities;
    }

    public Blob getPlayer() {
        return player;
    }

    public Abilities getAbilities() {
        return abilities;
    }

    public Blobs getEnemyBlobs() {
        return enemyBlobs;
    }

    // EFFECTS: returns ability in abilities with matching name; throws IndexOutOfBoundsException if none found
    public Ability getAbilityByName(String name) throws IndexOutOfBoundsException {
        return abilities.getByName(name);
    }

    // MODIFIES: this
    // EFFECTS: makes n = initialEnemies blobs using names in enemyBlobNames
    public Blobs makeBlobs() {

        Blobs blobs = new Blobs();
        try {
            blobs.makeBlobs(initialEnemies, enemyBlobNames);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        return blobs;
    }

    // MODIFIES: this
    // EFFECTS: makes n = abilityNames.size() abilities using names in abilityNames
    public Abilities makeAbilities() {

        Abilities abilities = new Abilities();
        try {
            abilities.makeAbilities(abilityNames);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        return abilities;
    }

    // MODIFIES: this
    // EFFECTS: makes player blob with name playerName, size as playerInitialSize, and color playerColor
    public Blob makePlayerBlob(String playerName, Color playerColor) {

        return new Blob(playerName, playerInitialSize, playerColor);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player", player.toJson());
        json.put("abilities", abilities.toJson());
        json.put("enemyBlobs", enemyBlobs.toJson());
        return json;
    }
}
