package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an ability having a name, description, stat to alter, and value to add
public class Ability implements Writable {
    private final String name;
    private final String description;
    private final String stat;
    private final int value;
    private final double positionX;
    private final double positionY;

    // EFFECTS: name of ability is set to abilityName;
    // description of ability is set to abilityDescription;
    // stat to alter is set to stat; value to add is set to value
    public Ability(String name, String description, String stat, int value, double positionX, double positionY) {
        this.name = name;
        this.description = description;
        this.stat = stat;
        this.value = value;
        this.positionX = positionX;
        this.positionY = positionY;
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

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("stat", stat);
        json.put("value", value);
        json.put("positionX", positionX);
        json.put("positionY", positionY);
        return json;
    }
}
