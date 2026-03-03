package com.api.tests.albums;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        java.util.Map<String, Object> albumData = TestDataProvider.getAlbumData();

        Response response = RequestBuilder.buildPostRequest("/albums", albumData)
                .post("/albums");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
}
