# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build artifact (jar file) to the working directory
COPY target/pos-order-service-0.0.1-SNAPSHOT.jar order-service.jar

# Expose the port the application runs on
EXPOSE 8761

# Run the jar file
ENTRYPOINT ["java", "-jar", "order-service.jar"]