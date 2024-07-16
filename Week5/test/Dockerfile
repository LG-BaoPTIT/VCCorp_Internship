
FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:8-jre-slim
EXPOSE 9090
COPY --from=build /app/target/devops-integration.jar devops-integration.jar
ENTRYPOINT ["java", "-jar", "/devops-integration.jar"]

