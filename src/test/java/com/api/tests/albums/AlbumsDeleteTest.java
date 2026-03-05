package com.api.tests.albums;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Albums API DELETE endpoints.
 * <p>
 * This class contains tests for DELETE operations on the /albums
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Albums API DELETE Tests")
public class AlbumsDeleteTest extends BaseTest {
    
    @Test
    @DisplayName("DELETE /albums/{id} deletes album")
    public void testDeleteAlbum() {
        int albumId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/albums/" + albumId)
                .delete("/albums/" + albumId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("DELETE /albums/999 on non-existing resource should return 404")
    public void testDeleteNonExistingAlbum_ShouldReturn404() {
        int nonExistingId = 999;
        
        Response response = RequestBuilder.buildDeleteRequest("/albums/" + nonExistingId)
                .delete("/albums/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("DELETE /albums/0 with invalid ID should return 404")
    public void testDeleteAlbumWithIdZero_ShouldReturn404() {
        int invalidId = 0;
        
        Response response = RequestBuilder.buildDeleteRequest("/albums/" + invalidId)
                .delete("/albums/" + invalidId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
