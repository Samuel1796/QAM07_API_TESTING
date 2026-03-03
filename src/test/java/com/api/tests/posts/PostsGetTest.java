package com.api.tests.posts;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Posts API GET endpoints.
 * <p>
 * This class contains tests for GET operations on the /posts
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Posts API GET Tests")
public class PostsGetTest extends BaseTest {
    
    /**
     * Tests GET /posts endpoint to retrieve all posts.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 200 OK</li>
     *   <li>Content-Type header is application/json</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("GET /posts returns 200 and list of posts")
    public void testGetAllPosts() {
        Response response = RequestBuilder.buildGetRequest("/posts")
                .get("/posts");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateHeader(response, "Content-Type", "application/json");
    }
    
    /**
     * Tests GET /posts/{id} endpoint to retrieve a specific post by ID.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 200 OK</li>
     *   <li>Returned post ID matches the requested ID</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("GET /posts/{id} returns specific post")
    public void testGetPostById() {
        int postId = 1;
        Response response = RequestBuilder.buildGetRequest("/posts/" + postId)
                .get("/posts/" + postId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", postId);
    }
    
    @Test
    @DisplayName("GET /posts/{id} validates JSON schema")
    public void testPostJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/posts/1")
                .get("/posts/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "post-schema.json");
    }
}
