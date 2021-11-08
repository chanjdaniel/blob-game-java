package model;

import exceptions.InvalidInputException;
import org.json.JSONObject;
import persistence.Writable;
import ui.Screen;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;
import java.util.ArrayList;

// Represents a blob having a name, size (diameter of circle), x and y position, color,
// list of abilities, and list of names of blobs eaten
public class Blob implements Writable {
    private String name;
    private final int playerInitialSize = 15;
    private int size;
    private int speed;
    private double positionX;
    private int movementX;
    private double positionY;
    private int movementY;
    private Color color;
    private Abilities abilities;
    private Blobs victims;

    // REQUIRES: blobName has length > 0; initialSize > 0
    // EFFECTS: name of blob is set to blobName;
    //          size of blob is set to initialSize; color of blob is set to blobColor;
    //          abilities is set to new Abilities; victims is set to new Victims.
    public Blob(String name, int size, int speed, double positionX, double positionY, Color color) {
        this.name = name;
        this.size = size;
        this.speed = speed;
        this.positionX = positionX;
        this.movementX = 0;
        this.positionY = positionY;
        this.movementY = 0;
        this.color = color;
        this.abilities = new Abilities();
        this.victims = new Blobs();
    }

    // EFFECTS: name of blob is set to blobName;
    //          size of blob is set to initialSize; color of blob is set to blobColor;
    //          this.abilities is set to abilities; this.victims is set to victims.
    public Blob(String name, int size, int speed, double positionX, int movementX, double positionY,
                int movementY, Color color, Abilities abilities, Blobs victims) {
        this.name = name;
        this.size = size;
        this.speed = speed;
        this.positionX = positionX;
        this.movementX = movementX;
        this.positionY = positionY;
        this.movementY = movementY;
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

    public int getSpeed() {
        return speed;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setMovementX(int movementX) {
        this.movementX = movementX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setMovementY(int movementY) {
        this.movementY = movementY;
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

    // MODIFIES: this.abilities
    // EFFECTS: removes first ability with name abilityName in this.abilities
    public void removeAbility(String abilityName) {
        abilities.removeByName(abilityName);
    }

    // REQUIRES: this.getSize() > enemyBlob.getSize()
    // MODIFIES: this.victims
    // EFFECTS: eats another blob; adds enemyBlob.getName() to victims;
    // increases this.size by sqrt of enemyBlob.getSize();
    public void eatBlob(Blob enemyBlob) {
        victims.addBlob(enemyBlob);
        size += enemyBlob.getSize() / 2;
    }

    // Updates the blob on clock tick
    // MODIFIES: this
    // EFFECTS:  blob is moved movementX units in X-axis and movementY units in Y-axis;
    //           blob is constrained to remain within boundaries of game;
    //           adjusted so that positive Y moves the blob up, and positive X moves the blob right
    public void move() {
        positionX = positionX + movementX;
        positionY = positionY - movementY;

        handleBoundary();
    }

    // Constrains blob so that it doesn't travel off sides of screen
    // MODIFIES: this
    // EFFECTS: blob's centre is constrained to remain within boundaries of game
    private void handleBoundary() {
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > GameScreen.RIGHT_WIDTH) {
            positionX = GameScreen.RIGHT_WIDTH;
        }
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > GamePanel.HEIGHT - 30) {
            positionY = GamePanel.HEIGHT - 30;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("size", size);
        json.put("speed", speed);
        json.put("positionX", positionX);
        json.put("movementX", movementX);
        json.put("positionY", positionY);
        json.put("movementY", movementY);
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
