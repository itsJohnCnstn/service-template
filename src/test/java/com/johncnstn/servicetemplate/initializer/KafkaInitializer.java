package com.johncnstn.servicetemplate.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String KAFKA_IMAGE_NAME = "confluentinc/cp-kafka:latest";
    private static final String SPRING_KAFKA_BOOTSTRAP_SERVERS = "spring.kafka.bootstrap-servers";

    private static final KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse(KAFKA_IMAGE_NAME));

    static {
        kafkaContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        context.getEnvironment()
                .getSystemProperties()
                .put(SPRING_KAFKA_BOOTSTRAP_SERVERS, kafkaContainer.getBootstrapServers());
    }
}
