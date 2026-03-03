package com.api.tests.todos;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Todos API PUT Tests")
public class TodosPutTest extends BaseTest {
    
    @Test
    @DisplayName("PUT /todos/{id} updates existing todo")
    public void testUpdateTodo() {
        int todoId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/todos/" + todoId, TestDataProvider.getTodoData())
                .put("/todos/" + todoId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("PUT /todos/999 on non-existing resource should return 404")
    public void testUpdateNonExistingTodo_ShouldReturn404() {
        int nonExistingId = 999;
        
        Response response = RequestBuilder.buildPutRequest("/todos/" + nonExistingId, TestDataProvider.getTodoData())
                .put("/todos/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("PUT /todos/201 beyond valid range should return 404")
    public void testUpdateTodoBeyondValidRange_ShouldReturn404() {
        int beyondRangeId = 201; // JSONPlaceholder has todos 1-200
        
        Response response = RequestBuilder.buildPutRequest("/todos/" + beyondRangeId, TestDataProvider.getTodoData())
                .put("/todos/" + beyondRangeId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
