# ---- Stage 1: Build ----
# Use a Maven image with JDK 21 to compile and package the application.
# This is a multi-stage build — the build tools never end up in the final image.
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

# Copy pom.xml first and download dependencies separately.
# Docker caches each layer. If pom.xml hasn't changed, this layer is cached
# and the dependency download is skipped on subsequent builds — much faster CI.
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Now copy source code and build the JAR
COPY src ./src
RUN mvn package -DskipTests -q

# ---- Stage 2: Run ----
# Use a minimal JRE image — no Maven, no JDK, no source code.
# eclipse-temurin:21-jre-alpine is ~180MB vs ~500MB for the build image.
# Smaller images = faster pulls, smaller attack surface, lower storage costs.
FROM eclipse-temurin:25-jre
WORKDIR /app

# Create a non-root user — running as root in a container is a security risk
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Use exec form (array syntax) not shell form — exec form doesn't spawn a shell,
# which means SIGTERM from Kubernetes reaches the JVM directly for graceful shutdown
ENTRYPOINT ["java", "-jar", "app.jar"]