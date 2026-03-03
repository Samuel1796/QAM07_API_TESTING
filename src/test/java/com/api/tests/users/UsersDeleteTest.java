package com.api.tests.users;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Users API DELETE Tests")
public class UsersDeleteTest extends BaseTest {
    
    @Test
    @DisplayName("DELETE /users/{id} deletes user")
    public void testDeleteUser() {
        int userId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/users/" + userId)
                .delete("/users/" + userId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
}
