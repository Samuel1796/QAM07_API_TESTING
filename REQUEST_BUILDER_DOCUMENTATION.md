# RequestBuilder and RequestSpecification Documentation

## Overview

`RequestBuilder.java` is a **utility class that simplifies HTTP request creation** for API testing. It provides static factory methods that build pre-configured `RequestSpecification` objects for different HTTP methods (GET, POST, PUT, DELETE).

**Location**: `src/test/java/com/api/utilities/RequestBuilder.java`

---

## What is RequestSpecification?

### Definition

`RequestSpecification` is a REST Assured class that **represents an HTTP request ready to be sent**. It contains all the configuration needed to make an API call.

### From REST Assured Library

```java
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
```

- **Part of**: io.restassured (REST Assured testing library)
- **Purpose**: Configure and execute HTTP requests
- **Builder Pattern**: Uses fluent API for method chaining

---

## RequestSpecification Components

A RequestSpecification can contain:

```
RequestSpecification
├── Headers
│   ├── Content-Type
│   ├── Accept
│   ├── Authorization
│   └── Custom headers
├── Base URI
├── Path/Endpoint
├── Query Parameters
├── Request Body
├── Cookies
├── Timeouts
├── Authentication
├── SSL/TLS Configuration
├── Proxy Settings
└── Filters (logging, reporting, etc.)
```

---

## RequestBuilder Methods

The RequestBuilder class provides **4 static factory methods** for building requests:

### 1. buildGetRequest()

```java
public static RequestSpecification buildGetRequest(String endpoint) {
    return given()
            .contentType("application/json")
            .when();
}
```

#### Purpose
Creates a **GET request specification** for retrieving resources.

#### Configuration Applied
| Config | Value | Purpose |
|--------|-------|---------|
| **HTTP Method** | GET | Implicit (used when calling .get()) |
| **Content-Type** | application/json | Tells server we expect JSON response |

#### Return Value
A `RequestSpecification` object ready for further configuration or execution.

#### Usage Example

```java
@Test
public void testGetPost() {
    // Build GET request
    RequestSpecification request = RequestBuilder.buildGetRequest("/posts/1");

    // Execute the request
    Response response = request.get("/posts/1");

    // Validate response
    ResponseValidator.validateStatusCode(response, 200);
}
```

#### What Happens Internally

```
RequestBuilder.buildGetRequest("/posts/1")
    ↓
given() - Start building request
    ↓
.contentType("application/json") - Add header
    ↓
.when() - Finalize configuration
    ↓
Return RequestSpecification
    ↓
request.get("/posts/1") - Execute GET to /posts/1
    ↓
Return Response object
```

#### Complete Request

```
GET /posts/1 HTTP/1.1
Host: https://jsonplaceholder.typicode.com
Content-Type: application/json
Accept: application/json
(inherited from BaseTest configuration)
```

---

### 2. buildPostRequest()

```java
public static RequestSpecification buildPostRequest(String endpoint, Object body) {
    return given()
            .contentType("application/json")
            .body(body)
            .when();
}
```

#### Purpose
Creates a **POST request specification** for creating new resources.

#### Configuration Applied
| Config | Value | Purpose |
|--------|-------|---------|
| **HTTP Method** | POST | Implicit (used when calling .post()) |
| **Content-Type** | application/json | Tells server we're sending JSON |
| **Request Body** | body (Object) | Serialized to JSON automatically |

#### Parameters

- **endpoint**: API endpoint path (kept for consistency, not directly used)
- **body**: Java object to be serialized to JSON
  - Can be Map, POJO, or any serializable object
  - REST Assured automatically converts to JSON

#### Return Value
A `RequestSpecification` object with body attached.

#### Usage Example

```java
@Test
public void testCreatePost() {
    // Create test data
    Map<String, Object> postData = new HashMap<>();
    postData.put("userId", 1);
    postData.put("title", "Test Post");
    postData.put("body", "This is a test post");

    // Build POST request with body
    RequestSpecification request = RequestBuilder.buildPostRequest("/posts", postData);

    // Execute the request
    Response response = request.post("/posts");

    // Validate response
    ResponseValidator.validateStatusCode(response, 201);
}
```

