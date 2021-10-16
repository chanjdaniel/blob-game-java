package test;

import model.Abilities;
import model.Ability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AbilitiesTest {
    private Abilities testAbilities;
    private Ability testAbility1;
    private Ability testAbility2;

    @BeforeEach
    void runBefore() {
        testAbilities = new Abilities();
        testAbility1 = new Ability("Test_Ability1", "");
        testAbility2 = new Ability("Test_Ability2", "");
    }

    @Test
    void testRemoveByNameSingle() {

        testAbilities.addAbility(testAbility1);
        assertEquals(1, testAbilities.getAbilities().size());
        testAbilities.removeByName(testAbility1.getName());
        assertEquals(0, testAbilities.getAbilities().size());
    }

    @Test
    void testRemoveByNameMultiple() {

        testAbilities.addAbility(testAbility1);
        testAbilities.addAbility(testAbility2);
        assertEquals(2, testAbilities.getAbilities().size());
        testAbilities.removeByName(testAbility1.getName());
        testAbilities.removeByName(testAbility2.getName());
        assertEquals(0, testAbilities.getAbilities().size());
    }

    @Test
    void testMakeAbilities() {

        ArrayList<String> testAbilityNames = new ArrayList<>(Arrays.asList(
                "Super Speed", "Physical Resistance", "Regeneration", "Sticky", "Heat Resistance",
                "Acid", "Digest", "Grow", "Jump", "Dash"));
        testAbilities.makeAbilities(testAbilityNames);
        assertEquals(10, testAbilities.getAbilities().size());
    }

    @Test
    void testAddNewAbilitySingle() {

        testAbilities.addNewAbility(testAbility1.getName());
        assertEquals(1, testAbilities.getAbilities().size());
        assertEquals(testAbility1.getName(), testAbilities.getAbilities().get(0).getName());
    }

    @Test
    void testAddNewAbilityMultiple() {

        testAbilities.addNewAbility(testAbility1.getName());
        testAbilities.addNewAbility(testAbility2.getName());
        assertEquals(2, testAbilities.getAbilities().size());
        assertEquals(testAbility1.getName(), testAbilities.getAbilities().get(0).getName());
        assertEquals(testAbility2.getName(), testAbilities.getAbilities().get(1).getName());
    }

    @Test
    void testGetByName() {

        testAbilities.addAbility(testAbility1);
        testAbilities.addAbility(testAbility2);
        assertEquals(testAbility1, testAbilities.getByName(testAbility1.getName()));
        assertEquals(testAbility2, testAbilities.getByName(testAbility2.getName()));
    }

    @Test
    void testGetLowerCaseNames() {

        testAbilities.addAbility(testAbility1);
        testAbilities.addAbility(testAbility2);
        assertEquals(testAbility1.getName().toLowerCase(), testAbilities.getLowerCaseNames().get(0));
        assertEquals(testAbility2.getName().toLowerCase(), testAbilities.getLowerCaseNames().get(1));
    }
}