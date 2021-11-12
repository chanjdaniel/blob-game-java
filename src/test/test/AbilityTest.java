package test;

import model.Ability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbilityTest {
    private Ability testAbility;

    @BeforeEach
    void runBefore() {
        String name = "testName";
        String description = "testDescription";
        String stat = "testStat";
        int value = 0;
        double positionX = 0;
        double positionY = 0;

        testAbility = new Ability(name, description, stat, value, positionX, positionY);
    }

    @Test
    void testConstructor() {

        assertEquals("testName", testAbility.getName());
        assertEquals("testDescription", testAbility.getDescription());
        assertEquals("testStat", testAbility.getStat());
        assertEquals(0, testAbility.getValue());
        assertEquals(0, testAbility.getPositionX());
        assertEquals(0, testAbility.getPositionY());
    }
}
