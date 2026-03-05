package com.api.testdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Test data generation for todo resources.
 * <p>
 * This class provides methods to generate test data specific to todo resources,
 * including valid todo data, invalid todo IDs, boundary value IDs, and
 * edge case data for testing.
 * </p>
 * <p>
 * All methods are static and the class cannot be instantiated.
 * </p>
 *
 * @author API Test Automation Team
 * @version 1.0
 * @since 3.0
 */
public class TodosTestData {
    
    /**
     * Maximum number of todos in JSONPlaceholder API.
     */
    public static final int MAX_TODOS = 200;
    
    /**
     * Maximum number of users in JSONPlaceholder API (for userId associations).
     */
    public static final int MAX_USERS = 10;
    
    /**
     * Private constructor to prevent instantiation.
     */
    private TodosTestData() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Generates random valid todo data.
     *
     * @return Map containing userId, title, and completed
     */
    public static Map<String, Object> getTodoData() {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", TestDataUtils.randomInt(1, MAX_USERS));
        todoData.put("title", TestDataUtils.randomString("Test Todo"));
        todoData.put("completed", TestDataUtils.randomInt(0, 1) == 1);
        return todoData;
    }
    
    /**
     * Generates an invalid todo ID outside the valid range.
     * Returns either 0, a negative number, or a number greater than 200.
     *
     * @return invalid todo ID
     */
    public static int getInvalidTodoId() {
        int choice = TestDataUtils.randomInt(1, 3);
        switch (choice) {
            case 1:
                return 0;
            case 2:
                return TestDataUtils.randomInt(-100, -1);
            default:
                return TestDataUtils.randomInt(MAX_TODOS + 1, MAX_TODOS + 100);
        }
    }
    
    /**
     * Generates boundary value todo IDs based on the specified boundary type.
     *
     * @param boundaryType the type of boundary value: "min", "max", "below_min", or "above_max"
     * @return boundary value todo ID
     */
    public static int getBoundaryTodoId(String boundaryType) {
        switch (boundaryType.toLowerCase()) {
            case "min":
                return 1;
            case "max":
                return MAX_TODOS;
            case "below_min":
                return 0;
            case "above_max":
                return MAX_TODOS + 1;
            default:
                return 1; // Safe default
        }
    }
    
    /**
     * Generates todo data with empty or null fields for edge case testing.
     * <p>
     * This method allows testing how the API handles todos with missing or empty data.
     * </p>
     *
     * @param fieldToEmpty the field to make empty or null: "userId", "title", "completed", or "all"
     * @return Map containing todo data with the specified field(s) empty or null
     */
    public static Map<String, Object> getTodoDataWithEmptyFields(String fieldToEmpty) {
        Map<String, Object> todoData = new HashMap<>();
        
        if (fieldToEmpty == null || fieldToEmpty.isEmpty()) {
            return getTodoData(); // Return normal data if parameter is invalid
        }
        
        String field = fieldToEmpty.toLowerCase();
        todoData.put("userId", field.equals("userid") || field.equals("all") ? null : TestDataUtils.randomInt(1, MAX_USERS));
        todoData.put("title", field.equals("title") || field.equals("all") ? "" : TestDataUtils.randomString("Test Todo"));
        todoData.put("completed", field.equals("completed") || field.equals("all") ? null : TestDataUtils.randomInt(0, 1) == 1);
        
        return todoData;
    }
    
    /**
     * Generates todo data with special characters for edge case testing.
     * <p>
     * This method creates todo data containing various special characters to test
     * how the API handles encoding and special character processing.
     * </p>
     *
     * @return Map containing todo data with special characters in title
     */
    public static Map<String, Object> getTodoDataWithSpecialCharacters() {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", TestDataUtils.randomInt(1, MAX_USERS));
        todoData.put("title", "Test Todo <>&\"'!@#$%^*()[]{}|\\:;,.<>?/~`");
        todoData.put("completed", TestDataUtils.randomInt(0, 1) == 1);
        return todoData;
    }
    
    /**
     * Generates todo data with an extremely long title for edge case testing.
     * <p>
     * This method creates todo data with a title of 10000 characters
     * to test how the API handles large payloads.
     * </p>
     *
     * @return Map containing todo data with very long title string
     */
    public static Map<String, Object> getTodoDataWithLongTitle() {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", TestDataUtils.randomInt(1, MAX_USERS));
        
        // Generate 10000 character title
        StringBuilder longTitle = new StringBuilder();
        while (longTitle.length() < 10000) {
            longTitle.append("A");
        }
        todoData.put("title", longTitle.toString());
        todoData.put("completed", TestDataUtils.randomInt(0, 1) == 1);
        
        return todoData;
    }
}
