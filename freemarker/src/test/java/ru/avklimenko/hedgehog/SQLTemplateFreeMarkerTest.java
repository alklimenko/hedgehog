package ru.avklimenko.hedgehog;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static ru.avklimenko.hedgehog.Utils.map;

public class SQLTemplateFreeMarkerTest {
    private SQLTemplateFreeMarker template = new SQLTemplateFreeMarker();

    @Test
    public void testGetSQLFromResource() {
        String sql = template.getSQLFromResource("ru/avklimenko/hedgehog/test.sql", map("id", 321));
        assertEquals(sql, "SELECT * FROM table WHERE id = 321");
    }

    @Test
    public void testGetSQLFromString() {
        String string = template.getSQLFromString("${a} + ${b} = ${c}", map("a", 2, "b", 3, "c", 5));
        assertEquals(string, "2 + 3 = 5");
    }

    @Test
    public void testNumberFormat() {
        String string = template.getSQLFromString("${a}", map("a", 2000000));
        assertEquals(string, "2000000");
        string = template.getSQLFromString("${a}", map("a", 234.345));
        assertEquals(string, "234.345");
    }
}