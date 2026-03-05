# Gherkin-Style Requirements Workflow

## Table of Contents
1. [Overview](#overview)
2. [What is Gherkin?](#what-is-gherkin)
3. [Implementation in This Project](#implementation-in-this-project)
4. [Requirements Document Structure](#requirements-document-structure)
5. [Design Document Structure](#design-document-structure)
6. [Tasks Document Structure](#tasks-document-structure)
7. [Workflow Process](#workflow-process)
8. [Writing Gherkin-Style Requirements](#writing-gherkin-style-requirements)
9. [Traceability Matrix](#traceability-matrix)
10. [Best Practices](#best-practices)
11. [Examples](#examples)

---

## Overview

This project uses a **Gherkin-inspired requirements workflow** to ensure clear, testable, and traceable specifications. While not using traditional Cucumber/BDD tooling, the project adopts Gherkin's Given-When-Then syntax in requirements documents to maintain clarity and testability.

### Key Benefits

✅ **Clear Communication** - Requirements are written in plain English  
✅ **Testability** - Each requirement maps directly to test cases  
✅ **Traceability** - Design and tasks reference specific requirements  
✅ **Collaboration** - Non-technical stakeholders can understand requirements  
✅ **Documentation** - Requirements serve as living documentation  

---

## What is Gherkin?

Gherkin is a Business Readable, Domain Specific Language (DSL) that describes software behavior without detailing implementation. It uses a structured format with keywords:

### Traditional Gherkin Keywords

```gherkin
Feature: User Authentication
  As a user
  I want to log in to the system
  So that I can access my account

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters valid username and password
    And clicks the login button
    Then the user should be redirected to the dashboard
    And a welcome message should be displayed
```

### Keywords Explained

- **Feature** - High-level description of functionality
- **Scenario** - Specific example of feature behavior
- **Given** - Initial context/preconditions
- **When** - Action or event
- **Then** - Expected outcome
- **And/But** - Additional steps

---

## Implementation in This Project

This project adapts Gherkin principles for API testing without using Cucumber. Instead of `.feature` files, we use:

### 1. Markdown Requirements Documents

Located in `.kiro/specs/{feature-name}/requirements.md`

**Structure:**
```markdown
# Requirements Document

## Requirement X: Feature Name

**User Story:** As a [role], I want [goal], so that [benefit]

#### Acceptance Criteria

1. WHEN [condition], THEN THE System SHALL [expected behavior]
2. FOR ALL [entities], THE System SHALL [requirement]
3. WHEN [action], THEN THE System SHALL verify [validation]
```

### 2. Design Documents

Located in `.kiro/specs/{feature-name}/design.md`

**Maps requirements to:**
- Architecture decisions
- Component specifications
- Correctness properties
- Implementation approach

### 3. Tasks Documents

Located in `.kiro/specs/{feature-name}/tasks.md`

**Breaks down implementation into:**
- Numbered tasks and subtasks
- Requirements traceability
- Acceptance criteria validation

---

## Requirements Document Structure

### Template

```markdown
# Requirements Document

## Introduction

[Brief description of the feature and its purpose]

## Glossary

- **Term1**: Definition
- **Term2**: Definition

## Requirements

### Requirement 1: [Requirement Name]

**User Story:** As a [role], I want [goal], so that [benefit]

#### Acceptance Criteria

1. WHEN [condition], THEN THE System SHALL [expected behavior]
2. FOR ALL [entities], THE System SHALL [requirement]
3. WHEN [action], THEN THE System SHALL verify [validation]

### Requirement 2: [Next Requirement]

[Continue pattern...]
```

### Key Elements

#### 1. Introduction
- Context for the feature
- Scope definition
- High-level goals

#### 2. Glossary
- Domain-specific terms
- System components
- API resources
- Technical concepts

#### 3. Requirements
Each requirement includes:
- **Requirement Number** - Unique identifier (e.g., Requirement 1, 2, 3)
- **Requirement Name** - Descriptive title
- **User Story** - As/Want/So that format
- **Acceptance Criteria** - Testable conditions using WHEN/THEN/FOR ALL

### Gherkin-Style Acceptance Criteria

The project uses a modified Gherkin syntax:

**Format:**
```
WHEN [condition/action], THEN THE System SHALL [expected behavior]
FOR ALL [entities], THE System SHALL [requirement]
```

**Examples:**
```
1. WHEN a valid POST request is sent to create a resource, 
   THEN THE Test_Framework SHALL verify the response status code is 201 Created

2. FOR ALL Resource_Types (albums, comments, photos, posts, todos, users), 
   THE Test_Framework SHALL include POST creation tests

3. WHEN testing GET requests by ID, 
   THEN THE Test_Framework SHALL verify the returned resource ID matches the requested ID
```

---

## Design Document Structure

### Template

```markdown
# Design Document

## Overview

[High-level design approach]

## Architecture

[System architecture and component relationships]

## Component Specifications

### Component 1: [Name]

**Purpose:** [What it does]

**Responsibilities:**
- [Responsibility 1]
- [Responsibility 2]

**Dependencies:**
- [Dependency 1]
- [Dependency 2]

**Validates Requirements:** [List requirement numbers]

## Correctness Properties

### Property 1: [Property Name]

*For any* [input conditions], when [action], the [expected outcome] should [behavior].

**Validates: Requirements X.Y, Z.W**

## Implementation Approach

[Detailed implementation strategy]

## Testing Strategy

[How requirements will be validated]
```

### Key Elements

#### 1. Architecture
- Component diagrams
- Data flow
- Integration points

#### 2. Component Specifications
- Detailed design for each component
- Clear responsibilities
- Requirement traceability

#### 3. Correctness Properties
- Universal properties that must hold
- Property-based testing specifications
- Requirement validation mapping

#### 4. Testing Strategy
- Unit tests
- Integration tests
- Property-based tests
- Example-based tests

---

## Tasks Document Structure

### Template

```markdown
# Implementation Plan: [Feature Name]

## Overview

[Brief description of implementation approach]

## Tasks

- [ ] 1. [Task Name]
  - [Task details]
  - _Requirements: X.Y, Z.W_

  - [ ] 1.1 [Subtask Name]
    - [Subtask details]
    - _Requirements: X.Y_

  - [ ] 1.2 [Another Subtask]
    - [Subtask details]
    - _Requirements: Z.W_

- [ ] 2. [Next Task]
  - [Task details]
  - _Requirements: A.B_

## Notes

- [Additional implementation notes]
- [Dependencies or considerations]
```

### Key Elements

#### 1. Task Hierarchy
- Top-level tasks (numbered 1, 2, 3...)
- Subtasks (numbered 1.1, 1.2, 2.1...)
- Clear parent-child relationships

#### 2. Requirements Traceability
Each task includes:
```markdown
_Requirements: 1.1, 1.2, 2.3_
```

This links tasks back to specific acceptance criteria.

#### 3. Task Status
- `[ ]` - Not started
- `[~]` - Queued
- `[-]` - In progress
- `[x]` - Completed

#### 4. Optional Tasks
- `[ ]*` - Optional task (can be skipped)

---

## Workflow Process

### Phase 1: Requirements Gathering

```
User Need → User Story → Acceptance Criteria
```

**Steps:**
1. Identify user need or feature request
2. Write user story in As/Want/So format
3. Define testable acceptance criteria using WHEN/THEN/FOR ALL
4. Review with stakeholders

**Example:**
```markdown
### Requirement 1: POST Request Resource Creation Tests

**User Story:** As a QA engineer, I want to verify that POST requests 
successfully create resources with correct status codes, so that I can 
ensure the API properly handles resource creation.

#### Acceptance Criteria

1. WHEN a valid POST request is sent to create a resource, 
   THEN THE Test_Framework SHALL verify the response status code is 201 Created
   
2. WHEN a POST request creates a resource, 
   THEN THE Test_Framework SHALL verify the response contains an assigned ID
```

### Phase 2: Design

```
Requirements → Architecture → Components → Properties
```

**Steps:**
1. Review all requirements
2. Design system architecture
3. Specify components and responsibilities
4. Define correctness properties
5. Map properties to requirements

**Example:**
```markdown
## Component: RequestBuilder

**Purpose:** Simplifies HTTP request creation with consistent configuration

**Responsibilities:**
- Build GET requests with proper headers
- Build POST requests with JSON body serialization
- Build PUT requests with JSON body serialization
- Build DELETE requests with proper headers

**Validates Requirements:** 6.2, 7.1, 7.2

### Property 1: POST Request Creates Resource with 201 Status

*For any* valid resource data and any resource type, when a POST request 
is sent to create the resource, the response status code should be 201 
Created and the response body should contain an assigned ID field.

**Validates: Requirements 1.1, 1.2**
```

### Phase 3: Task Breakdown

```
Design → Implementation Tasks → Subtasks → Execution
```

**Steps:**
1. Break design into implementable tasks
2. Create subtasks for complex tasks
3. Link tasks to requirements
4. Prioritize and sequence tasks
5. Execute tasks incrementally

**Example:**
```markdown
- [ ] 1. Create RequestBuilder utility class
  - Implement factory methods for HTTP requests
  - _Requirements: 6.2_

  - [ ] 1.1 Implement buildGetRequest method
    - Add Content-Type header
    - Return RequestSpecification
    - _Requirements: 6.2_

  - [ ] 1.2 Implement buildPostRequest method
    - Add Content-Type header
    - Serialize body to JSON
    - Return RequestSpecification
    - _Requirements: 6.2, 7.2_
```

### Phase 4: Implementation

```
Tasks → Code → Tests → Validation
```

**Steps:**
1. Implement task code
2. Write unit tests
3. Write integration tests
4. Validate against acceptance criteria
5. Update task status

### Phase 5: Validation

```
Tests → Requirements → Sign-off
```

**Steps:**
1. Run all tests
2. Verify each acceptance criterion is met
3. Generate test reports
4. Review with stakeholders
5. Document completion

---

## Writing Gherkin-Style Requirements

### Best Practices

#### 1. Use Clear, Testable Language

**GOOD:**
```
WHEN a valid POST request is sent to create a resource, 
THEN THE System SHALL verify the response status code is 201 Created
```

**AVOID:**
```
The system should probably return a success code when creating stuff
```

#### 2. Be Specific About Conditions

**GOOD:**
```
WHEN testing GET requests by ID, 
THEN THE System SHALL verify the returned resource ID matches the requested ID
```

**AVOID:**
```
GET requests should work correctly
```

#### 3. Use Consistent Terminology

**Define terms in glossary:**
```markdown
## Glossary

- **Resource_Type**: A distinct API entity (albums, comments, photos, posts, todos, users)
- **HTTP_Method**: The HTTP verb used for API operations (GET, POST, PUT, DELETE)
```

**Use consistently in requirements:**
```
FOR ALL Resource_Types, THE System SHALL include GET operation tests
```

#### 4. Make Requirements Atomic

**GOOD (one concern per criterion):**
```
1. WHEN a POST request creates a resource, 
   THEN THE System SHALL verify the response status code is 201 Created
   
2. WHEN a POST request creates a resource, 
   THEN THE System SHALL verify the response contains an assigned ID
```

**AVOID (multiple concerns):**
```
1. WHEN a POST request creates a resource, 
   THEN THE System SHALL verify the response status code is 201 Created 
   and contains an ID and has proper headers and validates the schema
```

#### 5. Use Quantifiers Appropriately

**FOR ALL:**
```
FOR ALL Resource_Types (albums, comments, photos, posts, todos, users), 
THE System SHALL include POST creation tests
```

**WHEN (specific condition):**
```
WHEN a PUT request attempts to update a non-existing resource, 
THEN THE System SHALL verify the response status code is 404 Not Found
```

### Acceptance Criteria Patterns

#### Pattern 1: Action → Validation
```
WHEN [action is performed], 
THEN THE System SHALL verify [expected outcome]
```

**Example:**
```
WHEN a DELETE request is sent to delete a resource, 
THEN THE System SHALL verify the response status code is 200 OK
```

#### Pattern 2: Universal Requirement
```
FOR ALL [entities], 
THE System SHALL [requirement]
```

**Example:**
```
FOR ALL Resource_Types, 
THE System SHALL create separate test files for GET operations
```

#### Pattern 3: Conditional Behavior
```
WHEN [condition is true], 
THEN THE System SHALL [behavior]
```

**Example:**
```
WHEN testing non-existing resources, 
THE System SHALL use resource IDs that are guaranteed not to exist
```

#### Pattern 4: Structural Requirement
```
WHEN [creating/organizing something], 
THE System SHALL [structural requirement]
```

**Example:**
```
WHEN creating test classes, 
THE System SHALL extend BaseTest for common setup and configuration
```

---

## Traceability Matrix

### Requirements → Design → Tasks → Tests

The project maintains traceability through explicit references:

#### Example Flow

**1. Requirement (requirements.md)**
```markdown
### Requirement 1: POST Request Resource Creation Tests

#### Acceptance Criteria

1.1. WHEN a valid POST request is sent to create a resource, 
     THEN THE Test_Framework SHALL verify the response status code is 201 Created
     
1.2. WHEN a POST request creates a resource, 
     THEN THE Test_Framework SHALL verify the response contains an assigned ID
```

**2. Design (design.md)**
```markdown
### Property 1: POST Request Creates Resource with 201 Status and Assigned ID

*For any* valid resource data and any resource type, when a POST request 
is sent to create the resource, the response status code should be 201 
Created and the response body should contain an assigned ID field.

**Validates: Requirements 1.1, 1.2**
```

**3. Task (tasks.md)**
```markdown
- [ ] 2. Create POST tests for all resources
  - Implement tests verifying 201 status code
  - Implement tests verifying ID assignment
  - _Requirements: 1.1, 1.2_

  - [ ] 2.1 Create PostsPostTest class
    - Test POST /posts returns 201
    - Test POST /posts returns ID
    - _Requirements: 1.1, 1.2_
```

**4. Test (PostsPostTest.java)**
```java
/**
 * Test class for Posts API POST endpoints.
 * 
 * Validates Requirements 1.1, 1.2 from crud-operation-test-coverage spec.
 */
@DisplayName("Posts API POST Tests")
public class PostsPostTest extends BaseTest {
    
    /**
     * Validates Requirement 1.1: POST returns 201 Created
     */
    @Test
    @DisplayName("POST /posts returns 201 Created status code")
    public void testCreatePostReturns201() {
        Map<String, Object> postData = PostsTestData.getPostData();

        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
    }
    
    /**
     * Validates Requirement 1.2: POST returns assigned ID
     */
    @Test
    @DisplayName("POST /posts returns created resource with ID")
    public void testCreatePostReturnsId() {
        Map<String, Object> postData = PostsTestData.getPostData();

        Response response = RequestBuilder.buildPostRequest("/posts", postData)
                .post("/posts");
        
        ResponseValidator.validateStatusCode(response, 201);
        
        Integer id = response.jsonPath().get("id");
        Assertions.assertNotNull(id, 
            "Response should contain an ID for the created post");
    }
}
```

### Traceability Benefits

✅ **Bidirectional Tracing** - From requirements to tests and back  
✅ **Impact Analysis** - Know which tests are affected by requirement changes  
✅ **Coverage Verification** - Ensure all requirements have tests  
✅ **Audit Trail** - Document why code exists  

---

## Best Practices

### 1. Start with User Stories

Every requirement should answer:
- **Who** needs this? (As a...)
- **What** do they need? (I want...)
- **Why** do they need it? (So that...)

### 2. Make Acceptance Criteria Testable

Each criterion should be:
- **Specific** - No ambiguity
- **Measurable** - Can be verified
- **Achievable** - Technically feasible
- **Relevant** - Supports user story
- **Testable** - Can write automated test

### 3. Use Consistent Keywords

**This project uses:**
- `WHEN` - Condition or action
- `THEN THE System SHALL` - Expected behavior
- `FOR ALL` - Universal quantifier

**Avoid mixing styles:**
```
❌ When the user does X, the system should maybe do Y
✅ WHEN the user does X, THEN THE System SHALL do Y
```

### 4. Keep Requirements Independent

Each requirement should:
- Stand alone
- Not depend on other requirements
- Be implementable separately

### 5. Review and Refine

**Review checklist:**
- [ ] Is the user story clear?
- [ ] Are acceptance criteria testable?
- [ ] Is terminology consistent?
- [ ] Are requirements independent?
- [ ] Is traceability maintained?

### 6. Maintain Living Documentation

**Update documents when:**
- Requirements change
- Design evolves
- Implementation deviates
- New insights emerge

---

## Examples

### Example 1: Complete Feature Specification

**Feature:** Test Data Modularization

**requirements.md:**
```markdown
### Requirement 1: Shared Utility Methods

**User Story:** As a test developer, I want shared utility methods for 
common test data generation, so that I can avoid code duplication across 
resource-specific test data classes.

#### Acceptance Criteria

1.1. WHEN generating random integers, 
     THEN THE System SHALL provide a utility method accepting min and max parameters
     
1.2. WHEN generating random strings, 
     THEN THE System SHALL provide a utility method accepting a prefix parameter
     
1.3. WHEN generating random emails, 
     THEN THE System SHALL provide a utility method returning valid email format
```

**design.md:**
```markdown
### Component: TestDataUtils

**Purpose:** Provides shared utility methods for test data generation

**Responsibilities:**
- Generate random integers within specified ranges
- Generate random strings with prefixes
- Generate random email addresses
- Generate random phone numbers
- Generate random geographic coordinates

**Validates Requirements:** 1.1, 1.2, 1.3

### Property 25: Random Integer Range Utility

*For any* valid min and max values where min ≤ max, when randomInt(min, max) 
is called, the returned value should be >= min and <= max.

**Validates: Requirements 1.1**
```

**tasks.md:**
```markdown
- [ ] 1. Create TestDataUtils class
  - Implement shared utility methods
  - _Requirements: 1.1, 1.2, 1.3_

  - [ ] 1.1 Implement randomInt method
    - Accept min and max parameters
    - Return value in range [min, max]
    - _Requirements: 1.1_

  - [ ] 1.2 Implement randomString method
    - Accept prefix parameter
    - Append UUID suffix
    - _Requirements: 1.2_

  - [ ] 1.3 Implement randomEmail method
    - Generate valid email format
    - Use UUID for uniqueness
    - _Requirements: 1.3_
```

**TestDataUtils.java:**
```java
/**
 * Shared utility methods for test data generation.
 * 
 * Validates Requirements 1.1, 1.2, 1.3 from test-data-modularization spec.
 */
public class TestDataUtils {
    
    /**
     * Generates a random integer between min (inclusive) and max (inclusive).
     * 
     * Validates Requirement 1.1
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    
    /**
     * Generates a random string with the specified prefix and a UUID suffix.
     * 
     * Validates Requirement 1.2
     */
    public static String randomString(String prefix) {
        return prefix + " " + UUID.randomUUID().toString().substring(0, 8);
    }
    
    /**
     * Generates a random email address.
     * 
     * Validates Requirement 1.3
     */
    public static String randomEmail() {
        return "test" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }
}
```

### Example 2: API Testing Requirement

**requirements.md:**
```markdown
### Requirement 4: GET Request Comprehensive Coverage

**User Story:** As a QA engineer, I want comprehensive GET request tests 
for all resources, so that I can verify data retrieval operations work correctly.

#### Acceptance Criteria

4.1. FOR ALL Resource_Types, 
     THE Test_Framework SHALL include tests for retrieving all resources
     
4.2. FOR ALL Resource_Types, 
     THE Test_Framework SHALL include tests for retrieving a specific resource by ID
     
4.3. WHEN testing GET requests, 
     THEN THE Test_Framework SHALL verify response status code is 200 OK
     
4.4. WHEN testing GET requests by ID, 
     THEN THE Test_Framework SHALL verify the returned resource ID matches the requested ID
```

**design.md:**
```markdown
### Property 3: GET Request Returns Valid Response

*For any* valid resource ID and any resource type, when a GET request is 
sent to retrieve that resource, the response status code should be 200 OK, 
the Content-Type header should be "application/json", and the returned 
resource ID should match the requested ID.

**Validates: Requirements 4.3, 4.4**
```

**tasks.md:**
```markdown
- [ ] 3. Create GET tests for all resources
  - _Requirements: 4.1, 4.2, 4.3, 4.4_

  - [ ] 3.1 Create PostsGetTest class
    - Test GET /posts returns 200
    - Test GET /posts/{id} returns specific post
    - Test returned ID matches requested ID
    - _Requirements: 4.1, 4.2, 4.3, 4.4_
```

**PostsGetTest.java:**
```java
/**
 * Test class for Posts API GET endpoints.
 * 
 * Validates Requirements 4.1, 4.2, 4.3, 4.4 from crud-operation-test-coverage spec.
 */
@DisplayName("Posts API GET Tests")
public class PostsGetTest extends BaseTest {
    
    /**
     * Validates Requirements 4.1, 4.3: GET all posts returns 200
     */
    @Test
    @DisplayName("GET /posts returns 200 and list of posts")
    public void testGetAllPosts() {
        Response response = RequestBuilder.buildGetRequest("/posts")
                .get("/posts");
        
        ResponseValidator.validateStatusCode(response, 200);
    }
    
    /**
     * Validates Requirements 4.2, 4.3, 4.4: GET by ID returns correct post
     */
    @Test
    @DisplayName("GET /posts/{id} returns specific post")
    public void testGetPostById() {
        int postId = 1;
        Response response = RequestBuilder.buildGetRequest("/posts/" + postId)
                .get("/posts/" + postId);
        
        ResponseValidator.validateStatusCode(response, 200);
        ResponseValidator.validateResponseBodyContains(response, "id", postId);
    }
}
```

---

## Summary

This project uses a **Gherkin-inspired requirements workflow** that:

✅ **Maintains clarity** through structured requirements  
✅ **Ensures testability** with specific acceptance criteria  
✅ **Provides traceability** from requirements to tests  
✅ **Supports collaboration** with readable documentation  
✅ **Enables validation** through automated testing  

By following this workflow, the project maintains high quality, comprehensive test coverage, and clear documentation throughout the development lifecycle.

### Key Takeaways

1. **Requirements use WHEN/THEN/FOR ALL syntax** for clarity
2. **Design maps requirements to properties** for validation
3. **Tasks reference requirements** for traceability
4. **Tests validate requirements** through automation
5. **Documentation is living** and continuously updated

This approach provides the benefits of BDD/Gherkin without the overhead of additional tooling, making it ideal for API test automation projects.
