# Docker Usage Guide

## Overview

This project supports both **Docker** and **Docker Compose** for containerized test execution. Choose the approach that best fits your needs.

## Quick Decision Guide

### Use Docker (Dockerfile only) when:
- ✅ Running tests against external API
- ✅ Simple single-container execution
- ✅ No additional services needed
- ✅ Quick test runs

### Use Docker Compose when:
- ✅ Want integrated Allure reporting
- ✅ Need multiple services orchestrated
- ✅ Testing in different environments
- ✅ Want persistent report history
- ✅ Team collaboration with consistent setup

---

## Option 1: Using Docker (Simple)

### Build the Image
```bash
docker build -t api-test-automation .
```

### Run Tests
```bash
docker run --rm -v ${PWD}/target:/app/target api-test-automation
```

### Run Tests with Custom Base URL
```bash
docker run --rm \
  -e BASE_URL=https://api.staging.example.com \
  -v ${PWD}/target:/app/target \
  api-test-automation
```

### View Test Reports
```bash
# Surefire reports
cat target/surefire-reports/*.txt

# Generate Allure report (requires Allure CLI installed locally)
allure serve target/allure-results
```

---

## Option 2: Using Docker Compose (Recommended)

### Prerequisites
```bash
# Install Docker Compose (if not already installed)
# For Windows/Mac: Included with Docker Desktop
# For Linux:
sudo apt-get install docker-compose
```

### Setup Environment Variables (Optional)
```bash
# Copy example environment file
cp .env.example .env

# Edit .env with your settings
nano .env
```

### Run Tests with Allure Reports

#### Start Everything
```bash
docker-compose up --build
```

This will:
1. Build the test image
2. Run all tests
3. Start Allure report server at http://localhost:5050
4. Keep services running until you stop them

#### Access Allure Reports
Open your browser to: **http://localhost:5050**

#### Stop Services
```bash
# Stop and remove containers
docker-compose down

# Stop and remove containers + volumes
docker-compose down -v
```

### Run Tests Only (No Report Server)
```bash
docker-compose up api-tests
```

### Run Report Server Only (After Tests)
```bash
# If you already have test results
docker-compose up allure-report
```

### Run in Background (Detached Mode)
```bash
# Start services in background
docker-compose up -d

# View logs
docker-compose logs -f api-tests

# Stop services
docker-compose down
```

### Run with Custom Environment
```bash
# Override environment variables
BASE_URL=https://api.staging.example.com docker-compose up

# Or use different .env file
docker-compose --env-file .env.staging up
```

---

## Common Workflows

### Workflow 1: Local Development
```bash
# 1. Make code changes in src/

# 2. Run tests with Docker Compose
docker-compose up --build api-tests

# 3. View results in target/ directory
ls -la target/surefire-reports/

# 4. View Allure reports
docker-compose up allure-report
# Open http://localhost:5050
```

### Workflow 2: CI/CD Pipeline
```bash
# 1. Build image
docker build -t api-tests:${BUILD_NUMBER} .

# 2. Run tests
docker run --rm \
  -v $(pwd)/target:/app/target \
  -e CI_MODE=true \
  api-tests:${BUILD_NUMBER}

# 3. Publish reports (handled by CI system)
```

### Workflow 3: Testing Different Environments
```bash
# Test against staging
BASE_URL=https://api.staging.example.com docker-compose up api-tests

# Test against production
BASE_URL=https://api.production.example.com docker-compose up api-tests

# Test against local mock server
BASE_URL=http://json-server:3000 docker-compose up
```

### Workflow 4: Parallel Test Execution
```bash
# Run multiple test suites in parallel
docker-compose up --scale api-tests=3
```

---

## Docker Compose Services

### Service: api-tests
**Purpose:** Runs the API test suite

**Configuration:**
- Built from Dockerfile
- Mounts target/ for reports
- Configurable via environment variables
- Resource limits: 2 CPU, 2GB RAM

**Environment Variables:**
- `BASE_URL` - API base URL
- `ENVIRONMENT` - Environment name
- `LOG_REQUESTS` - Enable request logging

### Service: allure-report
**Purpose:** Serves Allure test reports

**Configuration:**
- Uses frankescobar/allure-docker-service image
- Ports: 5050 (UI), 5252 (API)
- Persistent history via volume
- Auto-refreshes every 3 seconds

**Access:**
- UI: http://localhost:5050
- API: http://localhost:5252

### Service: json-server (Optional)
**Purpose:** Local mock API server

**Configuration:**
- Commented out by default
- Uncomment to enable
- Requires mock-data/db.json file
- Port: 3000

---

## Volume Management

### Volumes Created by Docker Compose

1. **./target** (bind mount)
   - Test reports and results
   - Surefire reports
   - Allure results
   - Persists on host machine

2. **allure-history** (named volume)
   - Allure report history
   - Trend graphs and statistics
   - Persists across container restarts

