package com.johncnstn.servicetemplate.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class PostgresInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final int POSTGRES_PORT = 5432;

    private static final String POSTGRES_USER = "service-template";
    private static final String POSTGRES_PASSWORD = "service-template";
    private static final String POSTGRES_DB = "service-template";

    private static final GenericContainer POSTGRES =
            new GenericContainer("postgres:16.2-alpine")
                    .withEnv("POSTGRES_DB", POSTGRES_DB)
                    .withEnv("POSTGRES_USER", POSTGRES_USER)
                    .withEnv("POSTGRES_PASSWORD", POSTGRES_PASSWORD)
                    .waitingFor(Wait.forListeningPort())
                    .withExposedPorts(POSTGRES_PORT);

    static {
        POSTGRES.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applyProperties(applicationContext);
    }

    private static String getHost() {
        return POSTGRES.getContainerIpAddress();
    }

    private static int getPort() {
        return POSTGRES.getMappedPort(POSTGRES_PORT);
    }

    private void applyProperties(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                        "spring.datasource.url:" + getUrl(),
                        "spring.datasource.username:" + POSTGRES_USER,
                        "spring.datasource.password:" + POSTGRES_PASSWORD)
                .applyTo(applicationContext);
    }

    private static String getUrl() {
        return "jdbc:postgresql:"
                + UriComponentsBuilder.newInstance()
                        .host(getHost())
                        .port(getPort())
                        .path(POSTGRES_DB)
                        .toUriString();
    }
}
