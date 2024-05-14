FROM eclipse-temurin:22.0.1_8-jdk-ubi9-minimal as build
WORKDIR /app
COPY . /app
VOLUME /app/target /root/.m2/
CMD ["./mvnw", "install"]