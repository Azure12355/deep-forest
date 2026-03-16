FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/deep-forest-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8101

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
