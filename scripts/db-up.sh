#!/usr/bin/env bash

source ./scripts/config.sh

echo "Starting the database container..."
docker run -d  --name  ${db_container_name} \
    -p 6604:3306 \
    --env="MYSQL_ROOT_PASSWORD=${dbroot_pass}" \
    --env="MYSQL_PASSWORD=${dbpass}" \
    --env="MYSQL_USER=${dbuser}" \
    --env="MYSQL_DATABASE=${database}" \
    mysql

echo "Fetching logs from the database container..."
docker logs ${db_container_name}

echo "Inspecting the database container..."
docker inspect ${db_container_name}

#docker exec -it db_container_name bash
#mysql -u tut_user -ptut_pass
#show databases;

