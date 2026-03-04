package com.api.tests.photos;

import com.api.base.BaseTest;
import com.api.testdata.PhotosTestData;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Photos API PUT Tests")
public class PhotosPutTest extends BaseTest {
    
    @Test
    @DisplayName("PUT /photos/{id} updates existing photo")
    public void testUpdatePhoto() {
        int photoId = 1;
        
        Response response = RequestBuilder.buildPutRequest("/photos/" + photoId, PhotosTestData.getPhotoData())
                .put("/photos/" + photoId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("PUT /photos/9999 on non-existing resource should return 404")
    public void testUpdateNonExistingPhoto_ShouldReturn404() {
        int nonExistingId = 9999;
        
        Response response = RequestBuilder.buildPutRequest("/photos/" + nonExistingId, PhotosTestData.getPhotoData())
                .put("/photos/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("PUT /photos/5001 beyond valid range should return 404")
    public void testUpdatePhotoBeyondValidRange_ShouldReturn404() {
        int beyondRangeId = 5001; // JSONPlaceholder has photos 1-5000
        
        Response response = RequestBuilder.buildPutRequest("/photos/" + beyondRangeId, PhotosTestData.getPhotoData())
                .put("/photos/" + beyondRangeId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
