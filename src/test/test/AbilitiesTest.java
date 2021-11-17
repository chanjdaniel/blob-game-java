package test;

import model.Abilities;
import model.Ability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.game.GameScreen;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbilitiesTest {
    private Abilities testAbilities;
    private ArrayList<Ability> jsonAbilities;
    private Ability testAbility1;
    private Ability testAbility2;

    @BeforeEach
    void runBefore() {
        testAbilities = new Abilities();
        jsonAbilities = testAbilities.getJsonAbilities();
        testAbility1 = new Ability(
                "testAbility1",
                "description",
                "stat",
                0,
                0,
                0);
        testAbility2 = new Ability(
                "testAbility2",
                "description",
                "stat",
                0,
                0,
                0);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testAbilities.getAbilities().size());
        assertNotNull(jsonAbilities);
    }

    @Test
    void testArrayListConstructor() {
        Abilities testAbilities2 = new Abilities(testAbilities.getAbilities());
        assertEquals(0, testAbilities2.getAbilities().size());
        assertNotNull(testAbilities2.getJsonAbilities());
    }

    @Test
    void testAddRemoveAbilityMultiple() {
        testAbilities.addAbility(testAbility1);
        assertEquals(1, testAbilities.getAbilities().size());
        testAbilities.addAbility(testAbility2);
        assertEquals(2, testAbilities.getAbilities().size());
        testAbilities.removeAbility(testAbility1);
        assertEquals(1, testAbilities.getAbilities().size());
        testAbilities.removeAbility(testAbility2);
        assertEquals(0, testAbilities.getAbilities().size());
    }

    @Test
    void testAddRandomAbility() {
        for (int i = 0; i < 100; i++) {
            testAbilities.addRandomAbility();
            assertEquals(i + 1, testAbilities.getAbilities().size());
        }

        for (Ability ab : testAbilities.getAbilities()) {
            Ability speed = jsonAbilities.get(0);
            Ability size = jsonAbilities.get(1);
            assertTrue(ab.getName().equals(speed.getName())
                    || ab.getName().equals(size.getName()));
            assertTrue(ab.getDescription().equals(speed.getDescription())
                    || ab.getDescription().equals(size.getDescription()));
            assertTrue(ab.getStat().equals(speed.getStat())
                    || ab.getStat().equals(size.getStat()));
            assertTrue(ab.getValue() == speed.getValue()
                    || ab.getValue() == size.getValue());
            assertTrue(50 <= ab.getPositionX() && ab.getPositionX() <= GameScreen.RIGHT_WIDTH - 50);
            assertTrue(50 <= ab.getPositionY() && ab.getPositionY() <= GameScreen.HEIGHT - 50);
        }
    }
}
