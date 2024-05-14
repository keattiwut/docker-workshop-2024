# docker-workshop-2024

Docker Workshop for Containerized and Kubernetes In Action 2024



## Chapter 1: Containerized

# Install Docker Desktop

## General (MacOS, Window)
https://www.docker.com/products/docker-desktop/

## MacOS -> Homebrew
`brew install --cask docker`

## Window -> Chocolatey
`choco install docker-desktop`

## Window -> Winget
`winget install -e --id Docker.DockerDesktop`


---

# Chapter 1: Run Your First Container

### Hello Docker

`docker pull hello-world`

`docker container run hello-world`

### Create Your Own Docker

`touch Dockerfile`

`docker build -t hello-my-world .`


---
# Chapter 2: Explore Container

## Docker Management Command

### Command Line Pattern

`docker {resource} {options} {command}`

### Docker Alias

***Full Form***
`docker container`

***Short Form (Alias)***
`docker`

***Example***
`docker container ps`

`docker ps`


### Run your own command

`docker run {image name} {command}`

### Find log in Container

#### Get log event in container

`docker logs <container>`

#### Feed log event continuous

`docker logs -f <container> --tail 10`


### Access to Container

`docker exec -it <container> /bin/bash`

---
# Chapter 3: Create Your Own Image

## Create From Docker Containers

### Run nginx 
`docker pull nginx:lastest`

`docker rull -t nginx-origin nginx`

### Customize index.html

`docker exec -it nginx /bin/bash`

`cd /etc/nginx/conf.d`

`cat default.conf`

`cd /usr/share/nginx/html`

#### Check Linux Release
`cat /etc/*release*`

#### Install VIM editor
`apt-get update && apt-get install vim`

`vi index.html`

### Pack to new Image

`docker commit -c {change message} {container-id} {repository-name}:{tag}`

### Review New Image

`docker inspect {image}`

`docker history {image}`

### Run Container with Override Argument

```Dockerfile
FROM alpine:3.17
ENTRYPOINT [ "ping" ]
CMD [ "-c", "3", "8.8.8.8" ]
```

`docker container run --rm -it my-ping:v1 -w 5 127.0.0.1`

---
## Create From Dockerfile (As Blueprint)

```Dockerfile
FROM nginx:latest
COPY hello.html /urs/share/nginx/html/index.html
```

`docker build -t my-nginx:v2 .`



## Multi-Stage Build

```Dockerfile
FROM nginx:latest as build
COPY hello.html /urs/share/nginx/html/index.html

FROM nginx:latest as runtime
COPY --from=build /urs/share/nginx/html/index.html /urs/share/nginx/html/index.html
```


### Best Practice for Docker Image

#### Keep Simple Image Layer

```Dockerfile
RUN apt-get update
RUN apt-get install -y ca-certificates
RUN rm -rf /var/lib/apt/lists/*
```

```Dockerfile
RUN apt-get update \
    && apt-get install -y ca-certificates \
    && rm -rf /var/lib/apt/lists/*
```

#### Always add nessesary file to images

`touch .dockerignore`

```.gitignore~  
/node_module
.git
```


### Best Practice for Image Tag

#### Keep Consistent Pattern

`{organization}/{image name}:{version}`

`sck/mynginx:1.0`

#### Always used version

**Don't**

```bash
docker image pull sck/mynginx:latest
```

```Dockerfile
FROM sck/mynginx:latest
```


**Do**

```bash
docker image pull sck/mynginx:1.0
```

```Dockerfile
FROM sck/mynginx:1.0
```

```bash
docker image pull sck/mynginx@sha256:45b23dee08af5e43a7fea6c4cf9c25ccf269ee113168c19722f87876677c5cb2`
```






# Data Volumes and Configuration

#### Print all Linux Environment Variables
`printenv`

## Command Line

`docker container run --rm -d --name my-nginx-v4 my-nginx:v4 -e HELLO=WORLD`


## Dockerfile

```Dockerfile
ENV key=value
```

# Dockerfile Cheetsheet
- https://devhints.io/dockerfile

## .dotenv

```.env
key=value
```

```bash
docker container run --rm --env-file .env -it ubuntu:latest
```

## Configure Application in Build Time

```Dockerfile
ARG BASE_IMAGE_VERSION=12.7-stretch
FROM node:${BASE_IMAGE_VERSION}
```

```bash
docker image build \
    -f Dockerfile.buildarg \
    --build-arg BASE_IMAGE_VERSION=12.7-alpine \
    -t my-node-app-test .
