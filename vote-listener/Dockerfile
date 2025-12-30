# Stage 1: build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY vote-base/pom.xml vote-base/pom.xml
COPY vote-listener/pom.xml vote-listener/pom.xml
COPY vote-api/pom.xml vote-api/pom.xml
RUN mvn -B -q -pl vote-listener -am dependency:go-offline

COPY vote-base vote-base
COPY vote-listener vote-listener
RUN mvn -B -q -pl vote-listener -am package -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/vote-listener/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]