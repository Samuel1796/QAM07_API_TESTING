package com.api.testdata;

import java.util.Random;
import java.util.UUID;

/**
 * Shared utility methods for test data generation.
 * <p>
 * This utility class provides common methods used across all resource-specific
 * test data classes for generating random values like integers, strings, emails,
 * phone numbers, and geographic coordinates.
 * </p>
 * <p>
 * All methods are static and the class cannot be instantiated.
 * </p>
 *
 * @author API Test Automation Team
 * @version 1.0
 * @since 3.0
 */
public class TestDataUtils {
    
    private static final Random random = new Random();
    
    /**
     * Private constructor to prevent instantiation.
     */
    private TestDataUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Generates a random integer between min (inclusive) and max (inclusive).
     *
     * @param min minimum value
     * @param max maximum value
     * @return random integer between min and max
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    
    /**
     * Generates a random string with the specified prefix and a UUID suffix.
     *
     * @param prefix the prefix for the random string
     * @return random string with prefix and UUID
     */
    public static String randomString(String prefix) {
        return prefix + " " + UUID.randomUUID().toString().substring(0, 8);
    }
    
    /**
     * Generates a random email address.
     *
     * @return random email address
     */
    public static String randomEmail() {
        return "test" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }
    
    /**
     * Generates a random phone number in format XXX-XXX-XXXX.
     *
     * @return random phone number
     */
    public static String randomPhone() {
        return String.format("%03d-%03d-%04d", 
            randomInt(100, 999), 
            randomInt(100, 999), 
            randomInt(1000, 9999));
    }
    
    /**
     * Generates a random latitude between -90 and 90.
     *
     * @return random latitude as string
     */
    public static String randomLatitude() {
        return String.format("%.4f", -90 + (random.nextDouble() * 180));
    }
    
    /**
     * Generates a random longitude between -180 and 180.
     *
     * @return random longitude as string
     */
    public static String randomLongitude() {
        return String.format("%.4f", -180 + (random.nextDouble() * 360));
    }
}
