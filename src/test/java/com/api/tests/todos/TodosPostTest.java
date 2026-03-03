package com.api.tests.todos;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
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
    
    @Test
    @DisplayName("POST /todos returns 201 Created status code")
    public void testCreateTodoReturnsCorrectStatusCode() {
        Response response = RequestBuilder.buildPostRequest("/todos", TestDataProvider.getTodoData())
                .post("/todos");
        
        Assertions.assertEquals(201, response.getStatusCode(), 
            "POST should return 201 Created, not 200 OK");
    }
    
    @Test
    @DisplayName("POST /todos returns created resource with ID")
    public void testCreateTodoReturnsId() {
        Response response = RequestBuilder.buildPostRequest("/todos", TestDataProvider.getTodoData())
                .post("/todos");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        Integer id = response.jsonPath().get("id");
        Assertions.assertNotNull(id, 
            "Response should contain an ID for the created todo");
    }
}
