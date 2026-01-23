#. Stage 1: Build the application
FROM gradle:8.6-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

#. Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar
COPY .env /app/.env

ENTRYPOINT ["java", "-jar", "/app/app.jar"]