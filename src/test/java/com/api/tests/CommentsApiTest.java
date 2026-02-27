package com.api.tests;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Comments API Tests")
public class CommentsApiTest extends BaseTest {
    
    @Test
    @DisplayName("GET /comments returns 200 and 500 comments")
    public void testGetAllComments() {
        Response response = RequestBuilder.buildGetRequest("/comments")
                .get("/comments");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodySize(response, 500);
    }
    
    @Test
    @DisplayName("GET /comments/{id} returns specific comment")
    public void testGetCommentById() {
        int commentId = 1;
        Response response = RequestBuilder.buildGetRequest("/comments/" + commentId)
                .get("/comments/" + commentId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", commentId);
    }
    
    @Test
    @DisplayName("POST /comments creates new comment")
    public void testCreateComment() {
        Response response = RequestBuilder.buildPostRequest("/comments", TestDataProvider.getCommentData())
                .post("/comments");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        Integer id = response.jsonPath().get("id");
        org.junit.jupiter.api.Assertions.assertNotNull(id, "Response should contain an id");
    }
    
    @Test
    @DisplayName("PUT /comments/{id} updates existing comment")
    public void testUpdateComment() {
        int commentId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/comments/" + commentId, TestDataProvider.getCommentData())
                .put("/comments/" + commentId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("DELETE /comments/{id} deletes comment")
    public void testDeleteComment() {
        int commentId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/comments/" + commentId)
                .delete("/comments/" + commentId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("GET /comments/{id} validates JSON schema")
    public void testCommentJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/comments/1")
                .get("/comments/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "comment-schema.json");
    }
}
