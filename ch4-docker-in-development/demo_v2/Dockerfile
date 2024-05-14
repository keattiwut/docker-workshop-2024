FROM eclipse-temurin:22.0.1_8-jdk-ubi9-minimal as build
WORKDIR /app
VOLUME /root/.m2/
COPY mvnw mvnw
COPY .mvn .mvn
COPY src src
EXPOSE 8080
ENTRYPOINT [ "./mvnw" ]
CMD ["spring-boot:run"]