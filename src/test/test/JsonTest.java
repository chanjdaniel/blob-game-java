package test;

import model.Ability;
import model.Blob;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    protected void checkBlob(Blob savedBlob, Blob loadedBlob) {
        assertEquals(savedBlob.getName(), loadedBlob.getName());
        assertEquals(savedBlob.getSize(), loadedBlob.getSize());
        assertEquals(savedBlob.getSpeed(), loadedBlob.getSpeed());
        assertEquals(savedBlob.getPositionX(), loadedBlob.getPositionX());
        assertEquals(savedBlob.getMovementX(), loadedBlob.getMovementX());
        assertEquals(savedBlob.getPositionY(), loadedBlob.getPositionY());
        assertEquals(savedBlob.getMovementY(), loadedBlob.getMovementY());
        checkColor(savedBlob.getColor(), loadedBlob.getColor());
        checkAbilities(savedBlob.getAbilities(), loadedBlob.getAbilities());
        checkBlobs(savedBlob.getVictims(), loadedBlob.getVictims());
    }

    protected void checkColor(Color savedBlobColor, Color loadedBlobColor) {
        assertEquals(savedBlobColor.getRed(), loadedBlobColor.getRed());
        assertEquals(savedBlobColor.getGreen(), loadedBlobColor.getGreen());
        assertEquals(savedBlobColor.getBlue(), loadedBlobColor.getBlue());
    }

    protected void checkAbilities(ArrayList<Ability> savedBlobAbilities, ArrayList<Ability> loadedBlobAbilities) {
        for (Ability sba : savedBlobAbilities) {
            Ability lba = loadedBlobAbilities.get(savedBlobAbilities.indexOf(sba));

            checkAbility(sba, lba);
        }
    }

    protected void checkAbility(Ability savedAbility, Ability loadedAbility) {
        assertEquals(savedAbility.getName(), loadedAbility.getName());
        assertEquals(savedAbility.getDescription(), loadedAbility.getDescription());
        assertEquals(savedAbility.getStat(), loadedAbility.getStat());
        assertEquals(savedAbility.getValue(), loadedAbility.getValue());
        assertEquals(savedAbility.getPositionX(), loadedAbility.getPositionX());
        assertEquals(savedAbility.getPositionY(), loadedAbility.getPositionY());
    }

    protected void checkBlobs(ArrayList<Blob> savedBlobs, ArrayList<Blob> loadedBlobs) {
        for (Blob sb : savedBlobs) {
            Blob lb = loadedBlobs.get(savedBlobs.indexOf(sb));

            checkBlob(sb, lb);
        }
    }
}
