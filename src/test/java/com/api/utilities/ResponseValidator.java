package com.api.utilities;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Response Validator utility for validating API responses.
 * <p>
 * This utility class provides comprehensive validation methods for API responses including:
 * <ul>
 *   <li>HTTP status code validation</li>
 *   <li>Response time validation</li>
 *   <li>HTTP header validation</li>
 *   <li>JSON schema validation</li>
 *   <li>Response body content validation</li>
 *   <li>Response array size validation</li>
 * </ul>
 * </p>
 * <p>
 * All validation methods use JUnit 5 assertions and provide detailed error messages
 * to help identify the cause of test failures.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
public class ResponseValidator {
    
    /**
     * Validates that the response status code matches the expected value.
     * <p>
     * This is the most common validation performed on API responses to ensure
     * the request was processed correctly (e.g., 200 OK, 201 Created, 404 Not Found).
     * </p>
     *
     * @param response the HTTP response to validate
     * @param expectedCode the expected HTTP status code
     * @throws AssertionError if the actual status code does not match the expected code
     */
    public static void validateStatusCode(Response response, int expectedCode) {
        Assertions.assertEquals(expectedCode, response.getStatusCode(),
                "Expected status code " + expectedCode + " but got " + response.getStatusCode());
    }
    
    /**
     * Validates that the response time is within acceptable limits.
     * <p>
     * This validation ensures the API responds within performance requirements.
     * Slow responses may indicate performance issues or network problems.
     * </p>
     *
     * @param response the HTTP response to validate
     * @param maxTimeMs the maximum acceptable response time in milliseconds
     * @throws AssertionError if the response time exceeds the maximum allowed time
     */
    public static void validateResponseTime(Response response, long maxTimeMs) {
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        Assertions.assertTrue(responseTime <= maxTimeMs,
                "Response time " + responseTime + "ms exceeded maximum " + maxTimeMs + "ms");
    }
    
    /**
     * Validates that a specific HTTP header exists and contains the expected value.
     * <p>
     * This is useful for validating Content-Type, Cache-Control, Authorization,
     * and other HTTP headers that affect API behavior.
     * </p>
     *
     * @param response the HTTP response to validate
     * @param headerName the name of the header to validate
     * @param expectedValue the expected value (or substring) of the header
     * @throws AssertionError if the header is missing or does not contain the expected value
     */
    public static void validateHeader(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        Assertions.assertNotNull(actualValue, "Header " + headerName + " not found in response");
        Assertions.assertTrue(actualValue.contains(expectedValue),
                "Header " + headerName + " expected to contain '" + expectedValue + "' but was '" + actualValue + "'");
    }
    
    /**
     * Validates that the response body matches a JSON schema definition.
     * <p>
     * JSON schema validation ensures the response structure, data types, and required
     * fields match the API specification. Schema files should be placed in the
     * src/test/resources/schemas/ directory.
     * </p>
     *
     * @param response the HTTP response to validate
     * @param schemaPath the path to the JSON schema file (relative to schemas/ directory)
     * @throws AssertionError if the schema file is not found or the response does not match the schema
     */
    public static void validateJsonSchema(Response response, String schemaPath) {
        InputStream schemaStream = ResponseValidator.class.getClassLoader()
                .getResourceAsStream("schemas/" + schemaPath);
        Assertions.assertNotNull(schemaStream, "Schema file not found: " + schemaPath);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaStream));
    }
    
    /**
     * Validates that a specific field in the response body contains the expected value.
     * <p>
     * This method uses JsonPath to extract a value from the response and compares it
     * to the expected value. It's useful for validating specific fields without
     * checking the entire response structure.
     * </p>
     *
     * @param response the HTTP response to validate
     * @param key the JsonPath expression to extract the value (e.g., "id", "user.name")
     * @param expectedValue the expected value of the field
     * @throws AssertionError if the actual value does not match the expected value
     */
    public static void validateResponseBodyContains(Response response, String key, Object expectedValue) {
        Object actualValue = response.jsonPath().get(key);
        Assertions.assertEquals(expectedValue, actualValue,
                "Expected " + key + " to be " + expectedValue + " but was " + actualValue);
    }
    
    /**
     * Validates that a JSON array in the response has the expected number of elements.
     * <p>
     * This is useful for validating list endpoints that should return a specific
     * number of items (e.g., "GET /users should return 10 users").
     * </p>
     *
     * @param response the HTTP response to validate (must contain a JSON array at root level)
     * @param expectedSize the expected number of elements in the array
     * @throws AssertionError if the actual array size does not match the expected size
     */
    public static void validateResponseBodySize(Response response, int expectedSize) {
        int actualSize = response.jsonPath().getList("$").size();
        Assertions.assertEquals(expectedSize, actualSize,
                "Expected response array size " + expectedSize + " but got " + actualSize);
    }
}
