# Task Manager API

REST API for task management built with **Spring Boot**. Designed with clean layered architecture, SOLID principles, and comprehensive test coverage.

## Tech Stack

- Java 25
- Spring Boot 4.0.5
- Spring Data JPA + Hibernate
- MySQL
- JUnit 6 + Mockito
- Maven

## Architecture

```
controller/        → REST endpoints + GlobalExceptionHandler
service/           → Business logic
repository/        → Data access (Spring Data JPA)
dto/request/       → TaskCreateRequest, TaskUpdateRequest
dto/response/      → TaskResponse, ErrorResponse
entity/            → Task (JPA entity)
mapper/            → TaskMapper (entity ↔ DTO conversion)
exception/         → TaskNotFoundException
enums/             → TaskStatus (PENDING, IN_PROGRESS, COMPLETED)
```

Design decisions:

- **Separate DTOs per operation** — `TaskCreateRequest` enforces mandatory title (`@NotBlank`), while `TaskUpdateRequest` allows partial updates with all fields optional. This follows the Interface Segregation Principle: each client depends only on the contract it needs.
- **Constructor injection** — No `@Autowired` on fields. Dependencies are explicit, immutable, and testable.
- **Static mapper class** — Conversion logic isolated from service and controller layers.
- **Centralized error handling** — `@RestControllerAdvice` with `GlobalExceptionHandler` manages `TaskNotFoundException` (404) and `MethodArgumentNotValidException` (400) with structured JSON error responses.

## API Endpoints

| Method | Endpoint          | Description       | Status  |
|--------|-------------------|-------------------|---------|
| POST   | `/api/tasks`      | Create a task     | 201     |
| GET    | `/api/tasks/{id}` | Get task by ID    | 200     |
| GET    | `/api/tasks`      | List all tasks    | 200     |
| PUT    | `/api/tasks/{id}` | Update a task     | 200     |
| DELETE | `/api/tasks/{id}` | Delete a task     | 204     |

### Request / Response Examples

**POST /api/tasks**
```json
// Request
{
  "title": "Implement authentication",
  "description": "Add JWT-based auth to the API"
}

// Response — 201 Created
{
  "id": 1,
  "title": "Implement authentication",
  "description": "Add JWT-based auth to the API",
  "state": "PENDING",
  "creationDate": "2026-05-01T12:00:00"
}
```

**PUT /api/tasks/1**
```json
// Request (all fields optional)
{
  "state": "IN_PROGRESS"
}

// Response — 200 OK
{
  "id": 1,
  "title": "Implement authentication",
  "description": "Add JWT-based auth to the API",
  "state": "IN_PROGRESS",
  "creationDate": "2026-05-01T12:00:00"
}
```

**Error Response — 400 Bad Request**
```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    {
      "field": "title",
      "message": "The title can't be empty"
    }
  ],
  "timestamp": "2026-05-01T12:00:00"
}
```

**Error Response — 404 Not Found**
```json
{
  "status": 404,
  "message": "Task not found with id: 99",
  "timestamp": "2026-05-01T12:00:00"
}
```

### Pagination

`GET /api/tasks` supports pagination via query parameters:

```
GET /api/tasks?page=0&size=10&sort=creationDate,desc
```

## Validation

| Field       | Create              | Update             |
|-------------|---------------------|--------------------|
| title       | Required, max 50    | Optional, 3–50     |
| description | Optional, max 300   | Optional, 3–200    |
| state       | Not accepted        | Optional (enum)    |

## Testing

Unit tests cover all `TaskService` methods with happy path and sad path scenarios using Mockito:

```
TaskServiceTest
├── create_ShouldReturnTaskResponse_WhenValidRequest
├── find_ShouldReturnTask_WhenValidID
├── find_ShouldThrowException_WhenInvalidID
├── update_ShouldReturnTask_WhenValidRequest
├── update_ShouldThrowException_WhenTaskNotFound
├── delete_ShouldDeleteTask_WhenIdExists
├── delete_ShouldThrowException_WhenTaskNotFound
└── findAll_ShouldReturnPage
```

Run tests:
```bash
./mvnw test
```

## Setup

**Prerequisites:** Java 25+, Maven, MySQL

1. Clone the repository:
```bash
git clone https://github.com/ManuelFalchettoni/task-manager-api.git
cd task-manager-api
```

2. Configure the database in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_manager
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080/api/tasks`.


