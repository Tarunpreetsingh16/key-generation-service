# Use an official Maven image as the base image
FROM maven:3.8.5-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files to the container
COPY pom.xml ./
COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a minimal Java runtime image to reduce image size
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy only the built jar file from the previous stage
COPY --from=build /app/target/key-generation-service*.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
