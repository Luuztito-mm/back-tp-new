# Imagen base con Java 21 (runtime liviano)
FROM eclipse-temurin:21-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR construido
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Exponemos el puerto del micro (el mismo que tengas en server.port)
EXPOSE 8082

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
