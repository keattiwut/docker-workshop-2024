
# Chapter 4: Docker Volume

## Share And Persist Data with Volume

## Persistent Your Data by Simple Volume 

```bash
docker container run --name demo alpine /bin/sh -c 'echo "This is a test" > sample.txt'

docker container diff demo
```

```bash
# Create brand new volume
docker volume create my-volume

# Inspect detail of volume
docker volume inspect my-volume

# Run a alpine container 
docker container run --name test2 -it --rm \
     -v my-volume:/data \
     alpine:latest /bin/bash

# Run another container (CentOS)
docker container run --name test2 -it --rm \
     -v my-volume:/app/data \
     centos:7 /bin/bash
```


### Whare data store in host

```bash
docker volume inspect my-volume
```

**Notes**
MacOS and WindowOS not run Docker as Native but Ran as VMs that is cause you can find in that location

```bash
# MacOS
~/Library/Containers/com.docker.docker/Data/vms/0

# Access to volume by VM (Docker)
docker container run -it --privileged --pid=host \
    debian nsenter -t 1 -m -u -n -i sh

# Access to path from 'docker volume inspect`
cd /var/lib/docker/volumes
```

### Mount Host Volume to Container


#### Workshop 1: Simeple Mount

```bash
docker container run --rm -it \
    -v $(pwd)/data.d:/app/src \
    alpine:latest /bin/sh
```
#### Workshop 2 Dynamic Nginx Page

```bash
docker container run --rm -d -p 8080:80 \
    -v $(pwd)/web.d:/usr/share/nginx/html/ \
    nginx:latest
```

#### Workshop 3 Nginx bind mount

```bash
touch /example.log
cat >/example.conf <<EOF
server {
  listen 80;
  server_name localhost;
  access_log /var/log/nginx/custom.host.access.log main;
  location / {
    root /usr/share/nginx/html;
    index index.html index.htm;
  }
}
EOF
```



```bash
# Clear Container

docker container rm $(docker container ls -aq)
docker volume rm $(docker volume ls -q)
```

## Create Volume in Dockerfile

```bash
docker image pull mongo

docker image inspect mongo

docker image inspect \
    --format='{{json .Config.Volumes}}' \
    mongo | jq .

docker run --name my-mongo -d mongo

# Inspect my-mongo
docker container inspect my-mongo
#or
docker inspect --format '{{json .Mounts}}' my-mongo | jq .

```




```bash
# For MacOS
brew install jq

# For Window via Chocolatey
choco install jq

# For Window via Winget
winget install -e --id stedolan.jq
```



---
# Using Docker as Development Environment
## JAVA

```bash
docker container run --rm -it \
    -w /app \
    -v $(pwd):/app \
    -v $(pwd)/target.d:/app/target \
    eclipse-temurin:22.0.1_8-jdk-ubi9-minimal \
    ./mvnw install
```

```Dockerfile
FROM eclipse-temurin:22.0.1_8-jdk-ubi9-minimal as build
WORKDIR /app
COPY $(pwd) /app
VOLUME $(pwd)/target.d /app/target
CMD ["./mvnw", "install"]
```

```bash
docker container run --rm -it \
    -v ~/.m2/:/root/.m2/ \
    -v ./target.d:/app/target/ \
    jdk-22-builder
```

#### Create File "Dockerfile.runtime"
```Dockerfile
FROM eclipse-temurin:22.0.1_8-jdk-ubi9-minimal as build
WORKDIR /app
COPY $(pwd) /app
VOLUME /root/.m2
RUN ./mvnw install

FROM clipse-temurin:22.0.1_8-jre-ubi9-minimal as runtime
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java"]
CMD ["-jar", "demo-0.0.1-SNAPSHOT.jar"]
```

**Build demo-app**
```bash
docker image build -t demo-app:v1 -f Dockerfile.runtime .
```
**Run Demo application**
```bash
docker container run --rm -d --name demo-app demo-app:v1
```

## JAVA Auto Restart for Development

```Dockerfile
FROM eclipse-temurin:22.0.1_8-jdk-ubi9-minimal as build
WORKDIR /app
VOLUME /root/.m2/ /app
ENTRYPOINT [ "./mvnw" ]
CMD ["spring-boot:run"]
```

```bash
# Build
docker image build -t demo-app:v2 .

# Run
docker container run --rm -d --name demo-app-v2 \
    -v ~/.m2:/root/.m2 \
    -v $(pwd):/app/ \
    -p 8080:8080 \
    demo-app:v2
```

