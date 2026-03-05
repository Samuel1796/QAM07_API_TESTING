package com.api.testdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Test data generation for user resources.
 * <p>
 * This class provides methods to generate test data specific to user resources,
 * including valid user data with nested objects, valid/invalid user IDs, 
 * boundary value IDs, and edge case data for testing.
 * </p>
 * <p>
 * All methods are static and the class cannot be instantiated.
 * </p>
 *
 * @author API Test Automation Team
 * @version 1.0
 * @since 3.0
 */
public class UsersTestData {
    
    /**
     * Maximum number of users in JSONPlaceholder API.
     */
    public static final int MAX_USERS = 10;
    
    /**
     * Private constructor to prevent instantiation.
     */
    private UsersTestData() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Generates random valid user data with nested address and company objects.
     *
     * @return Map containing name, username, email, address, phone, website, and company
     */
    public static Map<String, Object> getUserData() {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", TestDataUtils.randomString("Test User"));
        userData.put("username", TestDataUtils.randomString("user"));
        userData.put("email", TestDataUtils.randomEmail());
        
        // Create nested address object
        Map<String, Object> address = new HashMap<>();
        address.put("street", TestDataUtils.randomString("Street"));
        address.put("suite", "Apt. " + TestDataUtils.randomInt(1, 999));
        address.put("city", TestDataUtils.randomString("City"));
        address.put("zipcode", String.format("%05d", TestDataUtils.randomInt(10000, 99999)));
        
        // Create nested geo object within address
        Map<String, String> geo = new HashMap<>();
        geo.put("lat", TestDataUtils.randomLatitude());
        geo.put("lng", TestDataUtils.randomLongitude());
        address.put("geo", geo);
        
        userData.put("address", address);
        userData.put("phone", TestDataUtils.randomPhone());
        userData.put("website", TestDataUtils.randomString("website") + ".com");
        
        // Create nested company object
        Map<String, String> company = new HashMap<>();
        company.put("name", TestDataUtils.randomString("Company"));
        company.put("catchPhrase", TestDataUtils.randomString("Innovative solutions"));
        company.put("bs", TestDataUtils.randomString("synergize"));
        userData.put("company", company);
        
        return userData;
    }
    
    /**
     * Generates a valid random user ID within the range 1-10.
     *
     * @return valid user ID
     */
    public static int getValidUserId() {
        return TestDataUtils.randomInt(1, MAX_USERS);
    }
    
    /**
     * Generates an invalid user ID outside the valid range.
     * Returns either 0, a negative number, or a number greater than 10.
     *
     * @return invalid user ID
     */
    public static int getInvalidUserId() {
        int choice = TestDataUtils.randomInt(1, 3);
        switch (choice) {
            case 1:
                return 0;
            case 2:
                return TestDataUtils.randomInt(-100, -1);
            default:
                return TestDataUtils.randomInt(MAX_USERS + 1, MAX_USERS + 100);
        }
    }
    
    /**
     * Generates boundary value user IDs based on the specified boundary type.
     *
     * @param boundaryType the type of boundary value: "min", "max", "below_min", or "above_max"
     * @return boundary value user ID
     */
    public static int getBoundaryUserId(String boundaryType) {
        switch (boundaryType.toLowerCase()) {
            case "min":
                return 1;
            case "max":
                return MAX_USERS;
            case "below_min":
                return 0;
            case "above_max":
                return MAX_USERS + 1;
            default:
                return 1; // Safe default
        }
    }
    
    /**
     * Generates user data with invalid email formats for edge case testing.
     * <p>
     * This method creates user data with various invalid email formats to test
     * how the API handles email validation.
     * </p>
     *
     * @return Map containing user data with invalid email format
     */
    public static Map<String, Object> getUserDataWithInvalidEmail() {
        Map<String, Object> userData = getUserData();
        
        // Array of invalid email formats
        String[] invalidEmails = {
            "notanemail",
            "@example.com",
            "user@",
            "user..name@example.com",
            "user@.com",
            "user name@example.com",
            "user@example",
            ""
        };
        
        // Pick a random invalid email format
        int index = TestDataUtils.randomInt(0, invalidEmails.length - 1);
        userData.put("email", invalidEmails[index]);
        
        return userData;
    }
    
    /**
     * Generates user data with missing required fields for edge case testing.
     * <p>
     * This method creates partial user data with one or more required fields missing
     * to test how the API handles incomplete data.
     * </p>
     *
     * @return Map containing user data with missing fields
     */
    public static Map<String, Object> getUserDataWithMissingFields() {
        Map<String, Object> userData = new HashMap<>();
        
        // Randomly decide which fields to include (at least one field will be missing)
        int fieldsToInclude = TestDataUtils.randomInt(1, 6); // Include 1-6 out of 7 fields
        
        if (fieldsToInclude > 0 && TestDataUtils.randomInt(0, 1) == 1) {
            userData.put("name", TestDataUtils.randomString("Test User"));
        }
        if (fieldsToInclude > 1 && TestDataUtils.randomInt(0, 1) == 1) {
            userData.put("username", TestDataUtils.randomString("user"));
        }
        if (fieldsToInclude > 2 && TestDataUtils.randomInt(0, 1) == 1) {
            userData.put("email", TestDataUtils.randomEmail());
        }
        if (fieldsToInclude > 3 && TestDataUtils.randomInt(0, 1) == 1) {
            userData.put("phone", TestDataUtils.randomPhone());
        }
        if (fieldsToInclude > 4 && TestDataUtils.randomInt(0, 1) == 1) {
            userData.put("website", TestDataUtils.randomString("website") + ".com");
        }
        
        return userData;
    }
}