#### Body Serialization

**Input** (Java Map):
```java
{
    "userId": 1,
    "title": "Test Post",
    "body": "This is a test post"
}
```

**Serialized to JSON** (sent over network):
```json
{
  "userId": 1,
  "title": "Test Post",
  "body": "This is a test post"
}
```

#### Complete Request

```
POST /posts HTTP/1.1
Host: https://jsonplaceholder.typicode.com
Content-Type: application/json
Accept: application/json
Content-Length: 65

{
  "userId": 1,
  "title": "Test Post",
  "body": "This is a test post"
}
```

---

### 3. buildPutRequest()

```java
public static RequestSpecification buildPutRequest(String endpoint, Object body) {
    return given()
            .contentType("application/json")
            .body(body)
            .when();
}
```

#### Purpose
Creates a **PUT request specification** for updating existing resources.

#### Configuration Applied
| Config | Value | Purpose |
|--------|-------|---------|
| **HTTP Method** | PUT | Implicit (used when calling .put()) |
| **Content-Type** | application/json | Tells server we're sending JSON |
| **Request Body** | body (Object) | Serialized to JSON automatically |

#### Difference from POST
- **POST**: Creates NEW resource (typically returns 201 Created)
- **PUT**: Updates EXISTING resource (typically returns 200 OK)

#### Usage Example

```java
@Test
public void testUpdatePost() {
    // Create updated data
    Map<String, Object> updatedData = new HashMap<>();
    updatedData.put("userId", 1);
    updatedData.put("title", "Updated Title");
    updatedData.put("body", "Updated body content");

    // Build PUT request with updated body
    RequestSpecification request = RequestBuilder.buildPutRequest("/posts/1", updatedData);

    // Execute the request
    Response response = request.put("/posts/1");

    // Validate response
    ResponseValidator.validateStatusCode(response, 200);
}
```

#### Complete Request

```
PUT /posts/1 HTTP/1.1
Host: https://jsonplaceholder.typicode.com
Content-Type: application/json
Accept: application/json
Content-Length: 72

{
  "userId": 1,
  "title": "Updated Title",
  "body": "Updated body content"
}
```

---

### 4. buildDeleteRequest()

```java
public static RequestSpecification buildDeleteRequest(String endpoint) {
    return given()
            .contentType("application/json")
            .when();
}
```

#### Purpose
Creates a **DELETE request specification** for deleting resources.

#### Configuration Applied
| Config | Value | Purpose |
|--------|-------|---------|
| **HTTP Method** | DELETE | Implicit (used when calling .delete()) |
| **Content-Type** | application/json | Tells server we expect JSON response |

#### Note
- DELETE requests typically don't include a request body
- Content-Type is set for potential response content

#### Usage Example

```java
@Test
public void testDeletePost() {
    // Build DELETE request
    RequestSpecification request = RequestBuilder.buildDeleteRequest("/posts/1");

    // Execute the request
    Response response = request.delete("/posts/1");

    // Validate response
    ResponseValidator.validateStatusCode(response, 200);
}
```

#### Complete Request

```
DELETE /posts/1 HTTP/1.1
Host: https://jsonplaceholder.typicode.com
Content-Type: application/json
Accept: application/json
```

---

## How RequestBuilder Works with BaseTest

### Configuration Chain

```
BaseTest.setup()
    ↓
RestAssured.baseURI = "https://jsonplaceholder.typicode.com"
RestAssured.requestSpecification = requestSpec (with headers, filters)
    ↓
RequestBuilder.buildGetRequest("/posts")
    ↓
given() - Uses default requestSpec from BaseTest
    ↓
.contentType("application/json") - Adds/confirms header
    ↓
.when() - Ready to execute
    ↓
request.get("/posts")
    ↓
Executes with all configuration:
  - Base URI from BaseTest
  - Headers from BaseTest requestSpec
  - Additional headers from RequestBuilder
  - Allure filter from BaseTest
  - Logging from BaseTest
```

