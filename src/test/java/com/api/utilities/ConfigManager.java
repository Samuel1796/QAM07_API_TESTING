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
     */
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
     * @return the base URL from properties, or default "https://jsonplaceholder.typicode.com"
     */
    public static String getBaseUrl() {
        return properties.getProperty("base.url", "https://jsonplaceholder.typicode.com");
    }
    
    /**
     * Retrieves the default timeout value for API requests.
     * <p>
     * This timeout is applied to all HTTP requests to prevent indefinite waiting.
     * </p>
     *
     * @return the timeout in milliseconds, default is 5000ms (5 seconds)
     */
    public static int getDefaultTimeout() {
        return Integer.parseInt(properties.getProperty("default.timeout", "5000"));
    }
    
    /**
     * Retrieves the current test environment name.
     * <p>
     * This can be used to differentiate between test, staging, and production environments.
     * </p>
     *
     * @return the environment name, default is "test"
     */
    public static String getEnvironment() {
        return properties.getProperty("environment", "test");
    }
    
    /**
     * Determines whether HTTP requests should be logged.
     * <p>
     * When enabled, all request details including headers, body, and parameters
     * will be logged to the console and test reports.
     * </p>
     *
     * @return true if request logging is enabled, default is true
     */
    public static boolean shouldLogRequests() {
        return Boolean.parseBoolean(properties.getProperty("log.requests", "true"));
    }
    
    /**
     * Determines whether HTTP responses should be logged.
     * <p>
     * When enabled, all response details including status code, headers, and body
     * will be logged to the console and test reports.
     * </p>
     *
     * @return true if response logging is enabled, default is true
     */
    public static boolean shouldLogResponses() {
        return Boolean.parseBoolean(properties.getProperty("log.responses", "true"));
    }
}
