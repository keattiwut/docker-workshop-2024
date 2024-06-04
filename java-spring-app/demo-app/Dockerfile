FROM maven:3.9.7-sapmachine-22 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install


FROM eclipse-temurin:22.0.1_8-jre-ubi9-minimal as runtime
COPY --from=build /app/target/demo-app-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar" ]
CMD [ "app.jar" ]