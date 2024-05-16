# Chapter 1: Run Your First Container

## Hello Docker

```bash
docker pull hello-world
```

```bash
docker container run hello-world
```

## Create Your Own Docker

```bash
touch Dockerfile`
```

Dockerfile
```Dockerfile
FROM hello-world
```

```sh
docker build -t hello-my-world .
```