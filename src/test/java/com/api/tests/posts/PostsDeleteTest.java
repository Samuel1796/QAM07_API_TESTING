package com.api.tests.posts;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Posts API DELETE endpoints.
 * <p>
 * This class contains tests for DELETE operations on the /posts
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Posts API DELETE Tests")
public class PostsDeleteTest extends BaseTest {
    
    /**
     * Tests DELETE /posts/{id} endpoint to delete a post.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 200 OK</li>
     * </ul>
     * </p>
     * <p>
     * Note: JSONPlaceholder is a fake API, so the post is not actually deleted
     * but the API simulates the deletion.
     * </p>
     */
    @Test
    @DisplayName("DELETE /posts/{id} deletes post")
    public void testDeletePost() {
        int postId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/posts/" + postId)
                .delete("/posts/" + postId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
}
