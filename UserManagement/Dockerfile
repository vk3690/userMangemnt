FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/UserManagement-0.0.1-SNAPSHOT.jar UserManagement.jar

# Expose port 8080
EXPOSE 8093

# Run the application
ENTRYPOINT ["java", "-jar", "UserManagement.jar"]