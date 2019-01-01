#!/usr/bin/env bash

source ./scripts/docker-config.sh

echo "Stopping the database container..."
docker stop ${db_container}

#echo "Removing the database container..."
#docker rm ${db_container}