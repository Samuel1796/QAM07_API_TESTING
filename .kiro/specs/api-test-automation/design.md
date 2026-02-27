# Design Document: API Test Automation Framework

## Overview

This document describes the design of an API test automation framework built with REST Assured (Java) to validate the JSONPlaceholder API. The framework follows a modular architecture with clear separation between test configuration, test execution, validation logic, and reporting. It leverages Maven for dependency management, JUnit 5 for test orchestration, Allure for reporting, Docker for containerization, and GitHub Actions for CI/CD automation.

The framework is designed to be maintainable, extensible, and scalable, allowing easy addition of new test cases and API resources. It emphasizes reusability through base classes, utility methods, and configuration management.

## Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     CI/CD Pipeline                          │
│                   (GitHub Actions)                          │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────┐
│                  Docker Container                           │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              Test Framework                           │  │
│  │  ┌─────────────────────────────────────────────────┐  │  │
│  │  │         Test Layer                              │  │  │
│  │  │  - GET Tests                                    │  │  │
│  │  │  - POST Tests                                   │  │  │
│  │  │  - PUT Tests                                    │  │  │
│  │  │  - DELETE Tests                                 │  │  │
│  │  └─────────────────────────────────────────────────┘  │  │
│  │  ┌─────────────────────────────────────────────────┐  │  │
│  │  │      Validation Layer                           │  │  │
│  │  │  - Status Code Validator                        │  │  │
│  │  │  - Response Body Validator                      │  │  │
│  │  │  - Header Validator                             │  │  │
│  │  │  - JSON Schema Validator                        │  │  │
│  │  └─────────────────────────────────────────────────┘  │  │
│  │  ┌─────────────────────────────────────────────────┐  │  │
│  │  │       Utility Layer                             │  │  │
│  │  │  - Request Builder                              │  │  │
│  │  │  - Configuration Manager                        │  │  │
│  │  │  - Test Data Provider                           │  │  │
│  │  └─────────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              JSONPlaceholder API                            │
│  /posts  /comments  /albums  /photos  /todos  /users       │
└─────────────────────────────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Allure Reports                              │
│  - Test Results  - Execution Metrics  - Failure Details    │
└─────────────────────────────────────────────────────────────┘
```

### Layer Responsibilities

1. **Test Layer**: Contains JUnit 5 test classes for each API resource and HTTP method
2. **Validation Layer**: Provides reusable validation methods for API responses
3. **Utility Layer**: Offers helper methods for request building, configuration, and test data
4. **CI/CD Layer**: Automates test execution on code changes
5. **Containerization Layer**: Ensures consistent test execution environment

## Components and Interfaces

### 1. Configuration Manager

**Purpose**: Centralize configuration management for API base URL, timeouts, and test settings.

**Interface**:
```java
public class ConfigManager {
    public static String getBaseUrl();
    public static int getDefaultTimeout();
    public static String getEnvironment();
}
```

**Implementation Details**:
- Reads configuration from properties file (config.properties)
- Provides default values if configuration is missing
- Supports environment-specific configurations (dev, staging, prod)

### 2. Base Test Class

**Purpose**: Provide common setup and teardown logic for all test classes.

**Interface**:
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected RequestSpecification requestSpec;
    
    @BeforeAll
    public void setup();
    
    @AfterAll
    public void teardown();
    
    protected RequestSpecification getRequestSpec();
}
```

**Implementation Details**:
- Initializes REST Assured configuration
- Sets base URI from ConfigManager
- Configures request/response logging
- Sets default headers (Content-Type: application/json)
- All test classes extend this base class

### 3. Request Builder Utility

**Purpose**: Simplify building REST Assured requests with common configurations.

**Interface**:
```java
public class RequestBuilder {
    public static RequestSpecification buildGetRequest(String endpoint);
    public static RequestSpecification buildPostRequest(String endpoint, Object body);
    public static RequestSpecification buildPutRequest(String endpoint, Object body);
    public static RequestSpecification buildDeleteRequest(String endpoint);
    public static RequestSpecification buildRequestWithQueryParams(String endpoint, Map<String, String> params);
}
```

