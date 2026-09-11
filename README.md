# Reservation System

A backend REST API built with **Java and Spring Boot** for managing resource reservations such as meeting rooms, halls, and equipment.

The system allows users to request reservations for specific time ranges while enforcing business rules such as resource availability, reservation duration limits, and time conflict detection.

Reservations are created with a **PENDING** status and can later be approved or rejected by an administrator.

## Features

- Resource reservation management
- Generic resources such as rooms, halls, and equipment
- Reservation time validation
- Maximum reservation duration validation
- Reservation overlap detection
- Reservation approval workflow
- Reservation statuses:
    - `PENDING`
    - `CONFIRMED`
    - `REJECTED`
    - `CANCELLED`
- Admin approval/rejection workflow
- DTO and Mapper pattern
- Layered architecture
- Global exception handling
- Input validation

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- MariaDB
- Redis
- Bean Validation
- Lombok
- Maven
- Docker / Docker Compose

## Core Business Rules

A reservation can be created only when:

- The requested resource exists and is active.
- The reservation start time is before its end time.
- The reservation starts in the future.
- The reservation duration does not exceed 4 hours.
- The requested time range does not overlap with another active reservation for the same resource.

`PENDING` and `CONFIRMED` reservations are considered active when checking time conflicts.

## Approval Workflow

```text
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

New reservations are stored as `PENDING` until an administrator reviews them.

## Project Status

🚧 **Work in Progress**

Current development is focused on the reservation domain and business rules.

Planned next steps include:

- Admin reservation cartable
- Approve / Reject operations
- Spring Security and JWT authentication
- Role-based authorization
- Reservation cancellation
- Concurrency control for preventing double booking
- Redis integration
- Automated tests
- Dockerized deployment

## Architecture

The project follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

DTOs and Mappers are used to separate the API contract from persistence entities.

## Purpose

This project is being developed as a practical backend project to apply and strengthen Java and Spring Boot concepts through real-world business scenarios.