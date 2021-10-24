package test;

import model.Abilities;
import model.Blob;
import model.BlobGame;
import model.Blobs;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            BlobGame bg = new BlobGame("George", Color.blue);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
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
            checkBlob(sbg.getPlayer(), lbg.getPlayer());
            checkAbilities(sbg.getAbilities().getAbilities(), lbg.getAbilities().getAbilities());
            checkBlobs(sbg.getEnemyBlobs().getBlobs(), lbg.getEnemyBlobs().getBlobs());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBlobGame() {
        try {
            BlobGame sbg = new BlobGame("George", Color.blue);
            Blob player = sbg.getPlayer();
            Blobs enemyBlobs = sbg.getEnemyBlobs();

            // eats 3 blobs
            for (int i = 3; i > 0; i--) {
                Blob victim = enemyBlobs.getBlobs().get(0);
                player.getVictims().add(victim);
                enemyBlobs.getBlobs().remove(0);
            }

            // adds an ability
            sbg.getPlayer().addAbility(sbg.getAbilityByName("Jump"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBlobGame.json");
            writer.open();
            writer.write(sbg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBlobGame.json");
            BlobGame lbg = reader.read();
            checkBlob(sbg.getPlayer(), lbg.getPlayer());
            checkAbilities(sbg.getAbilities().getAbilities(), lbg.getAbilities().getAbilities());
            checkBlobs(sbg.getEnemyBlobs().getBlobs(), lbg.getEnemyBlobs().getBlobs());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}