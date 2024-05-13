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



```Dockerfile

```




---
# Public Your Image to Docker Hub
## Login
## Public
### How Manage Image Tag







---
# Using Docker as Development Environment
## JAVA
## Node






---
# Debugging Application in Container
## JAVA
## Node






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