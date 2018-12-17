#!/usr/bin/env bash

source ./scripts/config.sh

echo "&&&&&&&&&&&&&&&&&& Building the app container & linking to the database container with: &&&&&&&&&&&&&&&&&&"
echo "docker run \
     --name ${app_container_name} \
     -p 8080:8080 \
     -v ${migration_dirs} \
     -e dbserver=${dbserver}  \
     -e dbport=${dbport} \
     -e database=${database} \
     -e dbuser=${dbuser} \
     -e dbpassword=${dbpass} \
     --link ${db_container_name}:mysql \
     -it -d --rm ${app_image_name}"

     docker run \
     --name ${app_container_name} \
     -p 8080:8080 \
     -v ${migration_dirs} \
     -e dbserver=${dbserver}  \
     -e dbport=${dbport} \
     -e database=${database} \
     -e dbuser=${dbuser} \
     -e dbpassword=${dbpass} \
     --link ${db_container_name}:mysql \
     -it -d --rm ${app_image_name}

echo "&&&&&&&&&&&&&&&&&& Finished linking the app container with the database container &&&&&&&&&&&&&&&&&&"

docker inspect -f "{{ .HostConfig.Links }}" ${app_container_name}

