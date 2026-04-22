# Jobela API

## About the Project

Jobela API is a backend application for a job platform that connects candidates and employers, with an initial focus on the gastronomy and hospitality sector.

The project is designed as a production-style Spring Boot backend application with layered architecture, REST APIs, validation, exception handling, database migrations, Dockerized infrastructure, and automated testing.

Its purpose is both practical and educational: to simulate a real-world backend project and serve as a portfolio project for Java backend development roles.

---

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Validation
* Spring Security

### Database

* PostgreSQL
* Flyway
* H2 (testing)

### Infrastructure

* Docker
* Docker Compose
* Maven

### Mapping & Utilities

* MapStruct
* Lombok

### Testing

* JUnit 5
* Mockito
* MockMvc
* Testcontainers

---

## Features

### Implemented

#### User Module

* User creation and management
* Role-based user structure
* Validation and exception handling

#### Candidate Module

* Candidate profile management
* Candidate personal information
* Candidate-related submodules

#### Candidate Submodules

* Skills
* Languages
* Education
* Certifications
* Documents
* Work Experience
* Preferences
* Work Authorization
* Location Preferences

#### Architecture & Backend Features

* REST API design
* DTO-based request/response layer
* Layered architecture
* MapStruct mapping layer
* Global exception handling
* Validation with Jakarta Validation
* Flyway database migrations
* Dockerized PostgreSQL setup
* Multiple Spring profiles (`dev`, `test`)

#### Testing

* Unit testing
* Integration testing
* Controller testing with MockMvc

---

### In Progress

* Security and authentication
* Swagger/OpenAPI improvements
* Extended integration testing
* Testcontainers setup refinement

---

### Planned

* Employer module
* Job offer module
* Job application module
* Filtering and search improvements
* Deployment
* Public demo environment

---

## Project Structure

The project follows a feature-based modular architecture.

```text
src/main/java/com/jobela/jobela_api
├── candidate
├── common
├── security
├── user
└── config
```

Each module follows a layered structure:

```text
module
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
└── exception
```

This structure improves maintainability, scalability, and readability.

---

## Database & Migration

The application uses PostgreSQL as the primary development database.

Database schema changes are managed using Flyway migrations.

Migration files are located here:

```text
src/main/resources/db/migration
```

The project also uses separate configuration profiles:

* `application.yml`
* `application-dev.yml`
* `application-test.yml`

---

## Testing

The project includes automated testing with:

* JUnit 5
* Mockito
* MockMvc
* H2 test database
* Spring Boot test profile
* Testcontainers (planned / partial integration)

Tests focus on:

* Business logic validation
* API behavior
* Request validation
* Exception handling
* Service layer logic
* Controller integration testing

---

## Getting Started

### Prerequisites

Before running the project, make sure you have installed:

* Java 21
* Maven
* Docker
* Docker Compose

---

### Clone the Repository

```bash
git clone https://github.com/PeterImrich13/jobela-api.git
```

```bash
cd jobela-api
```

---

### Run PostgreSQL via Docker

```bash
docker compose up -d
```

---

### Run the Application

```bash
./mvnw spring-boot:run
```

The application usually runs with the `dev` profile.

---

### Run Tests

```bash
./mvnw test
```

---

## API Documentation

Swagger/OpenAPI documentation is available locally after running the application.


---

## Roadmap

* [x] User module
* [x] Candidate module
* [x] Candidate submodules
* [x] Flyway migrations
* [x] Dockerized PostgreSQL
* [x] Validation
* [x] Exception handling
* [x] Unit testing
* [x] Integration testing
* [ ] Security and authentication
* [ ] Employer module
* [ ] Job offer module
* [ ] Job application module
* [ ] Deployment
* [ ] Public demo

---

## Author

**Peter Imrich**

* GitHub: [https://github.com/PeterImrich13](https://github.com/PeterImrich13)

---

## Project Goal

The goal of Jobela API is to simulate a real-world backend system using clean architecture and production-style development practices.

This project serves as both a portfolio project and a learning platform for improving backend engineering skills.
