package com.api.utilities;

import io.restassured.specification.RequestSpecification;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Request Builder utility for constructing REST Assured HTTP requests.
 * <p>
 * This utility class provides static factory methods for building different types
 * of HTTP requests (GET, POST, PUT, DELETE) with common configurations applied.
 * It simplifies request creation and ensures consistency across all API tests.
 * </p>
 * <p>
 * All methods return a {@link RequestSpecification} that can be further customized
 * before execution.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
public class RequestBuilder {
    
    /**
     * Builds a GET request specification.
     * <p>
     * Creates a request with Content-Type set to application/json.
     * This is typically used for retrieving resources from the API.
     * </p>
     *
     * @param endpoint the API endpoint path (not used in current implementation but kept for future extensibility)
     * @return a configured RequestSpecification for GET requests
     */
    public static RequestSpecification buildGetRequest(String endpoint) {
        return given()
                .contentType("application/json")
                .when();
    }
    
    /**
     * Builds a POST request specification with a request body.
     * <p>
     * Creates a request with Content-Type set to application/json and includes
     * the provided body object which will be serialized to JSON.
     * This is typically used for creating new resources.
     * </p>
     *
     * @param endpoint the API endpoint path (not used in current implementation but kept for future extensibility)
     * @param body the request body object to be serialized to JSON
     * @return a configured RequestSpecification for POST requests
     */
    public static RequestSpecification buildPostRequest(String endpoint, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when();
    }
    
    /**
     * Builds a PUT request specification with a request body.
     * <p>
     * Creates a request with Content-Type set to application/json and includes
     * the provided body object which will be serialized to JSON.
     * This is typically used for updating existing resources.
     * </p>
     *
     * @param endpoint the API endpoint path (not used in current implementation but kept for future extensibility)
     * @param body the request body object to be serialized to JSON
     * @return a configured RequestSpecification for PUT requests
     */
    public static RequestSpecification buildPutRequest(String endpoint, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when();
    }
    
    /**
     * Builds a DELETE request specification.
     * <p>
     * Creates a request with Content-Type set to application/json.
     * This is typically used for deleting resources from the API.
     * </p>
     *
     * @param endpoint the API endpoint path (not used in current implementation but kept for future extensibility)
     * @return a configured RequestSpecification for DELETE requests
     */
    public static RequestSpecification buildDeleteRequest(String endpoint) {
        return given()
                .contentType("application/json")
                .when();
    }
    
    /**
     * Builds a request specification with query parameters.
     * <p>
     * Creates a request with Content-Type set to application/json and includes
     * the provided query parameters. This is useful for filtering, pagination,
     * or passing additional data in the URL.
     * </p>
     *
     * @param endpoint the API endpoint path (not used in current implementation but kept for future extensibility)
     * @param params a map of query parameter names and values
     * @return a configured RequestSpecification with query parameters
     */
    public static RequestSpecification buildRequestWithQueryParams(String endpoint, Map<String, String> params) {
        return given()
                .contentType("application/json")
                .queryParams(params)
                .when();
    }
}
