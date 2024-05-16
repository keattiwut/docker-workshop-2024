

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



