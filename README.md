# 💰 Expense Tracker REST API

A clean, production-ready **Expense Tracker REST API** built to manage, filter, sort, and analyze personal expenses.  
This project demonstrates **RESTful design, database modeling, query filtering, sorting, aggregation, and error handling**, aligned with real-world backend requirements.

---

## 🧩 Problem Statement

Build a REST API that allows users to:
- Record daily expenses
- Filter and sort expenses using query parameters
- Generate meaningful summaries such as **category-wise** and **monthly** spending

This task focuses on **data-driven backend development**, not just CRUD.

---

## 🏗️ Architecture Overview

```
Client (Postman / Browser / Frontend)
        │
        ▼
Expense Tracker REST API
        │
        ▼
Relational Database (MySQL / PostgreSQL / SQLite)
```

---

## ✨ Features Implemented

✔ Full CRUD operations on expenses  
✔ Advanced filtering by date, category, and amount  
✔ Sorting by date or amount (asc / desc)  
✔ Category-wise spending summary  
✔ Monthly spending summary  
✔ Input validation & proper HTTP status codes  
✔ Clean modular backend structure

---

## 📊 Data Model

### Expense Entity
| Field | Type | Description |
|------|-----|-------------|
| id | Long / UUID | Unique expense identifier |
| amount | Decimal | Expense amount |
| category | Enum | food, transport, entertainment, bills, shopping, other |
| date | Date | Expense date |

---

## 📡 API Endpoints

### 1️⃣ Create Expense
```
POST /expenses
```

**Request Body**
```json
{
  "amount": 250.50,
  "category": "food",
  "date": "2025-01-10"
}
```

**Response** `201 Created`
```json
{
  "id": 1,
  "amount": 250.50,
  "category": "food",
  "date": "2025-01-10"
}
```

---

### 2️⃣ Get Expense by ID
```
GET /expenses/{id}
```

**Response** `200 OK`
```json
{
  "id": 1,
  "amount": 250.50,
  "category": "food",
  "date": "2025-01-10"
}
```

**If not found** → `404 Not Found`

---

### 3️⃣ Update Expense
```
PUT /expenses/{id}
```

**Request Body**
```json
{
  "amount": 300,
  "category": "transport",
  "date": "2025-01-11"
}
```

---

### 4️⃣ Delete Expense
```
DELETE /expenses/{id}
```

**Response** → `204 No Content`

---

## 🔍 Filtering & Sorting

### Retrieve Expenses with Filters
```
GET /expenses
```

**Supported Query Parameters**
| Parameter | Description |
|---------|------------|
| start_date | Filter by start date |
| end_date | Filter by end date |
| category | Filter by category |
| min_amount | Minimum amount |
| max_amount | Maximum amount |
| sort_by | date or amount |
| order | asc or desc |

**Example**
```
GET /expenses?category=food&min_amount=100&sort_by=amount&order=desc
```

---

## 📈 Aggregation Endpoints

### Category-wise Summary
```
GET /expenses/summary/by-category
```

**Response**
```json
{
  "food": 550.75,
  "transport": 120.50,
  "shopping": 300
}
```

---

### Monthly Summary (Current Year)
```
GET /expenses/summary/monthly
```

**Response**
```json
{
  "January": 1200,
  "February": 950
}
```

---

## ⚠️ Error Handling

| Scenario | Status | Example |
|-------|-------|--------|
| Invalid date | 400 | `{"error": "Invalid date format"}` |
| Negative amount | 400 | `{"error": "Amount must be positive"}` |
| Not found | 404 | `{"error": "Expense not found"}` |

---

## 🛠️ Tech Stack

- **Java / Spring Boot** (API layer)
- **JPA / JDBC** (data access)
- **MySQL / PostgreSQL / SQLite** (database)
- **RESTful API principles**

---

## 🚀 How to Run

### 1️⃣ Configure Database
Update application properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/expense_db
spring.datasource.username=root
spring.datasource.password=password
```

### 2️⃣ Run Application
```
mvn spring-boot:run
```

API will be available at:
```
http://localhost:8080
```

---

## 🎯 Learning Outcomes

✔ Designing RESTful APIs beyond CRUD  
✔ Implementing complex filtering & sorting  
✔ Writing efficient aggregation queries  
✔ Handling invalid input gracefully  
✔ Structuring clean backend code

---

## 👤 Author

**CHANDRA SEKHAR**  
Backend Developer | API Design Enthusiast

---

⭐ If this project helped you learn backend fundamentals, give it a star!

