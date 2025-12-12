FROM maven:3.9.9-amazoncorretto-23-alpine AS build
WORKDIR /app
COPY ./pom.xml ./
RUN mvn dependency:go-offline
COPY . .
RUN mvn -q -DskipTests package

FROM azul/zulu-openjdk-alpine:23-jre-latest
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]