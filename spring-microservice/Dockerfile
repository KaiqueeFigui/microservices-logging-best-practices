FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package -DskipTests

FROM openjdk:17

COPY --from=builder /app/target/*.jar /app.jar
WORKDIR /app
COPY opentel/opentelemetry-javaagent.jar /app
ENV SPRING_PROFILES_ACTIVE=DEV
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
