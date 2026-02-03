# 🛒 SB-Ecommerce – Spring Boot E‑Commerce Backend

A **production‑style E‑commerce backend application** built using **Spring Boot**, following clean architecture, secure configuration management, and industry best practices. This project is designed to be **GitHub‑ready, interview‑ready, and scalable**, making it suitable for **SDE Intern / Junior Backend / New‑Grad roles**.

---

## 📌 Key Highlights 
* 🔐 JWT‑based authentication & authorization
* 🗄️ MySQL + JPA/Hibernate integration
* 🧱 Clean layered architecture (Controller → Service → Repository)
* 📄 Clear documentation & run instructions

---

## 🚀 Features

* User registration & login
* Role‑based access control (Admin / User)
* Product & category management
* Secure REST APIs
* Stateless authentication using JWT
* Centralized exception handling
* Configurable logging for debugging

---

## 🛠 Tech Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Security:** Spring Security, JWT
* **Database:** MySQL
* **ORM:** Spring Data JPA (Hibernate)
* **Build Tool:** Maven
* **Version Control:** Git & GitHub

---

## 🧱 Architecture Overview

```text
Client (Postman / Frontend)
        ↓
Controllers  →  Services  →  Repositories  →  Database
        ↓
 Spring Security + JWT Filter Chain
```

---

### ▶ Local Setup Steps

1. Copy the config template:

   ```bash
   cp src/main/resources/application-example.properties src/main/resources/application.properties
   ```
2. Update `application.properties` with:

   * Database username & password
   * JWT secret
   * Admin credentials
3. Create MySQL database:

   ```sql
   CREATE DATABASE ecommerce;
   ```

---

## ▶ How to Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8080
```

---

## 🔐 Security Design

* Stateless authentication using **JWT tokens**
* Password encryption with **BCrypt**
* Role‑based authorization using Spring Security
* Custom authentication & authorization filters

---

## 📡 Sample API Endpoints

| Method | Endpoint             | Description              |
| ------ | -------------------- | ------------------------ |
| POST   | `/api/auth/register` | User registration        |
| POST   | `/api/auth/login`    | User login               |
| GET    | `/api/products`      | Fetch all products       |
| POST   | `/api/products`      | Add product (Admin only) |

---

## 🧪 Logging & Debugging

Logging levels are configurable for:

* Spring Framework
* Hibernate SQL
* Spring Security

(Default logging is set to `INFO` for GitHub safety.)

---


## 🚫 What This Repo Intentionally Excludes

* Database passwords
* JWT secret keys
* `.env` files
* IDE & build artifacts

(These are ignored via `.gitignore`.)

---

## ⭐ Future Enhancements

* Docker & Docker Compose
* Redis caching
* Swagger / OpenAPI documentation
* API Gateway integration
* Microservices decomposition

---

## 👤 Author

**Yash Bhupendrabhai Ajmeri**
Master’s in Applied Computer Science – Concordia University
Backend Developer | Java | Spring Boot

---

> 💡 This repository is designed to reflect **real‑world backend engineering practices**, not just a demo project.
