package com.api.tests.albums;

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
 * Test class for Albums API POST endpoints.
 * <p>
 * This class contains tests for POST operations on the /albums
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Albums API POST Tests")
public class AlbumsPostTest extends BaseTest {
    
    @Test
    @DisplayName("POST /albums creates new album")
    public void testCreateAlbum() {
        Map<String, Object> albumData = TestDataProvider.getAlbumData();

        Response response = RequestBuilder.buildPostRequest("/albums", albumData)
                .post("/albums");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("POST /albums returns 201 Created status code")
    public void testCreateAlbumReturnsCorrectStatusCode() {
        Map<String, Object> albumData = TestDataProvider.getAlbumData();

        Response response = RequestBuilder.buildPostRequest("/albums", albumData)
                .post("/albums");
        
        Assertions.assertEquals(201, response.getStatusCode(), 
            "POST should return 201 Created, not 200 OK");
    }
    
    @Test
    @DisplayName("POST /albums returns created resource with ID")
    public void testCreateAlbumReturnsId() {
        Map<String, Object> albumData = TestDataProvider.getAlbumData();

        Response response = RequestBuilder.buildPostRequest("/albums", albumData)
                .post("/albums");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        Integer id = response.jsonPath().get("id");
        Assertions.assertNotNull(id, 
            "Response should contain an ID for the created album");
    }
}
