# === Stage 1: Build the application ===
# Use a base image with Gradle and JDK
FROM gradle:8.0.1-jdk17-alpine AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle build files to leverage caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build the application, skipping tests to speed up the build
RUN ./gradlew clean build -x test

# === Stage 2: Create the final production image ===
# Use a lean base image for the final runtime
FROM openjdk:17-jdk-slim

# Set the working directory for the final application
WORKDIR /app

# Copy the generated JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port your application will run on
EXPOSE 8080

# The command to run your application using the built JAR
ENTRYPOINT ["java", "-jar", "app.jar"]