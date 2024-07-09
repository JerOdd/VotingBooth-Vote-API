FROM openjdk:17
ADD target/*.jar vote-api.jar
ENTRYPOINT ["java","-jar","/vote-api.jar"]
