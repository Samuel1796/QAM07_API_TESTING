package com.api.tests;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Photos API Tests")
public class PhotosApiTest extends BaseTest {
    
    @Test
    @DisplayName("GET /photos returns 200 and 5000 photos")
    public void testGetAllPhotos() {
        Response response = RequestBuilder.buildGetRequest("/photos")
                .get("/photos");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodySize(response, 5000);
    }
    
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
    @DisplayName("POST /photos creates new photo")
    public void testCreatePhoto() {
        Response response = RequestBuilder.buildPostRequest("/photos", TestDataProvider.getPhotoData())
                .post("/photos");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("PUT /photos/{id} updates existing photo")
    public void testUpdatePhoto() {
        int photoId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/photos/" + photoId, TestDataProvider.getPhotoData())
                .put("/photos/" + photoId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("DELETE /photos/{id} deletes photo")
    public void testDeletePhoto() {
        int photoId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/photos/" + photoId)
                .delete("/photos/" + photoId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("GET /photos/{id} validates JSON schema")
    public void testPhotoJsonSchema() {
        Response response = RequestBuilder.buildGetRequest("/photos/1")
                .get("/photos/1");
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateJsonSchema(response, "photo-schema.json");
    }
}
