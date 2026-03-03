package com.api.tests.comments;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Comments API DELETE endpoints.
 * <p>
 * This class contains tests for DELETE operations on the /comments
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Comments API DELETE Tests")
public class CommentsDeleteTest extends BaseTest {
    
    @Test
    @DisplayName("DELETE /comments/{id} deletes comment")
    public void testDeleteComment() {
        int commentId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/comments/" + commentId)
                .delete("/comments/" + commentId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("DELETE /comments/999 on non-existing resource should return 404")
    public void testDeleteNonExistingComment_ShouldReturn404() {
        int nonExistingId = 999;
        
        Response response = RequestBuilder.buildDeleteRequest("/comments/" + nonExistingId)
                .delete("/comments/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("DELETE /comments/0 with invalid ID should return 404")
    public void testDeleteCommentWithIdZero_ShouldReturn404() {
        int invalidId = 0;
        
        Response response = RequestBuilder.buildDeleteRequest("/comments/" + invalidId)
                .delete("/comments/" + invalidId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
