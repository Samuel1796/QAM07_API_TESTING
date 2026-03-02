# API Test Automation - Test Plan Document

## Document Information

| Field | Value |
|-------|-------|
| **Project Name** | API Test Automation Framework |
| **Version** | 2.0 |
| **Date** | March 2, 2026 |
| **Author** | API Test Automation Team |
| **Status** | Active |

## Table of Contents

1. [Introduction](#1-introduction)
2. [Test Objectives](#2-test-objectives)
3. [Scope](#3-scope)
4. [Test Strategy](#4-test-strategy)
5. [Test Environment](#5-test-environment)
6. [Test Data](#6-test-data)
7. [Test Cases](#7-test-cases)
8. [Test Execution](#8-test-execution)
9. [Defect Management](#9-defect-management)
10. [Test Deliverables](#10-test-deliverables)
11. [Risks and Mitigation](#11-risks-and-mitigation)
12. [Approval](#12-approval)

---

## 1. Introduction

### 1.1 Purpose
This test plan document outlines the comprehensive testing strategy for the JSONPlaceholder API (https://jsonplaceholder.typicode.com/). The framework validates all CRUD operations across multiple API resources using REST Assured with Java 17.

### 1.2 Project Background
JSONPlaceholder is a free fake REST API for testing and prototyping. This test automation framework ensures the API behaves correctly and consistently across all endpoints, providing confidence in API reliability and functionality.

### 1.3 Document Scope
This document covers:
- Test objectives and scope
- Test strategy and approach
- Test environment setup
- Detailed test cases for all API resources
- Test execution procedures
- Reporting and metrics

---

## 2. Test Objectives

### 2.1 Primary Objectives
- Validate all CRUD operations (Create, Read, Update, Delete) across 6 API resources
- Ensure API responses conform to expected JSON schemas
- Verify HTTP status codes match specifications
- Validate response times are within acceptable limits (< 5 seconds)
- Confirm data integrity and consistency

### 2.2 Quality Goals
- Achieve 100% test coverage for all defined API endpoints
- Maintain 0% test failure rate for stable endpoints
- Generate comprehensive test reports with Allure
- Provide clear, actionable feedback on test failures

---

## 3. Scope

### 3.1 In Scope

#### API Resources Under Test
1. **Posts API** (`/posts`)
   - 100 posts available
   - Full CRUD operations
   - Query parameter filtering
   - Nested resource access (comments)

2. **Comments API** (`/comments`)
   - 500 comments available
   - Full CRUD operations
   - Relationship with posts

3. **Albums API** (`/albums`)
   - 100 albums available
   - Full CRUD operations
   - Relationship with users

4. **Photos API** (`/photos`)
   - 5000 photos available
   - Full CRUD operations
   - Relationship with albums

5. **Todos API** (`/todos`)
   - 200 todos available
   - Full CRUD operations
   - Relationship with users

6. **Users API** (`/users`)
   - 10 users available
   - Full CRUD operations
   - Complex nested data structures

#### Test Types
- **Functional Testing**: Validate API functionality
- **Integration Testing**: Verify resource relationships
- **Schema Validation**: Ensure response structure compliance
- **Performance Testing**: Validate response times
- **Negative Testing**: Test error handling

### 3.2 Out of Scope
- Load/Stress testing (not applicable for fake API)
- Security penetration testing
- Authentication/Authorization testing (API is public)
- Database validation (no direct database access)
- UI testing (API only)

---

## 4. Test Strategy

### 4.1 Testing Approach
- **Automated Testing**: 100% automated using REST Assured
- **Data-Driven**: Reusable test data providers
- **Modular Design**: Separate utility classes for reusability
- **Continuous Integration**: GitHub Actions for automated execution

### 4.2 Test Levels

#### Unit Level
- Individual API endpoint validation
- Single resource operations
- Isolated test execution

#### Integration Level
- Nested resource access (e.g., /posts/1/comments)
- Query parameter filtering
- Resource relationships

### 4.3 Test Techniques

#### Black Box Testing
- Equivalence Partitioning
- Boundary Value Analysis
- Error Guessing

#### Validation Techniques
- Status Code Validation
- Response Body Validation
- Header Validation
- JSON Schema Validation
- Response Time Validation

---

## 5. Test Environment

### 5.1 System Under Test
- **API Base URL**: https://jsonplaceholder.typicode.com
- **Protocol**: HTTPS
- **Response Format**: JSON
- **Authentication**: None (public API)

### 5.2 Test Framework Stack
| Component | Technology | Version |
|-----------|------------|---------|
| Programming Language | Java | 17 (LTS) |
| Test Framework | JUnit 5 | 5.11.3 |
| API Testing Library | REST Assured | 5.5.0 |
| Build Tool | Maven | 3.9+ |
| Reporting | Allure | 2.29.0 |
| Logging | SLF4J + Logback | 2.0.16 / 1.5.12 |
| CI/CD | GitHub Actions | Latest |
| Containerization | Docker | Latest |

### 5.3 Test Data
- **Source**: TestDataProvider utility class
- **Type**: Static test data for POST/PUT operations
- **Validation Data**: Predefined expected values

---

## 6. Test Data

### 6.1 Test Data Strategy
- Use TestDataProvider class for consistent test data
- Minimal data sets to reduce test execution time
- Valid data for positive test cases
- Invalid/edge case data for negative testing

### 6.2 Sample Test Data

#### Post Data
```json
{
  "userId": 1,
  "title": "Test Post Title",
  "body": "Test post body content for API testing"
}
```

#### Comment Data
```json
{
  "postId": 1,
  "name": "Test Comment",
  "email": "test@example.com",
  "body": "Test comment body content"
}
```

#### User Data
```json
{
  "name": "Test User",
  "username": "testuser",
  "email": "testuser@example.com",
  "address": {
    "street": "Test Street",
    "city": "Test City",
    "zipcode": "12345"
  }
}
```

---

## 7. Test Cases

### 7.1 Posts API Test Cases (9 tests)

| Test ID | Test Case | HTTP Method | Endpoint | Expected Result | Priority |
|---------|-----------|-------------|----------|-----------------|----------|
| POST-001 | Validate GET all posts | GET | /posts | Status 200, Content-Type: application/json | High |
| POST-002 | Validate GET post by ID | GET | /posts/{id} | Status 200, Correct post returned | High |
| POST-003 | Validate POST create new post | POST | /posts | Status 201, ID assigned | High |
| POST-004 | Validate PUT update post | PUT | /posts/{id} | Status 200, Updated data returned | High |
| POST-005 | Validate DELETE post | DELETE | /posts/{id} | Status 200 | High |
| POST-006 | Validate GET with query params | GET | /posts?userId=1 | Status 200, Filtered results | Medium |
| POST-007 | Validate GET nested comments | GET | /posts/1/comments | Status 200, Related comments | Medium |
| POST-008 | Validate JSON schema | GET | /posts/1 | Schema validation passes | High |
| POST-009 | Validate response time | GET | /posts | Response < 5000ms | Medium |

### 7.2 Comments API Test Cases (6 tests)

| Test ID | Test Case | HTTP Method | Endpoint | Expected Result | Priority |
|---------|-----------|-------------|----------|-----------------|----------|
| COM-001 | Validate GET all comments | GET | /comments | Status 200, 500 comments | High |
| COM-002 | Validate GET comment by ID | GET | /comments/{id} | Status 200, Correct comment | High |
| COM-003 | Validate POST create comment | POST | /comments | Status 201, ID assigned | High |
| COM-004 | Validate PUT update comment | PUT | /comments/{id} | Status 200, Updated data | High |
| COM-005 | Validate DELETE comment | DELETE | /comments/{id} | Status 200 | High |
| COM-006 | Validate JSON schema | GET | /comments/1 | Schema validation passes | High |

### 7.3 Albums API Test Cases (6 tests)

| Test ID | Test Case | HTTP Method | Endpoint | Expected Result | Priority |
|---------|-----------|-------------|----------|-----------------|----------|
| ALB-001 | Validate GET all albums | GET | /albums | Status 200, Albums list | High |
| ALB-002 | Validate GET album by ID | GET | /albums/{id} | Status 200, Correct album | High |
| ALB-003 | Validate POST create album | POST | /albums | Status 201, ID assigned | High |
| ALB-004 | Validate PUT update album | PUT | /albums/{id} | Status 200, Updated data | High |
| ALB-005 | Validate DELETE album | DELETE | /albums/{id} | Status 200 | High |
| ALB-006 | Validate JSON schema | GET | /albums/1 | Schema validation passes | High |

### 7.4 Photos API Test Cases (6 tests)

| Test ID | Test Case | HTTP Method | Endpoint | Expected Result | Priority |
|---------|-----------|-------------|----------|-----------------|----------|
| PHO-001 | Validate GET all photos | GET | /photos | Status 200, 5000 photos | High |
| PHO-002 | Validate GET photo by ID | GET | /photos/{id} | Status 200, Correct photo | High |
| PHO-003 | Validate POST create photo | POST | /photos | Status 201, ID assigned | High |
| PHO-004 | Validate PUT update photo | PUT | /photos/{id} | Status 200, Updated data | High |
| PHO-005 | Validate DELETE photo | DELETE | /photos/{id} | Status 200 | High |
| PHO-006 | Validate JSON schema | GET | /photos/1 | Schema validation passes | High |

### 7.5 Todos API Test Cases (6 tests)

| Test ID | Test Case | HTTP Method | Endpoint | Expected Result | Priority |
|---------|-----------|-------------|----------|-----------------|----------|
| TODO-001 | Validate GET all todos | GET | /todos | Status 200, 200 todos | High |
| TODO-002 | Validate GET todo by ID | GET | /todos/{id} | Status 200, Correct todo | High |
| TODO-003 | Validate POST create todo | POST | /todos | Status 201, ID assigned | High |
| TODO-004 | Validate PUT update todo | PUT | /todos/{id} | Status 200, Updated data | High |
| TODO-005 | Validate DELETE todo | DELETE | /todos/{id} | Status 200 | High |
| TODO-006 | Validate JSON schema | GET | /todos/1 | Schema validation passes | High |

### 7.6 Users API Test Cases (6 tests)

| Test ID | Test Case | HTTP Method | Endpoint | Expected Result | Priority |
|---------|-----------|-------------|----------|-----------------|----------|
| USER-001 | Validate GET all users | GET | /users | Status 200, 10 users | High |
| USER-002 | Validate GET user by ID | GET | /users/{id} | Status 200, Correct user | High |
| USER-003 | Validate POST create user | POST | /users | Status 201, ID assigned | High |
| USER-004 | Validate PUT update user | PUT | /users/{id} | Status 200, Updated data | High |
| USER-005 | Validate DELETE user | DELETE | /users/{id} | Status 200 | High |
| USER-006 | Validate JSON schema | GET | /users/1 | Schema validation passes | High |

### 7.7 Test Case Summary

| Resource | Total Tests | High Priority | Medium Priority | Low Priority |
|----------|-------------|---------------|-----------------|--------------|
| Posts | 9 | 7 | 2 | 0 |
| Comments | 6 | 6 | 0 | 0 |
| Albums | 6 | 6 | 0 | 0 |
| Photos | 6 | 6 | 0 | 0 |
| Todos | 6 | 6 | 0 | 0 |
| Users | 6 | 6 | 0 | 0 |
| **Total** | **39** | **37** | **2** | **0** |

---

## 8. Test Execution

### 8.1 Execution Strategy

#### Local Execution
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=PostsApiTest

# Run with clean build
mvn clean test
```

#### Docker Execution
```bash
# Build image
docker build -t api-test-automation .

# Run tests
docker run --rm -v ${PWD}/target:/app/target api-test-automation
```

#### CI/CD Execution
- Automatic execution on push to main/master/develop branches
- Automatic execution on pull requests
- Manual workflow dispatch available

### 8.2 Test Execution Schedule

| Environment | Frequency | Trigger |
|-------------|-----------|---------|
| Local | On-demand | Developer initiated |
| CI/CD | Automatic | Code push/PR |
| Nightly | Daily | Scheduled (if configured) |

### 8.3 Entry Criteria
- Test environment is accessible
- Test framework is properly configured
- All dependencies are resolved
- JSONPlaceholder API is available

### 8.4 Exit Criteria
- All test cases executed
- Test pass rate ≥ 95%
- No critical defects open
- Test reports generated
- Allure reports published

---

## 9. Defect Management

### 9.1 Defect Lifecycle
1. **New**: Defect identified during test execution
2. **Open**: Defect logged and assigned
3. **In Progress**: Under investigation/fix
4. **Fixed**: Code changes completed
5. **Retest**: Verification in progress
6. **Closed**: Verified and resolved
7. **Reopened**: Issue persists after fix

### 9.2 Defect Severity

| Severity | Description | Example |
|----------|-------------|---------|
| Critical | System crash, data loss | API completely unavailable |
| High | Major functionality broken | Wrong status code returned |
| Medium | Feature partially working | Missing optional field |
| Low | Minor issue, workaround exists | Typo in response message |

### 9.3 Defect Priority

| Priority | Description | Response Time |
|----------|-------------|---------------|
| P1 | Immediate fix required | < 4 hours |
| P2 | Fix in current sprint | < 2 days |
| P3 | Fix in next sprint | < 1 week |
| P4 | Fix when possible | Backlog |

---

## 10. Test Deliverables

### 10.1 Test Artifacts

#### Before Testing
- [x] Test Plan Document (this document)
- [x] Test Strategy
- [x] Test Environment Setup Guide
- [x] Test Data Specifications

#### During Testing
- [x] Test Execution Logs
- [x] Defect Reports
- [x] Test Progress Reports

#### After Testing
- [x] Test Summary Report
- [x] Allure Test Reports
- [x] Code Coverage Reports
- [x] Lessons Learned Document

### 10.2 Report Locations

| Report Type | Location | Format |
|-------------|----------|--------|
| Surefire Reports | target/surefire-reports/ | HTML/XML |
| Allure Results | target/allure-results/ | JSON |
| Allure Reports | target/allure-report/ | HTML |
| Test Logs | Console output | Text |
| CI/CD Artifacts | GitHub Actions | Downloadable |

---

## 11. Risks and Mitigation

### 11.1 Technical Risks

| Risk | Impact | Probability | Mitigation Strategy |
|------|--------|-------------|---------------------|
| API unavailability | High | Low | Implement retry logic, monitor API status |
| Network issues | Medium | Medium | Configure appropriate timeouts, use stable network |
| Dependency conflicts | Medium | Low | Use dependency management, regular updates |
| Test data inconsistency | Low | Low | Use TestDataProvider, version control test data |

### 11.2 Project Risks

| Risk | Impact | Probability | Mitigation Strategy |
|------|--------|-------------|---------------------|
| Resource unavailability | Medium | Low | Cross-train team members, document processes |
| Timeline delays | Medium | Medium | Prioritize test cases, automate where possible |
| Requirement changes | Low | Medium | Maintain flexible test design, modular approach |

---

## 12. Approval

### 12.1 Sign-off

| Role | Name | Signature | Date |
|------|------|-----------|------|
| Test Lead | _______________ | _______________ | _______________ |
| Project Manager | _______________ | _______________ | _______________ |
| QA Manager | _______________ | _______________ | _______________ |
| Development Lead | _______________ | _______________ | _______________ |

### 12.2 Document History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | 2026-02-27 | API Test Team | Initial version |
| 2.0 | 2026-03-02 | API Test Team | Updated with modern dependencies and logging |

---

## Appendix A: Test Execution Logs Format

### Sample Log Output
```
2026-03-02 10:26:51 - [PASSED]: GET /posts returns 200 and list of posts
2026-03-02 10:26:51 - [PASSED]: GET /posts/{id} returns specific post
2026-03-02 10:26:52 - [PASSED]: POST /posts creates new post
2026-03-02 10:26:52 - [PASSED]: PUT /posts/{id} updates existing post
2026-03-02 10:26:53 - [PASSED]: DELETE /posts/{id} deletes post
```

## Appendix B: JSON Schema Definitions

All JSON schemas are located in `src/test/resources/schemas/` directory:
- post-schema.json
- comment-schema.json
- album-schema.json
- photo-schema.json
- todo-schema.json
- user-schema.json

## Appendix C: Configuration Properties

```properties
base.url=https://jsonplaceholder.typicode.com
default.timeout=5000
environment=test
log.requests=true
log.responses=true
```

---

**End of Test Plan Document**
