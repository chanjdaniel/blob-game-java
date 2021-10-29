package model;

import exceptions.InvalidInputException;
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

    // EFFECTS: returns first Ability in abilities with matching name, throws IndexOutOfBoundsException if none found
    public Ability getByName(String name) throws IndexOutOfBoundsException {
        ArrayList<String> lowerCaseNames = this.getLowerCaseNames();
        int index = lowerCaseNames.indexOf(name.toLowerCase());

        return abilities.get(index);
    }

    // MODIFIES: this.abilities
    // EFFECTS: creates new ability with name abilityName and empty description;
    //          adds new ability to this.abilities; throws InvalidInputException if name.length() <= 0
    public void addNewAbility(String name) throws InvalidInputException {
        if (name.length() <= 0) {
            throw new InvalidInputException();
        }
        try {
            this.getByName(name);
            throw new InvalidInputException();
        } catch (IndexOutOfBoundsException e) {
            Ability newAbility;
            newAbility = new Ability(name, "");

            abilities.add(newAbility);
        }
    }

    // MODIFIES: this
    // EFFECTS: for each name in list of names, creates a new ability with name and empty description and
    //          adds to this.abilities; does nothing if abilities is not empty
    public void makeAbilities(ArrayList<String> names) {
        if (abilities.size() == 0) {
            for (String next : names) {
                try {
                    this.addNewAbility(next);
                } catch (InvalidInputException e) {
                    // don't add duplicate
                }
            }
        }
    }

    // MODIFIES: this.abilities
    // EFFECTS: removes first ability in this.abilities with matching name
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
