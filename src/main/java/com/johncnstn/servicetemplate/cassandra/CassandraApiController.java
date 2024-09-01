package com.johncnstn.servicetemplate.cassandra;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.johncnstn.servicetemplate.cassandra.repository.KafkaEventCassandraRepository;
import com.johncnstn.servicetemplate.cassandra.table.KafkaEventTable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class CassandraApiController implements CassandraApi {

    private final KafkaEventCassandraRepository repository;

    @Override
    public ResponseEntity<KafkaEventTable> createTable(KafkaEventTable table) {
        var savedTable = repository.save(table);
        log.info("Created table -> {}", savedTable);
        return new ResponseEntity<>(savedTable, CREATED);
    }

    @Override
    public ResponseEntity<List<KafkaEventTable>> listEvents(Integer page, Integer size) {
        return new ResponseEntity<>(repository.findAll(), OK);
    }
}
