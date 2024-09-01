#!/bin/bash
set -e

# Start Cassandra in the background
/docker-entrypoint.sh "$@" &

# Wait for Cassandra to be ready
until cqlsh -e "describe keyspaces"; do
    >&2 echo "Cassandra is unavailable - sleeping"
    sleep 5
done

# Execute the CQL script
cqlsh -f /docker-entrypoint-initdb.d/init.cql

# Bring Cassandra to the foreground
fg %1