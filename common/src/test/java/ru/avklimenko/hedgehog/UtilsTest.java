package ru.avklimenko.hedgehog;

import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class UtilsTest {

    @Test
    public void testMap() {
        Map resultMap = Utils.map("a", 1, "b", 1.3, 2, "d");
        assertEquals(resultMap.size(), 3);
        assertTrue(resultMap.containsKey("a"));
        assertTrue(resultMap.containsKey("b"));
        assertTrue(resultMap.containsKey("2"));
        assertEquals(resultMap.get("a"), 1);
        assertEquals(resultMap.get("b"), 1.3);
        assertEquals(resultMap.get("2"), "d");

        boolean t = false;
        try {
            Utils.map("a", 1, "b", 1.3, "c");
        } catch (IllegalArgumentException e) {
            t = true;
        }
        assertTrue(t, "Method should throw an exception for an odd number of arguments!");
    }
}