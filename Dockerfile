# Etapa de construcción
FROM eclipse-temurin:17-jdk-alpine AS build

# Directorio de trabajo
WORKDIR /app

# Copiar pom.xml y descargar las dependencias
COPY pom.xml .
RUN ./mvnw dependency:go-offline || mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar y generar el jar
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR creado
COPY --from=build /app/target/sgi-0.0.1-SNAPSHOT.jar /app/sgi.jar

# Puerto de Spring Boot
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "/app/sgi.jar"]
