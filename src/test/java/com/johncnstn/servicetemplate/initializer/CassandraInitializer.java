package com.johncnstn.servicetemplate.initializer;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class CassandraInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final int CASSANDRA_PORT = 9042;
    private static final String CASSANDRA_VERSION = "4.1.6";
    private static final String CASSANDRA_DATACENTER = "datacenter1";
    private static final String CASSANDRA_KEYSPACE = "service_template";

    private static final CassandraContainer<?> CASSANDRA_CONTAINER =
            new CassandraContainer<>("cassandra:" + CASSANDRA_VERSION)
                    .withExposedPorts(CASSANDRA_PORT).waitingFor(Wait.forListeningPort());

    static {
        CASSANDRA_CONTAINER.start(); // Start the container before initializing the context
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        applyProperties(applicationContext);
    }

    private void applyProperties(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                        "spring.data.cassandra.contact-points=" + getHost(),
                        "spring.data.cassandra.port=" + getPort(),
                        "spring.data.cassandra.local-datacenter=" + CASSANDRA_DATACENTER,
                        "spring.data.cassandra.keyspace-name=" + CASSANDRA_KEYSPACE,
                        "spring.data.cassandra.schema-action=create-if-not-exists")
                .applyTo(applicationContext);
        System.out.println("Cassandra running on: " + getHost() + ":" + getPort());
    }

    private static String getHost() {
        return CASSANDRA_CONTAINER.getHost();
    }

    private static int getPort() {
        return CASSANDRA_CONTAINER.getFirstMappedPort();
    }

}
