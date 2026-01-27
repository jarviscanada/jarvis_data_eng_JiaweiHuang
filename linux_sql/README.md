# Introduction
This Linux Host Agent is a system monitoring tool designed to capture and store hardware specifications and real-time resource utilization data. The core purpose of the project is to provide system administrators and DevOps engineers with a reliable, automated pipeline to monitor cluster health and track performance trends across multiple servers. 

Key technologies used for the project include:
- Linux: tools such as lscpu, vmstat, psql, etc., for system access
- Bash: scripting agent to parse system data and execute SQL commands
- PostgreSQL: relational database for structured data storage
- Docker: containerized PostgresSQL database
- Github: version control management 



# Quick Start
```
# Start a psql instance using psql_docker.sh
./scripts/psql_docker.sh create [db_username] [db_password]
./scripts/psql_docker.sh start

# Create tables using ddl.sql
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql

# Insert hardware specs data into the DB using host_info.sh
./scripts/host_info.sh localhost 5432 host_agent postgres password

# Insert hardware usage data into the DB using host_usage.sh
./scripts/host_usage.sh localhost 5432 host_agent postgres password

# Crontab setup (run host_usage every minute)
crontab -e
# Add the following line to crontab
* * * * * bash [project_path]/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log
```

# Implementation
## Architecture
The architecture consists of multiple Linux hosts running the Agent scripts. These agents communicate with a centralized PostgreSQL database instance running in a Docker container.
![architecture illustration](../assets/monitor_arch.drawio.png)
## Scripts
### psql_docker
This script manages the lifecycle of the PostgreSQL Docker container. It handles the creation of a Docker volume and provides a simple interface for starting or stopping the service.
```
# Create and start the container
./scripts/psql_docker.sh create [username] [password]

# Start/Stop existing container
./scripts/psql_docker.sh start
./scripts/psql_docker.sh stop
```
### host_info.sh
This script is executed once per host to capture hardware specifications. it uses ```lscpu``` to gather details and inserts them into the ```host_info``` table.
```
./scripts/host_info.sh [psql_host] [port] [db_name] [username] [password]
```

### host_usage.sh
This script is designed for periodic execution. It captures real-time data such as free memory and CPU idle percentages using ```vmstat``` and inserts them into ```host_usage```.
```
./scripts/host_usage.sh [psql_host] [port] [db_name] [username] [password]
```
### crontab
Crontab is used to automate the execution of ```host_usage.sh```. We scheduled the script to run every minute so that it ensures a high-resolution time series data for performance analysis.
```
* * * * * bash [project_path]/scripts/host_usage.sh [psql_host] [port] [db_name] [username] [password] > /tmp/host_usage.log
```

## Database Modeling
### ```host_info```
| Column Name  | Data Type | Constraints |
| ------------- | ------------- | ----------- |
| id | SERIAL | Primary Key |
| hostname | VARCHAR | Unique, NOT NULL |
| cpu_number | INT2 | NOT NULL |
| cpu_architecture | VARCHAR | NOT NULL |
| cpu_model | VARCHAR | NOT NULL |
| cpu_mhz | FLOAT8 | NOT NULL |
| l2_cache | INT4 | NOT NULL |
| timestamp | TIMESTAMP | NOT NULL |
| total_mem | INT4 | NOT NULL |

### ```host_usage```
| Column Name  | Data Type | Constraints |
| ------------- | ------------- | ----------- |
| timestamp | TIMESTAMP | NOT NULL |
| host_id | INT4 | Foreign Key (host_info.id) |
| memory_free | INT4 | NOT NULL |
| cpu_idle | INT2 | NOT NULL |
| cpu_kernel | INT2 | NOT NULL |
| disk_io | INT4 | NOT NULL |
| disk_available | INT4 | NOT NULL |

# Test
Testing was conducted in two phases:

- **DDL Testing:** The ```ddl.sql``` script was executed against a fresh database to ensure all tables and constraints were created correctly. Subsequent runs were tested to ensure the ```IF NOT EXISTS``` logic prevented errors.
- **Script Testing:** The Bash scripts were manually executed, and the database was queried using ```SELECT *``` to verify that the captured hardware data matched the output of system commands like ```lscpu``` and ```vmstat```.

# Deployment
The app was deployed via Docker for environemnt consistency. the project was managed through Github using a feature branch workflow. And scheduled data collection is handled by the Linux crontab utility.

# Improvements
1. Update the ```host_info``` script to detect and update records in case if a host?s hardware is upgraded.
2. Implement a bash component that sends a notification if cpu_idle stays below 10% for consecutive intervals.
3. Create a cleanup script to archive or delete usage data older than 30 days to save disk space.

