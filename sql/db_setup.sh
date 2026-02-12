#!/bin/bash

# create instance
docker run --name sql-practice \
       	-e POSTGRES_PASSWORD=password \
       	-d -p 5433:5432 \
       	postgres

# Wait for Postgres to start up
echo "Waiting 10 seconds for Postgres to initialize..."
sleep 10

# import data
psql -h localhost -p 5433 -U postgres -d postgres -f clubdata.sql

