# Use an official OpenJDK 17 JDK slim image as the base
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Create a volume mount point at /tmp
VOLUME /tmp

# Copy all JAR files from the target directory into the container as app.jar
COPY target/*.jar /app/dedis.jar

# Specify the command to run on container startup
ENTRYPOINT ["java", "-jar", "/app/dedis.jar"]