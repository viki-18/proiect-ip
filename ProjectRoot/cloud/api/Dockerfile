# Use an official OpenJDK runtime as a parent image
FROM openjdk:24-jdk-slim-bullseye
# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/api-0.0.1-SNAPSHOT.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]