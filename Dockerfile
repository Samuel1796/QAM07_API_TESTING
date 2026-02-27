# Use Maven with OpenJDK 17 as base image for modern Java support
FROM maven:3.9-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Set volume for test reports
VOLUME /app/target

# Run tests when container starts
CMD ["mvn", "clean", "test"]
