# 📚 Spring Boot Notes API

A RESTful API built with **Spring Boot**, **Spring Security 6**, **JWT**, and **MySQL** that allows users to register, log in, and securely manage their personal notes. It uses **DTOs (Data Transfer Objects)** to transfer data between application layers.

---

## 🚀 Technologies Used

- Java 17+
- Spring Boot
- Spring Security 6
- JWT (JSON Web Tokens)
- MySQL
- Maven
- DTOs for data transfer

---

## 🔐 Authentication

Authentication is handled using **JWT tokens**, which are generated upon login and required to access protected routes (`/api/**`).

---

## 📦 Endpoints

### 🛡️ Authentication

| Method | Route            | Description               |
|--------|------------------|---------------------------|
| POST   | `/auth/register` | Register a new user       |
| POST   | `/auth/login`    | Authenticate (log in)     |

> These routes return a JWT token that must be sent in the header when accessing protected routes:
> ```
> Authorization: Bearer <token>
> ```

---

### 👤 User Profile

| Method | Route                 | Description                    |
|--------|----------------------|--------------------------------|
| GET    | `/api/profile`       | Get user profile information   |
| DELETE | `/api/profile/remove`| Delete user account            |

---

### 📝 Notes

| Method | Route                      | Description                    |
|--------|----------------------------|--------------------------------|
| GET    | `/api/getNotes`           | Get all user notes             |
| GET    | `/api/getNote/{id}`       | Get a specific note by ID      |
| POST   | `/api/addNote`            | Create a new note              |
| PATCH  | `/api/update/{noteId}`    | Update an existing note        |
| DELETE | `/api/removeNote/{id}`    | Delete a note by its ID        |

---

## 🧱 Data Structure (DTOs)

> DTOs are used to receive and send data between the client and server, hiding internal models.

### 📄 Example: `NoteDto`

```json
{
  "title": "Note title",
  "content": "Note content"
}
