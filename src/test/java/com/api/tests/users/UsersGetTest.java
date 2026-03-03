package com.api.tests.users;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Users API GET Tests")
public class UsersGetTest extends BaseTest {
    
    @Test
    @DisplayName("GET /users/{id} returns specific user")
    public void testGetUserById() {
        int userId = 1;
        Response response = RequestBuilder.buildGetRequest("/users/" + userId)
                .get("/users/" + userId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", userId);
    }
}
