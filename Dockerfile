FROM eclipse-temurin:24-jdk-alpine AS builder
WORKDIR /app

RUN apk add --no-cache maven

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app

COPY --from=builder /app/target/monobank-mcp-server-*.jar ./monobank-mcp-server.jar

CMD ["java", "-jar", "monobank-mcp-server.jar"]