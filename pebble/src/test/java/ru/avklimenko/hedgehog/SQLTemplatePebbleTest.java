package ru.avklimenko.hedgehog;

import org.testng.Assert;
import org.testng.annotations.Test;

import static ru.avklimenko.hedgehog.Utils.map;

public class SQLTemplatePebbleTest extends Assert {
    private SQLTemplatePebble template = new SQLTemplatePebble();

    @Test
    public void testGetSQLFromResource() {
        String sql = template.getSQLFromResource("ru/avklimenko/hedgehog/test.sql", map("id", 123));
        assertEquals(sql, "SELECT * FROM table WHERE id = 123");
    }

    @Test
    public void testGetSQLFromString() {
        String string = template.getSQLFromString("{{a}} + {{b}} = {{c}}", map("a", 1, "b", 2, "c", 3));
        assertEquals(string, "1 + 2 = 3");
    }
}