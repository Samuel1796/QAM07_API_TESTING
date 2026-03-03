package com.api.tests.albums;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Albums API PUT endpoints.
 * <p>
 * This class contains tests for PUT operations on the /albums
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Albums API PUT Tests")
public class AlbumsPutTest extends BaseTest {
    
    @Test
    @DisplayName("PUT /albums/{id} updates existing album")
    public void testUpdateAlbum() {
        int albumId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/albums/" + albumId, TestDataProvider.getAlbumData())
                .put("/albums/" + albumId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("PUT /albums/999 on non-existing resource should return 404")
    public void testUpdateNonExistingAlbum_ShouldReturn404() {
        int nonExistingId = 999;
        
        Response response = RequestBuilder.buildPutRequest("/albums/" + nonExistingId, TestDataProvider.getAlbumData())
                .put("/albums/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
