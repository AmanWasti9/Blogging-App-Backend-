FROM maven:3-eclipse-temurin-22 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22.0.2_9-jre-alpine
COPY --from=build /target/*.jar demo.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","demo.jar"]