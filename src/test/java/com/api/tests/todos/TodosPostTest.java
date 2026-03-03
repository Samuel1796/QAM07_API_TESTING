package com.api.tests.todos;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Todos API POST Tests")
public class TodosPostTest extends BaseTest {
    
    @Test
    @DisplayName("POST /todos creates new todo")
    public void testCreateTodo() {
        Response response = RequestBuilder.buildPostRequest("/todos", TestDataProvider.getTodoData())
                .post("/todos");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
}
