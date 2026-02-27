package com.api.base;

import com.api.utilities.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected RequestSpecification requestSpec;
    
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
    
    protected RequestSpecification getRequestSpec() {
        return requestSpec;
    }
}
