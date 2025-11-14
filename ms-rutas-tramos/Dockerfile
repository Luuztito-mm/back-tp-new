FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos el jar que generaste con Maven
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
