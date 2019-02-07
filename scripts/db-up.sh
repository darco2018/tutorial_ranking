#!/usr/bin/env bash

source ./scripts/docker-config.sh

echo "Starting the database container... Reading MYSQL env variables from file..."
docker run -d --rm \
    --name  ${db_container} \
    -p 6604:3306 \
    --env-file=docker/config-mysql \
    ${db_image}

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Fetching logs from the database container..."
docker logs ${db_container}

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Inspecting the database container..."
docker inspect ${db_container}

#docker exec -it db_container bash
#mysql -u tut_user -ptut_pass
#show databases;

