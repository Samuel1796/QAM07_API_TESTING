package com.api.tests;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Test class for Posts API endpoints.
 * <p>
 * This class contains comprehensive tests for all CRUD operations on the /posts
 * resource of the JSONPlaceholder API. It validates:
 * <ul>
 *   <li>GET requests for retrieving posts</li>
 *   <li>POST requests for creating posts</li>
 *   <li>PUT requests for updating posts</li>
 *   <li>DELETE requests for deleting posts</li>
 *   <li>Query parameter filtering</li>
 *   <li>Nested resource access (comments)</li>
 *   <li>JSON schema validation</li>
 *   <li>Response time performance</li>
 * </ul>
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Posts API Tests")
public class PostsApiTest extends BaseTest {
    
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
    
    /**
     * Tests GET /posts with query parameters to filter posts by userId.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 200 OK</li>
     *   <li>All returned posts belong to the specified user (userId = 1)</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("GET /posts?userId=1 filters posts by user")
    public void testGetPostsByUserId() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", "1");
        
        Response response = RequestBuilder.buildRequestWithQueryParams("/posts", queryParams)
                .get("/posts");
        
        ResponseValidator.validateStatusCode(response, 200);
        
        // Verify all returned posts have userId = 1
        response.jsonPath().getList("userId", Integer.class).forEach(userId -> {
            org.junit.jupiter.api.Assertions.assertEquals(1, userId, 
                    "All posts should have userId = 1");
        });
    }
    
    /**
     * Tests GET /posts/{id}/comments nested endpoint to retrieve comments for a post.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 200 OK</li>
     *   <li>All returned comments belong to the specified post</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("GET /posts/1/comments returns comments for post")
    public void testGetPostComments() {
        int postId = 1;
        
        Response response = RequestBuilder.buildGetRequest("/posts/" + postId + "/comments")
                .get("/posts/" + postId + "/comments");
        
        ResponseValidator.validateStatusCode(response, 200);
        
        // Verify all comments belong to the specified post
        response.jsonPath().getList("postId", Integer.class).forEach(returnedPostId -> {
            org.junit.jupiter.api.Assertions.assertEquals(postId, returnedPostId,
                    "All comments should belong to post " + postId);
        });
    }
    
    /**
     * Tests JSON schema validation for a post response.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 200 OK</li>
     *   <li>Response structure matches the defined post-schema.json</li>
     *   <li>All required fields are present with correct data types</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("GET /posts/{id} validates JSON schema")
    public void testPostJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/posts/1")
                .get("/posts/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "post-schema.json");
    }
    
    /**
     * Tests response time performance for the posts endpoint.
     * <p>
     * Validates that:
     * <ul>
     *   <li>Response status code is 200 OK</li>
     *   <li>Response time is under 5000ms (5 seconds)</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("GET /posts validates response time")
    public void testResponseTime() {
        Response response = RequestBuilder.buildGetRequest("/posts")
                .get("/posts");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseTime(response, 5000);
    }
}
