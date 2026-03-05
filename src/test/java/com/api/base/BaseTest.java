package com.api.base;

import com.api.listeners.TestResultLogger;
import com.api.utilities.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Test class for all API test classes.
 * This abstract class provides centralized configuration and setup for REST Assured
 * that is shared across all API test classes. It establishes a foundation for:
 * <ul>
 *   <li>Base URI configuration for API endpoints</li>
 *   <li>Default HTTP headers (Content-Type and Accept)</li>
 *   <li>Request/response logging and validation</li>
 *   <li>Allure reporting integration for test reports</li>
 *   <li>Test result logging with timestamps and status</li>
 * </ul>
 *
 * <p><b>Usage:</b> All test classes must extend this class to inherit common
 * configuration and maintain consistency across the test suite. This prevents
 * code duplication and ensures uniform setup/teardown operations.</p>
 *
 * <p><b>Lifecycle:</b> The {@code @ExtendWith(TestResultLogger.class)} annotation
 * enables automatic logging of test execution results with detailed information
 * about test status, duration, and any failures.</p>
 *
 * <p><b>Note:</b> The {@code @TestInstance(TestInstance.Lifecycle.PER_CLASS)}
 * annotation is commented out but can be enabled if {@code @BeforeAll} methods
 * need to execute once per test class instance rather than once per test method.</p>
 */
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) //
@ExtendWith(TestResultLogger.class)
public abstract class BaseTest {
    
    /**
     * Logger instance for this class.
     * Used throughout the test suite to log:
     *
     */
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    
    /**
     * Request specification that contains common configuration for all API requests.
     *
     * <p>This is a statically initialized specification that is reused across all
     * test methods within the test suite. It encapsulates:</p>
     * <p>Since this is statically initialized, all HTTP requests
     * made through this specification will share the same configuration, reducing
     * setup overhead and ensuring consistency across the entire test suite.</p>
     */
    protected static RequestSpecification requestSpec;

    /**
     * Setup method executed once before all tests in the test class.
     *
     * <p>This initialization method configures the REST Assured framework with all
     * necessary settings to ensure consistent behavior across all API tests. The
     * sequence of operations is as follows:</p>
     *
     * <p> This centralized setup ensures that all test classes
     * inheriting from BaseTest will use the same configuration without redundant code.</p>
     */
    @BeforeAll
    public static void setup() {
        // Log the framework initialization with base URL from configuration
        logger.info("Initializing test framework with base URL: {}", ConfigManager.getBaseUrl());
        
        // Set the global base URI for all REST Assured requests
        RestAssured.baseURI = ConfigManager.getBaseUrl();
        
        // Create a RequestSpecBuilder to configure common request properties
        RequestSpecBuilder builder = new RequestSpecBuilder();

        // Set Content-Type to JSON for all requests
        builder.setContentType("application/json");

        // Set Accept header to JSON for all responses

        builder.addHeader("Accept", "application/json");

        // Add Allure filter to capture request/response details in Allure reports
        builder.addFilter(new AllureRestAssured());
        
        // Enable logging only when validation fails to reduce console output
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Build and store the request specification for use across all tests
        requestSpec = builder.build();
        
        // Log successful completion of the framework initialization
        logger.info("Test framework initialized successfully");
    }



    /**
     * Teardown method executed once after all tests in the test class.
     *
     * <p>This cleanup method performs resource management operations to ensure proper
     * test isolation and prevent state leakage between test classes. Operations include:</p>
     *
     * <p><b>Importance:</b> Proper cleanup prevents configuration state from persisting
     * across test classes, which could cause unexpected behavior or test failures.</p>
     */
    @AfterAll
    public static void teardown() {
        // Log the start of the cleanup process
        logger.info("Cleaning up test framework resources");
        
        // Reset REST Assured configuration to default values
        // This clears baseURI, headers, filters, and other global settings
        RestAssured.reset();
        
        // Log successful completion of cleanup
        logger.info("Test framework cleanup completed");
    }
}
