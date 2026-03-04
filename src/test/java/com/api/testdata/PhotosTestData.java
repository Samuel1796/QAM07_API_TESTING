package com.api.testdata;

import java.util.HashMap;
import java.util.Map;

/**
 * Test data generation for photo resources.
 * <p>
 * This class provides methods to generate test data specific to photo resources,
 * including valid photo data with placeholder image URLs.
 * </p>
 * <p>
 * All methods are static and the class cannot be instantiated.
 * </p>
 *
 * @author API Test Automation Team
 * @version 1.0
 * @since 3.0
 */
public class PhotosTestData {
    
    /**
     * Maximum number of photos in JSONPlaceholder API.
     */
    public static final int MAX_PHOTOS = 5000;
    
    /**
     * Maximum number of albums in JSONPlaceholder API (for albumId associations).
     */
    public static final int MAX_ALBUMS = 100;
    
    /**
     * Private constructor to prevent instantiation.
     */
    private PhotosTestData() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Generates random valid photo data.
     *
     * @return Map containing albumId, title, url, and thumbnailUrl
     */
    public static Map<String, Object> getPhotoData() {
        Map<String, Object> photoData = new HashMap<>();
        photoData.put("albumId", TestDataUtils.randomInt(1, MAX_ALBUMS));
        photoData.put("title", TestDataUtils.randomString("Test Photo"));
        
        // Generate random color codes for placeholder URLs
        String color = generateRandomColorCode();
        photoData.put("url", "https://via.placeholder.com/600/" + color);
        photoData.put("thumbnailUrl", "https://via.placeholder.com/150/" + color);
        
        return photoData;
    }
    
    /**
     * Generates a random 6-digit hexadecimal color code.
     *
     * @return 6-digit hex color code (without # prefix)
     */
    private static String generateRandomColorCode() {
        return String.format("%06x", TestDataUtils.randomInt(0, 0xFFFFFF));
    }
}
