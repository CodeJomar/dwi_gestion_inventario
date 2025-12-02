# Etapa de construcción (build)
FROM maven:3.9-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilar y empacar el JAR
RUN mvn -B -DskipTests clean package

# Etapa final (runtime)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR construido
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (por defecto 8080)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
