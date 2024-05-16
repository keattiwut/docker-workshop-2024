# Chapter 3: Create Your Own Image

## Create From Docker Containers

### Run nginx 

```sh
# Pulling
docker pull nginx:lastest

# Run container
docker run -t nginx-origin nginx
```


### Customize index.html

```sh
docker exec -it nginx /bin/bash

cd /etc/nginx/conf.d

cat default.conf

cd /usr/share/nginx/html
```


### Check Linux Release

```sh
cat /etc/*release*
```


### Install VIM editor

```sh
apt-get update && apt-get install vim

vi index.html
```


### Pack to new Image

```sh
docker commit -c {change message} {container-id} {repository-name}:{tag}
```


### Review New Image

```bash
# Explore your image
docker image inspect {image}

# Trace your image
docker image history {image}
```


### Run Container with Override Argument

```Dockerfile
FROM alpine:3.17
ENTRYPOINT [ "ping" ]
CMD [ "-c", "3", "8.8.8.8" ]
```

```bash
docker container run --rm -it my-ping:v1 -w 5 127.0.0.1
```

---
## Create From Dockerfile (As Blueprint)

```Dockerfile
FROM nginx:latest
COPY hello.html /urs/share/nginx/html/index.html
```

```bash
docker build -t my-nginx:v2 .
```



## Multi-Stage Build

```Dockerfile
FROM nginx:latest as build
COPY hello.html /urs/share/nginx/html/index.html

FROM nginx:latest as runtime
COPY --from=build /urs/share/nginx/html/index.html /urs/share/nginx/html/index.html
```


## Best Practice for Docker Image

### Keep Simple Image Layer

#### Don't

```Dockerfile
RUN apt-get update
RUN apt-get install -y ca-certificates
RUN rm -rf /var/lib/apt/lists/*
```

#### Do
```Dockerfile
RUN apt-get update \
    && apt-get install -y ca-certificates \
    && rm -rf /var/lib/apt/lists/*
```

### Always add nessesary file to images

```bash
touch .dockerignore
```

.gitignore
```.gitignore~  
/node_module
.git
```


## Best Practice for Image Tag

### Keep Consistent Pattern

```Dockerfile
{organization}/{image name}:{version}
```

```Dockerfile
sck/mynginx:1.0
```

### Avoid to use 'latest'

#### Don't

```bash
docker image pull sck/mynginx:latest
```

```Dockerfile
FROM sck/mynginx:latest
```


#### Do

```bash
docker image pull sck/mynginx:1.0
```

```Dockerfile
FROM sck/mynginx:1.0
```

```bash
docker image pull sck/mynginx@sha256:45b23dee08af5e43a7fea6c4cf9c25ccf269ee113168c19722f87876677c5cb2`
```
