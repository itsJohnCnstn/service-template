CREATE KEYSPACE IF NOT EXISTS service_template
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};