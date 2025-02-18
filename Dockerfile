FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
