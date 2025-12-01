# Usar la imagen oficial de OpenJDK 17 como base
FROM openjdk:17-jdk-slim AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias con Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código fuente del proyecto
COPY src /app/src

# Compilar y empaquetar la aplicación en un archivo JAR ejecutable
RUN mvn clean package -DskipTests

# Usar una imagen base de OpenJDK 17 para la ejecución
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR generado desde el paso de construcción
COPY --from=build /app/target/sgi-0.0.1-SNAPSHOT.jar /app/sgi.jar

# Exponer el puerto en el que la aplicación Spring Boot corre (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/sgi.jar"]
