package com.api.tests.posts;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Test class for Posts API PUT endpoints.
 * <p>
 * This class contains tests for PUT operations on the /posts
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Posts API PUT Tests")
public class PostsPutTest extends BaseTest {
    
    /**
     * Tests PUT /posts/{id} endpoint to update an existing post.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 200 OK</li>
     *   <li>Response contains the updated post with correct ID</li>
     * </ul>
     * </p>
     * <p>
     * Note: JSONPlaceholder is a fake API, so the post is not actually updated
     * but the API simulates the update and returns the modified data.
     * </p>
     */
    @Test
    @DisplayName("PUT /posts/{id} updates existing post")
    public void testUpdatePost() {
        int postId = 1;
        Map<String, Object> postData = TestDataProvider.getPostData();
        postData.put("id", postId);
        
        Response response = RequestBuilder.buildPutRequest("/posts/" + postId, postData)
                .put("/posts/" + postId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", postId);
    }
}
