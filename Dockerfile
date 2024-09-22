# Use an official OpenJDK runtime as a parent image
FROM openjdk:20-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot jar file to the container
COPY target/*.jar app.jar

# Expose the port your application will run on (use environment variable for Render's dynamic port)
EXPOSE 8080

# Run the application, binding it to the port provided by Render's PORT environment variable
ENTRYPOINT ["java", "-jar", "app.jar"]

# Optionally add this in case you need to specify the port using environment variable
CMD ["--server.port=${PORT:-8080}"]
