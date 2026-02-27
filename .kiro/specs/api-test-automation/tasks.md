# Implementation Plan: API Test Automation Framework

## Overview

This implementation plan breaks down the API test automation framework into discrete coding tasks. The framework will be built using Java, REST Assured, Maven, JUnit 5, and Allure reporting. Each task builds incrementally, starting with project setup, then core utilities, test implementations, and finally CI/CD integration.

## Tasks

- [ ] 1. Initialize Maven project structure and dependencies
  - Create Maven project with standard directory structure (src/test/java, src/test/resources)
  - Create pom.xml with project metadata (groupId, artifactId, version)
  - Add REST Assured dependency (io.rest-assured:rest-assured)
  - Add JUnit 5 dependencies (junit-jupiter-api, junit-jupiter-engine)
  - Add Allure reporting dependencies (allure-junit5, allure-rest-assured)
  - Add JSON schema validator dependency (io.rest-assured:json-schema-validator)
  - Configure Maven Surefire plugin for test execution
  - Configure Allure Maven plugin for report generation
  - Create .gitignore file excluding target/, .idea/, *.iml, .DS_Store
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6_

- [ ] 2. Create configuration management and base utilities
  - [ ] 2.1 Create config.properties file with base URL and settings
    - Define base.url=https://jsonplaceholder.typicode.com
    - Define default.timeout=5000
    - Define environment=test
    - Place in src/test/resources/
    - _Requirements: 1.6_
  
  - [ ] 2.2 Implement ConfigManager class
    - Create ConfigManager.java in utilities package
    - Implement getBaseUrl() method to read from properties
    - Implement getDefaultTimeout() method
    - Implement getEnvironment() method
    - Handle missing properties with default values
    - _Requirements: 1.1, 1.2_
  
  - [ ] 2.3 Implement BaseTest abstract class
    - Create BaseTest.java in base package
    - Add @TestInstance annotation for lifecycle management
    - Implement @BeforeAll setup() method to initialize REST Assured
    - Set base URI using ConfigManager
    - Configure request/response logging
    - Set default Content-Type header to application/json
    - Implement @AfterAll teardown() method
    - Create protected getRequestSpec() method
    - _Requirements: 1.1, 1.2, 1.3_

- [ ] 3. Implement utility classes for request building and validation
  - [ ] 3.1 Implement RequestBuilder utility class
    - Create RequestBuilder.java in utilities package
    - Implement buildGetRequest(String endpoint) method
    - Implement buildPostRequest(String endpoint, Object body) method
    - Implement buildPutRequest(String endpoint, Object body) method
    - Implement buildDeleteRequest(String endpoint) method
    - Implement buildRequestWithQueryParams(String endpoint, Map<String, String> params) method
    - Apply common headers and configurations in each method
    - _Requirements: 2.1, 2.2, 2.8, 3.1, 4.1, 5.1_
  
  - [ ] 3.2 Implement ResponseValidator utility class
    - Create ResponseValidator.java in utilities package
    - Implement validateStatusCode(Response response, int expectedCode) method
    - Implement validateResponseTime(Response response, long maxTimeMs) method
    - Implement validateHeader(Response response, String headerName, String expectedValue) method
    - Implement validateJsonSchema(Response response, String schemaPath) method
    - Implement validateResponseBodyContains(Response response, String key, Object expectedValue) method
    - Implement validateResponseBodySize(Response response, int expectedSize) method
    - Use REST Assured's validation methods with clear assertion messages
    - _Requirements: 6.1, 6.2, 6.3, 6.4, 6.5, 6.6, 6.7_
  
  - [ ] 3.3 Implement TestDataProvider utility class
    - Create TestDataProvider.java in utilities package
    - Implement getPostData() returning Map with userId, title, body
    - Implement getCommentData() returning Map with postId, name, email, body
    - Implement getAlbumData() returning Map with userId, title
    - Implement getPhotoData() returning Map with albumId, title, url, thumbnailUrl
    - Implement getTodoData() returning Map with userId, title, completed
    - Implement getUserData() returning Map with name, username, email, etc.
    - _Requirements: 3.1, 3.2, 4.1, 4.2_

