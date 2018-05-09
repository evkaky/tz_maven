FROM openjdk:8-jdk-alpine
COPY target/tz-1.0-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["/usr/bin/java", "-jar", "/app.jar"]