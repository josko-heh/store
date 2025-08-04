# 🛒 Store

A simple product service built with Spring Boot, Java 21, and PostgreSQL. It supports creating and retrieving products, with automatic currency conversion from EUR to USD using the HNB exchange rate API.

---

### Prerequisites

- Java 21
- Maven
- PostgreSQL

---

## 🐘 Run PostgreSQL

Start a PostgreSQL container using Docker:

```
docker run --name my-postgres \
  -e POSTGRES_USER=myuser \
  -e POSTGRES_PASSWORD=mypassword \
  -e POSTGRES_DB=mydb \
  -p 5432:5432 \
  -d postgres
```

Update your `application.yml` to match the database credentials above if you modified them.

---

## ☕ Run the Application

Make sure you’re using Java 21, then start the app:

```
mvn spring-boot:run
```

---

## 📚 API Documentation

Swagger UI:  
http://localhost:30000/swagger-ui/index.html

---

## 📦 API Usage

### Create Product

```
curl -X POST "http://localhost:30000/products" \
  -H "Content-Type: application/json" \
  -d '{
    "code": "ABC1234566",
    "name": "Wireless Mouse",
    "priceEur": 29.99,
    "isAvailable": true
  }'
```

### Get Product by Code

```
curl "http://localhost:30000/products/ABC1234566"
```

### Get All Products

```
curl "http://localhost:30000/products"
```

---

## 🧠 Features

- Java 21 + Spring Boot 3
- PostgreSQL with JPA/Hibernate
- HNB exchange rate integration
- Caching with scheduled refresh
- OpenAPI/Swagger for documentation

---


## 🛠️ TODOs

- Add authentication
- Add integration tests
- Integrate CI/CD pipeline  
