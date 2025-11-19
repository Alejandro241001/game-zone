# 1) Imagen base con Java 17 (la que usa Maven/Spring Boot por defecto)
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Dar permiso de ejecución al maven wrapper
RUN chmod +x mvnw

# Descargar dependencias y construir el JAR
RUN ./mvnw clean package -DskipTests

# 2) Segunda etapa: imagen final más ligera
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar el JAR construido en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Ejecutar el jar
ENTRYPOINT ["java","-jar","app.jar"]
