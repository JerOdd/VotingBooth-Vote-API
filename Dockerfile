# ---- Stage 1: Build the app ----
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy everything into the container
COPY . .

# Build the app (produces the JAR)
RUN mvn -B -DskipTests clean package

# ---- Stage 2: Run the app ----
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from the previous stage (adjust name if needed)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]