package test;

import exceptions.InvalidInputException;
import model.Abilities;
import model.Ability;
import model.Blob;
import model.BlobGame;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            BlobGame bg = new BlobGame("George", Color.blue);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException | InvalidInputException e) {
            // pass
        }
    }

    @Test
    void testWriterNewBlobGame() {
        try {
            BlobGame sbg = new BlobGame("George", Color.blue);
            JsonWriter writer = new JsonWriter("./data/testWriterNewBlobGame.json");
            writer.open();
            writer.write(sbg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNewBlobGame.json");
            BlobGame lbg = reader.read();
            checkBlob(sbg.getPlayerBlob(), lbg.getPlayerBlob());
            checkAbilities(sbg.getAbilities(), lbg.getAbilities());
            checkBlobs(sbg.getEnemyBlobs(), lbg.getEnemyBlobs());
        } catch (IOException | InvalidInputException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBlobGame() {
        try {
            ArrayList<Ability> jsonAbilities = new Abilities().getJsonAbilities();
            Ability speed = jsonAbilities.get(0);
            Ability size = jsonAbilities.get(1);
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

            // add 2 blobs to game
            Blob blob1 = new Blob("testName1", 10, 1, 0, 0, Color.BLUE);
            Blob blob2 = new Blob("testName2", 15, 2, 0, 0,Color.RED);
            blob1.addAbility(size);
            blob2.addAbility(speed);
            enemyBlobs.add(blob1);
            enemyBlobs.add(blob2);

            // add 2 abilities to game
            sbg.getAbilities().add(speed);
            sbg.getAbilities().add(size);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBlobGame.json");
            writer.open();
            writer.write(sbg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBlobGame.json");
            BlobGame lbg = reader.read();
            checkBlob(sbg.getPlayerBlob(), lbg.getPlayerBlob());
            checkAbilities(sbg.getAbilities(), lbg.getAbilities());
            checkBlobs(sbg.getEnemyBlobs(), lbg.getEnemyBlobs());
        } catch (IOException | InvalidInputException e) {
            fail("Exception should not have been thrown");
        }
    }
}