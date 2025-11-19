# Imagen base con Java 21
FROM eclipse-temurin:21-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el .jar generado al contenedor
# Usamos un comodín para no depender del nombre exacto
ARG JAR_FILE=target/api-gateway-1.0.0.jar
COPY ${JAR_FILE} app.jar

# Exponemos el puerto 8080 (donde corre el gateway)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
