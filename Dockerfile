# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Cachea dependencias separado del código fuente
# Solo se re-descarga si cambia el pom.xml
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]