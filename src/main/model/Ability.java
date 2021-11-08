package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an ability having a name, description, stat to alter, and value to add
public class Ability implements Writable {
    private final String name;
    private final String description;
    private final String stat;
    private final int value;

    // EFFECTS: name of ability is set to abilityName;
    // description of ability is set to abilityDescription;
    // stat to alter is set to stat; value to add is set to value
    public Ability(String name, String description, String stat, int value) {
        this.name = name;
        this.description = description;
        this.stat = stat;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStat() {
        return stat;
    }

    public int getValue() {
        return value;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("stat", stat);
        json.put("value", value);
        return json;
    }
}
