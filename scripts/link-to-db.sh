#!/usr/bin/env bash

source ./scripts/docker-config.sh

echo "&&&&&&&&&&&&&&&&&& Building the app container & linking to the database container with: &&&&&&&&&&&&&&&&&&"
echo "docker run \
     --name ${app_container} \
     -p ${serverport}:8080 \
     --env-file=docker/docker_env \
     -v LOG_DIR":"LOG_DIR \
     -v MIGRATION_DIR":"MIGRATION_DIR \
     -v /etc/timezone:/etc/timezone:ro \
     --link ${db_container}:mysql \
     -it -d --rm ${app_image}"

     docker run \
     --name ${app_container} \
     -p ${serverport}:8080 \
     --env-file=docker/docker_env \
     -v ${LOG_PATH}${app_name}":"${LOG_PATH}${app_name} \
     -v ${MIGRATION_PATH}${app_name}":"${MIGRATION_PATH}${app_name} \
     -v /etc/timezone:/etc/timezone:ro \
     --link ${db_container}:mysql \
     -it -d --rm ${app_image}


echo "&&&&&&&&&&&&&&&&&& Finished linking the app container with the database container &&&&&&&&&&&&&&&&&&"

docker inspect -f "{{ .HostConfig.Links }}" ${app_container}

