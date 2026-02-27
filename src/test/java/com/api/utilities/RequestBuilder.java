package com.api.utilities;

import io.restassured.specification.RequestSpecification;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestBuilder {
    
    public static RequestSpecification buildGetRequest(String endpoint) {
        return given()
                .contentType("application/json")
                .when();
    }
    
    public static RequestSpecification buildPostRequest(String endpoint, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when();
    }
    
    public static RequestSpecification buildPutRequest(String endpoint, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .when();
    }
    
    public static RequestSpecification buildDeleteRequest(String endpoint) {
        return given()
                .contentType("application/json")
                .when();
    }
    
    public static RequestSpecification buildRequestWithQueryParams(String endpoint, Map<String, String> params) {
        return given()
                .contentType("application/json")
                .queryParams(params)
                .when();
    }
}
