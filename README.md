# E-commerce Pricing Service

This project is a Spring Boot microservice that provides product price queries for an e-commerce platform.  
It follows **CQRS architecture** with separate command and query responsibilities.

- **Database**: H2 (in-memory)
- **Messaging**: Apache Kafka
- **API**: REST (Spring Web)
- **Architecture**: Hexagonal (Ports & Adapters)

## Features
- Query product prices based on brand, product, and application date
- Support for command operations (e.g., price insert/update)
- Publish domain events to Kafka for asynchronous processing

## Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA (H2)
- Spring Kafka
- Spring Validation & Actuator
- JUnit & Spring Boot Test

## Running the project
```bash
mvn spring-boot:run
