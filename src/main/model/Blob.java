package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;
import java.util.ArrayList;

// Represents a blob having a name, size (diameter of circle), x and y position, color,
// list of abilities, and list of names of blobs eaten
public class Blob implements Writable {
    private final String name;
    private int size;
    private int speed;
    private double positionX;
    private int movementX;
    private double positionY;
    private int movementY;
    private final Color color;
    private ArrayList<Ability> abilities;
    private Blobs victims;

    // For creating a new blob
    // REQUIRES: initialSize > 0
    // EFFECTS: constructs a blob;
    // movement is 0, abilities is new arraylist, victims is new blobs; others are given as parameters
    public Blob(String name, int size, int speed, double positionX, double positionY, Color color) {
        this.name = name;
        this.size = size;
        this.speed = speed;
        this.positionX = positionX;
        this.movementX = 0;
        this.positionY = positionY;
        this.movementY = 0;
        this.color = color;
        this.abilities = new ArrayList<>();
        this.victims = new Blobs();
    }

    // For loading a blob
    // REQUIRES: initialSize > 0
    // EFFECTS: constructs a blob; all fields given as parameters
    public Blob(String name, int size, int speed, double positionX, int movementX, double positionY,
                int movementY, Color color, ArrayList<Ability> abilities, Blobs victims) {
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

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setMovementX(int movementX) {
        this.movementX = movementX;
    }

    public int getMovementX() {
        return movementX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public int getMovementY() {
        return movementY;
    }

    public void setMovementY(int movementY) {
        this.movementY = movementY;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public ArrayList<Blob> getVictims() {
        return victims.getBlobs();
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
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
        json.put("abilities", abilitiesToJson());
        json.put("victims", victims.toJson());
        return json;
    }

    // EFFECTS: returns color as a JSONobject
    private JSONObject colorToJson() {
        JSONObject json = new JSONObject();
        json.put("r", color.getRed());
        json.put("g", color.getGreen());
        json.put("b", color.getBlue());
        return json;
    }

    // EFFECTS: returns abilities as a JSON array
    private JSONObject abilitiesToJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Ability a : abilities) {
            jsonArray.put(a.toJson());
        }

        jsonObject.put("abilities", jsonArray);
        return jsonObject;
    }
}
