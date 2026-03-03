package com.api.tests.albums;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for Albums API GET endpoints.
 * <p>
 * This class contains tests for GET operations on the /albums
 * resource of the JSONPlaceholder API.
 * </p>
 *
 * @author API Test Automation Team
 * @version 2.0
 * @since 1.0
 */
@DisplayName("Albums API GET Tests")
public class AlbumsGetTest extends BaseTest {
    
    @Test
    @DisplayName("GET /albums/{id} returns specific album")
    public void testGetAlbumById() {
        int albumId = 1;
        Response response = RequestBuilder.buildGetRequest("/albums/" + albumId)
                .get("/albums/" + albumId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", albumId);
    }
}
