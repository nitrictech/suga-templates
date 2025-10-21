# Hello World API Server (Java)

This project contains a Hello World API server built with Java and Spring Boot framework.

## Features

- RESTful API endpoints for Hello World functionality
- Integration with Suga to manage infrastructure
- Docker containerization
- Logging configuration with Logback

## Files Structure

- `pom.xml` - Maven build configuration with Spring Boot dependencies
- `src/main/java/com/example/helloworldapi/HelloWorldApiApplication.java` - Main Spring Boot application
- `src/main/java/com/example/helloworldapi/controller/HelloWorldController.java` - REST controller
- `src/main/resources/application.properties` - Application configuration
- `src/main/resources/logback.xml` - Logging configuration
- `Dockerfile` - Multi-stage Docker build configuration
- `.dockerignore` - Docker ignore patterns

## Prerequisites

- Java 8 or later
- Maven 3.6 or later (or use Maven wrapper)
- Internet connection for downloading dependencies

## To run locally

To run the application locally using the Suga development environment:

```bash
cd java-springboot
suga dev
```

This command will start the Spring Boot server on port 4000.

Alternatively, you can run it directly using Maven:

```bash
cd java-springboot
./mvnw spring-boot:run
```

Or using Maven directly:
```bash
cd java-springboot
mvn spring-boot:run
```

## To build

```bash
cd java-springboot
./mvnw clean install
```

## API Endpoints

- `GET /api/hello` - Returns "Hello, World!"
- `GET /api/hello/name?name=YourName` - Returns "Hello, YourName!" and logs the name to S3 bucket
- `GET /api/` - Returns welcome message with available endpoints
- `GET /api/logs` - Returns logged user names from S3 bucket

## Testing the API

Once the server is running (either on port 4000 directly or via the Suga Load Balancer on port 3000), you can test the endpoints using:

### curl

```bash
# Direct access (if not using Suga dev)
curl http://localhost:4000/api/hello
curl "http://localhost:4000/api/hello/name?name=John"
curl http://localhost:4000/api/logs

# Via Suga Load Balancer
curl http://localhost:3000/api/hello
curl "http://localhost:3000/api/hello/name?name=John"
curl http://localhost:3000/api/logs
```

## S3 Bucket Logging Feature

The server includes S3 bucket logging functionality using the Suga client:

- When users access `/api/hello/name?name=YourName` with a custom name (not "World"), the server logs the name to an S3 bucket
- Each entry includes a timestamp and the user's name
- The log file is stored as `user_names.txt` in the configured S3 bucket
- Example log entry: `[2025-10-20 17:52:30] User name: John`
- Access logged entries via the `/api/logs` endpoint

## Technology Stack

- **Java** - Popular, high-performance programming language
- **Spring Boot** - Framework for building production-ready Spring applications
- **Maven** - Build automation tool
- **Logback** - Logging framework
- **Suga Client** - Infrastructure Orchestrator
- **Docker** - Containerization

## Notes

- The server runs on port 4000 by default (configurable via PORT environment variable)
- The loadbalancer locally uses port 3000 and forwards to the server as needed.
- Spring Boot provides extensive features for rapid application development
- User names are only logged when they differ from the default "World" value