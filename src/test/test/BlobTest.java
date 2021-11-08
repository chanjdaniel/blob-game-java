package test;

import exceptions.InvalidInputException;
import model.Ability;
import model.Blob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BlobTest {
    private Blob testBlob1;
    private Blob testBlob2;
    private Ability testAbility1;
    private Ability testAbility2;
    private final Color blue = Color.blue;
    private final Color red = Color.red;

    @BeforeEach
    void runBefore() {
        testBlob1 = new Blob("test_blob1", 20, 0, 0, 0, blue);
        testBlob2 = new Blob("test_blob2", 10, 0, 0, 0, red);
        testAbility1 = new Ability("test_ability1", "test_description");
        testAbility2 = new Ability("test_ability2", "test_description");
    }

    @Test
    void testConstructor() {

        assertEquals("test_blob1", testBlob1.getName());
        assertEquals(20, testBlob1.getSize());
        assertEquals(0, testBlob1.getAbilities().size());
        assertEquals(0, testBlob1.getVictims().size());
    }

    @Test
    void testGetLowerCaseAbilityNames() {

        testBlob1.addAbility(testAbility1);
        testBlob1.addAbility(testAbility2);
        assertEquals(testAbility1.getName().toLowerCase(), testBlob1.getLowerCaseAbilityNames().get(0));
        assertEquals(testAbility2.getName().toLowerCase(), testBlob1.getLowerCaseAbilityNames().get(1));
    }

    @Test
    void testGetAbilityNames() {

        testBlob1.addAbility(testAbility1);
        testBlob1.addAbility(testAbility2);
        assertEquals(testAbility1.getName(), testBlob1.getAbilityNames().get(0));
        assertEquals(testAbility2.getName(), testBlob1.getAbilityNames().get(1));
    }

    @Test
    void testAddNewAbility() {

        assertEquals(0, testBlob1.getAbilities().size());
        testBlob1.addAbility(testAbility1);
        assertEquals(1, testBlob1.getAbilities().size());
        assertEquals(testAbility1, testBlob1.getAbilities().get(0));
    }

    @Test
    void testAddAbilityMultiple() {

        assertEquals(0, testBlob1.getAbilities().size());
        testBlob1.addAbility(testAbility1);
        testBlob1.addAbility(testAbility2);
        assertEquals(2, testBlob1.getAbilities().size());
        assertEquals(testAbility1, testBlob1.getAbilities().get(0));
        assertEquals(testAbility2, testBlob1.getAbilities().get(1));
    }

    @Test
    void testRemoveAbility() {

        testBlob1.addAbility(testAbility1);
        assertEquals(1, testBlob1.getAbilities().size());
        testBlob1.removeAbility(testAbility1.getName());
        assertEquals(0, testBlob1.getAbilities().size());
    }

    @Test
    void testRemove1AbilityWithOther() {

        testBlob1.addAbility(testAbility1);
        testBlob1.addAbility(testAbility2);
        assertEquals(2, testBlob1.getAbilities().size());
        testBlob1.removeAbility(testAbility1.getName());
        assertEquals(1, testBlob1.getAbilities().size());
        assertEquals(testAbility2, testBlob1.getAbilities().get(0));
    }

    @Test
    void testRemoveAbilityMultiple() {

        testBlob1.addAbility(testAbility1);
        testBlob1.addAbility(testAbility2);
        assertEquals(2, testBlob1.getAbilities().size());
        testBlob1.removeAbility(testAbility1.getName());
        testBlob1.removeAbility(testAbility2.getName());
        assertEquals(0, testBlob1.getAbilities().size());
    }

    @Test
    void testEatBlobMultiple() {

        testBlob1.eatBlob(testBlob2);
        testBlob1.eatBlob(testBlob2);
        assertEquals(40, testBlob1.getSize());
        assertEquals(2, testBlob1.getVictimNames().size());
        assertEquals("test_blob2", testBlob1.getVictimNames().get(0));
        assertEquals("test_blob2", testBlob1.getVictimNames().get(1));
    }
}