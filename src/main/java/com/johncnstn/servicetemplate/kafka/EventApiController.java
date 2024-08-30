package com.johncnstn.servicetemplate.kafka;

import static org.springframework.http.HttpStatus.CREATED;

import com.johncnstn.servicetemplate.kafka.model.KafkaEvent;
import com.johncnstn.servicetemplate.kafka.producer.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EventApiController implements EventApi {

    private final KafkaProducer producer;

    @Override
    public ResponseEntity<KafkaEvent> createEvent(KafkaEvent event) {
        producer.produceEvent(event);
        return new ResponseEntity<>(event, CREATED);
    }
}
