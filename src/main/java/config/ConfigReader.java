package config;

import exceptions.ConfigurationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {
    private ConfigReader() {}

    private static final Properties prop = new Properties();

    static {
        try(FileInputStream fileInputStream = new FileInputStream(ConfigurationManager.getConfigFilePath())){
            prop.load(fileInputStream);
        } catch (IOException e) {
            throw new ConfigurationException("Failed to load configuration file: %s".formatted(ConfigurationManager.getConfigFilePath()),e);
        }
    }

    //Return the property value as String
    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new ConfigurationException("Property '%s' is missing in the configuration.".formatted(key));
        }
        return value;
    }

    //Return the property value as Integer
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    //Return the property value as Boolean
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    public static String getPropertyOrSystem(String key) {
        String value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value;
        }
        return getProperty(key);
    }

}
