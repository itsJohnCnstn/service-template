package com.johncnstn.servicetemplate.initializer;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.CassandraContainer;

public class CassandraInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final CassandraContainer<?> cassandraContainer =
            new CassandraContainer<>("cassandra:4.0");

    static {
        cassandraContainer.start(); // Start the container before initializing the context
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // Dynamically set the contact points and port for Cassandra
        Map<String, Object> cassandraProperties = new HashMap<>();
        cassandraProperties.put(
                "spring.data.cassandra.contact-points", cassandraContainer.getHost());
        cassandraProperties.put(
                "spring.data.cassandra.port", cassandraContainer.getFirstMappedPort());
        cassandraProperties.put("spring.data.cassandra.local-datacenter", "datacenter1");
        // Add the Cassandra properties to the Spring Environment
        applicationContext
                .getEnvironment()
                .getPropertySources()
                .addFirst(new MapPropertySource("cassandraTestProperties", cassandraProperties));
    }
}
