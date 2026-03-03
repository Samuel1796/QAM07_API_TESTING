package com.api.tests.comments;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Comments API GET endpoints.
 * <p>
 * This class contains tests for GET operations on the /comments
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Comments API GET Tests")
public class CommentsGetTest extends BaseTest {
    
    @Test
    @DisplayName("GET /comments/{id} returns specific comment")
    public void testGetCommentById() {
        int commentId = 1;
        Response response = RequestBuilder.buildGetRequest("/comments/" + commentId)
                .get("/comments/" + commentId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", commentId);
    }
}
