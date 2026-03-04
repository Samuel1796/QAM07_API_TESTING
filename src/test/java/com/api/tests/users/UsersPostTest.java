package com.api.tests.users;

import com.api.base.BaseTest;
import com.api.testdata.UsersTestData;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Users API POST Tests")
public class UsersPostTest extends BaseTest {
    
    @Test
    @DisplayName("POST /users creates new user")
    public void testCreateUser() {
        Response response = RequestBuilder.buildPostRequest("/users", UsersTestData.getUserData())
                .post("/users");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("POST /users returns 201 Created status code")
    public void testCreateUserReturnsCorrectStatusCode() {
        Response response = RequestBuilder.buildPostRequest("/users", UsersTestData.getUserData())
                .post("/users");
        
        Assertions.assertEquals(201, response.getStatusCode(), 
            "POST should return 201 Created, not 200 OK");
    }
    
    @Test
    @DisplayName("POST /users returns created resource with ID")
    public void testCreateUserReturnsId() {
        Response response = RequestBuilder.buildPostRequest("/users", UsersTestData.getUserData())
                .post("/users");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        Integer id = response.jsonPath().get("id");
        Assertions.assertNotNull(id, 
            "Response should contain an ID for the created user");
    }
}
