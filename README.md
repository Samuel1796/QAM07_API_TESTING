# API Test Automation Framework

## Overview

A modern, comprehensive API test automation framework built with REST Assured (Java 17) to validate the JSONPlaceholder API (https://jsonplaceholder.typicode.com/). The framework tests all CRUD operations across multiple API resources including posts, comments, albums, photos, todos, and users.

## Features

- **REST Assured 5.5.0**: Latest version of the powerful Java library for API testing
- **JUnit 5.11.3**: Modern testing framework with advanced annotations and assertions
- **Allure Reports 2.29.0**: Beautiful and detailed test execution reports with rich visualizations
- **JSON Schema Validation**: Validates API response structures against defined schemas
- **Maven 3.9+**: Latest dependency management and build automation
- **Java 17**: Modern Java LTS version with enhanced performance and features
- **Docker**: Containerized test execution for consistency across environments
- **GitHub Actions**: Automated CI/CD pipeline with separate Allure report generation
- **Comprehensive Javadoc**: Full API documentation for all classes and methods

## Architecture

The framework follows a modular architecture with clear separation of concerns:

- **Base Layer**: Common setup and configuration (BaseTest)
- **Utility Layer**: Reusable helpers (ConfigManager, RequestBuilder, ResponseValidator, TestDataProvider)
- **Test Layer**: Test classes for each API resource with comprehensive coverage
- **Schema Layer**: JSON schema definitions for response validation
- **Logging**: SLF4J with Logback for structured logging

## Prerequisites

- **Java 17** or higher (LTS version recommended)
- **Maven 3.9** or higher
- **Docker** (optional, for containerized execution)
- **Git** for version control

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

### Viewing Test Results

#### Surefire Reports (Built-in)
After running tests, view HTML reports at:
```
target/surefire-reports/index.html
```

#### Allure Reports (Recommended)

**Note**: The Allure Maven plugin has known issues with dependency resolution. For local report generation, we recommend using the Allure CLI:

1. **Install Allure CLI**:
   ```bash
   # Using npm
   npm install -g allure-commandline
   
   # Or download from https://github.com/allure-framework/allure2/releases
   ```

2. **Generate and view report**:
   ```bash
   # After running tests
   allure serve target/allure-results
   ```

3. **Generate static report**:
   ```bash
   allure generate target/allure-results -o target/allure-report
   ```

### Docker Execution

The framework supports containerized test execution using Docker, ensuring consistency across different environments.

#### Prerequisites
- Docker Desktop installed and running
- Docker daemon accessible

#### Build Docker Image

Build the Docker image with all dependencies:

```bash
docker build -t api-test-automation .
```

This creates an image based on Maven 3.9 with Eclipse Temurin JDK 17, including:
- All Maven dependencies pre-downloaded
- Source code and test resources
- Configured test execution environment

#### Run Tests in Docker

Execute tests in a Docker container with volume mounting for reports:

```bash
# Windows (PowerShell) - RECOMMENDED - Use this simple command
docker run --rm -v ${PWD}/target:/app/target api-test-automation

# Windows (CMD) - Use absolute path with quotes
docker run --rm -v "C:\Users\YourUsername\path\to\project\target":/app/target api-test-automation

# Linux/Mac
docker run --rm -v $(pwd)/target:/app/target api-test-automation
```
**For PowerShell users (RECOMMENDED):**
- Use `${PWD}/target` - PowerShell automatically converts the path
- This is the simplest and most reliable method on Windows
**For CMD users:**
- You MUST use the absolute path in quotes
- Example: `docker run --rm -v "C:\path\to\project\target":/app/target api-test-automation`

**What happens:**
- Container starts with the test image
- Runs `mvn clean test` inside the container
- Test reports are written to `/app/target` inside container
- Volume mount maps container's `/app/target` to your local `target/` directory
- Reports are accessible on your host machine after tests complete
- Container is automatically removed after execution (`--rm` flag)

#### View Docker Test Reports

After running tests in Docker, reports are available locally:

```bash
# Surefire reports
target/surefire-reports/

# Allure results (generate report with Allure CLI)
allure serve target/allure-results
```

#### Docker in CI/CD Pipeline

The GitHub Actions workflow includes a `docker-test` job that:
1. Builds the Docker image in the CI environment
2. Runs tests in the container
3. Uploads test results as artifacts
4. Runs independently from the main test job for validation

This ensures the Docker setup works correctly and provides an additional validation layer.

#### Docker Best Practices

The project includes:
- **`.dockerignore`**: Excludes unnecessary files from the build context (IDE files, git, docs)
- **Layer caching**: `pom.xml` is copied first to cache dependency downloads
- **Volume mounting**: Test reports persist on the host machine
- **Clean execution**: `--rm` flag ensures containers don't accumulate

#### Troubleshooting Docker

**Docker daemon not running:**
```bash
# Windows: Start Docker Desktop
# Linux: sudo systemctl start docker
# Mac: Start Docker Desktop
```

**Permission denied (Linux):**
```bash
sudo usermod -aG docker $USER
# Log out and back in for changes to take effect
```

**Volume mounting issues:**
```bash
# Ensure the target directory exists
mkdir -p target

# Use absolute paths if relative paths fail
docker run --rm -v /absolute/path/to/target:/app/target api-test-automation
```

**Build fails:**
```bash
# Clean Docker cache and rebuild
docker system prune -a
docker build --no-cache -t api-test-automation .
```

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

The framework includes **39 automated tests** covering:

### Posts API (/posts) - 9 tests
- GET all posts
- GET post by ID
- POST create new post
- PUT update post
- DELETE post
- GET posts with query parameters (filtering)
- GET nested comments for post
- JSON schema validation
- Response time validation

### Comments API (/comments) - 6 tests
- GET all comments (validates 500 comments)
- GET comment by ID
- POST create new comment
- PUT update comment
- DELETE comment
- JSON schema validation

### Albums API (/albums) - 6 tests
- GET all albums
- GET album by ID
- POST create new album
- PUT update album
- DELETE album
- JSON schema validation

### Photos API (/photos) - 6 tests
- GET all photos (validates 5000 photos)
- GET photo by ID
- POST create new photo
- PUT update photo
- DELETE photo
- JSON schema validation

### Todos API (/todos) - 6 tests
- GET all todos (validates 200 todos)
- GET todo by ID
- POST create new todo
- PUT update todo
- DELETE todo
- JSON schema validation

### Users API (/users) - 6 tests
- GET all users (validates 10 users)
- GET user by ID
- POST create new user
- PUT update user
- DELETE user
- JSON schema validation

## Validation Features

- **Status Code Validation**: Verifies HTTP response codes (200, 201, 404, etc.)
- **Response Body Validation**: Checks response content and structure
- **Header Validation**: Validates response headers (Content-Type, Cache-Control, etc.)
- **JSON Schema Validation**: Validates response against defined schemas
- **Response Time Validation**: Ensures responses are within acceptable time limits (< 5 seconds)
- **Query Parameter Testing**: Tests filtering and nested routes
- **Data Integrity**: Validates returned data matches expected values

## CI/CD Pipeline

The project includes a GitHub Actions workflow with three jobs:

### 1. Test Job
- Builds the project with Maven
- Runs all 39 tests
- Uploads test results and Allure results as artifacts

### 2. Allure Report Job
- Downloads Allure results from test job
- Generates beautiful HTML reports using Allure Report Action
- Uploads generated reports as artifacts
- Maintains report history for trend analysis

### 3. Docker Test Job
- Builds Docker image
- Runs tests in containerized environment
- Uploads test results

**Workflow triggers**:
- Push to main, master, or develop branches
- Pull requests to main, master, or develop branches
- Manual workflow dispatch

View the workflow status and download reports from the GitHub Actions tab.

## Modern Dependencies (2026)

All dependencies are kept up-to-date with the latest stable versions:

- **Java**: 17 (LTS)
- **REST Assured**: 5.5.0
- **JUnit Jupiter**: 5.11.3
- **Allure**: 2.29.0
- **Jackson**: 2.18.2
- **SLF4J**: 2.0.16
- **Logback**: 1.5.12
- **Maven Surefire**: 3.5.2
- **Maven Compiler**: 3.13.0
- **AspectJ**: 1.9.22.1

## Javadoc Documentation

Generate comprehensive API documentation:

```bash
mvn javadoc:javadoc
```

View generated documentation at `target/site/apidocs/index.html`

All classes include detailed Javadoc with:
- Class-level descriptions
- Method-level documentation
- Parameter descriptions
- Return value documentation
- Usage examples where applicable

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Run tests to ensure they pass (`mvn test`)
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Submit a pull request

## Troubleshooting

### Tests fail with connection timeout
- Check your internet connection
- Verify the JSONPlaceholder API is accessible: https://jsonplaceholder.typicode.com/
- Increase timeout in `config.properties`

### Allure report not generating locally
- Install Allure CLI: `npm install -g allure-commandline`
- Run tests first: `mvn test`
- Generate report: `allure serve target/allure-results`
- Check that test results exist in `target/allure-results/`

### Docker build fails
- Ensure Docker is installed and running
- Check Dockerfile syntax
- Verify Maven dependencies can be downloaded
- Try: `docker system prune` to clean up Docker cache

### Java version mismatch
- Ensure Java 17 is installed: `java -version`
- Set JAVA_HOME environment variable to Java 17 installation
- Update Maven to use Java 17: `mvn -version`

## License

This project is for educational and testing purposes.

## Contact

For questions or issues, please open a GitHub issue.


