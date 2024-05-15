FROM postman/newman:6-alpine

COPY ./collection /etc/newman

CMD ["run", "postman_collection.json", \
    "-e", "postman_environment.json", \
    "-d", "datatest.csv" \
    "--reporters", "cli,junit", \
    "--reporter-junit-export", "results/report.xml"]