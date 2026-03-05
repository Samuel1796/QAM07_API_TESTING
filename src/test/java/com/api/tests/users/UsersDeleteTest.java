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
    
    @Test
    @DisplayName("DELETE /users/999 on non-existing resource should return 404")
    public void testDeleteNonExistingUser_ShouldReturn404() {
        int nonExistingId = 999;
        
        Response response = RequestBuilder.buildDeleteRequest("/users/" + nonExistingId)
                .delete("/users/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("DELETE /users/0 with invalid ID should return 404")
    public void testDeleteUserWithIdZero_ShouldReturn404() {
        int invalidId = 0;
        
        Response response = RequestBuilder.buildDeleteRequest("/users/" + invalidId)
                .delete("/users/" + invalidId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
