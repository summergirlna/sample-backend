FROM eclipse-temurin:25-jdk AS build

WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY sample-core sample-core
COPY sample-backend sample-backend
COPY sample-loadtest sample-loadtest

RUN chmod +x mvnw
RUN ./mvnw -pl sample-backend -am clean package -DskipTests


FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /workspace/sample-backend/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]