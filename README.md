# Service Template

Service template for new microservices.

## Tech Stack

- Java 21
- Gradle
- Spring Boot (Web, Data JPA)
- OpenAPI
- PostgreSQL + Flyway
- Docker + Docker Compose
- Spring Test + JUnit 5 + Mockito + AssertJ + Testcontainers
- Lombok, MapStruct

## Architecture

PostgreSQL was chosen as the database. It's is an open source object-relational database system with over 30 years of
active development that has earned it a strong reputation for reliability, feature robustness, and performance.

Openapi was chosen for designing, building, documenting and consuming REST APIs.

Kotlin was chosen for configuring Gradle.

CheckStyle, Jacoco and Pmd plugins were chosen for supporting quality and reliable code.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Project requires Java 21, Make, Docker.

### Installing

Instructions for Java: [AdoptOpenJDK installation](https://adoptopenjdk.net/installation.html?variant=openjdk11)

Instructions for Docker: [Docker installation](https://docs.docker.com/v17.12/install/)

Instructions for Make: [Install make on Windows](http://gnuwin32.sourceforge.net/packages/make.htm)

Build the project:

### Running with Make

#### Running infra using docker-compose

```bash
make docker-compose-up-infra
```

#### Running everything using docker-compose 

```bash
make docker-compose-up
```

## Contributing

When adding new feature to project make sure code satisfies rules made with CheckStyle, Pmd plugins.
Useful documentation:

For CheckStyle: [CheckStyle Rules](https://checkstyle.sourceforge.io/config_coding.html)

For Pmd: [Pmd Java Rules](https://pmd.github.io/pmd-6.20.0/pmd_rules_java_codestyle.html#shortvariable)

Also, minimal test coverage must be 75%. For more information visit
[Jacoco Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