### View Volumes
```bash
docker volume ls
```

### Inspect Volume
```bash
docker volume inspect allure-test-history
```

### Remove Volumes
```bash
# Remove all volumes
docker-compose down -v

# Remove specific volume
docker volume rm allure-test-history
```

---

## Network Configuration

### Network: test-network
- Bridge network for service communication
- Isolated from host network
- Services can communicate by service name

### View Networks
```bash
docker network ls
```

### Inspect Network
```bash
docker network inspect api-test-network
```

---

## Troubleshooting

### Issue 1: Port Already in Use
```
Error: Bind for 0.0.0.0:5050 failed: port is already allocated
```

**Solution:**
```bash
# Change port in docker-compose.yml
ports:
  - "5051:5050"  # Use different host port

# Or stop service using the port
lsof -ti:5050 | xargs kill -9
```

### Issue 2: Permission Denied on target/
```
Error: Permission denied: '/app/target'
```

**Solution:**
```bash
# Fix permissions on host
chmod -R 777 target/

# Or run with user permissions
docker-compose run --user $(id -u):$(id -g) api-tests
```

### Issue 3: Tests Not Running
```
Container exits immediately without running tests
```

**Solution:**
```bash
# Check logs
docker-compose logs api-tests

# Run interactively for debugging
docker-compose run api-tests bash
```

### Issue 4: Allure Reports Not Showing
```
Allure UI shows no results
```

**Solution:**
```bash
# Ensure tests ran first
docker-compose up api-tests

# Check if results exist
ls -la target/allure-results/

# Restart Allure service
docker-compose restart allure-report
```

### Issue 5: Out of Memory
```
Error: Java heap space
```

**Solution:**
```yaml
# Increase memory in docker-compose.yml
deploy:
  resources:
    limits:
      memory: 4G  # Increase from 2G
```

### Issue 6: Slow Build Times
```
Docker build takes too long
```

**Solution:**
```bash
# Use BuildKit for faster builds
DOCKER_BUILDKIT=1 docker-compose build

# Or enable BuildKit globally
export DOCKER_BUILDKIT=1
```

---

## Best Practices

### 1. Use .env for Configuration
```bash
# Don't hardcode values in docker-compose.yml
# Use .env file instead
cp .env.example .env
# Edit .env with your values
```

### 2. Clean Up Regularly
```bash
# Remove stopped containers
docker-compose down

# Remove volumes too
docker-compose down -v

# Remove unused images
docker image prune -a
```

### 3. Use Build Cache
```bash
# Docker caches layers
# Only pom.xml changes trigger dependency download
# Code changes don't re-download dependencies
```

### 4. Monitor Resource Usage
```bash
# Check container resource usage
docker stats

# Limit resources in docker-compose.yml
deploy:
  resources:
    limits:
      cpus: '2.0'
      memory: 2G
```

### 5. Use Health Checks
```yaml
# Add health checks to services
healthcheck:
  test: ["CMD", "curl", "-f", "http://localhost:5050"]
  interval: 30s
  timeout: 10s
  retries: 3
```

### 6. Version Control
```bash
# Add to .gitignore
.env
target/
.allure/

# Commit to version control
.env.example
docker-compose.yml
Dockerfile
```

---

## Advanced Usage

### Run Specific Test Class
```bash
docker-compose run api-tests mvn test -Dtest=PostsGetTest
```

### Run Tests with Maven Profile
```bash
docker-compose run api-tests mvn test -P integration-tests
```

### Debug Tests
```bash
# Run with debug port exposed
docker-compose run -p 5005:5005 api-tests \
  mvn test -Dmaven.surefire.debug
```

### Generate Reports Only
```bash
# Generate Allure report without running tests
docker-compose run api-tests mvn allure:report
```

### Export Test Results
```bash
# Copy results from container
docker cp api-test-runner:/app/target ./target-backup
```

---

## Comparison: Docker vs Docker Compose

| Feature | Docker | Docker Compose |
|---------|--------|----------------|
| **Complexity** | Simple | Moderate |
| **Services** | Single | Multiple |
| **Configuration** | Command line | YAML file |
| **Networking** | Manual | Automatic |
| **Volumes** | Manual | Automatic |
| **Orchestration** | No | Yes |
| **Best For** | Quick tests | Full environment |
| **Learning Curve** | Low | Medium |
| **Team Collaboration** | Manual setup | Consistent setup |

---

## Summary

### Use Docker when:
- Quick test execution needed
- Testing external API only
- Simple CI/CD integration
- No additional services required

### Use Docker Compose when:
- Want integrated reporting
- Need multiple services
- Team collaboration
- Environment consistency
- Development workflow

Both approaches are valid and supported. Choose based on your specific needs and team requirements.

---

## Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Allure Docker Service](https://github.com/fescobar/allure-docker-service)
- [Best Practices for Writing Dockerfiles](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
