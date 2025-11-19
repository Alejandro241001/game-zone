# Etapa de build: Java 21
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Damos permisos al Maven Wrapper
RUN chmod +x mvnw

# Compilamos el proyecto
RUN ./mvnw clean package -DskipTests

# Imagen final (runtime)
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiamos el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
