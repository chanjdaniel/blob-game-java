package test;

import exceptions.InvalidInputException;
import model.BlobGame;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

            checkBlob(sbg.getPlayer(), lbg.getPlayer());
            checkAbilities(sbg.getAbilities().getAbilities(), lbg.getAbilities().getAbilities());
        } catch (IOException | InvalidInputException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBlobGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBlobGame.json");
        try {
            BlobGame sbg = new BlobGame("George", Color.blue);

            // adds an ability
            sbg.getPlayer().addAbility(sbg.getAbilityByName("Jump"));

            BlobGame lbg = reader.read();

            checkBlob(sbg.getPlayer(), lbg.getPlayer());
            checkAbilities(sbg.getAbilities().getAbilities(), lbg.getAbilities().getAbilities());
        } catch (IOException | InvalidInputException e) {
            fail("Couldn't read from file");
        }
    }
}