- [ ] 4. Create JSON schema definitions
  - Create schemas directory in src/test/resources/
  - Create post-schema.json with required fields (userId, id, title, body)
  - Create comment-schema.json with required fields (postId, id, name, email, body)
  - Create album-schema.json with required fields (userId, id, title)
  - Create photo-schema.json with required fields (albumId, id, title, url, thumbnailUrl)
  - Create todo-schema.json with required fields (userId, id, title, completed)
  - Create user-schema.json with required fields (id, name, username, email, address, phone, website, company)
  - _Requirements: 6.4, 6.5, 6.6_

- [ ] 5. Implement Posts API test class
  - [ ] 5.1 Create PostsApiTest class extending BaseTest
    - Create PostsApiTest.java in tests package
    - Add @DisplayName("Posts API Tests") annotation
    - Extend BaseTest class
    - _Requirements: 2.1, 2.2, 3.1, 4.1, 5.1_
  
  - [ ] 5.2 Implement GET tests for Posts
    - Implement testGetAllPosts() - validate status 200
    - Implement testGetPostById() - validate returned post matches ID
    - Implement testGetPostsByUserId() - validate query parameter filtering
    - Implement testGetPostComments() - validate nested route /posts/1/comments
    - Add @Test and @DisplayName annotations to each test
    - Use ResponseValidator for assertions
    - _Requirements: 2.1, 2.2, 2.8, 2.9_
  
  - [ ] 5.3 Implement POST test for Posts
    - Implement testCreatePost() method
    - Use TestDataProvider.getPostData() for request body
    - Validate status code 201
    - Validate response contains created resource with ID
    - _Requirements: 3.1, 3.2_
  
  - [ ] 5.4 Implement PUT test for Posts
    - Implement testUpdatePost() method
    - Use TestDataProvider.getPostData() for request body
    - Validate status code 200
    - Validate response contains updated data
    - _Requirements: 4.1, 4.2_
  
  - [ ] 5.5 Implement DELETE test for Posts
    - Implement testDeletePost() method
    - Validate status code 200
    - _Requirements: 5.1_

- [ ] 6. Implement Comments API test class
  - Create CommentsApiTest.java extending BaseTest
  - Implement testGetAllComments() - validate status 200 and 500 comments
  - Implement testGetCommentById() - validate returned comment matches ID
  - Implement testCreateComment() - validate status 201
  - Implement testUpdateComment() - validate status 200
  - Implement testDeleteComment() - validate status 200
  - Add appropriate @Test and @DisplayName annotations
  - _Requirements: 2.3, 3.3, 4.3, 5.2_

- [ ] 7. Implement Albums API test class
  - Create AlbumsApiTest.java extending BaseTest
  - Implement testGetAllAlbums() - validate status 200
  - Implement testGetAlbumById() - validate returned album matches ID
  - Implement testCreateAlbum() - validate status 201 and response data
  - Implement testUpdateAlbum() - validate status 200 and updated data
  - Implement testDeleteAlbum() - validate status 200
  - Add appropriate @Test and @DisplayName annotations
  - _Requirements: 2.4, 3.4, 4.4, 5.3_

- [ ] 8. Implement Photos API test class
  - Create PhotosApiTest.java extending BaseTest
  - Implement testGetAllPhotos() - validate status 200 and 5000 photos
  - Implement testGetPhotoById() - validate returned photo matches ID
  - Implement testCreatePhoto() - validate status 201
  - Implement testUpdatePhoto() - validate status 200
  - Implement testDeletePhoto() - validate status 200
  - Add appropriate @Test and @DisplayName annotations
  - _Requirements: 2.5, 3.5, 4.5, 5.4_

- [ ] 9. Implement Todos API test class
  - Create TodosApiTest.java extending BaseTest
  - Implement testGetAllTodos() - validate status 200 and 200 todos
  - Implement testGetTodoById() - validate returned todo matches ID
  - Implement testCreateTodo() - validate status 201
  - Implement testUpdateTodo() - validate status 200
  - Implement testDeleteTodo() - validate status 200
  - Add appropriate @Test and @DisplayName annotations
  - _Requirements: 2.6, 3.6, 4.6, 5.5_

