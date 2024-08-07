#APP_VERSION_UNIX := $(shell echo `date -u '+%Y-%m-%d-%H-%M-%S'`-`git rev-parse --short HEAD`)
#APP_VERSION_WIN := $(shell FOR /F %i IN ('git rev-parse --short HEAD') DO set commit=%i & echo %date:~10,4%-%date:~4,2%-%date:~7,2%-%time:~0,2%-%time:~3,2%-%time:~6,2%-%commit%)
PROJECT_NAME := service-template
SERVICE_NAME := $(PROJECT_NAME)-backend
DOCKER_IMAGE := $(SERVICE_NAME):latest

clean:
ifeq ($(OS),Windows_NT)
	gradlew clean
else
	./gradlew clean
endif

build: clean
ifeq ($(OS),Windows_NT)
ifndef skip-check
	gradlew build
else
	gradlew build -x check
endif
else
ifndef skip-check
	./gradlew build
else
	./gradlew build -x check
endif
endif

docker-build: build
ifeq ($(OS),Windows_NT)
	docker build --build-arg APP_VERSION=$(APP_VERSION_WIN) -t $(DOCKER_IMAGE) .
else
	docker build --build-arg APP_VERSION=$(APP_VERSION_UNIX) -t $(DOCKER_IMAGE) .
endif

# Run only infra
docker-compose-up-infra: build
	docker-compose up -d --build

# Run everything
docker-compose-up-all: build
	docker-compose --profile backend up -d --build
