#!/bin/bash

# Run Newman tests
newman run postman_collection.json \
    --environment postman_environment.json \
    --reporters cli,junit \
    --reporter-junit-export results/report.xml

# Check if the Newman tests passed
if [ $? -ne 0 ]; then
    echo "Newman tests failed"
    exit 1
else
    echo "Newman tests passed"
fi
