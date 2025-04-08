FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/UserMonitor-0.0.1-SNAPSHOT.jar UserMonitor.jar

# Expose port 8080
EXPOSE 8095

# Run the application
ENTRYPOINT ["java", "-jar", "UserMonitor.jar"]