- [ ] 10. Implement Users API test class
  - Create UsersApiTest.java extending BaseTest
  - Implement testGetAllUsers() - validate status 200 and 10 users
  - Implement testGetUserById() - validate returned user matches ID
  - Implement testCreateUser() - validate status 201
  - Implement testUpdateUser() - validate status 200
  - Implement testDeleteUser() - validate status 200
  - Add appropriate @Test and @DisplayName annotations
  - _Requirements: 2.7, 3.7, 4.7, 5.6_

- [ ] 11. Add response validation and schema validation tests
  - [ ] 11.1 Add header validation tests
    - Add testResponseHeaders() to PostsApiTest
    - Validate Content-Type header is application/json
    - Use ResponseValidator.validateHeader()
    - _Requirements: 6.3_
  
  - [ ] 11.2 Add JSON schema validation tests
    - Add testPostJsonSchema() to PostsApiTest
    - Validate response against post-schema.json
    - Add similar schema validation tests to other test classes
    - Use ResponseValidator.validateJsonSchema()
    - _Requirements: 6.4, 6.5, 6.6_
  
  - [ ] 11.3 Add response time validation tests
    - Add testResponseTime() to PostsApiTest
    - Validate response time is under 5000ms
    - Use ResponseValidator.validateResponseTime()
    - _Requirements: 6.7_

- [ ] 12. Configure Allure reporting
  - Create allure.properties in src/test/resources/
  - Configure allure.results.directory=target/allure-results
  - Add @Step annotations to utility methods for better reporting
  - Verify pom.xml has allure-maven plugin configured
  - _Requirements: 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7_

- [ ] 13. Create README.md with setup and execution instructions
  - Create README.md in project root
  - Add project description and overview
  - Add prerequisites section (Java 11+, Maven 3.6+)
  - Add setup instructions (clone repo, install dependencies)
  - Add local execution instructions (mvn test, mvn clean test)
  - Add Allure report generation instructions (mvn allure:serve)
  - Add Docker execution instructions
  - Add CI/CD pipeline information
  - _Requirements: 8.3, 11.1, 11.2_

- [ ] 14. Checkpoint - Verify local test execution
  - Run mvn clean test to execute all tests
  - Verify all tests pass successfully
  - Verify console output shows test execution progress
  - Verify test summary is displayed
  - Generate Allure report with mvn allure:serve
  - Verify report displays in browser with all test results
  - Ask user if any issues arise
  - _Requirements: 11.1, 11.2, 11.3, 11.4, 11.5, 7.1, 7.2, 7.3, 7.4, 7.5, 7.6_

- [ ] 15. Create Dockerfile for containerization
  - Create Dockerfile in project root
  - Use maven:3.8-openjdk-11 as base image
  - Set working directory to /app
  - Copy pom.xml and download dependencies
  - Copy source code
  - Set CMD to execute mvn test
  - Configure volume mount for test reports (target/allure-results)
  - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5_

- [ ] 16. Create GitHub Actions CI/CD workflow
  - Create .github/workflows directory
  - Create ci.yml workflow file
  - Configure workflow to trigger on push and pull_request events
  - Add job to build Docker container
  - Add job to run tests inside Docker container
  - Configure artifact upload for Allure reports
  - Configure workflow to fail if tests fail
  - Add status badge configuration
  - _Requirements: 10.1, 10.2, 10.3, 10.4, 10.5, 10.6, 10.7_

- [ ] 17. Final checkpoint - Verify complete system
  - Run mvn clean test locally and verify all tests pass
  - Build Docker image and run tests in container
  - Verify test reports are generated and accessible
  - Commit all code to Git repository
  - Push to GitHub and verify CI/CD pipeline executes
  - Verify GitHub Actions workflow completes successfully
  - Verify test reports are available as workflow artifacts
  - Ask user if any issues arise
  - _Requirements: 8.1, 8.2, 8.4, 9.1, 9.4, 9.5, 10.1, 10.2, 10.3, 10.4, 10.5, 10.6, 10.7_

## Notes

- All test classes extend BaseTest for common setup and configuration
- ResponseValidator provides reusable validation methods across all tests
- TestDataProvider centralizes test data creation for maintainability
- JSON schemas enable comprehensive response structure validation
- Allure reporting provides detailed test execution insights
- Docker ensures consistent test execution environment
- GitHub Actions automates testing on every code change
- The framework follows Maven standard directory structure for easy navigation
