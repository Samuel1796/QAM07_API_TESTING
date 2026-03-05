package com.api.tests.todos;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Todos API GET Tests")
public class TodosGetTest extends BaseTest {
    
    @Test
    @DisplayName("GET /todos/{id} returns specific todo")
    public void testGetTodoById() {
        int todoId = 1;
        Response response = RequestBuilder.buildGetRequest("/todos/" + todoId)
                .get("/todos/" + todoId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", todoId);
    }
    
    @Test
    @DisplayName("GET /todos/{id} validates JSON schema")
    public void testTodoJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/todos/1")
                .get("/todos/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "todo-schema.json");
    }
    
    @Test
    @DisplayName("GET /todos returns 200 and list of todos")
    public void testGetAllTodos() {
        Response response = RequestBuilder.buildGetRequest("/todos")
                .get("/todos");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateHeader(response, "Content-Type", "application/json");
    }
}
