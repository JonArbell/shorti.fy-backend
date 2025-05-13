# Use an official OpenJDK runtime as a parent image
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the project files to the container
COPY . .

# Build the application, skipping tests
RUN mvn clean package -DskipTests

FROM openjdk:21

# Set the working directory in the container
WORKDIR /app

COPY --from=build /app/target/shortify-backend-0.0.1-SNAPSHOT.jar  /app/shortify-backend-0.0.1-SNAPSHOT.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "shortify-backend-0.0.1-SNAPSHOT.jar"]
