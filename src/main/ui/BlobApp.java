package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Blob game application
public class BlobApp {
    private static final String JSON_STORE = "./data/blobGame.json";
    private Blob player;
    private Abilities abilities;
    private Blobs enemyBlobs;
    private BlobGame blobGame;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    boolean keepGoing = true;

    // EFFECTS: runs the blob game application
    public BlobApp() throws IOException, NoSuchFieldException,
            ClassNotFoundException, IllegalAccessException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBlob();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBlob() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("e")) {
            doEatBlob();
        } else if (command.equals("p")) {
            doPlayerInfo();
        } else if (command.equals("s")) {
            saveBlobGame();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes new blob game or loads a saved blob game
    private void init() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        String selection = "";

        while (!selection.equals("n") && !selection.equals("l")) {
            System.out.println("\nChoose an option:");
            System.out.println("\tn -> start a new game");
            System.out.println("\tl - > load a saved game");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("n")) {
            initNew();
        } else if (selection.equals("l")) {
            initSaved();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes new blob game with player blob, enemy blobs, and available abilities
    private void initNew() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        String playerName = setPlayerName();
        Color playerColor = setPlayerColor();

        blobGame = new BlobGame(playerName, playerColor);
        player = blobGame.getPlayer();
        abilities = blobGame.getAbilities();
        enemyBlobs = blobGame.getEnemyBlobs();
    }

    // MODIFIES: this
    // EFFECTS: loads saved blob game with player blob, enemy blobs, and available abilities
    private void initSaved() {
        loadBlobGame();
        player = blobGame.getPlayer();
        abilities = blobGame.getAbilities();
        enemyBlobs = blobGame.getEnemyBlobs();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\te -> eat a blob");
        System.out.println("\tp -> view and edit player info");
        System.out.println("\ts -> save game");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of options for player info to user
    private void displayPlayerInfoMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> view player status");
        System.out.println("\ta -> view and edit abilities");
        System.out.println("\tv -> view victims");
        System.out.println("\tm -> return to menu");
    }

    // EFFECTS: conducts player info
    private void doPlayerInfo() {
        String selection = ""; // forces entry into loop

        while (!selection.equals("s") && !selection.equals("a")
                && !selection.equals("v") && !selection.equals("m")) {
            displayPlayerInfoMenu();
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("s")) {
            doPlayerStatus();
        } else if (selection.equals("a")) {
            doViewAbilities();
        } else if (selection.equals("v")) {
            doViewVictims();
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the selection portion of eating of a blob
    private void doEatBlob() {
        String selection = ""; // force entry into loop
        ArrayList<String> enemyBlobLowerCaseNames = enemyBlobs.getLowerCaseNames();

        while (!enemyBlobLowerCaseNames.contains(selection)) {
            // prints out names and sizes of enemy blobs
            ArrayList<String> enemyBlobNames = enemyBlobs.getNames();
            System.out.println("Blobs:");
            for (String next : enemyBlobNames) {
                int index = enemyBlobNames.indexOf(next);
                int size = enemyBlobs.getBlobs().get(index).getSize();
                System.out.print(next + ": ");
                System.out.println(size);
            }
            System.out.print("Type the name of the blob you want to try to eat: ");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        Blob food = enemyBlobs.getByName(selection);
        doEatBlob(food);
    }

    // MODIFIES: this
    // EFFECTS: conducts the eating portion of eating a blob;
    //          eats the selected blob if player.getSize() >= food.getSize(),
    //          else player gets eaten and game is over
    private void doEatBlob(Blob food) {
        if (player.getSize() >= food.getSize()) {
            System.out.println("You ate " + food.getName() + "!");
            player.eatBlob(food); // eats blob, food is added to player.victims and player.size grows by food.size
            enemyBlobs.removeBlob(food); // food is removed from game
        } else {
            System.out.println("They were bigger than you! You got eaten. :(");
            keepGoing = false; // game over
        }
    }

    // EFFECTS: conducts viewing of player status
    private void doPlayerStatus() {
        String selection = "";
        String name = player.getName();
        int size = player.getSize();
        int numVictims = player.getVictimNames().size();

        System.out.println("Your blob's name: " + name);
        System.out.println("Your blob's size: " + size);
        System.out.println("How many blob's you have eaten: " + numVictims);

        while (selection.isEmpty()) {
            System.out.println("Enter any key to return to the menu...");
            selection = input.next();
        }
    }

    // EFFECTS: conducts viewing of abilities
    private void doViewAbilities() {
        String selection = "";

        System.out.println("Your blob's abilities:");
        for (String next : player.getAbilityNames()) {
            System.out.println(next);
        }

        while (!selection.equals("a") && !selection.equals("r") && !selection.equals("m")) {
            // menu for editing abilities
            System.out.println(" ");
            System.out.println("\nChoose an option:");
            System.out.println("\ta -> add an ability");
            System.out.println("\tr - > remove an ability");
            System.out.println("\tm - > return to menu");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("a")) {
            doAddAbility();
        } else if (selection.equals("r")) {
            doRemoveAbility();
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts adding of an ability
    private void doAddAbility() {
        String selection = ""; // force entry into loop
        Ability ability;
        ArrayList<String> abilityNames = abilities.getLowerCaseNames();
        ArrayList<String> playerAbilityNames = player.getLowerCaseAbilityNames();

        // checks whether ability with name selection exists, and whether player already has it
        while (!abilityNames.contains(selection) || playerAbilityNames.contains(selection)) {
            System.out.println("Available abilities:");
            for (String next : blobGame.getAbilities().getNames()) {
                System.out.println(next);
            }

            System.out.println(" ");
            System.out.println(
                    "Type the name of the ability you want to add! (You can't add abilities you already have!)");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        ability = abilities.getByName(selection);
        player.addAbility(ability);
    }

    // MODIFIES: this
    // EFFECTS: conducts removal of an ability
    private void doRemoveAbility() {
        String selection = ""; // force entry into loop
        ArrayList<String> abilityNames = player.getLowerCaseAbilityNames();

        // checks whether player has ability with name selection
        while (!abilityNames.contains(selection)) {
            System.out.println("Your abilities:");
            for (String next : player.getAbilityNames()) {
                System.out.println(next);
            }
            System.out.println(" ");
            System.out.println("Type the name of the ability you want to remove!");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        player.removeAbility(selection);
    }

    // EFFECTS: conducts viewing of victims
    private void doViewVictims() {
        String selection = ""; // force entry into loop

        for (String next : player.getVictimNames()) {
            System.out.println(next);
        }

        while (selection.isEmpty()) {
            System.out.println("Press any key to return to the menu...");
            selection = input.next();
        }
    }

    // EFFECTS: returns player blob's name as entered by user
    public String setPlayerName() {
        System.out.println("Name your blob!");
        String selection = input.next();

        return selection;
    }

    // EFFECTS: returns player blob's color as entered by user
    public Color setPlayerColor() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        String selection = ""; // force entry into loop
        ArrayList<String> colors = new ArrayList<>(Arrays.asList(
                "Red", "Green", "Blue", "Cyan", "Magenta",
                "Orange", "Pink", "Yellow", "Black", "Gray"));

        ArrayList<String> lowerCaseColors = new ArrayList<>();
        for (String next : colors) {
            lowerCaseColors.add(next.toLowerCase());
        }

        while (!lowerCaseColors.contains(selection)) {
            // prints out names of available colors
            System.out.println("Choose a color!");
            for (String next : colors) {
                System.out.println(next);
            }
            selection = input.next();
            selection = selection.toLowerCase();
        }

        Field field = Class.forName("java.awt.Color").getField(selection);
        Color color = (Color)field.get(null);
        return color;
    }

    // EFFECTS: saves the blob game to file
    private void saveBlobGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(blobGame);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads blob game from file
    private void loadBlobGame() {
        try {
            blobGame = jsonReader.read();
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}