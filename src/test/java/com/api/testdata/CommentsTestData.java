package com.api.testdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Test data generation for comment resources.
 * <p>
 * This class provides methods to generate test data specific to comment resources,
 * including valid comment data with postId, name, email, and body fields.
 * </p>
 * <p>
 * All methods are static and the class cannot be instantiated.
 * </p>
 *
 * @author API Test Automation Team
 * @version 1.0
 * @since 3.0
 */
public class CommentsTestData {
    
    /**
     * Maximum number of comments in JSONPlaceholder API.
     */
    public static final int MAX_COMMENTS = 500;
    
    /**
     * Maximum number of posts in JSONPlaceholder API (for postId associations).
     */
    public static final int MAX_POSTS = 100;
    
    /**
     * Private constructor to prevent instantiation.
     */
    private CommentsTestData() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Generates random valid comment data.
     *
     * @return Map containing postId, name, email, and body
     */
    public static Map<String, Object> getCommentData() {
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("postId", TestDataUtils.randomInt(1, MAX_POSTS));
        commentData.put("name", TestDataUtils.randomString("Test Comment"));
        commentData.put("email", TestDataUtils.randomEmail());
        commentData.put("body", TestDataUtils.randomString("Test comment body"));
        return commentData;
    }
}
