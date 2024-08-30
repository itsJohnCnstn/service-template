package com.johncnstn.servicetemplate.kafka.producer;

import com.johncnstn.servicetemplate.kafka.model.KafkaEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    public static final String TOPIC = "test_topic";

    private final KafkaTemplate<UUID, KafkaEvent> kafkaTemplate;

    public void produceEvent(KafkaEvent event) {
        var key = event.key();
        kafkaTemplate.send(TOPIC, key, event);
        log.info("Producer produced the message {}", event);
    }
}
