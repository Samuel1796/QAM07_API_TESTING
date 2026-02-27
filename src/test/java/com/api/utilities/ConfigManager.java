package com.api.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;
    
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
    
    public static String getBaseUrl() {
        return properties.getProperty("base.url", "https://jsonplaceholder.typicode.com");
    }
    
    public static int getDefaultTimeout() {
        return Integer.parseInt(properties.getProperty("default.timeout", "5000"));
    }
    
    public static String getEnvironment() {
        return properties.getProperty("environment", "test");
    }
    
    public static boolean shouldLogRequests() {
        return Boolean.parseBoolean(properties.getProperty("log.requests", "true"));
    }
    
    public static boolean shouldLogResponses() {
        return Boolean.parseBoolean(properties.getProperty("log.responses", "true"));
    }
}
