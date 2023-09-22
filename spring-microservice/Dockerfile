FROM maven:3.8.7-openjdk-18-slim AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package -DskipTests

FROM openjdk:22-slim

COPY --from=builder /app/target/*.jar /app.jar
WORKDIR /app
COPY opentel/opentelemetry-javaagent.jar /app
ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-jar", "/app.jar"]
EXPOSE 8080
