package com.api.base;

import com.api.utilities.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

/**
 * Base Test class for all API test classes.
 * <p>
 * This abstract class provides common setup and configuration for REST Assured
 * that is shared across all test classes. It initializes the base URI, default headers,
 * logging configuration, and Allure reporting integration.
 * </p>
 * <p>
 * All test classes should extend this class to inherit the common configuration
 * and avoid code duplication.
 * </p>
 * <p>
 * The {@code @TestInstance(TestInstance.Lifecycle.PER_CLASS)} annotation ensures
 * that {@code @BeforeAll} methods are executed once per test class instance rather
 * than once per test method.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    
    /**
     * Request specification that contains common configuration for all API requests.
     * <p>
     * This includes base URI, default headers, logging filters, and Allure reporting.
     * </p>
     */
    protected RequestSpecification requestSpec;
    
    /**
     * Setup method executed once before all tests in the test class.
     * <p>
     * This method initializes REST Assured configuration including:
     * <ul>
     *   <li>Base URI from ConfigManager</li>
     *   <li>Default Content-Type and Accept headers</li>
     *   <li>Allure reporting filter for test reports</li>
     *   <li>Request/response logging based on configuration</li>
     * </ul>
     * </p>
     */
    @BeforeAll
    public void setup() {
        RestAssured.baseURI = ConfigManager.getBaseUrl();
        
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.addHeader("Accept", "application/json");
        builder.addFilter(new AllureRestAssured());
        
        if (ConfigManager.shouldLogRequests()) {
            builder.log(LogDetail.ALL);
        }
        
        requestSpec = builder.build();
        
        RestAssured.requestSpecification = requestSpec;
    }
    
    /**
     * Retrieves the configured request specification.
     * <p>
     * This method provides access to the request specification for test classes
     * that need to customize or inspect the configuration.
     * </p>
     *
     * @return the configured RequestSpecification instance
     */
    protected RequestSpecification getRequestSpec() {
        return requestSpec;
    }
}
