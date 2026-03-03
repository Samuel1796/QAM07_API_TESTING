package com.api.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Test Data Provider utility for generating random test data using Equivalence Partitioning.
 * <p>
 * This utility class provides factory methods for creating randomized test data objects
 * used in POST and PUT requests. All data is returned as Map objects that
 * REST Assured automatically serializes to JSON.
 * </p>
 * <p>
 * The test data follows the structure expected by the JSONPlaceholder API
 * and uses equivalence partitioning based on actual API resource limits:
 * - Posts: 100 posts (IDs 1-100)
 * - Comments: 500 comments (IDs 1-500)
 * - Albums: 100 albums (IDs 1-100)
 * - Photos: 5000 photos (IDs 1-5000)
 * - Todos: 200 todos (IDs 1-200)
 * - Users: 10 users (IDs 1-10)
 * </p>
 * <p>
 * Equivalence Partitioning Strategy:
 * - Valid partition: IDs within the actual resource range
 * - Boundary values: First ID (1), Last ID (max), Middle values
 * - Invalid partition: IDs outside the range (0, max+1, negative)
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
public class TestDataProvider {
    
    private static final Random random = new Random();
    
    // JSONPlaceholder API Resource Limits (Equivalence Partitioning Boundaries)
    private static final int MAX_USERS = 10;
    private static final int MAX_POSTS = 100;
    private static final int MAX_COMMENTS = 500;
    private static final int MAX_ALBUMS = 100;
    private static final int MAX_PHOTOS = 5000;
    private static final int MAX_TODOS = 200;
    
    /**
     * Generates a random integer between min (inclusive) and max (inclusive).
     *
     * @param min minimum value
     * @param max maximum value
     * @return random integer between min and max
     */
    private static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    
    /**
     * Generates a random string with the specified prefix and a UUID suffix.
     *
     * @param prefix the prefix for the random string
     * @return random string with prefix and UUID
     */
    private static String randomString(String prefix) {
        return prefix + " " + UUID.randomUUID().toString().substring(0, 8);
    }
    
    /**
     * Generates a random email address.
     *
     * @return random email address
     */
    private static String randomEmail() {
        return "test" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }
    
