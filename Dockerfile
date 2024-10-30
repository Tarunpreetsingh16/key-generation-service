# Use Maven image to build the project
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy all project files
COPY . .

# Build the project and package the JAR
RUN ./mvnw clean package -DskipTests

# Use a lightweight JDK to run the JAR
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/key-generation-service*.jar app.jar

# Expose the service port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]