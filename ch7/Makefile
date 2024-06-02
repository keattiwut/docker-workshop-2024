# Define variables
DOCKER_COMPOSE = docker-compose
IMAGE_NAME = spring-boot-app
RESULTS_DIR = results

.PHONY: all build up test clean

all: build test down

# Build the Docker images
build:
	$(DOCKER_COMPOSE) build

# Start the Docker containers
up:
	$(DOCKER_COMPOSE) up -d

# Run the Newman tests
test:
	$(DOCKER_COMPOSE) up --abort-on-container-exit --exit-code-from newman

# Stop and remove the Docker containers
down:
	$(DOCKER_COMPOSE) down

# Remove the built Docker images
clean: down
	docker rmi $(IMAGE_NAME)

# Create the results directory if it doesn't exist
# $(RESULTS_DIR):
# 	mkdir -p $(RESULTS_DIR)

# Ensure results directory exists before running tests
# test: $(RESULTS_DIR)
