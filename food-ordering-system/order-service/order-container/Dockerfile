# Base image
FROM openjdk:17-slim

# Metadata
LABEL maintainer="shvm.cloud"

# Set working directory inside container
WORKDIR /app

# Copy built jar file into the container
COPY target/order-container-1.0-SNAPSHOT.jar app.jar

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
