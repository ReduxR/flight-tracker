package io.github.reduxr;

import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading configuration properties from the resources.
 * ConfigLoader reads the properties file once on class loading and provides static access to keys.
 */
public class ConfigLoader {

    // Holds all properties loaded from config.properties
    private static final Properties props = new Properties();

    // Private constructor to prevent instantiation
    ConfigLoader() {}

    // Static block executed once when the class is loaded
    static {
        try (InputStream is = ConfigLoader.class.getResourceAsStream("/config.properties")) {

            // Throw an exception if the config file is not found
            if (is == null) {
                throw new RuntimeException("config.properties not found in resources!");
            }

            // Load properties from the input stream
            props.load(is);

        } catch (Exception e) {
            // Wrap and rethrow any exceptions during loading
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * Retrieves the value associated with the given key from the loaded properties.
     *
     * @param key The property key to look up
     * @return The value as a String, or null if the key does not exist
     */
    
    public static String getKey(String key) {
        return props.getProperty(key);
    }
}