```





---
# Share And Persist Data with Volume

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
# Public Your Image to Docker Hub

1. Access to https://hub.docker.com/
2. Login
3. Create repository
   

## Login
```bash
docker login -u {username}
```

```bash
docker image push {username}/{repository}:{tag}
```


```bash
# Create new image from existing by new repository name
docker image tag {current_tag} {new_repository}:{tag}
```

**Notes**
1. Don't forget to create repository on Docker Hub
2. Tag name pattern in Docker Hub is {username}/{repository}:{tag}




# Docker Scout

## Vulnerability Scanning on Local image

`docker scout overview`






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


---
# Debugging Application in Container
## JAVA
## Node






---
# Docker Networking

## Container Network Models (CNMs)

1. Network Sandbox (Container Network)
|-----------|
| Container |
| (Network) |
|-----------|
2. Endpoint (Container Endpoint)
|--[Endpoint]--|
|   Container  |
|   (Network)  |
|--------------|
3. Network (Bride Network)

--[Network]-|
            V           
|--[Endpoint]--|
|   Container  |
|   (Network)  |
|--------------|

## Explore Docker Network

```bash
docker network ls

docker network inspect bridge
```

**Notes**
Consider the IP address 192.168.1.0 with a subnet mask of 255.255.255.0:
IP Address: 11000000.10101000.00000001.11110000
Subnet Mask: 11111111.11111111.11111111.00000000

Subnet Calculate
- 172.17.0.2/16 11111111.11111111.00000000.00000000 2^16 = 65,536 (ip host) 
    -> ip range 172.17.0.2 to 172.17.255.255

- /26 11000000 2^6 = 64
- /27 11100000 2^5 = 32
- /28 11110000 2^4 = 16
- /29 11111000 2^3 = 8
- /30 11111100 2^2 = 4


```bash
192.168.1.0/26 # => 192.168.1.192 - 192.168.1.255 (64 host ip) 
```

## Bridge Mode

### Create own network

```bash
docker network create --driver bridge sample-net

docker network inspect sample-net | grep 'Subnet'

docker network create --driver bridge --subnet "10.1.0.0/16" test-net

docker container run --name c4 -d --rm \
    --network sample-net \
    alpine:latest \
    ping 127.0.0.1

docker network inspect sample-net

docker container exec -it c3 sh

# In C3 container
ping c4
```


## Multiple Network in Container

```bash
docker network create test-net

docker container run --name c5 --rm -d \
    --network sample-net
    alpine:latest ping 127.0.0.1

docker container run --name c6 --rm -d \
    --network sample-net \
    alpine:latest ping 127.0.0.1

docker network connect test-net c6
```


## mount host network to container

```bash
docker container run --rm -d --name c7 \
    --network host \
    alpine:latest \
    ping 127.0.0.1

docker container exec -it c7 sh

# In Container
ip addr

ip route
```


```bash
# MacOS get IP bridge100
/sbin/ifconfig -a
```

***SDNs is cheap, and each network provides added security by isolating resources from unauthorized access***


[c1]  +  [c2] -> [c3] --------> [c4]
[===network-web=====]
                 [ network-service ]


## Attach network container to another container

```bash
docker network create --driver bridge test-net

docker container run --name web -d \
    --network test-net \
    nginx:alpine

docker network inspect test-net

docker container run -it --rm \
    --network container:web \
    alpine:latest /bin/sh

# In container
apk update && apk add curl
curl localhost
```


## Container Port

## Reserve Proxy





---
# Create Application Cluster by Docker Compose
## Scaling You Applicaiton





---
# Create Local Test Environment Cluster
## How
## Docker Compose Play that Role






---
# Cloud Native Design Principle

## Twelvel Factor by Heroku

https://12factor.net/

## Reactive Design Architecture Principle

https://www.reactivemanifesto.org/

## Service Design Principles

![microservice.io](https://microservices.io/i/MicroservicePatternLanguage.jpg)



---
#References
- https://microservices.io/
- https://microservices.io/patterns/index.html