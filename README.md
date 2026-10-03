# 🎓 Student Management System (Mini-Project)

![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)

A simple full-stack mini-project built to demonstrate CRUD operations using Spring Boot and Vanilla JavaScript.

## 🚀 Project Overview

This is a demo project built for learning purposes. It shows how to connect a basic HTML/JS frontend to a Java Spring Boot backend using REST APIs. 

**Features:**
- **RESTful API:** Basic Controller, Service, and Repository layers.
- **Database Connection:** Connects to MySQL using Spring Data JPA.
- **Simple UI:** A basic HTML/CSS frontend to display, add, edit, and delete student records using the Fetch API.

## 🏗️ Architecture & Tech Stack Summary

**Backend (`/backend`):**
- **Java 21 & Spring Boot 3.x**
- **Spring Data JPA** (Hibernate) for robust ORM
- **MySQL Database**
- **Maven** build tool

**Frontend (`/frontend`):**
- **Vanilla JavaScript** (ES6+ async/await, Fetch API)
- **HTML5 & CSS3** (Custom properties, flexbox, CSS Grid)
- **FontAwesome** for scalable icons

## 📚 REST API Documentation

| Method | Endpoint | Description | Request Payload | Response Code |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/api/students` | Get all students | None | `200 OK` |
| `GET` | `/api/students/{id}` | Get student by ID | None | `200 OK` / `404 Not Found` |
| `GET` | `/api/students/roll/{rollNumber}` | Get student by Roll Number| None | `200 OK` / `404 Not Found` |
| `POST` | `/api/students` | Create new student | `{firstName, lastName, email, rollNumber, course, department}` | `201 Created` / `400 Bad Request` |
| `PUT` | `/api/students/{id}` | Update existing student | `{firstName, lastName, email, rollNumber, course, department}` | `200 OK` / `404 Not Found` |
| `DELETE`| `/api/students/{id}` | Delete a student | None | `204 No Content` / `404 Not Found` |

## 🛠️ Quickstart / Local Setup

### 1. Database Configuration
Ensure MySQL is running. Update `backend/src/main/resources/application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

### 2. Run the Backend
Navigate to the backend directory and run the Spring Boot application:
```bash
cd backend
./mvnw spring-boot:run
```
The API will be available at `http://localhost:8081/api/students`.

### 3. Run the Frontend
Open the `frontend/index.html` file in your browser, or use a tool like VS Code Live Server.

## 📖 Demo Notes
This project is meant for demonstration and learning purposes to understand how the frontend communicates with the backend.
