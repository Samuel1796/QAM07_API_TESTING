# API Test Automation Framework

## Overview

This is a comprehensive API test automation framework built with REST Assured (Java) to validate the JSONPlaceholder API (https://jsonplaceholder.typicode.com/). The framework tests all CRUD operations across multiple API resources including posts, comments, albums, photos, todos, and users.

## Features

- **REST Assured**: Powerful Java library for API testing
- **JUnit 5**: Modern testing framework with annotations and assertions
- **Allure Reports**: Beautiful and detailed test execution reports
- **JSON Schema Validation**: Validates API response structures
- **Maven**: Dependency management and build automation
- **Docker**: Containerized test execution for consistency
- **GitHub Actions**: CI/CD pipeline for automated testing

## Architecture

The framework follows a modular architecture with clear separation of concerns:

- **Base Layer**: Common setup and configuration (BaseTest)
- **Utility Layer**: Reusable helpers (ConfigManager, RequestBuilder, ResponseValidator, TestDataProvider)
- **Test Layer**: Test classes for each API resource
- **Schema Layer**: JSON schema definitions for validation

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Docker (optional, for containerized execution)
- Git

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd api-test-automation
```

### 2. Install Dependencies

Maven will automatically download all dependencies when you run tests:

```bash
mvn clean install
```

## Running Tests

### Local Execution

#### Run all tests:
```bash
mvn test
```

#### Run tests with clean build:
```bash
mvn clean test
```

#### Run specific test class:
```bash
mvn test -Dtest=PostsApiTest
```

#### Run specific test method:
```bash
mvn test -Dtest=PostsApiTest#testGetAllPosts
```

### Generate Allure Reports

#### Generate and open report in browser:
```bash
mvn allure:serve
```

#### Generate report to directory:
```bash
mvn allure:report
```

The report will be generated in `target/site/allure-maven-plugin/` directory.

### Docker Execution

#### Build Docker image:
```bash
docker build -t api-test-automation .
```

#### Run tests in Docker container:
```bash
docker run --rm -v $(pwd)/target:/app/target api-test-automation
```

The test reports will be available in the `target/` directory on your host machine.

## Project Structure

```
api-test-automation/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── api/
│       │           ├── base/
│       │           │   └── BaseTest.java
│       │           ├── tests/
│       │           │   ├── PostsApiTest.java
│       │           │   ├── CommentsApiTest.java
│       │           │   ├── AlbumsApiTest.java
│       │           │   ├── PhotosApiTest.java
│       │           │   ├── TodosApiTest.java
│       │           │   └── UsersApiTest.java
│       │           └── utilities/
│       │               ├── ConfigManager.java
│       │               ├── RequestBuilder.java
│       │               ├── ResponseValidator.java
│       │               └── TestDataProvider.java
│       └── resources/
│           ├── config.properties
│           ├── allure.properties
│           └── schemas/
│               ├── post-schema.json
│               ├── comment-schema.json
│               ├── album-schema.json
│               ├── photo-schema.json
│               ├── todo-schema.json
│               └── user-schema.json
├── .github/
│   └── workflows/
│       └── ci.yml
├── Dockerfile
├── pom.xml
├── .gitignore
└── README.md
```

## Configuration

Configuration settings are stored in `src/test/resources/config.properties`:

```properties
base.url=https://jsonplaceholder.typicode.com
default.timeout=5000
environment=test
log.requests=true
log.responses=true
```

## Test Coverage

The framework tests the following API resources:

### Posts API (/posts)
- GET all posts
- GET post by ID
- POST create new post
- PUT update post
- DELETE post
- GET posts with query parameters
- GET nested comments for post

### Comments API (/comments)
- GET all comments (validates 500 comments)
- GET comment by ID
- POST create new comment
- PUT update comment
- DELETE comment

### Albums API (/albums)
- GET all albums
- GET album by ID
- POST create new album
- PUT update album
- DELETE album

### Photos API (/photos)
- GET all photos (validates 5000 photos)
- GET photo by ID
- POST create new photo
- PUT update photo
- DELETE photo

### Todos API (/todos)
- GET all todos (validates 200 todos)
- GET todo by ID
- POST create new todo
- PUT update todo
- DELETE todo

### Users API (/users)
- GET all users (validates 10 users)
- GET user by ID
- POST create new user
- PUT update user
- DELETE user

## Validation Features

- **Status Code Validation**: Verifies HTTP response codes
- **Response Body Validation**: Checks response content and structure
- **Header Validation**: Validates response headers (Content-Type, etc.)
- **JSON Schema Validation**: Validates response against defined schemas
- **Response Time Validation**: Ensures responses are within acceptable time limits
- **Query Parameter Testing**: Tests filtering and nested routes

## CI/CD Pipeline

The project includes a GitHub Actions workflow that:

1. Triggers on push and pull requests
2. Builds the Docker container
3. Runs all tests inside the container
4. Publishes Allure reports as artifacts
5. Fails the workflow if tests fail

View the workflow status in the GitHub Actions tab.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests to ensure they pass
5. Submit a pull request

## Troubleshooting

### Tests fail with connection timeout
- Check your internet connection
- Verify the JSONPlaceholder API is accessible: https://jsonplaceholder.typicode.com/
- Increase timeout in config.properties

### Allure report not generating
- Ensure Allure Maven plugin is configured in pom.xml
- Run `mvn clean test` before generating report
- Check that test results exist in `target/allure-results/`

### Docker build fails
- Ensure Docker is installed and running
- Check Dockerfile syntax
- Verify Maven dependencies can be downloaded

## License

This project is for educational and testing purposes.

## Contact

For questions or issues, please open a GitHub issue.
