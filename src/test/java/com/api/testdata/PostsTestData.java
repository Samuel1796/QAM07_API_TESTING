package com.api.testdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Test data generation for post resources.
 * <p>
 * This class provides methods to generate test data specific to post resources,
 * including valid post data, valid/invalid post IDs, boundary value IDs, and
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
public class PostsTestData {
    
    /**
     * Maximum number of posts in JSONPlaceholder API.
     */
    public static final int MAX_POSTS = 100;
    
    /**
     * Maximum number of users in JSONPlaceholder API (for userId associations).
     */
    public static final int MAX_USERS = 10;
    
    /**
     * Private constructor to prevent instantiation.
     */
    private PostsTestData() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Generates random valid post data.
     *
     * @return Map containing userId, title, and body
     */
    public static Map<String, Object> getPostData() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", TestDataUtils.randomInt(1, MAX_USERS));
        postData.put("title", TestDataUtils.randomString("Test Post"));
        postData.put("body", TestDataUtils.randomString("Test post body"));
        return postData;
    }
    
    /**
     * Generates a valid random post ID within the range 1-100.
     *
     * @return valid post ID
     */
    public static int getValidPostId() {
        return TestDataUtils.randomInt(1, MAX_POSTS);
    }
    
    /**
     * Generates an invalid post ID outside the valid range.
     * Returns either 0, a negative number, or a number greater than 100.
     *
     * @return invalid post ID
     */
    public static int getInvalidPostId() {
        int choice = TestDataUtils.randomInt(1, 3);
        switch (choice) {
            case 1:
                return 0;
            case 2:
                return TestDataUtils.randomInt(-100, -1);
            default:
                return TestDataUtils.randomInt(MAX_POSTS + 1, MAX_POSTS + 100);
        }
    }
    
    /**
     * Generates boundary value post IDs based on the specified boundary type.
     *
     * @param boundaryType the type of boundary value: "min", "max", "below_min", or "above_max"
     * @return boundary value post ID
     */
    public static int getBoundaryPostId(String boundaryType) {
        switch (boundaryType.toLowerCase()) {
            case "min":
                return 1;
            case "max":
                return MAX_POSTS;
            case "below_min":
                return 0;
            case "above_max":
                return MAX_POSTS + 1;
            default:
                return 1; // Safe default
        }
    }
    
    /**
     * Generates post data with empty or null fields for edge case testing.
     * <p>
     * This method allows testing how the API handles posts with missing or empty data.
     * </p>
     *
     * @param fieldToEmpty the field to make empty or null: "userId", "title", "body", or "all"
     * @return Map containing post data with the specified field(s) empty or null
     */
    public static Map<String, Object> getPostDataWithEmptyFields(String fieldToEmpty) {
        Map<String, Object> postData = new HashMap<>();
        
        if (fieldToEmpty == null || fieldToEmpty.isEmpty()) {
            return getPostData(); // Return normal data if parameter is invalid
        }
        
        String field = fieldToEmpty.toLowerCase();
        postData.put("userId", field.equals("userid") || field.equals("all") ? null : TestDataUtils.randomInt(1, MAX_USERS));
        postData.put("title", field.equals("title") || field.equals("all") ? "" : TestDataUtils.randomString("Test Post"));
        postData.put("body", field.equals("body") || field.equals("all") ? "" : TestDataUtils.randomString("Test post body"));
        
        return postData;
    }
    
    /**
     * Generates post data with extremely long strings for edge case testing.
     * <p>
     * This method creates post data with a title of 1000 characters and a body of 10000 characters
     * to test how the API handles large payloads.
     * </p>
     *
     * @return Map containing post data with very long title and body strings
     */
    public static Map<String, Object> getPostDataWithLongStrings() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", TestDataUtils.randomInt(1, MAX_USERS));
        
        // Generate 1000 character title
        StringBuilder longTitle = new StringBuilder();
        while (longTitle.length() < 1000) {
            longTitle.append("A");
        }
        postData.put("title", longTitle.toString());
        
        // Generate 10000 character body
        StringBuilder longBody = new StringBuilder();
        while (longBody.length() < 10000) {
            longBody.append("B");
        }
        postData.put("body", longBody.toString());
        
        return postData;
    }
    
    /**
     * Generates post data with special characters for edge case testing.
     * <p>
     * This method creates post data containing various special characters to test
     * how the API handles encoding and special character processing.
     * </p>
     *
     * @return Map containing post data with special characters in title and body
     */
    public static Map<String, Object> getPostDataWithSpecialCharacters() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", TestDataUtils.randomInt(1, MAX_USERS));
        postData.put("title", "Test Post <>&\"'!@#$%^*()[]{}|\\:;,.<>?/~`");
        postData.put("body", "Test body with special chars: <script>alert('XSS')</script> & \"quotes\" & 'apostrophes' & symbols: !@#$%^&*()");
        return postData;
    }
}
