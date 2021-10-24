package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an ability having a name and a description
public class Ability implements Writable {
    private final String name;
    private final String description;

    // REQUIRES: abilityName has length > 0;
    // EFFECTS: name of ability is set to abilityName;
    // description of ability is set to abilityDescription;
    public Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        return json;
    }
}
