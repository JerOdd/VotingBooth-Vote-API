# ---- Stage 1: Build the app ----
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy everything into the container
COPY pom.xml .
COPY vote-base/pom.xml vote-base/pom.xml
COPY vote-api/pom.xml vote-api/pom.xml
COPY vote-listener/pom.xml vote-listener/pom.xml

# Build the app (produces the JAR)
RUN mvn -B -q -pl vote-api -am dependency:go-offline

# Copy source code
COPY vote-base vote-base
COPY vote-api vote-api

# Build module
RUN mvn -B -q -pl vote-api -am package -DskipTests

# ---- Stage 2: Run the app ----
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built JAR from the previous stage (adjust name if needed)
COPY --from=build /app/vote-api/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]