# Issue Tracker (Mini JIRA)

A lightweight **Issue Tracker REST API** built using **Java, Spring Boot, and Spring Data JPA**.
This project allows users to **create, update, view, and delete issues**, similar to a simplified version of JIRA.

The application follows a **layered architecture** where:

* **Controller** handles HTTP requests and responses
* **Service layer** contains business logic
* **Repository layer** communicates with the database using Spring Data JPA

---

# Tech Stack

* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL / H2 (depending on configuration)
* Maven

---

# Features

* Create a new issue
* View all issues
* View issue by ID
* Update issue
* Partial update using PATCH
* Delete issue
* Filter issues by status
* Role based access using Spring Security (`ADMIN`, `USER`)
* Global exception handling
* Enum based issue status

---

# Project Structure

```
controller  → Handles HTTP requests
service     → Business logic
repository  → Database access using JPA
model       → Entity classes
exception   → Custom exception handling
```

---

# API Endpoints

## Get All Issues

```
GET /api/issues
```

Returns all issues.

---

## Get Issue by ID

```
GET /api/issues/{id}
```

Returns a single issue by ID.

---

## Create Issue

```
POST /api/issues
```

Example Request Body:

```json
{
  "title": "Login Bug",
  "description": "Login button not working",
  "status": "OPEN",
  "priority": "HIGH"
}
```

---

## Update Issue

```
PUT /api/issues/issue
```

Updates an existing issue.

---

## Partial Update Issue

```
PATCH /api/issues/issue/{id}
```

Allows updating specific fields of an issue.

---

## Delete Issue

```
DELETE /api/issues/{id}
```

Deletes an issue.

---

## Get Issues by Status

```
GET /api/issues/status/{status}
```

Example:

```
GET /api/issues/status/OPEN
```

Returns all issues with the specified status.

---
## API Endpoints

### Auth
POST /auth/register
POST /auth/login

### Issues
GET /api/issues
POST /api/issues (ADMIN only)

# Issue Status Enum

Possible values:

```
OPEN
CLOSED
IN_PROGRESS
```

---

# Security

Currently role is stored in JWT for simplicity; in production, it should be validated from DB.

This project uses **Spring Security with role-based authorization**.

| Role  | Access                        |
| ----- | ----------------------------- |
| ADMIN | Create, Update, Delete Issues |
| USER  | View Issues                   |

---

# Future Improvements

* Pagination for issue listing
* Sorting and filtering
* DTO layer for API responses
* Swagger / OpenAPI documentation
* Frontend integration (React)

---

# Author

Vedant Kulkarni
System Engineer @ TCS
Java Backend Developer

GitHub:
https://github.com/vedant130699
