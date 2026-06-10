# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

# Descarga dependencias primero (aprovecha la caché de capas en rebuilds)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Compila y empaqueta (sin tests para acelerar el deploy)
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app

# Usuario no-root (buena práctica de seguridad)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copia el jar ejecutable que genera Spring Boot
COPY --from=build --chown=spring:spring /app/target/*.jar app.jar

# Imágenes semilla / placeholders que la app espera en /app/uploads
COPY --chown=spring:spring uploads ./uploads

ENV JAVA_OPTS=""
EXPOSE 8080

# Render inyecta la variable PORT en runtime; Spring Boot escucha en ella
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar --server.port=${PORT:-8080}"]