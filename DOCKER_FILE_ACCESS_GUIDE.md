# How to Access Files in Docker Desktop

## Quick Answer

**Use the persistent container setup:**

```bash
# Build and run
docker-compose -f docker-compose.persistent.yml up -d --build

# Then in Docker Desktop:
# Containers → api-tests-persistent → Files tab → Browse /app/target/
```

---

## Method 1: Persistent Container (Recommended) ⭐

### Step 1: Build and Run
```bash
docker-compose -f docker-compose.persistent.yml up -d --build
```

### Step 2: Wait for Tests to Complete
```bash
# View logs to see when tests finish
docker-compose -f docker-compose.persistent.yml logs -f
```

When you see "✅ Tests complete!", press `Ctrl+C`

### Step 3: Browse Files in Docker Desktop

1. Open **Docker Desktop**
2. Click **Containers** in left sidebar
3. Find **api-tests-persistent** (should show "Running")
4. Click on the container name
5. Click **Files** tab at the top
6. Navigate to `/app/target/`

**What you'll see:**
```
/app/target/
├── allure-results/          ← Allure JSON results
│   ├── *-result.json
│   └── *-container.json
├── surefire-reports/        ← JUnit XML reports
│   ├── TEST-*.xml
│   └── *.txt
├── classes/                 ← Compiled test classes
└── test-classes/            ← Compiled test classes
```

### Step 4: Stop Container When Done
```bash
docker-compose -f docker-compose.persistent.yml down
```

---

## Method 2: Manual Persistent Container

### Step 1: Build Image
```bash
docker build -f Dockerfile.persistent -t api-tests-persistent .
```

### Step 2: Run Container
```bash
docker run -d --name my-api-tests -v ${PWD}/target:/app/target api-tests-persistent
```

### Step 3: Browse in Docker Desktop
1. Open **Docker Desktop**
2. **Containers** → **my-api-tests**
3. Click **Files** tab
4. Browse `/app/target/`

### Step 4: Stop and Remove
```bash
docker stop my-api-tests
docker rm my-api-tests
```

---

## Method 3: Interactive Shell (Advanced)

### Start Interactive Container
```bash
docker run -it --name api-tests-shell -v ${PWD}/target:/app/target api-test-automation bash
```

### Inside Container, Run Commands
```bash
# Run tests
mvn test

# List files
ls -la target/

# View a file
cat target/surefire-reports/TEST-*.xml

# Exit when done
exit
```

### Browse in Docker Desktop
While container is running, you can browse files in Docker Desktop.

---

## Method 4: Copy Files from Container

If you just want to extract files without browsing:

```bash
# Run tests
docker run --name temp-tests api-test-automation

# Copy files out
docker cp temp-tests:/app/target ./target-from-docker

# Clean up
docker rm temp-tests
```

---

## What Files Can You Access?

### In Docker Desktop Files Tab:

**Location: `/app/target/`**

#### 1. Allure Results (`/app/target/allure-results/`)
- `*-result.json` - Test execution results
- `*-container.json` - Test container info
- `*-attachment.*` - Screenshots, logs, etc.

#### 2. Surefire Reports (`/app/target/surefire-reports/`)
- `TEST-*.xml` - JUnit XML reports
- `*.txt` - Text summaries
- `index.html` - HTML report (if generated)

#### 3. Compiled Classes (`/app/target/classes/` and `/app/target/test-classes/`)
- `.class` files - Compiled Java bytecode

#### 4. Maven Metadata
- `maven-status/` - Build metadata
- `surefire/` - Test execution metadata

---

## Docker Desktop File Operations

### What You Can Do in Files Tab:

✅ **Browse folders** - Click to navigate
✅ **View file content** - Click on file to preview
✅ **Download files** - Right-click → Save
✅ **Search files** - Use search box
✅ **Copy file path** - Right-click → Copy path

❌ **Cannot edit files** - Read-only view
❌ **Cannot upload files** - Use volumes instead
❌ **Cannot delete files** - Use docker exec instead

