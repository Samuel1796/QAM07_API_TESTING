package com.api.base;

import com.api.utilities.ConfigManager;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base class for property-based tests providing common configuration.
 * <p>
 * This class extends the standard BaseTest and provides additional configuration
 * specific to property-based testing using JUnit QuickCheck. All property test
 * classes should extend this base class to inherit common setup and configuration.
 * </p>
 * <p>
 * Property-based tests verify universal properties that should hold true across
 * all valid inputs by generating random test data and running tests multiple times
 * (default: 100 trials per property).
 * </p>
 * <p>
 * Configuration:
 * - Base URI: Configured from ConfigManager (JSONPlaceholder API)
 * - Default trials: 100 iterations per property test
 * - Custom generators: Available for all resource types (albums, comments, photos, posts, todos, users)
 * </p>
 * <p>
 * Usage Example:
 * <pre>
 * {@code
 * public class PostPropertyTest extends BasePropertyTest {
 *     
 *     @Property(trials = 100)
 *     public void postRequestCreatesResourceWith201AndId(
 *         @ForAll @From(PostDataGenerator.class) Map<String, Object> postData) {
 *         
 *         Response response = RequestBuilder.buildPostRequest("/posts", postData)
 *                 .post("/posts");
 *         
 *         ResponseValidator.validateStatusCode(response, 201);
 *         Integer id = response.jsonPath().get("id");
 *         Assertions.assertNotNull(id, "Response should contain an id");
 *     }
 * }
 * }
 * </pre>
 * </p>
 *
 * @author API Test Automation Team
 * @version 1.0
 * @see com.api.generators.AlbumDataGenerator
 * @see com.api.generators.CommentDataGenerator
 * @see com.api.generators.PhotoDataGenerator
 * @see com.api.generators.PostDataGenerator
 * @see com.api.generators.TodoDataGenerator
 * @see com.api.generators.UserDataGenerator
 */
public abstract class BasePropertyTest {
    
    /**
     * Default number of trials for property-based tests.
     * Each property test will run this many times with different random inputs.
     */
    protected static final int DEFAULT_TRIALS = 100;
    
    /**
     * Sets up the base configuration for all property-based tests.
     * Configures RestAssured with the base URI from ConfigManager.
     */
    @BeforeAll
    public static void setupPropertyTests() {
        RestAssured.baseURI = ConfigManager.getBaseUrl();
    }
}
