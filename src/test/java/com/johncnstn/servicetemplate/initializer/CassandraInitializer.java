package com.johncnstn.servicetemplate.initializer;

import com.datastax.oss.driver.api.core.CqlSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.net.InetSocketAddress;

// @Testcontainers
public class CassandraInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final int CASSANDRA_PORT = 9042;
    private static final String CASSANDRA_VERSION = "cassandra:latest";
    private static final String CASSANDRA_DATACENTER = "datacenter1";
    private static final String CASSANDRA_KEYSPACE = "service_template";

    private static final CassandraContainer<?> cassandra =
            new CassandraContainer<>(CASSANDRA_VERSION)
                    .withExposedPorts(CASSANDRA_PORT)
                    .waitingFor(Wait.forListeningPort());

    static {
        cassandra.start();
        var session =
                CqlSession.builder()
                        .addContactPoint(
                                new InetSocketAddress(
                                        cassandra.getHost(), cassandra.getFirstMappedPort()))
                        .withLocalDatacenter("datacenter1")
                        .build();
        createKeyspace(session);
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        applyProperties(applicationContext);
    }

    private void applyProperties(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                        "spring.cassandra.contact-points:" + getHost(),
                        "spring.cassandra.port:" + getPort(),
                        "spring.cassandra.local-datacenter:" + CASSANDRA_DATACENTER,
                        "spring.cassandra.keyspace-name:" + CASSANDRA_KEYSPACE,
                        "spring.cassandra.schema-action: create-if-not-exists")
                .applyTo(applicationContext);
    }

    private static String getHost() {
        return cassandra.getHost();
    }

    private static int getPort() {
        return cassandra.getFirstMappedPort();
    }

    static void createKeyspace(CqlSession session) {
        session.execute(
                "CREATE KEYSPACE IF NOT EXISTS "
                        + CASSANDRA_KEYSPACE
                        + " WITH replication = \n"
                        + "{'class':'SimpleStrategy','replication_factor':'1'};");
    }
}
