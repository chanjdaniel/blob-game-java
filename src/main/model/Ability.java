package model;

// Represents an ability having a name and a description
public class Ability {
    private String name;
    private String description;

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
}
