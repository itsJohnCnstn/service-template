package com.johncnstn.servicetemplate.cassandra;

import com.johncnstn.servicetemplate.cassandra.table.KafkaEventTable;
import com.johncnstn.servicetemplate.model.UnexpectedError;
import com.johncnstn.servicetemplate.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@Api(value = "Tables")
public interface CassandraApi {
    String createEventPath = "/api/v1/tables";
    String listEventsPath = "/api/v1/tables";

    @ApiOperation(
            value = "Create a table",
            nickname = "createTable",
            notes = "",
            response = KafkaEventTable.class,
            tags = {
                "tables",
            })
    @ApiResponses(
            value = {
                @ApiResponse(code = 201, message = "Create foo", response = KafkaEventTable.class),
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
    ResponseEntity<KafkaEventTable> createTable(
            @ApiParam(value = "", required = true) @Valid @RequestBody KafkaEventTable table);

    @ApiOperation(
            value = "List all kafka events from cassandra",
            nickname = "listKafkaEvent",
            notes = "",
            response = KafkaEventTable.class,
            responseContainer = "List",
            tags = {
                "events",
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        code = 200,
                        message = "A paged array of kafka events",
                        response = User.class,
                        responseContainer = "List"),
                @ApiResponse(
                        code = 200,
                        message = "unexpected error",
                        response = UnexpectedError.class)
            })
    @RequestMapping(
            value = listEventsPath,
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<KafkaEventTable>> listEvents(
            @Min(1)
                    @ApiParam(value = "", defaultValue = "1")
                    @Valid
                    @RequestParam(value = "page", required = false, defaultValue = "1")
                    Integer page,
            @Min(1)
                    @Max(100)
                    @ApiParam(value = "", defaultValue = "30")
                    @Valid
                    @RequestParam(value = "size", required = false, defaultValue = "30")
                    Integer size);
}
