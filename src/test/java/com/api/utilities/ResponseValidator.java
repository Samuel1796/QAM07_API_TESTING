package com.api.utilities;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class ResponseValidator {
    
    public static void validateStatusCode(Response response, int expectedCode) {
        Assertions.assertEquals(expectedCode, response.getStatusCode(),
                "Expected status code " + expectedCode + " but got " + response.getStatusCode());
    }
    
    public static void validateResponseTime(Response response, long maxTimeMs) {
        long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
        Assertions.assertTrue(responseTime <= maxTimeMs,
                "Response time " + responseTime + "ms exceeded maximum " + maxTimeMs + "ms");
    }
    
    public static void validateHeader(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        Assertions.assertNotNull(actualValue, "Header " + headerName + " not found in response");
        Assertions.assertTrue(actualValue.contains(expectedValue),
                "Header " + headerName + " expected to contain '" + expectedValue + "' but was '" + actualValue + "'");
    }
    
    public static void validateJsonSchema(Response response, String schemaPath) {
        InputStream schemaStream = ResponseValidator.class.getClassLoader()
                .getResourceAsStream("schemas/" + schemaPath);
        Assertions.assertNotNull(schemaStream, "Schema file not found: " + schemaPath);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaStream));
    }
    
    public static void validateResponseBodyContains(Response response, String key, Object expectedValue) {
        Object actualValue = response.jsonPath().get(key);
        Assertions.assertEquals(expectedValue, actualValue,
                "Expected " + key + " to be " + expectedValue + " but was " + actualValue);
    }
    
    public static void validateResponseBodySize(Response response, int expectedSize) {
        int actualSize = response.jsonPath().getList("$").size();
        Assertions.assertEquals(expectedSize, actualSize,
                "Expected response array size " + expectedSize + " but got " + actualSize);
    }
}
