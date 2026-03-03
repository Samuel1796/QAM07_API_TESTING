package com.api.tests;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Base class for edge case tests across all API resources.
 * <p>
 * Provides parameterized tests for common edge cases like boundary IDs,
 * invalid IDs, and error scenarios that apply to all resources.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 */
public abstract class BaseEdgeCaseTest extends BaseTest {

    /**
     * Returns the resource endpoint (e.g., "/posts", "/users", "/todos")
     */
    protected abstract String getResourceEndpoint();

    /**
     * Returns the minimum valid ID for this resource
     */
    protected abstract int getMinValidId();

    /**
     * Returns the maximum valid ID for this resource
     */
    protected abstract int getMaxValidId();

    @ParameterizedTest(name = "GET {0} with ID {1} should return {2}")
    @CsvSource({
        "minimum boundary, 1, 200",
        "maximum boundary, 100, 200",
        "below minimum, 0, 404",
        "above maximum, 101, 404",
        "negative, -1, 404"
    })
    @DisplayName("Boundary ID tests")
    public void testBoundaryIds(String scenario, int id, int expectedStatus) {
        // Override min/max based on resource
        int testId = id;
        if (scenario.equals("minimum boundary")) {
            testId = getMinValidId();
        } else if (scenario.equals("maximum boundary")) {
            testId = getMaxValidId();
        } else if (scenario.equals("below minimum")) {
            testId = getMinValidId() - 1;
        } else if (scenario.equals("above maximum")) {
            testId = getMaxValidId() + 1;
        }

        Response response = RequestBuilder.buildGetRequest(getResourceEndpoint() + "/" + testId)
                .get(getResourceEndpoint() + "/" + testId);

        ResponseValidator.validateStatusCode(response, expectedStatus);
        
        if (expectedStatus == 200) {
            ResponseValidator.validateResponseBodyContains(response, "id", testId);
        }
    }

    /**
     * Returns test data for the resource (to be implemented by subclasses)
     */
    protected abstract java.util.Map<String, Object> getTestData();
}
