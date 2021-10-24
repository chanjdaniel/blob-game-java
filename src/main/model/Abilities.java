package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of ability
public class Abilities implements Writable {
    private ArrayList<Ability> abilities;

    // EFFECTS: abilities is set to empty ArrayList<Ability>
    public Abilities() {
        abilities = new ArrayList<>();
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public ArrayList<Ability> getAbilities() {
        return this.abilities;
    }

    // EFFECTS: returns list of names of abilities in this.abilities
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();

        for (Ability next : abilities) {
            names.add(next.getName());
        }

        return names;
    }

    // EFFECTS: return list of names of abilities in this.abilities in lower case
    public ArrayList<String> getLowerCaseNames() {
        ArrayList<String> lowerCaseNames = new ArrayList<>();

        for (String next : this.getNames()) {
            lowerCaseNames.add(next.toLowerCase());
        }

        return lowerCaseNames;
    }

    // REQUIRES: one, and only one Ability with name (not case-sensitive) is in abilities
    // EFFECTS: returns Ability in abilities with matching name
    public Ability getByName(String name) {
        ArrayList<String> lowerCaseNames = this.getLowerCaseNames();
        int index = lowerCaseNames.indexOf(name.toLowerCase());

        return abilities.get(index);
    }

    // REQUIRES: abilityName has length > 0; ability with abilityName is not already in this.abilities
    // MODIFIES: this.abilities
    // EFFECTS: creates new ability with name abilityName and empty description;
    //          adds new ability to this.abilities
    public void addNewAbility(String name) {
        Ability newAbility;
        newAbility = new Ability(name, "");

        abilities.add(newAbility);
    }

    // REQUIRES: this.abilities is empty
    // MODIFIES: this
    // EFFECTS: for each name in list of names, creates a new ability with name and empty description and
    //          adds to this.abilities
    public void makeAbilities(ArrayList<String> names) {
        for (String next : names) {
            this.addNewAbility(next);
        }
    }

    // REQUIRES: one, and only one Ability with name (not case-sensitive) is in abilities
    // MODIFIES: this.abilities
    // EFFECTS: removes ability in this.abilities with matching name
    public void removeByName(String name) {
        Ability toRemove = getByName(name);
        abilities.remove(toRemove);
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
