# Reservation System

A backend REST API built with **Java 21 and Spring Boot** for managing
reservations of generic resources such as meeting rooms, halls, and
equipment.

The system allows users to request resources for specific time ranges
while enforcing availability, duration, conflict-detection,
authorization, and approval rules.

Reservations are created with a **PENDING** status and can later move
through an administrator-controlled approval workflow.

## Features

-   User, resource, and reservation management
-   Generic resource types such as rooms, halls, and equipment
-   Reservation time validation
-   Maximum reservation duration of 4 hours
-   Reservation overlap detection
-   Approval workflow with:
    -   `PENDING`
    -   `CONFIRMED`
    -   `REJECTED`
    -   `CANCELLED`
-   `PENDING` and `CONFIRMED` reservations block overlapping time slots
-   Admin/user reservation status-transition rules
-   Role-based access with `ADMIN` and `USER`
-   JWT-based authentication
-   JWT invalidation / blacklist support
-   DTO and Mapper pattern
-   Layered architecture
-   Input validation
-   Global exception handling
-   Database schema versioning with Liquibase
-   Environment-based configuration for database and JWT secrets

## Tech Stack

-   Java 21
-   Spring Boot 3.x
-   Spring Web
-   Spring Data JPA / Hibernate
-   Spring Security
-   JWT
-   MariaDB
-   Liquibase
-   Spring Data Redis
-   Bean Validation
-   Lombok
-   Maven
-   Docker / Docker Compose

> Redis is included in the project stack, while application-level
> caching/integration is part of the next development phase.

## Core Business Rules

A reservation can be created only when:

-   The requested resource exists and is active.
-   The reservation start time is before its end time.
-   The reservation starts in the future.
-   The reservation duration does not exceed 4 hours.
-   The requested time range does not overlap with a `PENDING` or
    `CONFIRMED` reservation for the same resource.

Adjacent reservations are allowed when one reservation starts exactly
when another one ends.

New reservations are created as `PENDING`.

## Approval Workflow

``` text
User creates reservation
        |
        v
     PENDING
      /   \
     v     v
CONFIRMED  REJECTED
     |
     v
 CANCELLED
```

Administrators can approve or reject pending reservations. Confirmed
reservations can later be cancelled according to the application's
status-transition rules. Owners can cancel their own eligible
reservations.

Reservation details such as resource and time range are intentionally
not editable in the current MVP; status transitions are handled
separately.

## Security

The application uses Spring Security with JWT-based authentication.

High-level authentication flow:

``` text
Login
  |
  v
Authentication
  |
  v
JWT issued to client
  |
  v
Authorization: Bearer <token>
  |
  v
JWT Filter
  |
  v
Token validation
  |
  v
SecurityContext
  |
  v
Protected endpoint
```

JWTs identify the authenticated user and are validated on protected
requests. Invalidated tokens can be stored in the token blacklist so
they are no longer accepted.

Authorization rules distinguish between `ADMIN` and `USER` operations.

## Database Migrations

Database schema changes are managed with **Liquibase**.

The project uses a master changelog with ordered child changelogs for
the current schema:

``` text
db/changelog/
├── db.changelog-master.yaml
└── changes/
    ├── 001-create-users.yaml
    ├── 002-create-resources.yaml
    ├── 003-create-reservations.yaml
    └── 004-create-invalidatedToken.yaml
```

Current migrations cover:

-   Users
-   Resources
-   Reservations
-   Invalidated JWT tokens
-   Primary keys and identity columns
-   Required/unique constraints
-   Reservation foreign-key relationships

Hibernate is configured with:

``` properties
spring.jpa.hibernate.ddl-auto=validate
```

This keeps schema evolution under Liquibase while Hibernate validates
that the persistence model matches the database schema.

## Architecture

The project follows a layered architecture:

``` text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

DTOs and Mappers separate the external API contract from persistence
entities.

Business rules are kept in the service layer, while repositories are
responsible for persistence and database queries.

## Current Project Status

🚧 **Work in Progress**

Completed/currently implemented areas include:

-   Core domain model
-   User and resource services
-   Reservation creation and validation
-   Reservation overlap detection
-   Approval/status-transition business rules
-   Spring Security
-   JWT generation and validation
-   JWT authentication filter
-   JWT invalidation support
-   Role-based authorization logic
-   Liquibase migration structure and schema changelogs
-   MariaDB persistence
-   Docker Compose support

## Next Steps

-   Run and verify the complete Liquibase migration against MariaDB
-   Redis caching/integration
-   Automated unit and integration tests
-   Concurrency control to prevent double booking under simultaneous
    requests
-   Final Docker/configuration review
-   Clean Code and refactoring pass
-   Interview-oriented project review

## Concurrency Note

The current overlap validation prevents conflicting reservations during
normal request processing, but a concurrent **check-then-act race
condition** can still occur if two requests pass the overlap check
before either transaction commits.

Concurrency control is intentionally planned as a later project
exercise, with approaches such as database locking and distributed
locking to be evaluated.

## Purpose

This project is being developed as a practical backend project for
strengthening Java and Spring Boot skills through realistic business
scenarios, including authentication, authorization, workflow management,
database migrations, caching, testing, and concurrency.