---

## Accessing Specific Files

### View Allure Results
```
Docker Desktop → Containers → api-tests-persistent → Files
→ /app/target/allure-results/
→ Click any *-result.json file to view
```

### View Test Reports
```
Docker Desktop → Containers → api-tests-persistent → Files
→ /app/target/surefire-reports/
→ Click TEST-*.xml to view
```

### Download Files
```
Right-click on file → Save As
```

---

## Troubleshooting

### Issue 1: Container Not Showing in Docker Desktop

**Cause:** Container exited too quickly

**Solution:** Use persistent container:
```bash
docker-compose -f docker-compose.persistent.yml up -d --build
```

### Issue 2: Files Tab is Empty

**Cause:** Tests haven't run yet or failed

**Solution:** Check logs:
```bash
docker-compose -f docker-compose.persistent.yml logs
```

### Issue 3: Can't Find target/ Folder

**Navigate to:** `/app/target/` (not just `/target/`)

The full path inside container is `/app/target/`

### Issue 4: Files Not Updating

**Cause:** Volume mount not working

**Solution:** Ensure volume is mounted:
```bash
docker inspect api-tests-persistent | grep -A 5 Mounts
```

Should show: `"Source": "C:\\path\\to\\your\\project\\target"`

---

## Comparison of Methods

| Method | Pros | Cons | Best For |
|--------|------|------|----------|
| **Persistent Container** | ✅ Easy<br>✅ Stays running<br>✅ Browse anytime | ❌ Uses resources | Development |
| **Interactive Shell** | ✅ Full control<br>✅ Run commands | ❌ Manual<br>❌ Terminal needed | Debugging |
| **Copy Files** | ✅ Files on host<br>✅ No container needed | ❌ Extra step<br>❌ Not real-time | One-time extraction |
| **Volume Mount** | ✅ Automatic sync<br>✅ Files on host | ✅ Best option! | Always use this |

---

## Recommended Workflow

### For Regular Use:

**The files are ALREADY on your host machine!**

When you run:
```bash
docker run --rm -v ${PWD}/target:/app/target api-test-automation
```

The `-v ${PWD}/target:/app/target` means:
- Files written to `/app/target/` in container
- Automatically appear in `./target/` on your host
- **No need to browse in Docker Desktop!**

**Just open your local `target/` folder:**
```bash
# Windows Explorer
explorer target

# Or view in VS Code
code target/allure-results
```

### For Browsing Inside Container:

If you specifically want to see files INSIDE the container:

```bash
# Use persistent container
docker-compose -f docker-compose.persistent.yml up -d --build

# Browse in Docker Desktop
# Containers → api-tests-persistent → Files → /app/target/
```

---

## Summary

### ✅ Easiest Way to Access Files:

**They're already on your computer!**

```bash
# Run tests
docker run --rm -v ${PWD}/target:/app/target api-test-automation

# Files are in your local target/ folder
cd target
ls
```

### ✅ To Browse Inside Container:

```bash
# Use persistent container
docker-compose -f docker-compose.persistent.yml up -d --build

# Then: Docker Desktop → Containers → api-tests-persistent → Files
```

### ✅ To Download Specific Files:

Right-click in Docker Desktop Files tab → Save As

---

## Quick Reference

```bash
# Build persistent container
docker build -f Dockerfile.persistent -t api-tests-persistent .

# Run persistent container
docker run -d --name my-tests -v ${PWD}/target:/app/target api-tests-persistent

# Browse in Docker Desktop
# Containers → my-tests → Files → /app/target/

# Stop container
docker stop my-tests
docker rm my-tests

# Or use docker-compose
docker-compose -f docker-compose.persistent.yml up -d --build
docker-compose -f docker-compose.persistent.yml down
```

---

**Remember:** With volume mounts (`-v ${PWD}/target:/app/target`), files are automatically synced to your local machine. You usually don't need to browse inside the container!
