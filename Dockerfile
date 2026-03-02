# ==============================================================================
# DOCKERFILE EXPLANATION: API Test Automation Framework
# ==============================================================================
# This Dockerfile creates a containerized environment for running API tests
# with all dependencies pre-installed and configured.
# ==============================================================================

# ------------------------------------------------------------------------------
# STEP 1: Base Image Selection
# ------------------------------------------------------------------------------
# Use Maven with OpenJDK 17 as base image for modern Java support
# - maven:3.9 = Maven version 3.9 (build tool)
# - eclipse-temurin-17 = Eclipse Temurin JDK 17 (Java runtime)
# - This image includes both Maven and Java pre-installed
# - Size: ~500MB (includes OS, Java, Maven, and dependencies)
FROM maven:3.9-eclipse-temurin-17

# ------------------------------------------------------------------------------
# STEP 2: Set Working Directory
# ------------------------------------------------------------------------------
# Set working directory inside the container to /app
# - All subsequent commands will execute from this directory
# - Files copied into the container will go here by default
# - When the container runs, it starts in this directory
WORKDIR /app

# ------------------------------------------------------------------------------
# STEP 3: Copy Dependencies File (Optimization Layer)
# ------------------------------------------------------------------------------
# Copy pom.xml first to leverage Docker cache for dependencies
# - Docker caches each layer (instruction) separately
# - If pom.xml doesn't change, Docker reuses the cached dependency layer
# - This means dependencies are only downloaded when pom.xml changes
# - Saves time on subsequent builds (can save 2-5 minutes per build)
COPY pom.xml .

# ------------------------------------------------------------------------------
# STEP 4: Download Dependencies (Cached Layer)
# ------------------------------------------------------------------------------
# Download all Maven dependencies defined in pom.xml
# - mvn dependency:go-offline = Downloads all dependencies
# - -B = Batch mode (non-interactive, no progress bars)
# - This layer is cached and only re-runs if pom.xml changes
# - Downloads REST Assured, JUnit, Allure, Jackson, etc.
# - All dependencies are stored in /root/.m2/repository inside container
RUN mvn dependency:go-offline -B

# ------------------------------------------------------------------------------
# STEP 5: Copy Source Code
# ------------------------------------------------------------------------------
# Copy source code from host machine to container
# - Copies everything in src/ directory (test code, resources, schemas)
# - This layer rebuilds every time source code changes
# - Placed AFTER dependency download to maximize cache efficiency
# - If only code changes (not pom.xml), dependencies aren't re-downloaded
COPY src ./src

# ------------------------------------------------------------------------------
# STEP 6: Define Volume Mount Point
# ------------------------------------------------------------------------------
# Set volume for test reports
# - VOLUME /app/target = Declares /app/target as a mount point
# - When running the container, you can mount your host's target/ directory here
# - Example: docker run -v $(pwd)/target:/app/target
# - This allows test reports generated inside container to persist on host
# - Without this, reports would be lost when container stops
VOLUME /app/target

# ------------------------------------------------------------------------------
# STEP 7: Define Container Startup Command
# ------------------------------------------------------------------------------
# Run tests when container starts
# - CMD = Default command executed when container starts
# - ["mvn", "test"] = Runs Maven test goal (without clean)
# - We skip 'clean' to avoid permission issues with volume-mounted target directory
# - test = Compiles code and runs all JUnit tests
# - Test results are written to /app/target (which is volume-mounted)
# - Container exits after tests complete
CMD ["mvn", "test"]

# ==============================================================================
# HOW TO USE THIS DOCKERFILE:
# ==============================================================================
# 
# 1. BUILD THE IMAGE:
#    docker build -t api-test-automation .
#    - Reads this Dockerfile
#    - Creates an image named "api-test-automation"
#    - Downloads base image, installs dependencies, copies code
#    - Image is stored locally and can be reused
#
# 2. RUN TESTS IN CONTAINER:
#    docker run --rm -v $(pwd)/target:/app/target api-test-automation
#    - Creates a new container from the image
#    - --rm = Automatically remove container after it exits
#    - -v $(pwd)/target:/app/target = Mount host's target/ to container's /app/target
#    - Executes CMD instruction (mvn clean test)
#    - Test reports appear in your local target/ directory
#    - Container is deleted after tests finish
#
# 3. VIEW REPORTS:
#    - Reports are in target/surefire-reports/ on your host machine
#    - Allure results are in target/allure-results/
#    - Generate Allure report: allure serve target/allure-results
#
# ==============================================================================
# BENEFITS OF DOCKER FOR TESTING:
# ==============================================================================
# 
# 1. CONSISTENCY: Same environment everywhere (dev, CI, prod)
# 2. ISOLATION: Tests run in clean environment every time
# 3. PORTABILITY: Works on any machine with Docker installed
# 4. NO LOCAL SETUP: Don't need Java/Maven installed on host
# 5. REPRODUCIBILITY: Same results regardless of host OS
# 6. CI/CD READY: Easy integration with GitHub Actions, Jenkins, etc.
#
# ==============================================================================
