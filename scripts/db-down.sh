#!/usr/bin/env bash

source ./scripts/config.sh

echo "Stopping the database container..."
docker stop ${db_container_name}

#echo "Removing the database container..."
#docker rm ${db_container_name}