package com.johncnstn.servicetemplate.kafka.consumer;

import com.johncnstn.servicetemplate.kafka.model.KafkaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test_topic", groupId = "com.johncnstn")
    public void consume(KafkaEvent event) {
        log.info("Consumer consume Kafka event -> {}", event);
    }
}
