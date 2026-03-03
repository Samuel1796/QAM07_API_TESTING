package com.api.tests.users;

import com.api.tests.BaseEdgeCaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Edge Case Tests for Users API.
 * <p>
 * Tests boundary values, invalid inputs, malformed data,
 * and other edge cases for the /users endpoint.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 */
@Epic("Users API")
@Feature("Edge Cases")
@DisplayName("Users API Edge Case Tests")
public class UsersEdgeCaseTest extends BaseEdgeCaseTest {

    @Override
    protected String getResourceEndpoint() {
        return "/users";
    }

    @Override
    protected int getMinValidId() {
        return 1;
    }

    @Override
    protected int getMaxValidId() {
        return 10;
    }

    @Override
    protected Map<String, Object> getTestData() {
        return TestDataProvider.getUserData();
    }
    
    @Test
    @DisplayName("POST /users with invalid email format")
    @Description("Test creating user with malformed email address")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateUserWithInvalidEmail() {
        Map<String, Object> userData = TestDataProvider.getUserDataWithInvalidEmail();
        
        Response response = RequestBuilder.buildPostRequest("/users", userData)
                .post("/users");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
}
