FROM postman/newman:latest

VOLUME [ "/etc/newman/postman_collection.json", \
    "/etc/newman/postman_environment.json", \
    "/etc/newman/results"]

CMD ["run", "postman_collection.json", \
    "--environment", "postman_environment.json", \
    "--reporters", "cli,junit", \
    "--reporter-junit-export", "results/report.xml"]