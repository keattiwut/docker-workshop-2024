# Chapter 1: Run Your First Container

## Hello Docker

```bash
docker image pull hello-world

docker container run hello-world
```

```bash
docker image pull rancher/cowsay

docker container run rancher/cowsay Hello
```




## Create Your Own Docker

```bash
touch Dockerfile`
```

Dockerfile
```Dockerfile
FROM hello-world
```

```bash
docker build -t hello-my-world .
```