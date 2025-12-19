# 💰 Expense Tracker REST API

A clean, production-ready Expense Tracker REST API built with Spring Boot to manage, filter, sort, and analyze personal expenses.

## 📋 Table of Contents

- [Problem Statement](#-problem-statement)
- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Data Model](#-data-model)
- [API Endpoints](#-api-endpoints)
- [Getting Started](#-getting-started)
- [Usage Examples](#-usage-examples)
- [Error Handling](#-error-handling)
- [Learning Outcomes](#-learning-outcomes)
- [Contributing](#-contributing)
- [Author](#-author)

---

## 🎯 Problem Statement

Build a REST API that allows users to:
- Record daily expenses with detailed information
- Filter and sort expenses using flexible query parameters
- Generate meaningful summaries such as category-wise and monthly spending analytics

This project focuses on **data-driven backend development**, demonstrating advanced filtering, sorting, and aggregation capabilities beyond simple CRUD operations.

---

## ✨ Features

- ✅ **Full CRUD Operations** - Create, Read, Update, Delete expenses
- 🔍 **Advanced Filtering** - Filter by date range, category, and amount
- 🔄 **Flexible Sorting** - Sort by date or amount (ascending/descending)
- 📊 **Category-wise Summary** - Analyze spending by category
- 📅 **Monthly Summary** - Track expenses month-by-month
- ✔️ **Input Validation** - Proper validation with meaningful error messages
- 🏗️ **Clean Architecture** - Modular and maintainable code structure

---

## 🏛️ Architecture

```
┌─────────────────────────────────┐
│  Client (Postman/Browser/App)  │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│  Expense Tracker REST API       │
│  (Spring Boot)                  │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│  MySQL Database                 │
└─────────────────────────────────┘
```

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 21** | Programming Language |
| **Spring Boot 3.x** | Application Framework |
| **Spring Data JPA** | Data Access Layer |
| **Hibernate** | ORM Framework |
| **MySQL** | Database |
| **Maven** | Build Tool |
| **REST API** | API Design Pattern |

---

## 📊 Data Model

### Expense Entity

| Field | Type | Description |
|-------|------|-------------|
| `id` | Long | Unique expense identifier (Primary Key) |
| `amount` | Double | Expense amount (must be positive) |
| `category` | Enum | FOOD, TRANSPORT, ENTERTAINMENT, BILLS, SHOPPING, OTHER |
| `date` | LocalDate | Expense date (YYYY-MM-DD) |
| `description` | String | Optional description/note |

---

## 🔌 API Endpoints

### 1️⃣ Create Expense

```http
POST /expenses
Content-Type: application/json

{
  "amount": 250.50,
  "category": "FOOD",
  "date": "2025-01-10",
  "description": "Lunch at restaurant"
}
```

**Response: `201 Created`**
```json
{
  "id": 1,
  "amount": 250.50,
  "category": "FOOD",
  "date": "2025-01-10",
  "description": "Lunch at restaurant"
}
```

---

### 2️⃣ Get Expense by ID

```http
GET /expenses/{id}
```

**Response: `200 OK`**
```json
{
  "id": 1,
  "amount": 250.50,
  "category": "FOOD",
  "date": "2025-01-10",
  "description": "Lunch at restaurant"
}
```

**Error: `404 Not Found`**
```json
{
  "error": "Expense not found with id: 5"
}
```

---

### 3️⃣ Update Expense

```http
PUT /expenses/{id}
Content-Type: application/json

{
  "amount": 300.00,
  "category": "TRANSPORT",
  "date": "2025-01-11",
  "description": "Bus fare"
}
```

**Response: `200 OK`** (Updated expense object)

---

### 4️⃣ Delete Expense

```http
DELETE /expenses/{id}
```

**Response: `204 No Content`**

---

### 5️⃣ Get All Expenses with Filtering & Sorting

```http
GET /expenses?category=FOOD&minAmount=100&sortBy=amount&order=desc
```

#### Query Parameters

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `startDate` | String | Filter by start date | `2025-01-01` |
| `endDate` | String | Filter by end date | `2025-01-31` |
| `category` | String | Filter by category | `FOOD` |
| `minAmount` | Double | Minimum amount | `100.0` |
| `maxAmount` | Double | Maximum amount | `1000.0` |
| `sortBy` | String | Sort field (`date` or `amount`) | `amount` |
| `order` | String | Sort order (`asc` or `desc`) | `desc` |

**Example Response: `200 OK`**
```json
[
  {
    "id": 3,
    "amount": 450.00,
    "category": "FOOD",
    "date": "2025-01-15",
    "description": "Dinner"
  },
  {
    "id": 1,
    "amount": 250.50,
    "category": "FOOD",
    "date": "2025-01-10",
    "description": "Lunch"
  }
]
```

---

### 6️⃣ Category-wise Summary

```http
GET /expenses/summary/by-category
```

**Response: `200 OK`**
```json
{
  "FOOD": 550.75,
  "TRANSPORT": 120.50,
  "SHOPPING": 300.00,
  "BILLS": 450.00
}
```

---

### 7️⃣ Monthly Summary (Current Year)

```http
GET /expenses/summary/monthly
```

**Response: `200 OK`**
```json
{
  "JANUARY": 1200.00,
  "FEBRUARY": 950.00,
  "MARCH": 1500.00
}
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- Postman (optional, for testing)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/expense-tracker-api.git
   cd expense-tracker-api
   ```

2. **Create MySQL Database**
   ```sql
   CREATE DATABASE expense_tracker_db;
   ```

3. **Configure Database Connection**

   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   ```

4. **Build the Project**
   ```bash
   mvn clean install
   ```

5. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

6. **Access the API**
   ```
   http://localhost:8080
   ```

---

## 💡 Usage Examples

### Example 1: Create and Track Daily Expenses

```bash
# Create a food expense
curl -X POST http://localhost:8080/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 45.00,
    "category": "FOOD",
    "date": "2025-01-15",
    "description": "Breakfast"
  }'
```

### Example 2: Find All Food Expenses Over $100

```bash
curl "http://localhost:8080/expenses?category=FOOD&minAmount=100"
```

### Example 3: Get Expenses for January Sorted by Amount

```bash
curl "http://localhost:8080/expenses?startDate=2025-01-01&endDate=2025-01-31&sortBy=amount&order=desc"
```

### Example 4: Analyze Spending by Category

```bash
curl http://localhost:8080/expenses/summary/by-category
```

---

## ⚠️ Error Handling

The API returns appropriate HTTP status codes and error messages:

| Status Code | Description | Example Response |
|-------------|-------------|------------------|
| `400 Bad Request` | Validation error or invalid input | `{"amount": "Amount must be positive"}` |
| `404 Not Found` | Resource not found | `{"error": "Expense not found with id: 5"}` |
| `500 Internal Server Error` | Server error | `{"error": "Internal Server Error"}` |

---

## 📚 Learning Outcomes

This project demonstrates:

- ✅ Designing RESTful APIs beyond basic CRUD operations
- ✅ Implementing complex filtering and sorting with query parameters
- ✅ Writing efficient aggregation queries using SQL GROUP BY and SUM
- ✅ Handling invalid input gracefully with proper validation
- ✅ Structuring clean, maintainable backend code
- ✅ Following REST API best practices and conventions

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**CHANDRA SEKHAR**

Backend Developer | Spring Boot & REST API Enthusiast
