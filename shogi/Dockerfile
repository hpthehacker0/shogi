# ==========================================
# Stage 1: Build the Application
# ==========================================
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give execution permission to the Maven wrapper
RUN chmod +x ./mvnw

# Download dependencies (this step is cached to speed up future builds)
RUN ./mvnw dependency:go-offline

# Copy the actual source code
COPY src src

# Package the application (skip tests to make deployment faster)
RUN ./mvnw clean package -DskipTests

# ==========================================
# Stage 2: Run the Application
# ==========================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Expose port 8080 for the Spring Boot web server
EXPOSE 8080

# Copy the built .jar file from the builder stage
# (Using a wildcard *.jar so we don't have to hardcode the version number!)
COPY --from=builder /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]