# Jobela API

Backend application for job platform connecting candidates and employers.

## Tech stack
- Java 21
- Spring Boot
- PostgreSQL
- Flyway
- Docker Compose

## Features
- User management
- Candidate profile (CV-like structure)
- Modular architecture (DTO, service, repository)

## Run project

1. Start database:
   docker compose up -d

2. Run application:
   ./mvnw spring-boot:run

3. API runs on:
   http://localhost:8080