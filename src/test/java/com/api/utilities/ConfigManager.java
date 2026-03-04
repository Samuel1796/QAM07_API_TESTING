package com.api.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Manager for API Test Automation Framework.
 * <p>
 * This class provides centralized configuration management by reading properties
 * from the config.properties file. It uses a singleton pattern with static initialization
 * to ensure configuration is loaded once and accessible throughout the test framework.
 * </p>
 * <p>
 * All methods provide default values if properties are missing, ensuring the framework
 * can run even with minimal configuration.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
public class ConfigManager {
    
    /**
     * Properties object holding all configuration key-value pairs.
     */
    private static Properties properties;
    
    /**
     * Static initialization block that loads configuration properties.
     * <p>
     * Attempts to load config.properties from the classpath. If the file is not found
     * or cannot be loaded, the exception is logged and default values will be used.
     * </p>
     **/
    static {
        properties = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves the base URL for the API under test.
     * <p>
     * This URL is used as the foundation for all API endpoint requests.
     * </p>
     *
     * @return the base URL from properties"
     */
    public static String getBaseUrl() {
        return properties.getProperty("base.url", " ");
    }


    


}
