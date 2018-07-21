package ru.avklimenko.hedgehog;

import org.reflections.Reflections;
import ru.avklimenko.hedgehog.exceptions.HHException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public final class SQLBuilder {
    private ISQLTemplate template;

    private static volatile SQLBuilder instance = null;

    private String getISQLTemplateClassName() {
        Reflections reflections = new Reflections(ISQLTemplate.class.getName().replaceAll("\\.ISQLTemplate$", ""));
        Set<Class<? extends ISQLTemplate>> classes = reflections.getSubTypesOf(ISQLTemplate.class);
        Iterator<Class<? extends ISQLTemplate>> iterator = classes.iterator();
        if (!iterator.hasNext()) {
            throw new HHException("Unable to find a class implements ISQLTemplate!");
        }
        Class<? extends ISQLTemplate> next = iterator.next();
        if (iterator.hasNext()) {
            throw new HHException("If in the \"hedgehog.properties\" does not specify the parameter \"hedgehog.template\","
                    + " then there must be only one class that implements ISQLTemplate interface!");
        }
        return next.getName();
    }

    private SQLBuilder() {
        Properties properties = new Properties();
        String templateClass = null;
        try {
            properties.load(SQLBuilder.class.getResourceAsStream("/hedgehog.properties"));
            templateClass = properties.getProperty("hedgehog.template");
        } catch (IOException e) {
            //
        }
        if (templateClass == null) {
            templateClass = getISQLTemplateClassName();
        }
        Class<?> clazz;
        try {
            clazz = Class.forName(templateClass);
        } catch (ClassNotFoundException e) {
            throw new HHException("Class \"" + templateClass + "\" not found!", e);
        }
        if (!ISQLTemplate.class.isAssignableFrom(clazz)) {
            throw new HHException("Class \"" + templateClass + "\" must implement \"ISQLTemplate\" interface!");
        }
        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new HHException("Class \"" + templateClass + "\" must have default constructor!");
        }
        Object object;
        try {
            object = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new HHException("Error creating instance of the class \"" + templateClass + "\"!", e);
        }
        template = (ISQLTemplate)object;
    }

    public static SQLBuilder getInstance() {
        if (instance == null) {
            synchronized (SQLBuilder.class) {
                if (instance == null) {
                    instance = new SQLBuilder();
                }
            }
        }
        return instance;
    }

    public String getSQLFromResource(String resourceName, Map<String, Object> context) {
        return template.getSQLFromResource(resourceName, context);
    }

    public String getSQLFromFile(String fileName, Map<String, Object> context) {
        return template.getSQLFromFile(fileName, context);
    }

    public String getSQLFromString(String templateString, Map<String, Object> context) {
        return template.getSQLFromString(templateString, context);
    }
}
