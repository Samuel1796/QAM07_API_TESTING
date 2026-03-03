package com.api.tests.albums;

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
        java.util.Map<String, Object> albumData = TestDataProvider.getAlbumData();

        Response response = RequestBuilder.buildPostRequest("/albums", albumData)
                .post("/albums");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
}
