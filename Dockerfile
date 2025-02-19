FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV DB_NAME=voltoja \
    DB_USER=root \
    DB_PASS=root \
    PORT=3003 \
    JWT_SCREET=pimenta

EXPOSE 3003

ENTRYPOINT ["java", "-jar", "app.jar"]
