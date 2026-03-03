package com.api.tests.photos;

import com.api.base.BaseTest;
import com.api.utilities.RequestBuilder;
import com.api.utilities.ResponseValidator;
import com.api.utilities.TestDataProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Photos API POST Tests")
public class PhotosPostTest extends BaseTest {
    
    @Test
    @DisplayName("POST /photos creates new photo")
    public void testCreatePhoto() {
        Response response = RequestBuilder.buildPostRequest("/photos", TestDataProvider.getPhotoData())
                .post("/photos");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
}