**Implementation Details**:
- Wraps REST Assured request building
- Applies common headers and configurations
- Handles request body serialization
- Supports query parameter addition

### 4. Response Validator

**Purpose**: Provide reusable validation methods for API responses.

**Interface**:
```java
public class ResponseValidator {
    public static void validateStatusCode(Response response, int expectedCode);
    public static void validateResponseTime(Response response, long maxTimeMs);
    public static void validateHeader(Response response, String headerName, String expectedValue);
    public static void validateJsonSchema(Response response, String schemaPath);
    public static void validateResponseBodyContains(Response response, String key, Object expectedValue);
    public static void validateResponseBodySize(Response response, int expectedSize);
}
```

**Implementation Details**:
- Uses REST Assured's built-in validation methods
- Provides clear assertion messages
- Supports JSON schema validation using json-schema-validator
- Validates response timing for performance testing

### 5. Test Data Provider

**Purpose**: Generate and provide test data for POST and PUT requests.

**Interface**:
```java
public class TestDataProvider {
    public static Map<String, Object> getPostData();
    public static Map<String, Object> getCommentData();
    public static Map<String, Object> getAlbumData();
    public static Map<String, Object> getPhotoData();
    public static Map<String, Object> getTodoData();
    public static Map<String, Object> getUserData();
}
```

**Implementation Details**:
- Returns Map objects representing JSON request bodies
- Provides valid test data for each resource type
- Can be extended to support data-driven testing

### 6. Test Classes

**Purpose**: Contain actual test methods for each API resource and operation.

**Structure**:
```java
@DisplayName("Posts API Tests")
public class PostsApiTest extends BaseTest {
    
    @Test
    @DisplayName("GET /posts returns 200 and list of posts")
    public void testGetAllPosts();
    
    @Test
    @DisplayName("GET /posts/{id} returns specific post")
    public void testGetPostById();
    
    @Test
    @DisplayName("POST /posts creates new post")
    public void testCreatePost();
    
    @Test
    @DisplayName("PUT /posts/{id} updates existing post")
    public void testUpdatePost();
    
    @Test
    @DisplayName("DELETE /posts/{id} deletes post")
    public void testDeletePost();
    
    @Test
    @DisplayName("GET /posts?userId=1 filters posts by user")
    public void testGetPostsByUserId();
    
    @Test
    @DisplayName("GET /posts/1/comments returns comments for post")
    public void testGetPostComments();
}
```

**Similar test classes for**:
- CommentsApiTest
- AlbumsApiTest
- PhotosApiTest
- TodosApiTest
- UsersApiTest

### 7. JSON Schema Definitions

**Purpose**: Define expected JSON structures for schema validation.

**Location**: src/test/resources/schemas/

**Files**:
- post-schema.json
- comment-schema.json
- album-schema.json
- photo-schema.json
- todo-schema.json
- user-schema.json

**Example (post-schema.json)**:
```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "required": ["userId", "id", "title", "body"],
  "properties": {
    "userId": {"type": "integer"},
    "id": {"type": "integer"},
    "title": {"type": "string"},
    "body": {"type": "string"}
  }
}
```

## Data Models

### API Resource Models

While the framework primarily works with JSON responses, we define expected structures for documentation:

**Post**:
```
{
  userId: integer
  id: integer
  title: string
  body: string
}
```

**Comment**:
```
{
  postId: integer
  id: integer
  name: string
  email: string
  body: string
}
```

**Album**:
```
{
  userId: integer
  id: integer
  title: string
}
```

**Photo**:
```
{
  albumId: integer
  id: integer
  title: string
  url: string
  thumbnailUrl: string
}
```

**Todo**:
```
{
  userId: integer
  id: integer
  title: string
  completed: boolean
}
```

**User**:
```
{
  id: integer
  name: string
  username: string
  email: string
  address: {
    street: string
    suite: string
    city: string
    zipcode: string
    geo: {
      lat: string
      lng: string
    }
  }
  phone: string
  website: string
  company: {
    name: string
    catchPhrase: string
    bs: string
  }
}
```

### Configuration Model

**config.properties**:
```
base.url=https://jsonplaceholder.typicode.com
default.timeout=5000
environment=test
log.requests=true
log.responses=true
```

## Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system—essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*

