package com.api.tests.users;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Users API POST Tests")
public class UsersPostTest extends BaseTest {
    
    @Test
    @DisplayName("POST /users creates new user")
    public void testCreateUser() {
        Response response = RequestBuilder.buildPostRequest("/users", TestDataProvider.getUserData())
                .post("/users");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
}
