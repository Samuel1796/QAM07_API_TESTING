# validateJsonSchema() Method - Complete Explanation

## Overview

The `validateJsonSchema()` method **validates that an API response body matches a predefined JSON Schema**. It ensures the response structure, data types, and required fields conform to the expected API specification.

**Location**: `src/test/java/com/api/utilities/ResponseValidator.java` (lines 82-89)

---

## Method Signature

```java
public static void validateJsonSchema(Response response, String schemaPath)
```

### Parameters

| Parameter | Type | Purpose | Example |
|-----------|------|---------|---------|
| **response** | `Response` | The HTTP response object from REST Assured | Response from GET /posts/1 |
| **schemaPath** | `String` | Relative path to schema file (no "schemas/" prefix) | "post-schema.json" |

### Return Value
- **void** - Returns nothing
- **Throws AssertionError** - If validation fails

---

## Method Breakdown - Line by Line

### Line 1: Method Declaration

```java
public static void validateJsonSchema(Response response, String schemaPath) {
```

| Property | Meaning |
|----------|---------|
| **public** | Can be called from any test class |
| **static** | No instance needed; call as `ResponseValidator.validateJsonSchema()` |
| **void** | Returns nothing; used for assertions/side effects |

---

### Lines 2-3: Load Schema File

```java
InputStream schemaStream = ResponseValidator.class.getClassLoader()
        .getResourceAsStream("schemas/" + schemaPath);
```

**What it does**: Loads the JSON schema file from the classpath.

#### Breaking it down:

1. **ResponseValidator.class**
   - Gets the ResponseValidator class object
   - Used to access the ClassLoader

2. **.getClassLoader()**
   - Gets the class loader that loaded ResponseValidator
   - ClassLoader manages resources in the project

3. **.getResourceAsStream()**
   - Loads a resource file as an InputStream
   - Reads file bytes into memory

4. **"schemas/" + schemaPath**
   - Full path to schema file
   - Example: `"schemas/" + "post-schema.json"` = `"schemas/post-schema.json"`

#### Directory Structure

```
src/test/resources/
├── schemas/
│   ├── post-schema.json ◄─── Loaded here
│   ├── user-schema.json
│   ├── comment-schema.json
│   ├── album-schema.json
│   ├── photo-schema.json
│   └── todo-schema.json
└── (other resources)
```

#### Why ClassLoader?

The ClassLoader is used because:
- Resources are part of the JAR/compiled classpath
- Works in any environment (local, CI/CD, Docker)
- No hardcoded filesystem paths needed
- Portable across operating systems

#### What Gets Returned

An **InputStream** object:
```
InputStream schemaStream
    ↓
Open connection to post-schema.json file
    ↓
Ready to read file bytes
```

---

### Line 4: Verify Schema File Exists

```java
Assertions.assertNotNull(schemaStream, "Schema file not found: " + schemaPath);
```

**What it does**: Checks that the schema file was actually found.

#### Breaking it down:

1. **Assertions.assertNotNull()**
   - JUnit 5 assertion method
   - Fails if the first argument is `null`

2. **schemaStream**
   - The InputStream object created in previous step
   - Is `null` if file doesn't exist
   - Is non-null if file successfully loaded

3. **"Schema file not found: " + schemaPath**
   - Error message shown if assertion fails
   - Example: `"Schema file not found: post-schema.json"`

#### What Happens

**If schema file exists**:
```
schemaStream is not null
    ↓
Assertion passes
    ↓
Continue to next line
```

**If schema file doesn't exist**:
```
schemaStream is null (file not found)
    ↓
Assertion fails
    ↓
Test FAILS with message: "Schema file not found: post-schema.json"
    ↓
Test execution stops
```

#### Error Example

```
AssertionError: Schema file not found: post-schema.json

Expected: not null
Actual: null
```

---

### Line 5: Validate Response Against Schema

```java
response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaStream));
```

**What it does**: Validates that the response body matches the JSON schema.

#### Breaking it down:

1. **response**
   - The HTTP response from REST Assured
   - Contains status, headers, and body

2. **.then()**
   - REST Assured method for assertions
   - Transitions from request building to validation

3. **.assertThat()**
   - Starts the assertion chain
   - Prepares to check response properties

4. **.body()**
   - Assertion method for response body
   - Checks the JSON content

5. **JsonSchemaValidator.matchesJsonSchema(schemaStream)**
   - REST Assured JSON schema validation
   - Compares response body to schema
   - Returns a Matcher object

#### The Validation Process

```
response.body (JSON from API)
    ↓
JsonSchemaValidator.matchesJsonSchema(schemaStream)
    ↓
Compares structure and types
    ↓
Checks required fields
    ↓
Validates data formats
    ↓
Returns pass/fail result
    ↓
.assertThat() checks result
    ↓
If pass: Continue
If fail: Throw AssertionError
```

