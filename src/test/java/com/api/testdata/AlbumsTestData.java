package com.api.testdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Test data generation for album resources.
 * <p>
 * This class provides methods to generate test data specific to album resources,
 * including valid album data, valid/invalid album IDs, and boundary value IDs.
 * </p>
 * <p>
 * All methods are static and the class cannot be instantiated.
 * </p>
 *
 * @author API Test Automation Team
 * @version 1.0
 * @since 3.0
 */
public class AlbumsTestData {
    
    /**
     * Maximum number of albums in JSONPlaceholder API.
     */
    public static final int MAX_ALBUMS = 100;
    
    /**
     * Maximum number of users in JSONPlaceholder API (for userId associations).
     */
    private static final int MAX_USERS = 10;
    
    /**
     * Private constructor to prevent instantiation.
     */
    private AlbumsTestData() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Generates random valid album data.
     *
     * @return Map containing userId and title
     */
    public static Map<String, Object> getAlbumData() {
        Map<String, Object> albumData = new HashMap<>();
        albumData.put("userId", TestDataUtils.randomInt(1, MAX_USERS));
        albumData.put("title", TestDataUtils.randomString("Test Album"));
        return albumData;
    }
    


}
