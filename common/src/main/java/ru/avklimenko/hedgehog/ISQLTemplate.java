package ru.avklimenko.hedgehog;

import java.util.Map;

public interface ISQLTemplate {
    String getSQLFromResource(String resourceName, Map<String, Object> context);

    String getSQLFromFile(String fileName, Map<String, Object> context);

    String getSQLFromString(String templateString, Map<String, Object> context);
}
