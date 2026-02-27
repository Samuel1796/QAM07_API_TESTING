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

@DisplayName("Posts API Tests")
public class PostsApiTest extends BaseTest {
    
    @Test
    @DisplayName("GET /posts returns 200 and list of posts")
    public void testGetAllPosts() {
        Response response = RequestBuilder.buildGetRequest("/posts")
                .get("/posts");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateHeader(response, "Content-Type", "application/json");
    }
    
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
    
    @Test
    @DisplayName("DELETE /posts/{id} deletes post")
    public void testDeletePost() {
        int postId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/posts/" + postId)
                .delete("/posts/" + postId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
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
    
    @Test
    @DisplayName("GET /posts/{id} validates JSON schema")
    public void testPostJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/posts/1")
                .get("/posts/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "post-schema.json");
    }
    
    @Test
    @DisplayName("GET /posts validates response time")
    public void testResponseTime() {
        Response response = RequestBuilder.buildGetRequest("/posts")
                .get("/posts");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseTime(response, 5000);
    }
}
