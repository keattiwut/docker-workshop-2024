
## Build with java

```bash
docker run -it --rm \
    -v "$(pwd)":/usr/src/mymaven \
    -w /usr/src/mymaven maven:3.9.7-eclipse-temurin-22-alpine \
    mvn clean install
```


## Build Java Springboot 

```Dockerfile
FROM maven:3.9.7-eclipse-temurin-22-alpine as build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean install

FROM eclipse-temurin:22-jre-ubi9-minimal as runtime
COPY --from=build /app/target/demo-app-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]
```

## Build API Test by Newman

```Dockerfile
FROM postman/newman:6-alpine

COPY ./collection /etc/newman

CMD ["run", "postman_collection.json", \
    "-e", "postman_environment.json", \
    "-d", "datatest.csv", \
    "--reporters", "cli,junit", \
    "--reporter-junit-export", "results/report.xml"]
```


