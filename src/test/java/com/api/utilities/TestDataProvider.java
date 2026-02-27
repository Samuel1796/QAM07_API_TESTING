package com.api.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Data Provider utility for generating test data.
 * <p>
 * This utility class provides factory methods for creating test data objects
 * used in POST and PUT requests. All data is returned as Map objects that
 * REST Assured automatically serializes to JSON.
 * </p>
 * <p>
 * The test data follows the structure expected by the JSONPlaceholder API
 * and can be easily extended or modified for different test scenarios.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
public class TestDataProvider {
    
    /**
     * Generates test data for creating or updating a post.
     * <p>
     * Returns a Map containing userId, title, and body fields that match
     * the Post resource structure in the JSONPlaceholder API.
     * </p>
     *
     * @return a Map containing post test data
     */
    public static Map<String, Object> getPostData() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", 1);
        postData.put("title", "Test Post Title");
        postData.put("body", "Test post body content for API testing");
        return postData;
    }
    
    /**
     * Generates test data for creating or updating a comment.
     * <p>
     * Returns a Map containing postId, name, email, and body fields that match
     * the Comment resource structure in the JSONPlaceholder API.
     * </p>
     *
     * @return a Map containing comment test data
     */
    public static Map<String, Object> getCommentData() {
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("postId", 1);
        commentData.put("name", "Test Comment");
        commentData.put("email", "test@example.com");
        commentData.put("body", "Test comment body content");
        return commentData;
    }
    
    /**
     * Generates test data for creating or updating an album.
     * <p>
     * Returns a Map containing userId and title fields that match
     * the Album resource structure in the JSONPlaceholder API.
     * </p>
     *
     * @return a Map containing album test data
     */
    public static Map<String, Object> getAlbumData() {
        Map<String, Object> albumData = new HashMap<>();
        albumData.put("userId", 1);
        albumData.put("title", "Test Album Title");
        return albumData;
    }
    
    /**
     * Generates test data for creating or updating a photo.
     * <p>
     * Returns a Map containing albumId, title, url, and thumbnailUrl fields
     * that match the Photo resource structure in the JSONPlaceholder API.
     * </p>
     *
     * @return a Map containing photo test data
     */
    public static Map<String, Object> getPhotoData() {
        Map<String, Object> photoData = new HashMap<>();
        photoData.put("albumId", 1);
        photoData.put("title", "Test Photo Title");
        photoData.put("url", "https://via.placeholder.com/600/test");
        photoData.put("thumbnailUrl", "https://via.placeholder.com/150/test");
        return photoData;
    }
    
    /**
     * Generates test data for creating or updating a todo item.
     * <p>
     * Returns a Map containing userId, title, and completed fields that match
     * the Todo resource structure in the JSONPlaceholder API.
     * </p>
     *
     * @return a Map containing todo test data
     */
    public static Map<String, Object> getTodoData() {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", 1);
        todoData.put("title", "Test Todo Title");
        todoData.put("completed", false);
        return todoData;
    }
    
    /**
     * Generates test data for creating or updating a user.
     * <p>
     * Returns a Map containing all user fields including nested address and company
     * objects that match the User resource structure in the JSONPlaceholder API.
     * This is the most complex test data structure with multiple nested levels.
     * </p>
     *
     * @return a Map containing user test data with nested address and company objects
     */
    public static Map<String, Object> getUserData() {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", "Test User");
        userData.put("username", "testuser");
        userData.put("email", "testuser@example.com");
        
        Map<String, Object> address = new HashMap<>();
        address.put("street", "Test Street");
        address.put("suite", "Apt. 123");
        address.put("city", "Test City");
        address.put("zipcode", "12345");
        
        Map<String, String> geo = new HashMap<>();
        geo.put("lat", "40.7128");
        geo.put("lng", "-74.0060");
        address.put("geo", geo);
        
        userData.put("address", address);
        userData.put("phone", "123-456-7890");
        userData.put("website", "testuser.com");
        
        Map<String, String> company = new HashMap<>();
        company.put("name", "Test Company");
        company.put("catchPhrase", "Testing is our business");
        company.put("bs", "test automation solutions");
        userData.put("company", company);
        
        return userData;
    }
}
