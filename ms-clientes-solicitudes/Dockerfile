FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ARG JAR_FILE=target/ms-clientes-solicitudes-1.0.0.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
##*.jar en el tarjet/*.jar