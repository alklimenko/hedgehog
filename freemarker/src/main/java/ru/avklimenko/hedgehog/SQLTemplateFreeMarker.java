package ru.avklimenko.hedgehog;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class SQLTemplateFreeMarker implements ISQLTemplate {
    private final Logger logger = Logger.getLogger(SQLTemplateFreeMarker.class);
    private Configuration cfg;

    public SQLTemplateFreeMarker() {
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(SQLTemplateFreeMarker.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    private String implementTemplate(Template template, Map<String, Object> context) {
        StringWriter stringWriter = new StringWriter();
        try {
            template.process(context, stringWriter);
        } catch (TemplateException | IOException e) {
            logger.error("Error getting sql from template!", e);
            return null;
        }

        return stringWriter.toString();
    }

    @Override
    public String getSQLFromResource(String resourceName, Map<String, Object> context) {
        Template template;
        try {
            template = cfg.getTemplate(resourceName);
        } catch (IOException e) {
            logger.error("Error getting template!", e);
            return null;
        }

        return implementTemplate(template, context);
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
        Template template;
        try {
            template = new Template("template", new StringReader(templateString), cfg);
        } catch (IOException e) {
            logger.error("Error getting template from string!", e);
            return null;
        }

        return implementTemplate(template, context);
    }
}
