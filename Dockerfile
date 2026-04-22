# Use Java 17
FROM openjdk:17-jdk-slim

# Copy jar
COPY target/IssueTracker-0.0.1-SNAPSHOT.jar app.jar

# Run app
ENTRYPOINT ["java", "-jar", "/app.jar"]