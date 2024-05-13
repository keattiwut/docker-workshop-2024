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




# Chapter 1: Run Your First Container

### Hello Docker

`docker pull hello-world`

`docker container run hello-world`

### Create Your Own Docker

`touch Dockerfile`

`docker build -t hello-my-world .`



# Chapter 2: Explore Container

### Run your own command

`docker run {image name} {command}`

### Find log in Container

#### Get log event in container

`docker logs <container>`

#### Feed log event continuous

`docker logs -f <container> --tail 10`


### Access to Container

`docker exec -it <container> /bin/bash`


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


## Create From Dockerfile (As Blueprint)
## Multi-Stage Build


# Manage Application Configuration by Environment 
## Command Line
## Dockerfile
## .dotenv



# Share And Persist Data with Volume



# Public Your Image to Docker Hub
## Login
## Public
### How Manage Image Tag


# Using Docker as Development Environment
## JAVA
## Node

# Debugging Application in Container
## JAVA
## Node



# Create Application Cluster by Docker Compose
## Scaling You Applicaiton


# Create Local Test Environment Cluster
## How
## Docker Compose Play that Role



# Cloud Native Design Principle

## Twelvel Factor by Heroku

https://12factor.net/

## Reactive Design Architecture Principle

https://www.reactivemanifesto.org/


References
- https://microservices.io/
- https://microservices.io/patterns/index.html