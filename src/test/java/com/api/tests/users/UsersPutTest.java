package com.api.tests.users;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
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
}
