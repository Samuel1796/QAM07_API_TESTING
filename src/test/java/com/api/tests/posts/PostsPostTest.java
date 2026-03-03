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
 * Test class for Posts API POST endpoints.
 * <p>
 * This class contains tests for POST operations on the /posts
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Posts API POST Tests")
public class PostsPostTest extends BaseTest {
    
    /**
     * Tests POST /posts endpoint to create a new post.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 201 Created</li>
     *   <li>Response contains an assigned ID for the new post</li>
     * </ul>
     * </p>
     * <p>
     * Note: JSONPlaceholder is a fake API, so the post is not actually created
     * but the API simulates the creation and returns ID 101.
     * </p>
     */
    @Test
    @DisplayName("POST /posts creates new post")
    public void testCreatePost() {
        Map<String, Object> postData = TestDataProvider.getPostData();
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        // Verify response contains an ID (JSONPlaceholder returns id: 101 for new posts)
        Integer id = response.jsonPath().get("id");
        org.junit.jupiter.api.Assertions.assertNotNull(id, "Response should contain an id");
    }
}
