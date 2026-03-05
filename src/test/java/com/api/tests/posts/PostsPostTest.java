package com.api.tests.posts;

import com.api.base.BaseTest;
import com.api.testdata.PostsTestData;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
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
        Map<String, Object> postData = PostsTestData.getPostData();
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        // Verify response contains an ID (JSONPlaceholder returns id: 101 for new posts)
        Integer id = response.jsonPath().get("id");
        Assertions.assertNotNull(id, "Response should contain an id");
    }
    
    /**
     * Tests POST /posts returns correct status code 201 for creation.
     * <p>
     * Bug Detection Test: Verifies the API returns 201 Created (not 200 OK)
     * when a resource is successfully created.
     * </p>
     */
    @Test
    @DisplayName("POST /posts returns 201 Created status code")
    public void testCreatePostReturnsCorrectStatusCode() {
        Map<String, Object> postData = PostsTestData.getPostData();
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        // Verify correct 201 status code (not 200)
        ResponseValidator.validateStatusCode(response, 201);
        Assertions.assertEquals(201, response.getStatusCode(), 
            "POST should return 201 Created, not 200 OK");
    }
    
    /**
     * Tests POST /posts returns created resource with ID 101.
     * <p>
     * Bug Detection Test: Verifies that JSONPlaceholder returns ID 101
     * for newly created posts (as documented in their guide).
     * </p>
     */
    @Test
    @DisplayName("POST /posts returns ID 101 for new resource")
    public void testCreatePostReturnsId101() {
        Map<String, Object> postData = PostsTestData.getPostData();
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        Integer id = response.jsonPath().get("id");
        Assertions.assertEquals(101, id, 
            "JSONPlaceholder should return ID 101 for new posts");
    }
    
    /**
     * Tests POST /posts returns all submitted fields in response.
     * <p>
     * Bug Detection Test: Verifies the API echoes back all fields
     * that were submitted in the POST request.
     * </p>
     */
    @Test
    @DisplayName("POST /posts returns all submitted fields")
    public void testCreatePostReturnsAllFields() {
        Map<String, Object> postData = PostsTestData.getPostData();
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        // Verify all submitted fields are in response
        Assertions.assertEquals(postData.get("title"), 
            response.jsonPath().get("title"), "Title should match submitted data");
        Assertions.assertEquals(postData.get("body"), 
            response.jsonPath().get("body"), "Body should match submitted data");
        Assertions.assertEquals(postData.get("userId"), 
            response.jsonPath().get("userId"), "UserId should match submitted data");
    }
}
