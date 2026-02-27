# Use Maven with OpenJDK 11 as base image
FROM maven:3.8-openjdk-11

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