### Inheritance of Configuration

**From BaseTest**:
- Base URI: `https://jsonplaceholder.typicode.com`
- Content-Type: `application/json`
- Accept: `application/json`
- Allure filter: Captures request/response
- Logging: On failure

**From RequestBuilder**:
- Confirms Content-Type: `application/json`
- Adds request body (if applicable)
- HTTP method (implicit via .get()/.post() etc)

---

## Method Chaining (Fluent API)

RequestSpecification supports **fluent API** for method chaining:

### Example: Add More Headers

```java
@Test
public void testWithCustomHeader() {
    Map<String, Object> postData = TestDataProvider.getPostData();

    // Build and chain additional configuration
    RequestSpecification request = RequestBuilder.buildPostRequest("/posts", postData)
            .header("Authorization", "Bearer token123")
            .header("X-Custom-Header", "CustomValue")
            .queryParam("notify", "true");

    Response response = request.post("/posts");

    ResponseValidator.validateStatusCode(response, 201);
}
```

### What Gets Sent

```
POST /posts?notify=true HTTP/1.1
Host: https://jsonplaceholder.typicode.com
Content-Type: application/json
Accept: application/json
Authorization: Bearer token123
X-Custom-Header: CustomValue

{...request body...}
```

---

## Common RequestSpecification Methods

Beyond what RequestBuilder provides, you can use these methods:

```java
RequestSpecification request = RequestBuilder.buildGetRequest("/posts")
    // Headers
    .header("Authorization", "Bearer token")
    .headers(headerMap)

    // Query Parameters
    .queryParam("page", 1)
    .queryParams(paramMap)

    // Path Parameters
    .pathParam("id", 1)

    // Request Body
    .body(bodyObject)

    // Cookies
    .cookie("sessionId", "abc123")

    // Timeout
    .timeout(Duration.ofSeconds(5))

    // Custom configuration
    .config(RestAssured.config())

    // Execute request
    .get("/posts")
    .post("/posts")
    .put("/posts")
    .delete("/posts")
    .patch("/posts");
```

---

## Real-World Example

### Complete Test Using RequestBuilder

```java
@Test
@DisplayName("Create, Update, and Delete a Post")
public void testPostLifecycle() {
    // 1. CREATE - POST
    Map<String, Object> newPost = TestDataProvider.getPostData();

    RequestSpecification createRequest = RequestBuilder.buildPostRequest("/posts", newPost);
    Response createResponse = createRequest.post("/posts");

    ResponseValidator.validateStatusCode(createResponse, 201);
    int postId = createResponse.jsonPath().getInt("id");

    // 2. READ - GET
    RequestSpecification readRequest = RequestBuilder.buildGetRequest("/posts/" + postId);
    Response readResponse = readRequest.get("/posts/" + postId);

    ResponseValidator.validateStatusCode(readResponse, 200);
    ResponseValidator.validateJsonSchema(readResponse, "post-schema.json");

    // 3. UPDATE - PUT
    Map<String, Object> updatedPost = TestDataProvider.getPostData();
    updatedPost.put("title", "Updated Title");

    RequestSpecification updateRequest = RequestBuilder.buildPutRequest("/posts/" + postId, updatedPost);
    Response updateResponse = updateRequest.put("/posts/" + postId);

    ResponseValidator.validateStatusCode(updateResponse, 200);

    // 4. DELETE
    RequestSpecification deleteRequest = RequestBuilder.buildDeleteRequest("/posts/" + postId);
    Response deleteResponse = deleteRequest.delete("/posts/" + postId);

    ResponseValidator.validateStatusCode(deleteResponse, 200);
}
```

### Execution Flow

