package com.api.tests.photos;

import com.api.base.BaseTest;
import com.api.testdata.PhotosTestData;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Photos API POST Tests")
public class PhotosPostTest extends BaseTest {
    
    @Test
    @DisplayName("POST /photos creates new photo")
    public void testCreatePhoto() {
        Response response = RequestBuilder.buildPostRequest("/photos", PhotosTestData.getPhotoData())
                .post("/photos");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    @Test
    @DisplayName("POST /photos returns 201 Created status code")
    public void testCreatePhotoReturnsCorrectStatusCode() {
        Response response = RequestBuilder.buildPostRequest("/photos", PhotosTestData.getPhotoData())
                .post("/photos");
        
        Assertions.assertEquals(201, response.getStatusCode(), 
            "POST should return 201 Created, not 200 OK");
    }
    
    @Test
    @DisplayName("POST /photos returns created resource with ID")
    public void testCreatePhotoReturnsId() {
        Response response = RequestBuilder.buildPostRequest("/photos", PhotosTestData.getPhotoData())
                .post("/photos");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        Integer id = response.jsonPath().get("id");
        Assertions.assertNotNull(id, 
            "Response should contain an ID for the created photo");
    }
}