---

## Complete Execution Flow

### Step-by-Step Walkthrough

```
Test Method Called
    ↓
Response response = request.get("/posts/1")
    ↓
ResponseValidator.validateJsonSchema(response, "post-schema.json")
    ↓
┌─────────────────────────────────────┐
│ Line 2-3: Load Schema               │
│ Get ClassLoader                     │
│ Find schemas/post-schema.json       │
│ Create InputStream                  │
└──────────┬──────────────────────────┘
           ↓
     schemaStream = <InputStream>
           ↓
┌─────────────────────────────────────┐
│ Line 4: Verify File Exists          │
│ Assert schemaStream is not null     │
└──────────┬──────────────────────────┘
           ↓
    ✅ File found, continue
           ↓
┌─────────────────────────────────────┐
│ Line 5: Validate Response           │
│ Compare response.body to schema     │
│ Check structure and types           │
│ Verify required fields              │
│ Validate field formats              │
└──────────┬──────────────────────────┘
           ↓
    ✅ OR ❌ Schema matches or fails
           ↓
End of Method
```

---

## Real-World Example

### Test Code

```java
@Test
@DisplayName("GET /posts/{id} validates JSON schema")
public void testPostJsonSchema() {
    // 1. Make API request
    Response response = RequestBuilder.buildGetRequest("/posts/1")
            .get("/posts/1");

    // 2. Validate status code
    ResponseValidator.validateStatusCode(response, 200);

    // 3. Validate response schema
    ResponseValidator.validateJsonSchema(response, "post-schema.json");
}
```

### What Happens

**Step 1: Make request to API**
```
GET https://jsonplaceholder.typicode.com/posts/1
    ↓
Response received:
{
  "userId": 1,
  "id": 1,
  "title": "sunt aut facere repellat provident...",
  "body": "quia et suscipit..."
}
```

**Step 2: Status code validated**
```
Status code = 200 ✅
```

**Step 3: Schema validation**

**The Schema** (`post-schema.json`):
```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "required": ["userId", "id", "title", "body"],
  "properties": {
    "userId": { "type": "integer" },
    "id": { "type": "integer" },
    "title": { "type": "string" },
    "body": { "type": "string" }
  }
}
```

**Validation Checks**:
```
Response Structure Check:
  ✅ Is JSON object? YES

Required Fields Check:
  ✅ userId present? YES
  ✅ id present? YES
  ✅ title present? YES
  ✅ body present? YES

Data Type Check:
  ✅ userId is integer? YES (1)
  ✅ id is integer? YES (1)
  ✅ title is string? YES ("sunt aut...")
  ✅ body is string? YES ("quia et...")

All Checks Pass ✅
Test Passes
```

---

## Failure Scenarios

### Scenario 1: Schema File Not Found

```java
ResponseValidator.validateJsonSchema(response, "invalid-schema.json");
```

**Result**:
```
AssertionError: Schema file not found: invalid-schema.json

Stack trace:
  at ResponseValidator.validateJsonSchema() line 4
  at PostsApiTest.testPostJsonSchema() line 52
```

---

### Scenario 2: Response Missing Required Field

**Response from API**:
```json
{
  "userId": 1,
  "id": 1,
  "title": "Test Post"
  // ❌ Missing "body" field
}
```

**Result**:
```
AssertionError: JSON Schema Validation Failed

Message:
  Expected to have field 'body' but was not found

Validation Error:
  $.body is missing but it is required
```

---

### Scenario 3: Wrong Data Type

**Response from API**:
```json
{
  "userId": "1",  // ❌ Should be integer, not string
  "id": 1,
  "title": "Test Post",
  "body": "Test body"
}
```

**Result**:
```
AssertionError: JSON Schema Validation Failed

Message:
  $.userId: integer found, string expected

Validation Error:
  Instance type (string) does not match any allowed primitive type
```

---

## Usage Throughout Test Suite

### PostsApiTest

```java
@Test
public void testPostJsonSchema() {
    Response response = RequestBuilder.buildGetRequest("/posts/1")
            .get("/posts/1");
    ResponseValidator.validateStatusCode(response, 200);
    ResponseValidator.validateJsonSchema(response, "post-schema.json");
}
```

### CommentsApiTest

```java
@Test
public void testCommentJsonSchema() {
    Response response = RequestBuilder.buildGetRequest("/comments/1")
            .get("/comments/1");
    ResponseValidator.validateStatusCode(response, 200);
    ResponseValidator.validateJsonSchema(response, "comment-schema.json");
}
```

