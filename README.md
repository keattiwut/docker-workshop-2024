# docker-workshop-2024

Docker Workshop for Containerized and Kubernetes In Action 2024



## Chapter 1: Containerized

# Install Docker Desktop

## General (MacOS, Window)
https://www.docker.com/products/docker-desktop/

## MacOS -> Homebrew
```bash
brew install --cask docker
```

## Window -> Chocolatey
```powershell
choco install docker-desktop
```

## Window -> Winget
```powershell
winget install -e --id Docker.DockerDesktop
```



# Data Volumes and Configuration

#### Print all Linux Environment Variables

```bash
printenv
```

## Command Line

```bash
docker container run --rm -d --name my-nginx-v4 my-nginx:v4 -e HELLO=WORLD
```


## Dockerfile

```Dockerfile
ENV key=value
```

# [Dockerfile Cheetsheet](https://devhints.io/dockerfile)


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
# Public Your Image to Docker Hub

1. Access to https://hub.docker.com/
2. Login
3. Create repository
   

## Login

```bash
docker login -u {username}

docker image push {username}/{repository}:{tag}
```

```bash
# Create new image from existing by new repository name
docker image tag {current_tag} {new_repository}:{tag}
```

***Notes***
1. Don't forget to create repository on Docker Hub
2. Tag name pattern in Docker Hub is {username}/{repository}:{tag}




# Docker Scout

## Vulnerability Scanning on Local image

```bash
docker scout overview
```




---
# Cloud Native Design Principle

## [Twelve Factors](https://12factor.net/)
## [Reactive Design Architecture Principle](https://www.reactivemanifesto.org/)
## [Microservice Service Design](https://microservices.io/)
![microservice.io](https://microservices.io/i/MicroservicePatternLanguage.jpg)




---
# References
- [microservice pattern](https://microservices.io/)
- [container-structure-test](https://github.com/GoogleContainerTools/container-structure-test)
- [semeticversion](https://semver.org/)