package com.api.utilities;

import java.util.HashMap;
import java.util.Map;

public class TestDataProvider {
    
    public static Map<String, Object> getPostData() {
        Map<String, Object> postData = new HashMap<>();
        postData.put("userId", 1);
        postData.put("title", "Test Post Title");
        postData.put("body", "Test post body content for API testing");
        return postData;
    }
    
    public static Map<String, Object> getCommentData() {
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("postId", 1);
        commentData.put("name", "Test Comment");
        commentData.put("email", "test@example.com");
        commentData.put("body", "Test comment body content");
        return commentData;
    }
    
    public static Map<String, Object> getAlbumData() {
        Map<String, Object> albumData = new HashMap<>();
        albumData.put("userId", 1);
        albumData.put("title", "Test Album Title");
        return albumData;
    }
    
    public static Map<String, Object> getPhotoData() {
        Map<String, Object> photoData = new HashMap<>();
        photoData.put("albumId", 1);
        photoData.put("title", "Test Photo Title");
        photoData.put("url", "https://via.placeholder.com/600/test");
        photoData.put("thumbnailUrl", "https://via.placeholder.com/150/test");
        return photoData;
    }
    
    public static Map<String, Object> getTodoData() {
        Map<String, Object> todoData = new HashMap<>();
        todoData.put("userId", 1);
        todoData.put("title", "Test Todo Title");
        todoData.put("completed", false);
        return todoData;
    }
    
    public static Map<String, Object> getUserData() {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", "Test User");
        userData.put("username", "testuser");
        userData.put("email", "testuser@example.com");
        
        Map<String, Object> address = new HashMap<>();
        address.put("street", "Test Street");
        address.put("suite", "Apt. 123");
        address.put("city", "Test City");
        address.put("zipcode", "12345");
        
        Map<String, String> geo = new HashMap<>();
        geo.put("lat", "40.7128");
        geo.put("lng", "-74.0060");
        address.put("geo", geo);
        
        userData.put("address", address);
        userData.put("phone", "123-456-7890");
        userData.put("website", "testuser.com");
        
        Map<String, String> company = new HashMap<>();
        company.put("name", "Test Company");
        company.put("catchPhrase", "Testing is our business");
        company.put("bs", "test automation solutions");
        userData.put("company", company);
        
        return userData;
    }
}
