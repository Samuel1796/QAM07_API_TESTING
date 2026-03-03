package com.api.tests.photos;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Photos API GET Tests")
public class PhotosGetTest extends BaseTest {
    
    @Test
    @DisplayName("GET /photos/{id} returns specific photo")
    public void testGetPhotoById() {
        int photoId = 1;
        Response response = RequestBuilder.buildGetRequest("/photos/" + photoId)
                .get("/photos/" + photoId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", photoId);
    }
}
