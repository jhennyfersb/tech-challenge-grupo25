# Build stage - uses JDK for compilation
FROM eclipse-temurin:21-jdk AS build

# Copy the source code and pom.xml
COPY pom.xml .
COPY src ./src

# Install Maven and build the project
RUN apt-get update \
    && apt-get install -y maven \
    && mvn clean package -DskipTests

# Runtime stage - uses JRE for running the application
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build target/rede-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]