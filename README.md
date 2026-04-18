# BestBuy Product Service

A Java Spring Boot service that manages the product catalog. Supports full CRUD operations and is consumed by both the store front and store admin frontends.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/products` | List all products (optional `?category=` filter) |
| GET | `/products/{id}` | Get a single product |
| POST | `/products` | Create a product |
| PUT | `/products/{id}` | Update a product |
| DELETE | `/products/{id}` | Delete a product |
| GET | `/actuator/health` | Health check |

## Setup

```bash
./mvnw spring-boot:run
```

Service runs on port `8080` by default.

## Docker

```bash
docker build -t bestbuy-product-service .
docker run -p 8080:8080 bestbuy-product-service
```
