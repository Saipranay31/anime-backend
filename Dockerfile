# Stage 1: Build the application with Java 24 & Maven
FROM eclipse-temurin:24-jdk AS build
WORKDIR /app

# Copy Maven wrapper & pom.xml first
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code and build the JAR
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the Spring Boot JAR
FROM eclipse-temurin:24-jre
WORKDIR /app

COPY --from=build /app/target/catalog-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
