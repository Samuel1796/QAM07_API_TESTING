# API Test Automation Framework - Project Structure

## Overview

This is a comprehensive REST API test automation framework built with Java 17, REST Assured, JUnit 5, and Maven. The framework tests the JSONPlaceholder API (https://jsonplaceholder.typicode.com) and follows industry best practices for maintainability, scalability, and reporting.

## Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming language |
| Maven | 3.x | Build and dependency management |
| REST Assured | 5.5.0 | REST API testing library |
| JUnit 5 | 5.11.4 | Testing framework |
| Allure | 2.27.0 | Test reporting and visualization |
| Jackson | 2.18.2 | JSON processing |
| SLF4J/Logback | 2.0.16/1.5.15 | Logging framework |

## Project Directory Structure

```
QAM07_API_TESTING/
├── .github/
│   └── workflows/
│       └── ci.yml                          # GitHub Actions CI/CD pipeline
├── .kiro/
│   └── specs/                              # Feature specifications and design docs
│       ├── api-test-automation/
│       ├── crud-operation-test-coverage/
│       ├── separate-crud-test-files/
│       ├── test-data-modularization/
│       └── test-environment-consistency/
├── docs/                                   # Documentation files
│   ├── PROJECT_STRUCTURE.md               # This file
│   ├── BASE_TEST_AND_TEST_FILES.md        # Test architecture guide
│   └── GHERKIN_WORKFLOW.md                # Requirements workflow guide
├── src/
│   ├── main/java/                         # Main application code (minimal)
│   └── test/
│       ├── java/com/api/
│       │   ├── base/
│       │   │   └── BaseTest.java          # Base test class with common setup
│       │   ├── listeners/
│       │   │   └── TestResultLogger.java  # JUnit 5 test execution logger
│       │   ├── testdata/                  # Modular test data generators
│       │   │   ├── TestDataUtils.java     # Shared utility methods
│       │   │   ├── AlbumsTestData.java    # Album-specific test data
│       │   │   ├── CommentsTestData.java  # Comment-specific test data
│       │   │   ├── PhotosTestData.java    # Photo-specific test data
│       │   │   ├── PostsTestData.java     # Post-specific test data
│       │   │   ├── TodosTestData.java     # Todo-specific test data
│       │   │   └── UsersTestData.java     # User-specific test data
│       │   ├── tests/                     # Test classes organized by resource
│       │   │   ├── albums/
│       │   │   │   ├── AlbumsGetTest.java
│       │   │   │   ├── AlbumsPostTest.java
│       │   │   │   ├── AlbumsPutTest.java
│       │   │   │   └── AlbumsDeleteTest.java
│       │   │   ├── comments/
│       │   │   │   ├── CommentsGetTest.java
│       │   │   │   ├── CommentsPostTest.java
│       │   │   │   ├── CommentsPutTest.java
│       │   │   │   └── CommentsDeleteTest.java
│       │   │   ├── photos/
│       │   │   │   ├── PhotosGetTest.java
│       │   │   │   ├── PhotosPostTest.java
│       │   │   │   ├── PhotosPutTest.java
│       │   │   │   └── PhotosDeleteTest.java
│       │   │   ├── posts/
│       │   │   │   ├── PostsGetTest.java
│       │   │   │   ├── PostsPostTest.java
│       │   │   │   ├── PostsPutTest.java
│       │   │   │   └── PostsDeleteTest.java
│       │   │   ├── todos/
│       │   │   │   ├── TodosGetTest.java
│       │   │   │   ├── TodosPostTest.java
│       │   │   │   ├── TodosPutTest.java
│       │   │   │   └── TodosDeleteTest.java
│       │   │   └── users/
│       │   │       ├── UsersGetTest.java
│       │   │       ├── UsersPostTest.java
│       │   │       ├── UsersPutTest.java
│       │   │       └── UsersDeleteTest.java
│       │   └── utilities/
│       │       ├── ConfigManager.java      # Configuration management
│       │       ├── RequestBuilder.java     # HTTP request builder
│       │       ├── ResponseValidator.java  # Response validation utilities
│       │       └── TestDataProvider.java   # Legacy test data provider (deprecated)
│       └── resources/
│           ├── config.properties           # Environment configuration
│           ├── logback-test.xml           # Logging configuration
│           ├── allure.properties          # Allure reporting configuration
│           └── schemas/                    # JSON schema files for validation
│               ├── album-schema.json
│               ├── comment-schema.json
│               ├── photo-schema.json
│               ├── post-schema.json
│               ├── todo-schema.json
│               └── user-schema.json
├── target/                                 # Build output directory
│   ├── allure-results/                    # Allure test results
│   └── surefire-reports/                  # JUnit test reports
├── Dockerfile                              # Docker container configuration
├── pom.xml                                 # Maven project configuration
├── README.md                               # Project overview and setup
├── TEST_PLAN.md                           # Comprehensive test plan
└── TEST_OPTIMIZATION_SUMMARY.md           # Test optimization documentation

```

## Core Components

### 1. Base Package (`com.api.base`)

**BaseTest.java**
- Abstract base class for all test classes
- Initializes REST Assured configuration
- Sets up base URI, headers, and logging
- Integrates Allure reporting
- Provides common setup and teardown methods
- Uses `@TestInstance(Lifecycle.PER_CLASS)` for efficient resource management

### 2. Listeners Package (`com.api.listeners`)

**TestResultLogger.java**
- JUnit 5 `TestWatcher` extension
- Automatically logs test execution results
- Captures PASSED, FAILED, ABORTED, and DISABLED states
- Includes timestamps and failure reasons
- Provides detailed error messages for debugging

### 3. Test Data Package (`com.api.testdata`)

Modular test data generation following the Single Responsibility Principle:

**TestDataUtils.java**
- Shared utility methods for all test data classes
- Random integer generation within ranges
- Random string generation with UUID suffixes
- Email address generation
- Phone number generation (XXX-XXX-XXXX format)
- Geographic coordinate generation (latitude/longitude)

**Resource-Specific Test Data Classes**
- `AlbumsTestData.java` - Album data and ID generation
- `CommentsTestData.java` - Comment data with email validation
- `PhotosTestData.java` - Photo data with placeholder URLs
- `PostsTestData.java` - Post data with edge cases (empty fields, long strings, special characters)
- `TodosTestData.java` - Todo data with completion status
- `UsersTestData.java` - User data with nested address and company objects

Each class provides:
- Valid data generation methods
- Valid/invalid ID generation
- Boundary value ID generation
- Edge case data methods (where applicable)

### 4. Tests Package (`com.api.tests`)

Tests are organized by resource type and HTTP method:

**Organization Pattern:**
```
tests/
└── {resource}/
    ├── {Resource}GetTest.java      # GET operations
    ├── {Resource}PostTest.java     # POST operations (create)
    ├── {Resource}PutTest.java      # PUT operations (update)
    └── {Resource}DeleteTest.java   # DELETE operations
```

**Supported Resources:**
- Albums (100 resources)
- Comments (500 resources)
- Photos (5000 resources)
- Posts (100 resources)
- Todos (200 resources)
- Users (10 resources)

### 5. Utilities Package (`com.api.utilities`)

**ConfigManager.java**
- Centralized configuration management
- Loads properties from `config.properties`
- Provides methods for:
  - Base URL retrieval
  - Timeout configuration
  - Environment selection
  - Logging preferences
- Uses singleton pattern with static initialization

**RequestBuilder.java**
- Factory methods for building HTTP requests
- Supports GET, POST, PUT, DELETE operations
- Handles query parameters
- Ensures consistent Content-Type headers
- Simplifies request creation across tests

**ResponseValidator.java**
- Comprehensive response validation utilities
- Status code validation
- Header validation
- JSON schema validation
- Response body field validation
- Provides detailed assertion messages

**TestDataProvider.java** (Deprecated)
- Legacy monolithic test data provider
- Maintained for backward compatibility
- Being replaced by modular `testdata` package
- Delegates to new test data classes

### 6. Resources Directory (`src/test/resources`)

**config.properties**
```properties
base.url=https://jsonplaceholder.typicode.com
default.timeout=5000
environment=test
log.requests=true
```

**logback-test.xml**
- Configures SLF4J logging
- Sets log levels for different packages
- Defines console and file appenders
- Formats log output

**allure.properties**
- Configures Allure reporting
- Sets project name and description

**schemas/**
- JSON schema files for response validation
- One schema per resource type
- Validates response structure and data types

## Build Configuration (pom.xml)

### Key Dependencies

1. **REST Assured** (5.5.0)
   - Core API testing library
   - JSON schema validator

2. **JUnit 5** (5.11.4)
   - Modern testing framework
   - Parameterized tests support
   - Extension model

3. **Allure** (2.27.0)
   - Beautiful test reports
   - REST Assured integration
   - Screenshot and attachment support

4. **Jackson** (2.18.2)
   - JSON serialization/deserialization
   - Used by REST Assured

5. **SLF4J/Logback** (2.0.16/1.5.15)
   - Logging facade and implementation

### Maven Plugins

1. **maven-compiler-plugin** (3.13.0)
   - Compiles Java 17 source code

2. **maven-surefire-plugin** (3.5.2)
   - Executes JUnit tests
   - Integrates AspectJ for Allure
   - Generates test reports

3. **allure-maven** (2.12.0)
   - Generates Allure reports
   - Command: `mvn allure:serve`

4. **maven-javadoc-plugin** (3.11.2)
   - Generates API documentation

## CI/CD Integration

**GitHub Actions** (`.github/workflows/ci.yml`)
- Automated test execution on push/PR
- Maven build and test
- Allure report generation
- Artifact storage

**Docker Support** (`Dockerfile`)
- Containerized test execution
- Consistent environment across platforms
- Easy integration with CI/CD pipelines

## Test Execution

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=PostsGetTest
```

### Run Tests for Specific Resource
```bash
mvn test -Dtest="Posts*Test"
```

### Generate Allure Report
```bash
mvn allure:serve
```

### Run with Docker
```bash
docker build -t api-tests .
docker run api-tests
```

## Design Patterns Used

1. **Page Object Model (Adapted for APIs)**
   - Utilities encapsulate API interaction logic
   - Test data classes encapsulate data generation

2. **Factory Pattern**
   - RequestBuilder creates configured requests
   - Test data classes generate appropriate data

3. **Singleton Pattern**
   - ConfigManager ensures single configuration instance

4. **Builder Pattern**
   - REST Assured's fluent API
   - RequestSpecBuilder for request configuration

5. **Template Method Pattern**
   - BaseTest provides template for test setup/teardown
   - Subclasses implement specific test logic

## Best Practices Implemented

1. **Separation of Concerns**
   - Tests, utilities, and data are separated
   - Each class has a single responsibility

2. **DRY (Don't Repeat Yourself)**
   - Common setup in BaseTest
   - Reusable utilities and test data

3. **Readable Test Names**
   - `@DisplayName` annotations
   - Descriptive method names

4. **Comprehensive Documentation**
   - JavaDoc for all public methods
   - Inline comments for complex logic

5. **Modular Test Data**
   - Resource-specific test data classes
   - Shared utilities for common operations

6. **Consistent Naming Conventions**
   - Test files: `{Resource}{Method}Test.java`
   - Test methods: `test{Action}{Expected}`

7. **Comprehensive Validation**
   - Status codes, headers, body content
   - JSON schema validation

8. **Detailed Reporting**
   - Allure integration
   - Test result logging
   - Failure diagnostics

## Future Enhancements

1. **Property-Based Testing**
   - Integration with jqwik or QuickCheck
   - Automated test case generation

2. **Performance Testing**
   - Response time validation
   - Load testing integration

3. **Database Validation**
   - Direct database queries for verification

4. **API Mocking**
   - WireMock integration for isolated testing

5. **Contract Testing**
   - Pact integration for consumer-driven contracts

## Contributing

When adding new tests or features:

1. Follow existing package structure
2. Extend BaseTest for new test classes
3. Use RequestBuilder and ResponseValidator
4. Add appropriate test data methods
5. Include JavaDoc documentation
6. Add @DisplayName annotations
7. Update relevant documentation

## Support

For questions or issues:
- Review existing test examples
- Check documentation in `docs/` directory
- Refer to TEST_PLAN.md for test coverage details
