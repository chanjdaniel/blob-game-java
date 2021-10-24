package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

// Represents a blob having a name, size (diameter of circle), color,
// list of abilities, and list of names of blobs eaten
public class Blob implements Writable {
    private String name;
    private int size;
    private Color color;
    private Abilities abilities;
    private Blobs victims;

    // REQUIRES: blobName has length > 0; initialSize > 0
    // EFFECTS: name of blob is set to blobName;
    //          size of blob is set to initialSize; color of blob is set to blobColor;
    //          abilities is set to new Abilities; victims is set to new Victims.
    public Blob(String name, int size, Color color) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.abilities = new Abilities();
        this.victims = new Blobs();
    }

    // REQUIRES: blobName has length > 0; initialSize > 0
    // EFFECTS: name of blob is set to blobName;
    //          size of blob is set to initialSize; color of blob is set to blobColor;
    //          this.abilities is set to abilities; this.victims is set to victims.
    public Blob(String name, int size, Color color, Abilities abilities, Blobs victims) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.abilities = abilities;
        this.victims = victims;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities.getAbilities();
    }

    public ArrayList<String> getAbilityNames() {
        return abilities.getNames();
    }

    public ArrayList<String> getLowerCaseAbilityNames() {
        return abilities.getLowerCaseNames();
    }

    public ArrayList<Blob> getVictims() {
        return victims.getBlobs();
    }

    public ArrayList<String> getVictimNames() {
        return victims.getNames();
    }

    public void addAbility(Ability ability) {
        abilities.addAbility(ability);
    }

    // REQUIRES: one, and only one ability with name abilityName is in this.abilities
    // MODIFIES: this.abilities
    // EFFECTS: removes ability with name abilityName in this.abilities
    public void removeAbility(String abilityName) {
        abilities.removeByName(abilityName);
    }

    // REQUIRES: this.size >= enemyBlob.getSize()
    // MODIFIES: this.victims
    // EFFECTS: eats another blob; adds enemyBlob.getName() to victims;
    //          increases this.size by enemyBlob.getSize()
    public void eatBlob(Blob enemyBlob) {
        victims.addBlob(enemyBlob);
        size += enemyBlob.getSize();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("size", size);
        json.put("color", colorToJson());
        json.put("abilities", abilities.toJson());
        json.put("victims", victims.toJson());
        return json;
    }

    private JSONObject colorToJson() {
        JSONObject json = new JSONObject();
        json.put("r", color.getRed());
        json.put("g", color.getGreen());
        json.put("b", color.getBlue());
        return json;
    }
}
