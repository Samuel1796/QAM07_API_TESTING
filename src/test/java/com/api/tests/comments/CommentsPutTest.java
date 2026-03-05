package com.api.tests.comments;

import com.api.base.BaseTest;
import com.api.testdata.CommentsTestData;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Comments API PUT endpoints.
 * <p>
 * This class contains tests for PUT operations on the /comments
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Comments API PUT Tests")
public class CommentsPutTest extends BaseTest {
    
    @Test
    @DisplayName("PUT /comments/{id} updates existing comment")
    public void testUpdateComment() {
        int commentId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/comments/" + commentId, CommentsTestData.getCommentData())
                .put("/comments/" + commentId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("PUT /comments/999 on non-existing resource should return 404")
    public void testUpdateNonExistingComment_ShouldReturn404() {
        int nonExistingId = 999;
        
        Response response = RequestBuilder.buildPutRequest("/comments/" + nonExistingId, CommentsTestData.getCommentData())
                .put("/comments/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("PUT /comments/501 beyond valid range should return 404")
    public void testUpdateCommentBeyondValidRange_ShouldReturn404() {
        int beyondRangeId = 501; // JSONPlaceholder has comments 1-500
        
        Response response = RequestBuilder.buildPutRequest("/comments/" + beyondRangeId, CommentsTestData.getCommentData())
                .put("/comments/" + beyondRangeId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