    /**
     * Generates a random phone number in format XXX-XXX-XXXX.
     *
     * @return random phone number
     */
    private static String randomPhone() {
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
    private static String randomLatitude() {
        return String.format("%.4f", -90 + (random.nextDouble() * 180));
    }
    
    /**
     * Generates a random longitude between -180 and 180.
     *
     * @return random longitude as string
     */
    private static String randomLongitude() {
        return String.format("%.4f", -180 + (random.nextDouble() * 360));
    }
    
    /**
     * Generates a valid random user ID based on equivalence partitioning.
     * <p>
     * Valid partition: 1-10 (JSONPlaceholder has 10 users)
     * </p>
     *
     * @return random valid user ID between 1 and 10
     */
    public static int getValidUserId() {
        return randomInt(1, MAX_USERS);
    }
    
    /**
     * Generates a valid random post ID based on equivalence partitioning.
     * <p>
     * Valid partition: 1-100 (JSONPlaceholder has 100 posts)
     * </p>
     *
     * @return random valid post ID between 1 and 100
     */
    public static int getValidPostId() {
        return randomInt(1, MAX_POSTS);
    }
    
    /**
     * Generates a valid random album ID based on equivalence partitioning.
     * <p>
     * Valid partition: 1-100 (JSONPlaceholder has 100 albums)
     * </p>
     *
     * @return random valid album ID between 1 and 100
     */
    public static int getValidAlbumId() {
        return randomInt(1, MAX_ALBUMS);
    }
    
    /**
     * Generates an invalid user ID for negative testing.
     * <p>
     * Invalid partition: 0, negative, or greater than 10
     * </p>
     *
     * @return invalid user ID (0, 11, or negative)
     */
    public static int getInvalidUserId() {
        int[] invalidIds = {0, -1, MAX_USERS + 1, 999};
        return invalidIds[random.nextInt(invalidIds.length)];
    }
    
    /**
     * Generates an invalid post ID for negative testing.
     * <p>
     * Invalid partition: 0, negative, or greater than 100
     * </p>
     *
     * @return invalid post ID
     */
    public static int getInvalidPostId() {
        int[] invalidIds = {0, -1, MAX_POSTS + 1, 9999};
        return invalidIds[random.nextInt(invalidIds.length)];
    }
    
    /**
     * Generates an invalid album ID for negative testing.
     * <p>
     * Invalid partition: 0, negative, or greater than 100
     * </p>
     *
     * @return invalid album ID
     */
    public static int getInvalidAlbumId() {
        int[] invalidIds = {0, -1, MAX_ALBUMS + 1, 9999};
        return invalidIds[random.nextInt(invalidIds.length)];
    }
    
    /**
     * Generates boundary value user IDs for edge case testing.
     * <p>
     * Boundary values: 1 (min), 10 (max), 0 (below min), 11 (above max)
     * </p>
     *
     * @param boundaryType "min", "max", "below_min", "above_max"
     * @return boundary value user ID
     */
    public static int getBoundaryUserId(String boundaryType) {
        switch (boundaryType.toLowerCase()) {
            case "min": return 1;
            case "max": return MAX_USERS;
            case "below_min": return 0;
            case "above_max": return MAX_USERS + 1;
            default: return 1;
        }
    }
    
    /**
     * Generates boundary value post IDs for edge case testing.
     * <p>
     * Boundary values: 1 (min), 100 (max), 0 (below min), 101 (above max)
     * </p>
     *
     * @param boundaryType "min", "max", "below_min", "above_max"
     * @return boundary value post ID
     */
    public static int getBoundaryPostId(String boundaryType) {
        switch (boundaryType.toLowerCase()) {
            case "min": return 1;
            case "max": return MAX_POSTS;
            case "below_min": return 0;
            case "above_max": return MAX_POSTS + 1;
            default: return 1;
        }
    }
    
    /**
     * Generates boundary value todo IDs for edge case testing.
     * <p>
     * Boundary values: 1 (min), 200 (max), 0 (below min), 201 (above max)
     * </p>
     *
     * @param boundaryType "min", "max", "below_min", "above_max"
     * @return boundary value todo ID
     */
    public static int getBoundaryTodoId(String boundaryType) {
        switch (boundaryType.toLowerCase()) {
            case "min": return 1;
            case "max": return MAX_TODOS;
            case "below_min": return 0;
            case "above_max": return MAX_TODOS + 1;
            default: return 1;
        }
    }

    /**
     * Generates an invalid todo ID for negative testing.
     * <p>
     * Invalid partition: 0, negative, or greater than 200
     * </p>
     *
     * @return invalid todo ID
     */
    public static int getInvalidTodoId() {
        int[] invalidIds = {0, -1, MAX_TODOS + 1, 9999};
        return invalidIds[random.nextInt(invalidIds.length)];
    }

    /**
     * Generates post data with empty/null fields for edge case testing.
     *
     * @param fieldToEmpty "title", "body", "userId", or "all"
     * @return post data with specified empty fields
     */
    public static Map<String, Object> getPostDataWithEmptyFields(String fieldToEmpty) {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", fieldToEmpty.equals("userId") || fieldToEmpty.equals("all") ? null : getValidUserId());
        postData.put("title", fieldToEmpty.equals("title") || fieldToEmpty.equals("all") ? "" : randomString("Test Post"));
        postData.put("body", fieldToEmpty.equals("body") || fieldToEmpty.equals("all") ? "" : "Test post body");
        return postData;
    }
    
    /**
     * Generates post data with extremely long strings for edge case testing.
     *
     * @return post data with very long title and body
     */
    public static Map<String, Object> getPostDataWithLongStrings() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", getValidUserId());
        postData.put("title", "A".repeat(1000)); // 1000 character title
        postData.put("body", "B".repeat(10000)); // 10000 character body
        return postData;
    }
    
