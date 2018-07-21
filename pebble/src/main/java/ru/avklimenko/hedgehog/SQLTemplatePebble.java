package ru.avklimenko.hedgehog;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class SQLTemplatePebble implements ISQLTemplate {
    private final Logger logger = Logger.getLogger(SQLTemplatePebble.class);
    private PebbleEngine engineResource;
    private PebbleEngine engineString;

    @SuppressWarnings("unused")
    public SQLTemplatePebble() {
        PebbleEngine.Builder builder = new PebbleEngine.Builder();
        engineResource = builder.build();
        engineString = builder.loader(new StringLoader()).build();
    }

    private String getSQLFromTemplate(PebbleTemplate template, Map<String, Object> context) {
        Writer writer = new StringWriter();
        try {
            template.evaluate(writer, context);
        } catch (PebbleException | IOException e) {
            logger.error("Error getting sql from template!", e);
            return null;
        }
        String sql = writer.toString();
        logger.debug(sql);
        return sql;
    }

    @Override
    public String getSQLFromResource(String resourceName, Map<String, Object> context) {
        PebbleTemplate compiledTemplate;
        try {
            compiledTemplate = engineResource.getTemplate(resourceName);
        } catch (PebbleException e) {
            logger.error("Error getting template from resource \"" + resourceName + "\"!", e);
            return null;
        }
        return getSQLFromTemplate(compiledTemplate, context);
    }

    @Override
    public String getSQLFromFile(String fileName, Map<String, Object> context) {
        String templateString = Utils.getTextFileContent(fileName);
        if (templateString == null) {
            logger.error("Error reading contents of the file \"" + fileName + "\"!");
            return null;
        }
        return getSQLFromString(templateString, context);
    }

    @Override
    public String getSQLFromString(String templateString, Map<String, Object> context) {
        PebbleTemplate template;
        try {
            template = engineString.getTemplate(templateString);
        } catch (PebbleException e) {
            logger.error("Error creating sql from template string!", e);
            return null;
        }
        return getSQLFromTemplate(template, context);
    }
}
