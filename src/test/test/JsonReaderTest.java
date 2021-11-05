package test;

import exceptions.InvalidInputException;
import model.BGame;
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
            BGame bg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNewBlobGame() {
        JsonReader reader = new JsonReader("./data/testReaderNewBlobGame.json");
        try {
            BGame sbg = new BGame("George", Color.blue);
            BGame lbg = reader.read();

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
            BGame sbg = new BGame("George", Color.blue);

            // adds an ability
            sbg.getPlayer().addAbility(sbg.getAbilityByName("Jump"));

            BGame lbg = reader.read();

            checkBlob(sbg.getPlayer(), lbg.getPlayer());
            checkAbilities(sbg.getAbilities().getAbilities(), lbg.getAbilities().getAbilities());
        } catch (IOException | InvalidInputException e) {
            fail("Couldn't read from file");
        }
    }
}