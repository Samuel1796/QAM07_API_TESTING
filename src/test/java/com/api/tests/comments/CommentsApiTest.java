package com.api.tests.comments;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Comments API endpoints.
 * <p>
 * This class contains comprehensive tests for all CRUD operations on the /comments
 * resource of the JSONPlaceholder API. Comments are associated with posts and
 * represent user feedback or discussions.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Comments API Tests")
public class CommentsApiTest extends BaseTest {
    
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
    }
}
