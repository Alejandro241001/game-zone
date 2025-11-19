# Etapa de build: Java 21
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Imagen final (runtime)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Declaramos argumentos para recibir variables desde Render
ARG DB_URL
ARG DB_USER
ARG DB_PASSWORD
ARG DB_DRIVER
ARG DB_PORT
ARG DB_NAME
ARG UPLOAD_PATH
ARG CORS_ALLOWED_ORIGINS
ARG JWT_SECRET
ARG JWT_KEYSTORE_PASSWORD
ARG JWT_KEYSTORE_ALIAS
ARG JWT_KEYSTORE_PATH

# Exportamos las variables dentro del contenedor
ENV DB_URL=$DB_URL \
    DB_USER=$DB_USER \
    DB_PASSWORD=$DB_PASSWORD \
    DB_DRIVER=$DB_DRIVER \
    DB_PORT=$DB_PORT \
    DB_NAME=$DB_NAME \
    UPLOAD_PATH=$UPLOAD_PATH \
    CORS_ALLOWED_ORIGINS=$CORS_ALLOWED_ORIGINS \
    JWT_SECRET=$JWT_SECRET \
    JWT_KEYSTORE_PASSWORD=$JWT_KEYSTORE_PASSWORD \
    JWT_KEYSTORE_ALIAS=$JWT_KEYSTORE_ALIAS \
    JWT_KEYSTORE_PATH=$JWT_KEYSTORE_PATH

# Copiamos el jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
