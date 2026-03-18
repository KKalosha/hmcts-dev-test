# HMCTS Developer Technical Test – Task Management System

This project implements a simple task management system for caseworkers.
It was built as part of the HMCTS Developer Technical Test.

The goal of the system is to allow caseworkers to create and manage tasks through a web interface backed by an API.

---

# Solution Overview

The application consists of two parts:

• A **Spring Boot backend API** responsible for managing tasks and storing them in a database.
• A **Node.js Express frontend** that provides a simple user interface using the GOV.UK Design System.

The system allows users to create, view, update and delete tasks.

---

# Backend

The backend is implemented using **Java 21 and Spring Boot**.

It exposes a REST API that supports the following operations:

• Create a task
• Retrieve all tasks
• Retrieve a task by ID
• Update the status of a task
• Delete a task

Each task contains the following fields:

• Title
• Description (optional)
• Status
• Due date and time

Tasks are stored in an **H2 in-memory database** using **Spring Data JPA**.

Validation is implemented to ensure:

• Title is required
• Due date cannot be in the past

Unit tests are implemented for the controller layer using **JUnit and Mockito**.

The API is documented using **OpenAPI / Swagger**, which can be accessed once the backend is running.

---

# Frontend

The frontend is implemented using **Node.js with Express** and **Nunjucks templates**.

The UI uses components from the **GOV.UK Design System** to provide a clean and accessible interface.

Through the interface, users can:

• Create tasks
• View all tasks
• Mark tasks as done
• Delete tasks

The frontend communicates with the backend API using **Axios**.

Basic error handling has been implemented to handle cases where the backend service is not available.

---

# Running the Application

Start the backend first.

From the backend directory:

```bash
cd backend
./gradlew bootRun
```

The backend runs on:

```
http://localhost:4000
```

Swagger API documentation:

```
http://localhost:4000/swagger-ui.html
```

Then start the frontend.

From the frontend directory:

```bash
cd frontend
yarn install
yarn start
```

The frontend runs on:

```
http://localhost:3100
```

---

# Running Tests

Backend unit tests can be executed with:

```bash
cd backend
./gradlew test
```

---

# API Endpoints

GET /tasks
Returns all tasks.

GET /tasks/{id}
Returns a task by its ID.

POST /tasks
Creates a new task.

PATCH /tasks/{id}/status
Updates the status of a task.

DELETE /tasks/{id}
Deletes a task.

---

# Notes

The project uses an **H2 in-memory database**, so all data is reset when the backend restarts.

Both the backend and frontend are designed to be simple and readable while following standard development practices such as separation of concerns, validation, testing, and error handling.
