# Hello World API Server (Kotlin)

This project contains a Hello World API server built with Kotlin and Ktor framework.

## Features

- RESTful API endpoints for Hello World functionality
- Integration with Suga to manage infrastructure
- Docker containerization
- Logging configuration with Logback

## Files Structure

- `build.gradle.kts` - Gradle build configuration with Kotlin DSL
- `gradle.properties` - Gradle properties with version definitions
- `src/main/kotlin/com/example/helloworldapi/Application.kt` - Main Ktor application
- `src/main/resources/application.conf` - Application configuration
- `src/main/resources/logback.xml` - Logging configuration
- `Dockerfile` - Multi-stage Docker build configuration
- `.dockerignore` - Docker ignore patterns

## Prerequisites

- Java 8 or later
- Gradle 8.4 or later (or use Gradle wrapper)
- Internet connection for downloading dependencies

## To run locally

To run the application locally using the Suga development environment:

```bash
cd kotlin-ktor
suga dev
```

This command will start the Ktor server on port 4000.

Alternatively, you can run it directly using Gradle:

```bash
cd kotlin-ktor
./gradlew run
```

Or using Gradle directly:
```bash
cd kotlin-ktor
gradle run
```

## To build

```bash
cd kotlin-ktor
./gradlew build
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

- **Kotlin** - Modern JVM language with concise syntax
- **Ktor** - Asynchronous framework for building connected applications
- **Gradle** - Build automation tool with Kotlin DSL
- **Logback** - Logging framework
- **Suga Client** - Infrastructure Orchestrator
- **Docker** - Containerization

## Notes

- The server runs on port 4000 by default (configurable via PORT environment variable)
- The loadbalancer locally uses port 3000 and forwards to the server as needed.
- Ktor provides built-in support for asynchronous request handling
- User names are only logged when they differ from the default "World" value
