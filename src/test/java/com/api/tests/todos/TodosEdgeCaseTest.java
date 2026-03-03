package com.api.tests.todos;

import com.api.tests.BaseEdgeCaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Edge Case Tests for Todos API.
 * <p>
 * Tests boundary values, invalid inputs, malformed data,
 * and other edge cases for the /todos endpoint.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 */
@Epic("Todos API")
@Feature("Edge Cases")
@DisplayName("Todos API Edge Case Tests")
public class TodosEdgeCaseTest extends BaseEdgeCaseTest {

    @Override
    protected String getResourceEndpoint() {
        return "/todos";
    }

    @Override
    protected int getMinValidId() {
        return 1;
    }

    @Override
    protected int getMaxValidId() {
        return 200;
    }

    @Override
    protected Map<String, Object> getTestData() {
        return TestDataProvider.getTodoData();
    }

    @ParameterizedTest(name = "POST /todos with empty {0}")
    @CsvSource({
        "title, title",
        "userId, userId",
        "all, ''"
    })
    @DisplayName("Empty field tests")
    @Description("Test creating todo with empty or null fields")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateTodoWithEmptyFields(String fieldType, String fieldToCheck) {
        Map<String, Object> todoData = TestDataProvider.getTodoDataWithEmptyFields(fieldType);

        Response response = RequestBuilder.buildPostRequest("/todos", todoData)
                .post("/todos");

        ResponseValidator.validateStatusCode(response, 201);
    }

    @Test
    @DisplayName("POST /todos with special characters")
    @Description("Test creating todo with special characters in title")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateTodoWithSpecialCharacters() {
        Map<String, Object> todoData = TestDataProvider.getTodoDataWithSpecialCharacters();

        Response response = RequestBuilder.buildPostRequest("/todos", todoData)
                .post("/todos");

        ResponseValidator.validateStatusCode(response, 201);
    }
}
