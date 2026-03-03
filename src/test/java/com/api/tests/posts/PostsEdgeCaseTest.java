package com.api.tests.posts;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Edge Case Tests for Posts API.
 * <p>
 * Tests boundary values, invalid inputs, empty fields, special characters,
 * and other edge cases for the /posts endpoint.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 */
@DisplayName("Posts API Edge Case Tests")
public class PostsEdgeCaseTest extends BaseTest {
    
    @Test
    @DisplayName("GET /posts with boundary ID - minimum value (1)")
    public void testGetPostWithMinimumBoundaryId() {
        int minId = TestDataProvider.getBoundaryPostId("min");
        
        Response response = RequestBuilder.buildGetRequest("/posts/" + minId)
                .get("/posts/" + minId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", minId);
    }
    
    @Test
    @DisplayName("GET /posts with boundary ID - maximum value (100)")
    public void testGetPostWithMaximumBoundaryId() {
        int maxId = TestDataProvider.getBoundaryPostId("max");
        
        Response response = RequestBuilder.buildGetRequest("/posts/" + maxId)
                .get("/posts/" + maxId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", maxId);
    }
    
    @Test
    @DisplayName("GET /posts with ID below minimum (0)")
    public void testGetPostWithIdBelowMinimum() {
        int belowMinId = TestDataProvider.getBoundaryPostId("below_min");
        
        Response response = RequestBuilder.buildGetRequest("/posts/" + belowMinId)
                .get("/posts/" + belowMinId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("GET /posts with ID above maximum (101)")
    public void testGetPostWithIdAboveMaximum() {
        int aboveMaxId = TestDataProvider.getBoundaryPostId("above_max");
        
        Response response = RequestBuilder.buildGetRequest("/posts/" + aboveMaxId)
                .get("/posts/" + aboveMaxId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("GET /posts with negative ID")
    public void testGetPostWithNegativeId() {
        Response response = RequestBuilder.buildGetRequest("/posts/-1")
                .get("/posts/-1");
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("GET /posts with non-numeric ID")
    public void testGetPostWithNonNumericId() {
        Response response = RequestBuilder.buildGetRequest("/posts/abc")
                .get("/posts/abc");
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("POST /posts with empty title")
    public void testCreatePostWithEmptyTitle() {
        Map<String, Object> postData = TestDataProvider.getPostDataWithEmptyFields("title");
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        ResponseValidator.validateResponseBodyContains(response, "title", "");
    }
    
    @Test
    @DisplayName("POST /posts with empty body")
    public void testCreatePostWithEmptyBody() {
        Map<String, Object> postData = TestDataProvider.getPostDataWithEmptyFields("body");
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        ResponseValidator.validateResponseBodyContains(response, "body", "");
    }
    
    @Test
    @DisplayName("POST /posts with all empty fields")
    public void testCreatePostWithAllEmptyFields() {
        Map<String, Object> postData = TestDataProvider.getPostDataWithEmptyFields("all");
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("POST /posts with extremely long strings")
    public void testCreatePostWithLongStrings() {
        Map<String, Object> postData = TestDataProvider.getPostDataWithLongStrings();
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        // Verify long strings were accepted
        String title = response.jsonPath().get("title");
        String body = response.jsonPath().get("body");
        assertEquals(1000, title.length(), "Title should be 1000 characters");
        assertEquals(10000, body.length(), "Body should be 10000 characters");
    }
    
    @Test
    @DisplayName("POST /posts with special characters")
    public void testCreatePostWithSpecialCharacters() {
        Map<String, Object> postData = TestDataProvider.getPostDataWithSpecialCharacters();
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        // Verify special characters were accepted
        String title = response.jsonPath().get("title");
        String body = response.jsonPath().get("body");
        assertTrue(title.contains("<script>"), "Title should contain special characters");
        assertTrue(body.contains("!@#$%"), "Body should contain special characters");
    }
    
    @Test
    @DisplayName("POST /posts with invalid userId")
    public void testCreatePostWithInvalidUserId() {
        Map<String, Object> postData = TestDataProvider.getPostDataWithEmptyFields("userId");
        
        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("PUT /posts with non-existent ID")
    public void testUpdateNonExistentPost() {
        int invalidId = TestDataProvider.getInvalidPostId();
        Map<String, Object> postData = TestDataProvider.getPostData();
        
        Response response = RequestBuilder.buildPutRequest("/posts/" + invalidId, postData)
                .put("/posts/" + invalidId);
        
        // JSONPlaceholder returns 200 for non-existent IDs, but may return 404 or 500 for invalid IDs
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404 || statusCode == 500,
                "Status code should be 200, 404, or 500, but was " + statusCode);
    }
    
    @Test
    @DisplayName("DELETE /posts with non-existent ID")
    public void testDeleteNonExistentPost() {
        int invalidId = TestDataProvider.getInvalidPostId();
        
        Response response = RequestBuilder.buildDeleteRequest("/posts/" + invalidId)
                .delete("/posts/" + invalidId);
        
        // JSONPlaceholder returns 200 for non-existent IDs, but may return 404 or 500 for invalid IDs
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404 || statusCode == 500,
                "Status code should be 200, 404, or 500, but was " + statusCode);
    }

}
