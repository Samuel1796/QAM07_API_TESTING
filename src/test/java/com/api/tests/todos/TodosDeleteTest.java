package com.api.tests.todos;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Todos API DELETE Tests")
public class TodosDeleteTest extends BaseTest {
    
    @Test
    @DisplayName("DELETE /todos/{id} deletes todo")
    public void testDeleteTodo() {
        int todoId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/todos/" + todoId)
                .delete("/todos/" + todoId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("DELETE /todos/999 on non-existing resource should return 404")
    public void testDeleteNonExistingTodo_ShouldReturn404() {
        int nonExistingId = 999;
        
        Response response = RequestBuilder.buildDeleteRequest("/todos/" + nonExistingId)
                .delete("/todos/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("DELETE /todos/0 with invalid ID should return 404")
    public void testDeleteTodoWithIdZero_ShouldReturn404() {
        int invalidId = 0;
        
        Response response = RequestBuilder.buildDeleteRequest("/todos/" + invalidId)
                .delete("/todos/" + invalidId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
