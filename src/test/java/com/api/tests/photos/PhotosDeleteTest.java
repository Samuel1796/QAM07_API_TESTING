package com.api.tests.photos;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Photos API DELETE Tests")
public class PhotosDeleteTest extends BaseTest {
    
    @Test
    @DisplayName("DELETE /photos/{id} deletes photo")
    public void testDeletePhoto() {
        int photoId = 1;
        
        Response response = RequestBuilder.buildDeleteRequest("/photos/" + photoId)
                .delete("/photos/" + photoId);
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    @Test
    @DisplayName("DELETE /photos/9999 on non-existing resource should return 404")
    public void testDeleteNonExistingPhoto_ShouldReturn404() {
        int nonExistingId = 9999;
        
        Response response = RequestBuilder.buildDeleteRequest("/photos/" + nonExistingId)
                .delete("/photos/" + nonExistingId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
    
    @Test
    @DisplayName("DELETE /photos/0 with invalid ID should return 404")
    public void testDeletePhotoWithIdZero_ShouldReturn404() {
        int invalidId = 0;
        
        Response response = RequestBuilder.buildDeleteRequest("/photos/" + invalidId)
                .delete("/photos/" + invalidId);
        
        ResponseValidator.validateStatusCode(response, 404);
    }
}
