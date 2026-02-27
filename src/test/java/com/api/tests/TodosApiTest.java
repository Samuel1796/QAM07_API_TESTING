package com.api.tests;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Todos API Tests")
public class TodosApiTest extends BaseTest {
    
    @Test
    @DisplayName("GET /todos returns 200 and 200 todos")
    public void testGetAllTodos() {
        Response response = RequestBuilder.buildGetRequest("/todos")
                .get("/todos");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodySize(response, 200);
    }
    
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
    @DisplayName("POST /todos creates new todo")
    public void testCreateTodo() {
        Response response = RequestBuilder.buildPostRequest("/todos", TestDataProvider.getTodoData())
                .post("/todos");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("PUT /todos/{id} updates existing todo")
    public void testUpdateTodo() {
        int todoId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/todos/" + todoId, TestDataProvider.getTodoData())
                .put("/todos/" + todoId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("DELETE /todos/{id} deletes todo")
    public void testDeleteTodo() {
        int todoId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/todos/" + todoId)
                .delete("/todos/" + todoId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("GET /todos/{id} validates JSON schema")
    public void testTodoJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/todos/1")
                .get("/todos/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "todo-schema.json");
    }
}
