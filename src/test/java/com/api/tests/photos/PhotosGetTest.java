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
    
    @Test
    @DisplayName("GET /photos/{id} validates JSON schema")
    public void testPhotoJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/photos/1")
                .get("/photos/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "photo-schema.json");
    }
    
    @Test
    @DisplayName("GET /photos returns 200 and list of photos")
    public void testGetAllPhotos() {
        Response response = RequestBuilder.buildGetRequest("/photos")
                .get("/photos");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateHeader(response, "Content-Type", "application/json");
    }
}
