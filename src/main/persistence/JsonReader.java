package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// CITATION: code modeled after code provided in JsonSerializationDemo
// Represents a reader that reads blob game from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads blob game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BlobGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBlobGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses blob game from JSON object and returns it
    private BlobGame parseBlobGame(JSONObject jsonObject) {
        Blob player = parseBlob(jsonObject.getJSONObject("player"));
        Abilities abilities = parseAbilities(jsonObject.getJSONObject("abilities"));
        Blobs enemyBlobs = parseBlobs(jsonObject.getJSONObject("enemyBlobs"));

        return new BlobGame(player, abilities, enemyBlobs);
    }

    // EFFECTS: parses blob from JSON object and returns it
    private Blob parseBlob(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int size = jsonObject.getInt("size");
        Color color = parseColor(jsonObject.getJSONObject("color"));
        Abilities abilities = parseAbilities(jsonObject.getJSONObject("abilities"));
        Blobs victims = parseBlobs(jsonObject.getJSONObject("victims"));

        return new Blob(name, size, color, abilities, victims);
    }

    // EFFECTS: parses color from JSON object and returns it
    private Color parseColor(JSONObject jsonObject) {
        int r = jsonObject.getInt("r");
        int g = jsonObject.getInt("g");
        int b = jsonObject.getInt("b");

        return new Color(r,g,b);
    }

    // EFFECTS: parses abilities from JSON object and returns it
    private Abilities parseAbilities(JSONObject jsonObject) {
        Abilities ab = new Abilities();
        addAbilities(ab, jsonObject);
        return ab;
    }

    // MODIFIES: ab
    // EFFECTS: parses abilities from JSON object and adds them to abilities
    private void addAbilities(Abilities ab, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("abilities");
        for (Object json : jsonArray) {
            JSONObject nextAbility = (JSONObject) json;
            addAbility(ab, nextAbility);
        }
    }

    // MODIFIES: ab
    // EFFECTS: parses ability from JSON object and adds it to abilities
    private void addAbility(Abilities ab, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        Ability ability = new Ability(name, description);
        ab.addAbility(ability);
    }

    // EFFECTS: parses blobs from JSON object and returns it
    private Blobs parseBlobs(JSONObject jsonObject) {
        Blobs bbs = new Blobs();
        addBlobs(bbs, jsonObject);
        return bbs;
    }

    // MODIFIES: bbs
    // EFFECTS: parses blob from JSON object and adds them to blobs
    private void addBlobs(Blobs bbs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("blobs");
        for (Object json : jsonArray) {
            JSONObject nextBlob = (JSONObject) json;
            addBlob(bbs, nextBlob);
        }
    }

    // MODIFIES: bbs
    // EFFECTS: parses blob from JSON object and adds it to blobs
    private void addBlob(Blobs bbs, JSONObject jsonObject) {
        Blob blob = parseBlob(jsonObject);
        bbs.addBlob(blob);
    }
}