    /**
     * Generates post data with special characters for edge case testing.
     *
     * @return post data with special characters
     */
    public static Map<String, Object> getPostDataWithSpecialCharacters() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", getValidUserId());
        postData.put("title", "Test <script>alert('XSS')</script> & \"quotes\" 'apostrophes'");
        postData.put("body", "Special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?`~");
        return postData;
    }
    
    /**
     * Generates user data with invalid email format for edge case testing.
     *
     * @return user data with invalid email
     */
    public static Map<String, Object> getUserDataWithInvalidEmail() {
        Map<String, Object> userData = getUserData();
        String[] invalidEmails = {"invalid", "test@", "@example.com", "test@@example.com", "test@.com"};
        userData.put("email", invalidEmails[random.nextInt(invalidEmails.length)]);
        return userData;
    }
    
    /**
     * Generates user data with missing required fields for edge case testing.
     *
     * @return user data with missing fields
     */
    public static Map<String, Object> getUserDataWithMissingFields() {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", randomString("Test User"));
        // Missing username, email, and other fields
        return userData;
    }
    
    /**
     * Generates todo data with empty/null fields for edge case testing.
     *
     * @param fieldToEmpty "title", "userId", "completed", or "all"
     * @return todo data with specified empty fields
     */
    public static Map<String, Object> getTodoDataWithEmptyFields(String fieldToEmpty) {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", fieldToEmpty.equals("userId") || fieldToEmpty.equals("all") ? null : getValidUserId());
        todoData.put("title", fieldToEmpty.equals("title") || fieldToEmpty.equals("all") ? "" : randomString("Test Todo"));
        todoData.put("completed", fieldToEmpty.equals("completed") || fieldToEmpty.equals("all") ? null : random.nextBoolean());
        return todoData;
    }

    /**
     * Generates todo data with special characters for edge case testing.
     *
     * @return todo data with special characters
     */
    public static Map<String, Object> getTodoDataWithSpecialCharacters() {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", getValidUserId());
        todoData.put("title", "Test <script>alert('XSS')</script> & special !@#$%^&*() chars");
        todoData.put("completed", random.nextBoolean());
        return todoData;
    }

    /**
     * Generates todo data with extremely long title for edge case testing.
     *
     * @return todo data with very long title
     */
    public static Map<String, Object> getTodoDataWithLongTitle() {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", getValidUserId());
        todoData.put("title", "A".repeat(10000)); // 10000 character title
        todoData.put("completed", random.nextBoolean());
        return todoData;
    }

    /**
     * Generates random test data for creating or updating a post.
     * <p>
     * Uses equivalence partitioning:
     * - userId: Valid partition (1-10) based on actual user count
     * - title and body: Random unique strings
     * </p>
     *
     * @return a Map containing randomized post test data
     */
    public static Map<String, Object> getPostData() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", getValidUserId());
        postData.put("title", randomString("Test Post"));
        postData.put("body", "Test post body content " + UUID.randomUUID().toString());
        return postData;
    }
    
    /**
     * Generates random test data for creating or updating a comment.
     * <p>
     * Uses equivalence partitioning:
     * - postId: Valid partition (1-100) based on actual post count
     * - name, email, body: Random unique strings
     * </p>
     *
     * @return a Map containing randomized comment test data
     */
    public static Map<String, Object> getCommentData() {
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("postId", getValidPostId());
        commentData.put("name", randomString("Test Comment"));
        commentData.put("email", randomEmail());
        commentData.put("body", "Test comment body " + UUID.randomUUID().toString());
        return commentData;
    }
    
    /**
     * Generates random test data for creating or updating an album.
     * <p>
     * Uses equivalence partitioning:
     * - userId: Valid partition (1-10) based on actual user count
     * - title: Random unique string
     * </p>
     *
     * @return a Map containing randomized album test data
     */
    public static Map<String, Object> getAlbumData() {
        Map<String, Object> albumData = new HashMap<>();
        albumData.put("userId", getValidUserId());
        albumData.put("title", randomString("Test Album"));
        return albumData;
    }
    
    /**
     * Generates random test data for creating or updating a photo.
     * <p>
     * Uses equivalence partitioning:
     * - albumId: Valid partition (1-100) based on actual album count
     * - title, url, thumbnailUrl: Random unique strings
     * </p>
     *
     * @return a Map containing randomized photo test data
     */
    public static Map<String, Object> getPhotoData() {
        Map<String, Object> photoData = new HashMap<>();
        int randomColor = randomInt(100000, 999999);
        photoData.put("albumId", getValidAlbumId());
        photoData.put("title", randomString("Test Photo"));
        photoData.put("url", "https://via.placeholder.com/600/" + randomColor);
        photoData.put("thumbnailUrl", "https://via.placeholder.com/150/" + randomColor);
        return photoData;
    }
    
    /**
     * Generates random test data for creating or updating a todo item.
     * <p>
     * Uses equivalence partitioning:
     * - userId: Valid partition (1-10) based on actual user count
     * - title: Random unique string
     * - completed: Random boolean (true/false partition)
     * </p>
     *
     * @return a Map containing randomized todo test data
     */
    public static Map<String, Object> getTodoData() {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", getValidUserId());
        todoData.put("title", randomString("Test Todo"));
        todoData.put("completed", random.nextBoolean());
        return todoData;
    }
    
    /**
     * Generates random test data for creating or updating a user.
     * <p>
     * Uses equivalence partitioning and random generation for all fields:
     * - All string fields: Random unique strings
     * - Nested objects (address, geo, company): Random data
     * - Geographic coordinates: Valid latitude/longitude ranges
     * - Phone: Valid format XXX-XXX-XXXX
     * - Email: Valid email format
     * </p>
     *
     * @return a Map containing randomized user test data with nested address and company objects
     */
    public static Map<String, Object> getUserData() {
        Map<String, Object> userData = new HashMap<>();
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        
        userData.put("name", "Test User " + uniqueId);
        userData.put("username", "testuser" + uniqueId);
        userData.put("email", randomEmail());
        
        Map<String, Object> address = new HashMap<>();
        address.put("street", randomString("Street"));
        address.put("suite", "Apt. " + randomInt(100, 999));
        address.put("city", randomString("City"));
        address.put("zipcode", String.format("%05d", randomInt(10000, 99999)));
        
        Map<String, String> geo = new HashMap<>();
        geo.put("lat", randomLatitude());
        geo.put("lng", randomLongitude());
        address.put("geo", geo);
        
        userData.put("address", address);
        userData.put("phone", randomPhone());
        userData.put("website", "testuser" + uniqueId + ".com");
        
        Map<String, String> company = new HashMap<>();
        company.put("name", randomString("Company"));
        company.put("catchPhrase", randomString("Innovation"));
        company.put("bs", randomString("solutions"));
        userData.put("company", company);
        
        return userData;
    }
}
