# Create volume
```bash
$ docker volume create sample-volume
```

# Run new container
```bash
$ docker run --rm -it -v sample-volume:/data --name dbstore ubuntu /bin/bash 
```

# Create new file in container
```bash
$ cd data && touch new_data1.txt && touch new_data2.txt
```

# Backup volume to compressed file
```bash
$ docker run --rm -v sample-volume:/volume \
    -v $(pwd):/backup \
    busybox \
    tar czvf /backup/backup.tar.gz -C /volume .
```

# restore to docker volume
```bash
$ docker volume create sample-volume-restore
```

```bash
$ docker run --rm \
    -v sample-volume-restore:/volume \
    -v $(pwd):/backup \
    busybox  \
    tar xzvf /backup/backup.tar.gz -C /volume
```

# Run new container with restore docker volume
```bash
$ docker run --rm -it -v sample-volume-restore:/data alpine sh
```
