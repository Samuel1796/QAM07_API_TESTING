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
    
    /**
     * Generates a valid random album ID within the range 1-100.
     *
     * @return valid album ID
     */
    public static int getValidAlbumId() {
        return TestDataUtils.randomInt(1, MAX_ALBUMS);
    }
    
    /**
     * Generates an invalid album ID outside the valid range.
     * Returns either 0, a negative number, or a number greater than 100.
     *
     * @return invalid album ID
     */
    public static int getInvalidAlbumId() {
        int choice = TestDataUtils.randomInt(1, 3);
        switch (choice) {
            case 1:
                return 0;
            case 2:
                return TestDataUtils.randomInt(-100, -1);
            default:
                return TestDataUtils.randomInt(MAX_ALBUMS + 1, MAX_ALBUMS + 100);
        }
    }
    
    /**
     * Generates boundary value album IDs based on the specified boundary type.
     *
     * @param boundaryType the type of boundary value: "min", "max", "below_min", or "above_max"
     * @return boundary value album ID
     */
    public static int getBoundaryAlbumId(String boundaryType) {
        switch (boundaryType.toLowerCase()) {
            case "min":
                return 1;
            case "max":
                return MAX_ALBUMS;
            case "below_min":
                return 0;
            case "above_max":
                return MAX_ALBUMS + 1;
            default:
                return 1; // Safe default
        }
    }
}
