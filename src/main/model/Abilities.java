package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.Writable;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.io.IOException;
import java.util.ArrayList;

import static model.BlobGame.randIntBetweenValues;

// Represents a list of ability
public class Abilities implements Writable {
    private ArrayList<Ability> abilities;
    private ArrayList<Ability> jsonAbilities;

    // EFFECTS: abilities is set to empty ArrayList<Ability>
    public Abilities() {
        abilities = new ArrayList<>();

        // Reads abilities from file
        try {
            readAbilities();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: abilities is set to loa
    public Abilities(ArrayList<Ability> loa) {
        abilities = new ArrayList<>(loa);

        // Reads abilities from file
        try {
            readAbilities();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ability> getAbilities() {
        return this.abilities;
    }

    public ArrayList<Ability> getJsonAbilities() {
        return jsonAbilities;
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void removeAbility(Ability ability) {
        abilities.remove(ability);
    }

    // MODIFIES: this
    // EFFECTS: reads abilities from file and returns abilities
    private void readAbilities() throws IOException {
        String abilitiesSource = "./data/abilities.json";
        JsonReader reader = new JsonReader(abilitiesSource);
        String jsonData = reader.readFile(abilitiesSource);
        JSONObject jsonObject = new JSONObject(jsonData);

        jsonAbilities = reader.parseArrayListAbility(jsonObject);
    }

    public void addRandomAbility() {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("abilities", abilitiesToJson());
        return json;
    }

    // EFFECTS: returns Abilities in abilities as a JSON array
    private JSONArray abilitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ability a : abilities) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
