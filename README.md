# QuoApp - Backend

> 🔧 **Status**: Deployed and currently in development/testing phase.

## 👤 Preloaded Users for Testing

Use the following users to authenticate and test the API:

| Username | Password      |
|----------|---------------|
| alice    | `password123` |
| bob      | `securePass!` |
| eve      | `mySecret#`   |

---

## 🚀 Base URL

https://resulting-rattlesnake-matiasnm-db6f89e2.koyeb.app


## 📚 API Documentation

Full Swagger UI is available here:  
[Swagger UI](https://resulting-rattlesnake-matiasnm-db6f89e2.koyeb.app/swagger-ui/index.html#/)

---

## ✅ Tech Stack

- Java 17+
- Spring Boot
- Spring Security (JWT)
- PostgreSQL (hosted on [Neon](https://neon.tech))
- RESTful APIs with OpenAPI 3.1
- Deployed on [Koyeb](https://koyeb.com)

---

## 🔐 Authentication

All protected endpoints require a **Bearer JWT token**.  
Use `/login` to authenticate and receive your token.

---

## 📦 Main API Endpoints

### 🔑 Auth

- `POST /login` – Authenticate and receive JWT token.

### 👤 User

- `GET /users/me`
- `PUT /users/update`
- `PUT /users/update-avatar?avatar={url}`
- `DELETE /users/deactivate`

### 💸 Portfolio

- `POST /portfolio/trade`
- `GET /portfolio/overview`
- `GET /portfolio/performance`
- `GET /portfolio/history`
- `GET /portfolio/assets/{asset}`

### 📈 Market Data

- `GET /coins`
- `GET /rates`

---

## 🗄️ Database Schema (PostgreSQL on Neon)

### Table: `users`

| Column   | Type         | Constraints               |
|----------|--------------|---------------------------|
| id       | bigint       | PK, auto-increment        |
| username | varchar(255) | NOT NULL                  |
| mail     | varchar(255) | NOT NULL                  |
| phone    | varchar(255) |                           |
| password | varchar(255) | NOT NULL (BCrypt encoded) |
| avatar   | varchar(255) |                           |
| active   | boolean      |                           |

---

### Table: `portfolios`

| Column   | Type    | Constraints                  |
|----------|---------|------------------------------|
| id       | bigint  | PK, auto-increment           |
| balance  | double  |                              |
| user_id  | bigint  | UNIQUE, FK → users(id)       |

---

### Table: `transactions`

| Column              | Type         | Constraints                        |
|---------------------|--------------|------------------------------------|
| id                  | bigint       | PK, auto-increment                 |
| price_at_transaction| double       | NOT NULL                           |
| quantity            | double       | NOT NULL                           |
| timestamp           | timestamp    | NOT NULL                           |
| type                | varchar(255) | CHECK ["BUY", "SELL"]              |
| asset               | varchar(255) | CHECK ["BTC", ..., "DOT"]          |
| portfolio_id        | bigint       | FK → portfolios(id)                |
| exchange_rate_id    | bigint       | FK → exchange_rates(id), nullable |

---

### Table: `exchange_rates`

| Column | Type | Constraints        |
|--------|------|--------------------|
| id     | bigint | PK, auto-increment |
| date   | date   | UNIQUE, NOT NULL  |

---

### Table: `exchange_rate_details`

| Column           | Type           | Constraints                                   |
|------------------|----------------|-----------------------------------------------|
| exchange_rate_id | bigint         | FK → exchange_rates(id), part of composite PK |
| rates_key        | varchar(255)   | ENUM: ARS, BRL, MXN, COP (part of PK)         |
| rate             | double         | NOT NULL                                      |

---

## 🧭 Entity Relationship Diagram (ERD)

```text
users
│
├──< portfolios (1:1)
      │
      ├──< transactions (1:N)
            └─── exchange_rates (0..1:1)
                  └──< exchange_rate_details (1:N)
