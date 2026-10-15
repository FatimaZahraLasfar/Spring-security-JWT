# Spring Boot Security with JWT Authentication

This project is a complete demonstration of implementing token-based authentication in a Spring Boot application using JSON Web Tokens (JWT) and Spring Security. It provides a RESTful API for user registration and login, returning a JWT that can be used to access protected endpoints.

## Features

*   **User Registration:** Endpoint to create new user accounts.
*   **User Authentication:** Endpoint for users to log in and receive a JWT.
*   **JWT-based Authorization:** Secures API endpoints, requiring a valid JWT in the `Authorization` header.
*   **Custom User Details Service:** Implements Spring Security's `UserDetailsService` to load user-specific data from the database.
*   **In-Memory Database:** Utilizes H2 for a lightweight, zero-configuration setup perfect for development and demonstration.
*   **Stateless Security:** Configured for stateless session management, ideal for REST APIs.

## Technologies Used

*   **Java 17**
*   **Spring Boot**
*   **Spring Security**
*   **Spring Data JPA**
*   **JJWT (Java JWT)** library for creating and parsing JWTs.
*   **H2 Database**
*   **Lombok**
*   **Maven**

## Getting Started

Follow these instructions to get the project up and running on your local machine.

### Prerequisites

*   JDK 17 or a later version.
*   Apache Maven.

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/FatimaZahraLasfar/Spring-security-JWT.git
    cd Spring-security-JWT
    ```

2.  **Build and run the project using the Maven wrapper:**
    *   On macOS/Linux:
        ```bash
        ./mvnw spring-boot:run
        ```
    *   On Windows:
        ```bash
        mvnw.cmd spring-boot:run
        ```

3.  The application will start on `http://localhost:8080`.

### Configuration

The primary application configuration is located in `src/main/resources/application.properties`.

*   `jwt.secret`: A Base64 encoded secret key used for signing and verifying JWTs.
*   `jwt.expiration`: The token's validity period in milliseconds (default is `3600000`, which is 1 hour).
*   `spring.datasource.*`: Configuration for the H2 in-memory database.
*   `spring.h2.console.*`: Settings to enable and configure the H2 database console.

## API Endpoints

The base path for the authentication API is `/api/auth`.

### 1. Register a New User

Creates a new user account and returns a JWT upon successful registration.
<img width="892" height="697" alt="Screenshot 2026-10-10 200409" src="https://github.com/user-attachments/assets/5eb8de10-e354-4629-943f-680c1c027281" />


*   **Endpoint:** `POST /api/auth/register`
*   **Request Body:**
    ```json
    {
      "email": "test@example.com",
      "password": "password123"
    }
    ```
*   **Success Response (`200 OK`):**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNj...<rest_of_token>"
    }
    ```

### 2. Authenticate a User (Login)
<img width="887" height="680" alt="Screenshot 2026-10-10 200425" src="https://github.com/user-attachments/assets/bbdf1474-0eec-410d-9de6-fae6b6fcf23b" />


Authenticates a user with their email and password and returns a JWT.

*   **Endpoint:** `POST /api/auth/login`
*   **Request Body:**
    ```json
    {
      "email": "test@example.com",
      "password": "password123"
    }
    ```
*   **Success Response (`200 OK`):**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNj...<rest_of_token>"
    }
    ```

### 3. Access a Protected Resource
<img width="891" height="657" alt="Screenshot 2026-10-10 200515" src="https://github.com/user-attachments/assets/91371745-8144-4c5e-8583-302b37e7272f" />


An example of a protected endpoint that requires a valid JWT for access.

*   **Endpoint:** `GET /api/auth/profile`
*   **Headers:** The request must include the JWT in the `Authorization` header with the `Bearer` prefix.
    ```
    Authorization: Bearer <your_jwt_token>
    ```
*   **Success Response (`200 OK`):**
    ```
    "Accès autorisé ! Bienvenue dans votre espace sécurisé."
    ```
*   **Error Response (`403 Forbidden`):** If the token is missing, invalid, or expired.

## Accessing the H2 Database Console

This project uses an in-memory H2 database, which is reset every time the application starts. You can access its web console to view the data.

1.  Navigate to `http://localhost:8080/h2-console` in your browser.
2.  Use the following settings from `application.properties` to connect:
    *   **JDBC URL:** `jdbc:h2:mem:securitydbdemo`
    *   **User Name:** `sa`
    *   **Password:** (leave this field blank)

Click **Connect** to access the database schema and run queries on the `users` table.