```
Test Starts
    ↓
create() - POST /posts with body
    ↓
Extract postId from response
    ↓
read() - GET /posts/{id}
    ↓
Validate schema
    ↓
update() - PUT /posts/{id} with updated body
    ↓
Validate response
    ↓
delete() - DELETE /posts/{id}
    ↓
Validate deletion
    ↓
Test Complete
```

---

## Benefits of RequestBuilder

| Benefit | Explanation |
|---------|------------|
| **Consistency** | All requests built the same way |
| **Simplicity** | Hide REST Assured complexity |
| **Reusability** | Static methods available everywhere |
| **Maintainability** | Change request config in one place |
| **Readability** | Clear intent with method names |
| **Testability** | Easy to mock or override |

---

## Design Pattern: Factory Method

RequestBuilder implements the **Factory Method Pattern**:

```
┌─────────────────────────────┐
│      RequestBuilder         │
├─────────────────────────────┤
│ + buildGetRequest()         │ ◄─── Factory method
│ + buildPostRequest()        │ ◄─── Factory method
│ + buildPutRequest()         │ ◄─── Factory method
│ + buildDeleteRequest()      │ ◄─── Factory method
└─────────────────────────────┘
         │
         │ creates
         ↓
┌─────────────────────────────┐
│  RequestSpecification       │
├─────────────────────────────┤
│ - contentType               │
│ - body                      │
│ - headers                   │
│ - queryParams               │
│ + header()                  │
│ + queryParam()              │
│ + get()                     │
│ + post()                    │
│ + put()                     │
│ + delete()                  │
└─────────────────────────────┘
```

**Benefits**:
- Hide creation complexity
- Consistent object construction
- Easy to add new request types

---

## Best Practices

### ✅ DO:

1. **Use RequestBuilder for consistency**
   ```java
   // ✅ Good - Using RequestBuilder
   RequestSpecification request = RequestBuilder.buildGetRequest("/posts/1");
   Response response = request.get("/posts/1");
   ```

2. **Chain additional configuration as needed**
   ```java
   // ✅ Good - Adding custom header
   RequestSpecification request = RequestBuilder.buildPostRequest("/posts", data)
       .header("Authorization", "Bearer token");
   ```

3. **Test data with test utility**
   ```java
   // ✅ Good - Using TestDataProvider
   Map<String, Object> data = TestDataProvider.getPostData();
   RequestSpecification request = RequestBuilder.buildPostRequest("/posts", data);
   ```

### ❌ DON'T:

1. **Don't bypass RequestBuilder**
   ```java
   // ❌ Bad - Rebuilding what RequestBuilder does
   RequestSpecification request = given()
       .contentType("application/json")
       .when();
   ```

2. **Don't hardcode values**
   ```java
   // ❌ Bad - Hardcoded endpoint
   Response response = RequestBuilder.buildGetRequest("/posts")
       .get("/posts");

   // ✅ Good - Use variable
   String endpoint = "/posts";
   Response response = RequestBuilder.buildGetRequest(endpoint)
       .get(endpoint);
   ```

3. **Don't ignore errors**
   ```java
   // ❌ Bad - Not validating response
   RequestSpecification request = RequestBuilder.buildPostRequest("/posts", data);
   Response response = request.post("/posts");
   // What if status is 500?

   // ✅ Good - Always validate
   ResponseValidator.validateStatusCode(response, 201);
   ```

---

## Summary

**RequestBuilder** is a **utility factory class** that simplifies HTTP request creation by:

1. **Providing consistent request configuration** - All requests get Content-Type header
2. **Supporting all HTTP methods** - GET, POST, PUT, DELETE
3. **Hiding REST Assured complexity** - Tests focus on what, not how
4. **Enabling method chaining** - Easy to add custom headers or parameters
5. **Working seamlessly with BaseTest** - Inherits base URI and other configs

**RequestSpecification** is the **REST Assured object that represents a configured HTTP request** ready to be executed, containing:
- Headers
- Request body
- Query parameters
- Base URI
- Filters
- And more...

Together, they form the **foundation of how tests send API requests** in this framework!


