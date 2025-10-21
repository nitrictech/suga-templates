# Multi-stage Docker build for Kotlin Ktor application

# Build stage
FROM gradle:8.5-jdk8 AS build

# Set working directory
WORKDIR /app

# Copy Gradle configuration files
COPY build.gradle.kts gradle.properties ./

# Download dependencies (this layer will be cached if build files don't change)
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

# Build the application
RUN gradle build --no-daemon

# Runtime stage
FROM openjdk:8-jre-slim

# Set working directory
WORKDIR /app

# Create a non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the built JAR from build stage
COPY --from=build /app/build/libs/kotlin-ktor-1.0.0-all.jar app.jar

# Change ownership to appuser
RUN chown -R appuser:appuser /app
USER appuser

# Expose port 8080
EXPOSE 8080

# Set JVM options for containerized environment
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check - using a simple endpoint
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/ || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]