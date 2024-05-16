
---
# Create Local Test Environment Cluster
## How
```bash
# postgres db
docker pull postgres:alpine

docker volume create pg-data

docker network create db-network

docker run -d --rm --name postgres \
    -e POSTGRES_USER=dockeruser \
    -e POSTGRES_PASSWORD=dockerpass \
    -e POSTGRES_DB=pets \
    -v pg-data:/var/lib/postgresql/data \
    -v $(pwd)/db:/docker-entrypoint-initdb.d \
    --network db-network \
    -p 5432:5432 \
    postgres:alpine

# postgress
docker volume create admin-data

docker run -d --rm --name pgadmin \
    -e PGADMIN_DEFAULT_EMAIL=admin@acme.com \
    -e PGADMIN_DEFAULT_PASSWORD=admin \
    -v admin-data:/var/lib/pgadmin \
    --network db-network \
    -p 5050:80 \
    dpage/pgadmin4
```


```yaml
services: 
  db:
    image: postgres:alpine
    environment:
    - POSTGRES_USER=dockeruser
    - POSTGRES_PASSWORD=dockerpass
    - POSTGRES_DB=pets
    volumes:
    - pg-data:/var/lib/postgresql/data
    - ./db:/docker-entrypoint-initdb.d
  
  pgadmin:
    image: dpage/pgadmin4
    ports:
      - "5050:80"
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@acme.com
      PGADMIN_DEFAULT_PASSWORD: admin
    volumes:
      - pgadmin-data:/var/lib/pgadmin

volumes: 
  pg-data:
  pgadmin-data:
```

```bash
docker compose up -d

docker compose ps

docker volume ls

docker network ls
```
