#!/bin/sh

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

# Start docker if it is not running
sudo systemctl status docker || sudo systemctl start docker

# Check container status (returns 0 if found, non-zero if not)
docker container inspect jrvs-psql
container_status=$?

# User switch case to handle create|stop|start options
case $cmd in 
  create)
  
  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 1	
  fi

  # Check # of CLI arguments
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi
  
  # Create a docker volume for data persistence
  docker volume create pgdata
  
  # Start the container
  # -d: detached, --name: container name, -p: host:container port mapping
  docker run --name jrvs-psql -e POSTGRES_USER="$db_username" -e POSTGRES_PASSWORD="$db_password" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
  
  exit $?
  ;;

  start|stop) 
  # Check instance status; exit 1 if container has not been created
  if [ $container_status -ne 0 ]; then
    echo "Container 'jrvs-psql' does not exist. Use 'create' first."
    exit 1
  fi

  # Start or stop the container based on user input
  docker container $cmd jrvs-psql
  exit $?
  ;;	
  
  *)
  echo 'Illegal command'
  echo 'Usage: ./scripts/psql_docker.sh create|start|stop [db_username] [db_password]'
  exit 1
  ;;
esac
