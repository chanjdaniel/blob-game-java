package ui;

import exceptions.InvalidInputException;
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
    private Blob player;
    private Abilities abilities;
    private Blobs enemyBlobs;
    private BlobGame blobGame;
    private Scanner input;
    boolean keepGoing = true;

    // EFFECTS: runs the blob game application
    public BlobApp() {
        runBlob();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBlob() {
        String command;

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
        doQuitGame();
    }

    // MODIFIES: this
    // EFFECTS: initializes new blob game or loads a saved blob game
    private void init() {
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
        } else {
            initSaved();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes new blob game with player blob, enemy blobs, and available abilities
    private void initNew() {
        String playerName = setPlayerName();
        Color playerColor;
        try {
            playerColor = setPlayerColor();
        } catch (ReflectiveOperationException e) {
            System.out.println("Something went wrong with the colors! Continuing with cyan as default...");
            playerColor = Color.cyan;
        }

        blobGame = new BlobGame(playerName, playerColor);
        player = blobGame.getPlayer();
        abilities = blobGame.getAbilities();
        enemyBlobs = blobGame.getEnemyBlobs();
    }

    // MODIFIES: this
    // EFFECTS: loads saved blob game with player blob, enemy blobs, and available abilities
    private void initSaved() {
        doLoadBlobGame();
        player = blobGame.getPlayer();
        abilities = blobGame.getAbilities();
        enemyBlobs = blobGame.getEnemyBlobs();
    }

    // EFFECTS: processes quitting of the game
    private void doQuitGame() {
        String command = "";

        while (!command.equals("y") && !command.equals("n")) {
            System.out.println("Save before quitting?");
            System.out.println("y / n");
            command = input.next();
            command = command.toLowerCase();
        }

        if (command.equals("y")) {
            doSaveBlobGame();
        }
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "e":
                doEatBlob();
                break;
            case "p":
                doPlayerInfo();
                break;
            case "s":
                doSaveBlobGame();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
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

        switch (selection) {
            case "s":
                doPlayerStatus();
                break;
            case "a":
                doViewAbilities();
                break;
            case "v":
                doViewVictims();
                break;
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
        try {
            player.eatBlob(food); // eats blob, food is added to player.victims and player.size grows by food.size
            System.out.println("You ate " + food.getName() + "!");
            enemyBlobs.removeBlob(food); // food is removed from game
        } catch (InvalidInputException e) {
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
        String selection = "";

        while (selection.isEmpty()) {
            System.out.println("Name your blob!");
            selection = input.next();
        }

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
        return (Color) field.get(null);
    }

    // EFFECTS: returns a file path for a JSONStore from jsonStoreName
    private String generateJsonStore(String jsonStoreName) {
        return "./data/saves/" + jsonStoreName + ".json";
    }

    // EFFECTS: saves the blob game to file with jsonStore
    private void saveBlobGame(String jsonStore) {
        try {
            JsonWriter jsonWriter = new JsonWriter(jsonStore);
            jsonWriter.open();
            jsonWriter.write(blobGame);
            jsonWriter.close();
            System.out.println("Saved game to " + jsonStore);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonStore);
        }
    }

    // EFFECTS: saves the blob game to file with save name
    private void doSaveBlobGame() {
        System.out.println("Enter your save name...");
        String selection = input.next();
        String jsonStore = generateJsonStore(selection);
        saveBlobGame(jsonStore);
    }

    // MODIFIES: this
    // EFFECTS: loads blob game from file with jsonStore
    private void loadBlobGame(String jsonStore) {
        try {
            JsonReader jsonReader = new JsonReader(jsonStore);
            blobGame = jsonReader.read();
            System.out.println("Loaded game from " + jsonStore);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + jsonStore);
            doLoadBlobGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads blob game from file with save name
    private void doLoadBlobGame() {
        System.out.println("Enter your save name...");
        String selection = input.next();
        String jsonStore = generateJsonStore(selection);
        loadBlobGame(jsonStore);
    }
}