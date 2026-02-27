package com.api.tests;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Users API Tests")
public class UsersApiTest extends BaseTest {
    
    @Test
    @DisplayName("GET /users returns 200 and 10 users")
    public void testGetAllUsers() {
        Response response = RequestBuilder.buildGetRequest("/users")
                .get("/users");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodySize(response, 10);
    }
    
    @Test
    @DisplayName("GET /users/{id} returns specific user")
    public void testGetUserById() {
        int userId = 1;
        Response response = RequestBuilder.buildGetRequest("/users/" + userId)
                .get("/users/" + userId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", userId);
    }
    
    @Test
    @DisplayName("POST /users creates new user")
    public void testCreateUser() {
        Response response = RequestBuilder.buildPostRequest("/users", TestDataProvider.getUserData())
                .post("/users");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("PUT /users/{id} updates existing user")
    public void testUpdateUser() {
        int userId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/users/" + userId, TestDataProvider.getUserData())
                .put("/users/" + userId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("DELETE /users/{id} deletes user")
    public void testDeleteUser() {
        int userId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/users/" + userId)
                .delete("/users/" + userId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("GET /users/{id} validates JSON schema")
    public void testUserJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/users/1")
                .get("/users/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "user-schema.json");
    }
}
