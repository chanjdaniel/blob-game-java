package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

// Represents a list of blob
public class Blobs implements Writable {
    private ArrayList<Blob> blobs;

    // EFFECTS: blobs is set to empty ArrayList<Blob>
    public Blobs() {
        blobs = new ArrayList<>();
    }

    public ArrayList<Blob> getBlobs() {
        return blobs;
    }

    public void addBlob(Blob blob) {
        blobs.add(blob);
    }

    public void removeBlob(Blob blob) {
        blobs.remove(blob);
    }

    // EFFECTS: returns list of names of blobs in this.blobs
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();

        for (Blob next : blobs) {
            names.add(next.getName());
        }

        return names;
    }

    // EFFECTS: returns list of names of blobs in this.blobs in lower case
    public ArrayList<String> getLowerCaseNames() {
        ArrayList<String> lowerCaseNames = new ArrayList<>();

        for (Blob next : blobs) {
            lowerCaseNames.add(next.getName().toLowerCase());
        }

        return lowerCaseNames;
    }

    // REQUIRES: one, and only one Blob with name (not case-sensitive) is in blobs
    // EFFECTS: returns Blob in blobs with matching name
    public Blob getByName(String name) {
        ArrayList<String> lowerCaseNames = this.getLowerCaseNames();
        int index = lowerCaseNames.indexOf(name);

        return blobs.get(index);
    }

    // EFFECTS: returns a random int between the values min and max (inclusive)
    public int randIntBetweenValues(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    // REQUIRES: this.blobs is empty; blobsToMake > 0; blobNames.size() >= blobsToMake
    // MODIFIES: this
    // EFFECTS: creates n = blobsToMake new blobs with random size [1, 50], random color,
    //          and name from blobNames, and adds them to this.blobs
    public void makeBlobs(int blobsToMake, ArrayList<String> blobNames) {
        int minSize = 1;
        int maxSize = 50;
        int minRGB = 0;
        int maxRGB = 255;

        for (int i = blobsToMake; i > 0; i--) {
            String name = blobNames.get(i - 1);
            int size = randIntBetweenValues(minSize, maxSize);
            int r = randIntBetweenValues(minRGB, maxRGB);
            int g = randIntBetweenValues(minRGB, maxRGB);
            int b = randIntBetweenValues(minRGB, maxRGB);
            Color color = new Color(r, g, b);

            Blob newBlob;
            newBlob = new Blob(name, size, color);
            this.addBlob(newBlob);
        }
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
