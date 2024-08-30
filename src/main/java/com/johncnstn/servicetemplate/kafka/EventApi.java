package com.johncnstn.servicetemplate.kafka;

import com.johncnstn.servicetemplate.kafka.model.KafkaEvent;
import com.johncnstn.servicetemplate.model.UnexpectedError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Validated
@Api(value = "Events")
public interface EventApi {

    String createEventPath = "/api/v1/events";

    @ApiOperation(
            value = "Create an event",
            nickname = "createEvent",
            notes = "",
            response = KafkaEvent.class,
            tags = {
                "events",
            })
    @ApiResponses(
            value = {
                @ApiResponse(code = 201, message = "Sent event", response = KafkaEvent.class),
                @ApiResponse(
                        code = 200,
                        message = "unexpected error",
                        response = UnexpectedError.class)
            })
    @RequestMapping(
            value = createEventPath,
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<KafkaEvent> createEvent(
            @ApiParam(value = "", required = true) @Valid @RequestBody KafkaEvent event);
}
