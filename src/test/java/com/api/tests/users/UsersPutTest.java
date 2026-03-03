package com.api.tests.users;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Users API PUT Tests")
public class UsersPutTest extends BaseTest {
    
    @Test
    @DisplayName("PUT /users/{id} updates existing user")
    public void testUpdateUser() {
        int userId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/users/" + userId, TestDataProvider.getUserData())
                .put("/users/" + userId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("PUT /users/999 on non-existing resource should return 404")
    public void testUpdateNonExistingUser_ShouldReturn404() {
        int nonExistingId = 999;
        
        Response response = RequestBuilder.buildPutRequest("/users/" + nonExistingId, TestDataProvider.getUserData())
                .put("/users/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("PUT /users/11 beyond valid range should return 404")
    public void testUpdateUserBeyondValidRange_ShouldReturn404() {
        int beyondRangeId = 11; // JSONPlaceholder has users 1-10
        
        Response response = RequestBuilder.buildPutRequest("/users/" + beyondRangeId, TestDataProvider.getUserData())
                .put("/users/" + beyondRangeId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
