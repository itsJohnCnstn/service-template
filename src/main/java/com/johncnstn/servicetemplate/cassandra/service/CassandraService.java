package com.johncnstn.servicetemplate.cassandra.service;

import com.johncnstn.servicetemplate.cassandra.repository.KafkaEventCassandraRepository;
import com.johncnstn.servicetemplate.cassandra.table.KafkaEventTable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CassandraService {

    private KafkaEventCassandraRepository kafkaEventCassandraRepository;

    public List<KafkaEventTable> getKafkaEvents() {
        return kafkaEventCassandraRepository.findAll();
    }
}
