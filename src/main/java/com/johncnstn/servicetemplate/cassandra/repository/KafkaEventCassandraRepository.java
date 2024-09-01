package com.johncnstn.servicetemplate.cassandra.repository;

import com.johncnstn.servicetemplate.cassandra.table.KafkaEventTable;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaEventCassandraRepository extends CassandraRepository<KafkaEventTable, UUID> {}
