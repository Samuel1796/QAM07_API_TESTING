package com.api.tests.comments;

import com.api.base.BaseTest;
import com.api.testdata.CommentsTestData;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Comments API POST endpoints.
 * <p>
 * This class contains tests for POST operations on the /comments
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Comments API POST Tests")
public class CommentsPostTest extends BaseTest {
    
    @Test
    @DisplayName("POST /comments creates new comment")
    public void testCreateComment() {
        Response response = RequestBuilder.buildPostRequest("/comments", CommentsTestData.getCommentData())
                .post("/comments");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("POST /comments returns 201 Created status code")
    public void testCreateCommentReturnsCorrectStatusCode() {
        Response response = RequestBuilder.buildPostRequest("/comments", CommentsTestData.getCommentData())
                .post("/comments");
        
        Assertions.assertEquals(201, response.getStatusCode(), 
            "POST should return 201 Created, not 200 OK");
    }
    
    @Test
    @DisplayName("POST /comments returns created resource with ID")
    public void testCreateCommentReturnsId() {
        Response response = RequestBuilder.buildPostRequest("/comments", CommentsTestData.getCommentData())
                .post("/comments");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        Integer id = response.jsonPath().get("id");
        Assertions.assertNotNull(id, 
            "Response should contain an ID for the created comment");
    }
}