### UsersApiTest

```java
@Test
public void testUserJsonSchema() {
    Response response = RequestBuilder.buildGetRequest("/users/1")
            .get("/users/1");
    ResponseValidator.validateStatusCode(response, 200);
    ResponseValidator.validateJsonSchema(response, "user-schema.json");
}
```

### Pattern

Every test follows the same pattern:
1. Make request
2. Validate status code
3. Validate JSON schema

---

## Benefits of JSON Schema Validation

| Benefit | Explanation |
|---------|------------|
| **Structural Validation** | Ensures response has correct structure |
| **Type Checking** | Verifies all fields have correct data types |
| **Required Fields** | Confirms all mandatory fields are present |
| **Format Validation** | Checks email, URL, date formats |
| **Automated Testing** | No need to manually check each field |
| **Documentation** | Schema files document API contract |
| **Catch Breaking Changes** | Detects when API response structure changes |
| **Reusable** | Same schema validates multiple responses |

---

## How It Works with Other Validators

### Comprehensive Test Example

```java
@Test
public void testPostEndpointComprehensive() {
    Response response = RequestBuilder.buildGetRequest("/posts/1")
            .get("/posts/1");

    // 1. Validate status code
    ResponseValidator.validateStatusCode(response, 200);

    // 2. Validate response structure via schema
    ResponseValidator.validateJsonSchema(response, "post-schema.json");

    // 3. Validate specific field value
    ResponseValidator.validateResponseBodyContains(response, "id", 1);

    // 4. Validate header
    ResponseValidator.validateHeader(response, "Content-Type", "application/json");
}
```

**Flow**:
```
Status Code: 200 ✅
    ↓
Schema Match: Structure OK ✅
    ↓
Field Content: id = 1 ✅
    ↓
Header Present: Content-Type = application/json ✅
    ↓
All Validations Pass ✅
Test Passes
```

---

## Technical Details

### REST Assured Integration

The method uses **io.restassured** library:
```java
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
```

### JSON Schema Standard

Uses **JSON Schema Draft-07** (as specified in schema files):
```json
"$schema": "http://json-schema.org/draft-07/schema#"
```

### Assertion Framework

Uses **JUnit 5** assertions:
```java
import org.junit.jupiter.api.Assertions;
```

---

## Best Practices

### ✅ DO:

1. **Always validate schema**
   ```java
   // ✅ Good
   ResponseValidator.validateJsonSchema(response, "post-schema.json");
   ```

2. **Keep schema filenames consistent**
   ```java
   // ✅ Good naming convention
   "post-schema.json"
   "user-schema.json"
   "comment-schema.json"
   ```

3. **Validate before using response data**
   ```java
   // ✅ Good order
   ResponseValidator.validateStatusCode(response, 200);
   ResponseValidator.validateJsonSchema(response, "post-schema.json");
   int id = response.jsonPath().getInt("id");  // Now safe to use
   ```

### ❌ DON'T:

1. **Don't assume API response is correct**
   ```java
   // ❌ Bad - No validation
   int id = response.jsonPath().getInt("id");  // What if field missing?

   // ✅ Good
   ResponseValidator.validateJsonSchema(response, "post-schema.json");
   int id = response.jsonPath().getInt("id");
   ```

2. **Don't hardcode schema filenames**
   ```java
   // ❌ Bad - In every test
   ResponseValidator.validateJsonSchema(response, "post-schema.json");

   // ✅ Good - Use constant
   private static final String POST_SCHEMA = "post-schema.json";
   ResponseValidator.validateJsonSchema(response, POST_SCHEMA);
   ```

3. **Don't use wrong schema**
   ```java
   // ❌ Bad - Using wrong schema
   Response commentResponse = request.get("/comments/1");
   ResponseValidator.validateJsonSchema(commentResponse, "post-schema.json");

   // ✅ Good
   ResponseValidator.validateJsonSchema(commentResponse, "comment-schema.json");
   ```

---

## Summary

The `validateJsonSchema()` method is a **powerful validation tool** that:

1. **Loads** the JSON schema file from classpath
2. **Verifies** the schema file exists
3. **Compares** the API response body against the schema
4. **Ensures** the response has the correct structure, types, and required fields
5. **Fails** the test if validation doesn't pass

### Key Points:

✅ **Static method** - Call directly: `ResponseValidator.validateJsonSchema()`
✅ **Two parameters** - Response object and schema filename
✅ **Uses ClassLoader** - Works in any environment
✅ **Fails fast** - Stops test on schema mismatch
✅ **Clear errors** - Shows what validation failed
✅ **Foundation validation** - Ensures API contract compliance

This method is **essential for catching breaking changes** and ensuring API responses always match the expected specification!


