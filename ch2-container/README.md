# Chapter 2: Explore Container

## Docker Management Command

### Command Line Pattern

```sh
docker {resource} {options} {command}
```

## Docker Alias

### Full Form Command

```sh
docker container
```

### Short Form (alias)

```sh
docker
```

### Example

```sh
# Get container process
docker container ps

# Get container process
docker ps
```


## Run your own command

```sh
docker run {image name} {command}
```

## Find log in Container

#### Get log event in container

```sh
docker logs <container>
```

#### Feed log event continuous

```sh
docker logs -f <container> --tail 10
```

-f, --follow : follow container log

--tail {no of line} : tailing logs with latest line


## Explore your container

```sh
docker exec -it {container_name or container hash} /bin/bash
```