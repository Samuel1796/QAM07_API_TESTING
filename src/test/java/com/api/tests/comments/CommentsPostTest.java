package com.api.tests.comments;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
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
        Response response = RequestBuilder.buildPostRequest("/comments", TestDataProvider.getCommentData())
                .post("/comments");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
}
