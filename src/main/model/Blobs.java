package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.Writable;
import ui.game.GamePanel;
import ui.game.GameScreen;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static model.BlobGame.randIntBetweenValues;

// Represents a list of blob
public class Blobs implements Writable {
    private ArrayList<Blob> blobs;
    private JSONArray jsonNames;

    // EFFECTS: blobs is set to empty ArrayList<Blob>
    public Blobs() {
        blobs = new ArrayList<>();

        // Reads blobNames from file
        try {
            readBlobNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: blobs is set to lob
    public Blobs(ArrayList<Blob> lob) {
        blobs = new ArrayList<>(lob);

        // Reads blobNames from file
        try {
            readBlobNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void readBlobNames() throws IOException {
        String nameSource = "./data/blobNames.json";
        JsonReader reader = new JsonReader(nameSource);
        String jsonData = reader.readFile(nameSource);

        jsonNames = new JSONArray(jsonData);
    }

    // MODIFIES: this
    // EFFECTS: generates a new random blob and adds it to blobs
    public void addRandomBlob() {
        String name = getRandomName();

        int minSize = 10;
        int maxSize = 50;
        int size = randIntBetweenValues(minSize, maxSize);

        int minSpeed = 1;
        int maxSpeed = 5;
        int speed = randIntBetweenValues(minSpeed, maxSpeed);

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
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"}) // long method due to switch
    private ArrayList<Double> makeRandomBoundaryPositionXY() {
        ArrayList<Double> positionXY = new ArrayList<>();
        double randX = randIntBetweenValues(0, GameScreen.RIGHT_WIDTH);
        double randY = randIntBetweenValues(0, GamePanel.HEIGHT);
        double positionX = 0.0;
        double positionY = 0.0;

        // (yes, this coin has 4 sides)
        int coinFlip = randIntBetweenValues(0, 3);
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
