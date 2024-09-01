package com.johncnstn.servicetemplate.integration.api;

import static com.johncnstn.servicetemplate.kafka.EventApi.createEventPath;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.johncnstn.servicetemplate.integration.AbstractIntegrationTest;
import com.johncnstn.servicetemplate.kafka.model.KafkaEvent;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class EventApiControllerTest extends AbstractIntegrationTest {

    @Test
    public void createHappy() throws Exception {
        // GIVEN
        var eventToSend = new KafkaEvent(UUID.randomUUID());
        // WHEN
        var response =
                createResultActions(eventToSend, createEventPath)
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse();
        var sentEvent = objectMapper.readValue(response.getContentAsByteArray(), KafkaEvent.class);
        // THEN
        assertSoftly(
                softly -> {
                    softly.assertThat(sentEvent.key()).isEqualTo(eventToSend.key());
                });
    }
}
