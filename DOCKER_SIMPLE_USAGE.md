# Simple Docker Usage (No Report Server)

## You Have 3 Options Now

### Option 1: Simple Docker (Recommended for You) ⭐

**Just run tests, no extra containers:**

```bash
docker run --rm -v ${PWD}/target:/app/target api-test-automation
```

**What you get:**
- ✅ Runs tests only
- ✅ One container (exits after tests)
- ✅ No report server
- ✅ Results in `target/allure-results/`

**View reports:**
```bash
allure serve target/allure-results
```

---

### Option 2: Simplified Docker Compose

**Use the new simplified compose file:**

```bash
docker-compose -f docker-compose.simple.yml up --build
```

**What you get:**
- ✅ Runs tests only
- ✅ One container (exits after tests)
- ✅ No report server
- ✅ Results in `target/allure-results/`
- ✅ Environment variable support

**View reports:**
```bash
allure serve target/allure-results
```

---

### Option 3: Full Docker Compose (With Report Server)

**Use the original compose file (if you change your mind):**

```bash
docker-compose up --build
```

**What you get:**
- ✅ Runs tests
- ✅ Starts report server
- ✅ Two containers
- ✅ Report at http://localhost:5050

---

## Recommended Workflow for You

Since you don't want the report server container:

### Step 1: Run Tests
```bash
# Simple Docker (easiest)
docker run --rm -v ${PWD}/target:/app/target api-test-automation

# OR simplified Docker Compose
docker-compose -f docker-compose.simple.yml up --build
```

### Step 2: View Reports Locally
```bash
# Install Allure CLI (one-time)
npm install -g allure-commandline

# View reports
allure serve target/allure-results
```

---

## What Changed?

### Before (docker-compose.yml):
- 2 containers: api-tests + allure-report-server
- Ports 5050 and 5252 exposed
- Report server stays running

### Now (docker-compose.simple.yml):
- 1 container: api-tests only
- No ports exposed
- Container exits after tests
- View reports locally with Allure CLI

---

## Quick Commands

```bash
# Run tests (simple Docker)
docker run --rm -v ${PWD}/target:/app/target api-test-automation

# Run tests (simplified compose)
docker-compose -f docker-compose.simple.yml up

# View reports
allure serve target/allure-results

# Clean up
docker-compose -f docker-compose.simple.yml down
```

---

## Files You Have Now

1. **`Dockerfile`** - Basic test container
2. **`docker-compose.yml`** - Full setup with report server (2 containers)
3. **`docker-compose.simple.yml`** - Tests only (1 container) ⭐ **Use this one**
4. **`Dockerfile.with-report`** - Enhanced Dockerfile with report generation

---

## My Recommendation

**Use simple Docker command:**
```bash
docker run --rm -v ${PWD}/target:/app/target api-test-automation
```

**Why?**
- Simplest approach
- No docker-compose needed
- One container, exits after tests
- No extra services running
- View reports locally when you want

**Then view reports:**
```bash
allure serve target/allure-results
```

This is clean, simple, and exactly what you want!
