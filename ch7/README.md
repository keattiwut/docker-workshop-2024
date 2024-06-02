
## Docker Compose Play that Role


```bash
docker compose up

docker compose down

docker compose build postgres spring-boot-app --no-cache




# Exec Qeury
docker exec -it postgres psql -U myusername -d mydatabase -c "select count(1) from users"

# Check maximum connection
docker exec -it postgres \
    psql -U myusername -d mydatabase \
    -c "SELECT name, setting FROM pg_settings WHERE name = 'max_connections'"
```


#### Use docker run api test

```bash
docker run --rm -it \
    -v $(pwd)/collection:/etc/newman/collection \
    -v $(pwd)/results:/etc/newman/results \
    --network ch7-docker-for-development_default \
    postman/newman:latest \
    run \
        ./collection/postman_collection.json \
        -e ./collection/postman_environment.json \
        -d ./collection/datatest.csv \
        -reporters cli,junit --reporter-junit-export="newman-report.xml"
```
