package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.Writable;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static model.BlobGame.randIntBetweenValues;

// Represents a list of blob
public class Blobs implements Writable {
    private ArrayList<Blob> blobs;
    private JSONArray jsonNames;
    public static final int MIN_SIZE = 10;
    public static final int MAX_SIZE = 50;
    public static final int MIN_SPEED = 1;
    public static final int MAX_SPEED = 8;

    // EFFECTS: blobs is set to empty ArrayList<Blob>
    public Blobs() {
        blobs = new ArrayList<>();

        // Reads blobNames from file
        readBlobNames();
    }

    // EFFECTS: blobs is set to lob
    public Blobs(ArrayList<Blob> lob) {
        blobs = new ArrayList<>(lob);

        // Reads blobNames from file
        readBlobNames();
    }

    public ArrayList<Blob> getBlobs() {
        return blobs;
    }

    public void setBlobs(ArrayList<Blob> blobs) {
        this.blobs = blobs;
    }

    public JSONArray getJsonNames() {
        return jsonNames;
    }

    public void addBlob(Blob blob) {
        blobs.add(blob);
    }

    public void removeBlob(Blob blob) {
        blobs.remove(blob);
    }

    // MODIFIES: this
    // EFFECTS: reads blobNames from file and returns list of names
    public void readBlobNames() {
        String nameSource = "./data/blobNames.json";
        JsonReader reader = new JsonReader(nameSource);
        String jsonData = null;
        try {
            jsonData = reader.readFile(nameSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonNames = new JSONArray(jsonData);
    }

    // MODIFIES: this
    // EFFECTS: generates a new random blob and adds it to blobs
    public void addRandomBlob() {
        String name = getRandomName();

        int size = randIntBetweenValues(MIN_SIZE, MAX_SIZE);

        int speed = randIntBetweenValues(MIN_SPEED, MAX_SPEED);

        ArrayList<Double> randomBoundaryXY = makeRandomBoundaryPositionXY();
        double positionX = randomBoundaryXY.get(0);
        double positionY = randomBoundaryXY.get(1);

        int minRGB = 0;
        int maxRGB = 255;
        int r = randIntBetweenValues(minRGB, maxRGB);
        int g = randIntBetweenValues(minRGB, maxRGB);
        int b = randIntBetweenValues(minRGB, maxRGB);
        Color color = new Color(r, g, b);

        Blob blob = new Blob(name, size, speed, positionX, positionY, color);
        this.addBlob(blob);
    }

    private String getRandomName() {
        int index = randIntBetweenValues(0, jsonNames.length() - 1);
        return jsonNames.getString(index);
    }

    // EFFECTS: creates an ArrayList of Double with a random XY position on the boundary of the screen
    //          positionX at index 0; positionY at index 1
    private ArrayList<Double> makeRandomBoundaryPositionXY() {

        // (yes, this coin has 4 sides)
        double randX = randIntBetweenValues(0, GameScreen.RIGHT_WIDTH);
        double randY = randIntBetweenValues(0, GamePanel.HEIGHT);
        int coinFlip = randIntBetweenValues(0, 3);

        return boundaryChooser(coinFlip, randX, randY);
    }

    public ArrayList<Double> boundaryChooser(int coinFlip, double randX, double randY) {
        ArrayList<Double> positionXY = new ArrayList<>();
        double positionX = 0.0;
        double positionY = 0.0;
        switch (coinFlip) {
            case 0: // north
                positionX = randX;
                break;
            case 1: // east
                positionX = GameScreen.RIGHT_WIDTH;
                positionY = randY;
                break;
            case 2: // south
                positionX = randX;
                positionY = GamePanel.HEIGHT;
                break;
            case 3: // west
                positionY = randY;
                break;
        }
        positionXY.add(positionX);
        positionXY.add(positionY);
        return positionXY;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("blobs", blobsToJson());
        return json;
    }

    private JSONArray blobsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Blob b : blobs) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }
}
