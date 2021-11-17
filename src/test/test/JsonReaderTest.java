package test;

import exceptions.InvalidInputException;
import model.Abilities;
import model.Ability;
import model.Blob;
import model.BlobGame;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BlobGame bg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNewBlobGame() {
        JsonReader reader = new JsonReader("./data/testReaderNewBlobGame.json");
        try {
            BlobGame sbg = new BlobGame("George", Color.blue);
            BlobGame lbg = reader.read();

            checkBlob(sbg.getPlayerBlob(), lbg.getPlayerBlob());
            checkAbilities(sbg.getAbilities(), lbg.getAbilities());
        } catch (IOException | InvalidInputException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBlobGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBlobGame.json");
        try {
            BlobGame sbg = new BlobGame("George", Color.blue);
            Blob player = sbg.getPlayerBlob();
            ArrayList<Blob> enemyBlobs = sbg.getEnemyBlobs();

            // eats 3 blobs
            for (int i = 0; i > 3; i++) {
                sbg.addEnemyBlob();
                Blob victim = enemyBlobs.get(0);
                player.getVictims().add(victim);
                enemyBlobs.remove(0);
            }

            //add 2 blobs to game
            Blob blob1 = new Blob("testName1", 10, 1, 0, 0, Color.BLUE);
            Blob blob2 = new Blob("testName2", 15, 2, 0, 0,Color.RED);
            enemyBlobs.add(blob1);
            enemyBlobs.add(blob2);

            // add 2 abilities to game
            ArrayList<Ability> jsonAbilities = new Abilities().getJsonAbilities();
            Ability speed = jsonAbilities.get(0);
            Ability size = jsonAbilities.get(1);
            sbg.getAbilities().add(speed);
            sbg.getAbilities().add(size);

            BlobGame lbg = reader.read();

            checkBlob(sbg.getPlayerBlob(), lbg.getPlayerBlob());
            checkAbilities(sbg.getAbilities(), lbg.getAbilities());
        } catch (IOException | InvalidInputException e) {
            fail("Couldn't read from file");
        }
    }
}