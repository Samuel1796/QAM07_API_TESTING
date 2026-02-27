package com.api.tests;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Albums API endpoints.
 * <p>
 * This class contains comprehensive tests for all CRUD operations on the /albums
 * resource of the JSONPlaceholder API. Albums are collections of photos associated
 * with users.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Albums API Tests")
public class AlbumsApiTest extends BaseTest {
    
    @Test
    @DisplayName("GET /albums returns 200")
    public void testGetAllAlbums() {
        Response response = RequestBuilder.buildGetRequest("/albums")
                .get("/albums");
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("GET /albums/{id} returns specific album")
    public void testGetAlbumById() {
        int albumId = 1;
        Response response = RequestBuilder.buildGetRequest("/albums/" + albumId)
                .get("/albums/" + albumId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", albumId);
    }
    
    @Test
    @DisplayName("POST /albums creates new album")
    public void testCreateAlbum() {
        Response response = RequestBuilder.buildPostRequest("/albums", TestDataProvider.getAlbumData())
                .post("/albums");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        // Verify response contains the submitted data
        String title = response.jsonPath().get("title");
        org.junit.jupiter.api.Assertions.assertEquals("Test Album Title", title);
    }
    
    @Test
    @DisplayName("PUT /albums/{id} updates existing album")
    public void testUpdateAlbum() {
        int albumId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/albums/" + albumId, TestDataProvider.getAlbumData())
                .put("/albums/" + albumId);
        
        ResponseValidator.validateStatusCode(response, 200);
        
        // Verify response contains the updated data
        String title = response.jsonPath().get("title");
        org.junit.jupiter.api.Assertions.assertEquals("Test Album Title", title);
    }
    
    @Test
    @DisplayName("DELETE /albums/{id} deletes album")
    public void testDeleteAlbum() {
        int albumId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/albums/" + albumId)
                .delete("/albums/" + albumId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("GET /albums/{id} validates JSON schema")
    public void testAlbumJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/albums/1")
                .get("/albums/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "album-schema.json");
    }
}
