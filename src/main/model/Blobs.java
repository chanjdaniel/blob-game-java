package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

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

    // EFFECTS: returns first Blob in blobs with matching name, throws IndexOutOfBoundsException if none found
    public Blob getByName(String name) throws IndexOutOfBoundsException {
        ArrayList<String> lowerCaseNames = this.getLowerCaseNames();
        int index = lowerCaseNames.indexOf(name);

        return blobs.get(index);
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
