package com.api.tests.posts;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
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
    
    /**
     * Tests PUT /posts/{id} on non-existing resource (ID 999).
     * <p>
     * Bug Detection Test: Verifies API returns 404 when updating a resource
     * that doesn't exist.
     * </p>
     */
    @Test
    @DisplayName("PUT /posts/999 on non-existing resource should return 404")
    public void testUpdateNonExistingPost_ShouldReturn404() {
        int nonExistingId = 999;
        Map<String, Object> postData = TestDataProvider.getPostData();
        postData.put("id", nonExistingId);
        
        Response response = RequestBuilder.buildPutRequest("/posts/" + nonExistingId, postData)
                .put("/posts/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    /**
     * Tests PUT /posts/{id} on resource beyond valid range (ID 101+).
     * <p>
     * Bug Detection Test: JSONPlaceholder has posts 1-100.
     * Tests that API returns 404 when updating ID 101 or higher.
     * </p>
     */
    @Test
    @DisplayName("PUT /posts/101 beyond valid range should return 404")
    public void testUpdatePostBeyondValidRange_ShouldReturn404() {
        int beyondRangeId = 101;
        Map<String, Object> postData = TestDataProvider.getPostData();
        postData.put("id", beyondRangeId);
        
        Response response = RequestBuilder.buildPutRequest("/posts/" + beyondRangeId, postData)
                .put("/posts/" + beyondRangeId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    /**
     * Tests PUT /posts/0 with invalid ID (zero).
     * <p>
     * Bug Detection Test: Verifies API returns 404 with ID 0,
     * which is typically invalid.
     * </p>
     */
    @Test
    @DisplayName("PUT /posts/0 with invalid ID zero should return 404")
    public void testUpdatePostWithIdZero_ShouldReturn404() {
        int invalidId = 0;
        Map<String, Object> postData = TestDataProvider.getPostData();
        postData.put("id", invalidId);
        
        Response response = RequestBuilder.buildPutRequest("/posts/" + invalidId, postData)
                .put("/posts/" + invalidId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    /**
     * Tests PUT /posts/{id} returns updated fields in response.
     * <p>
     * Bug Detection Test: Verifies the API returns all updated fields
     * in the response body.
     * </p>
     */
    @Test
    @DisplayName("PUT /posts/{id} returns all updated fields")
    public void testUpdatePostReturnsAllFields() {
        int postId = 1;
        Map<String, Object> postData = TestDataProvider.getPostData();
        postData.put("id", postId);
        
        Response response = RequestBuilder.buildPutRequest("/posts/" + postId, postData)
                .put("/posts/" + postId);
        
        ResponseValidator.validateStatusCode(response, 200);
        
        // Verify all fields are returned
        Assertions.assertEquals(postData.get("title"), 
            response.jsonPath().get("title"), "Title should match updated data");
        Assertions.assertEquals(postData.get("body"), 
            response.jsonPath().get("body"), "Body should match updated data");
        Assertions.assertEquals(postData.get("userId"), 
            response.jsonPath().get("userId"), "UserId should match updated data");
    }
}
