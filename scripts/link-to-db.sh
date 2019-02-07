#!/usr/bin/env bash

source ./scripts/docker-config.sh

echo "&&&&&&&&&&&&&&&&&& Building the app container & linking to the database container with: &&&&&&&&&&&&&&&&&&"

echo "docker run \
     --name ${app_container} \
     -p ${serverport}:8080 \
     --env-file=docker/config-mysql \
     -v ${LOG_ABS_PATH}${app_name}":"${LOG_ABS_PATH}${app_name} \
     -v ${MIGRATION_ABS_PATH}${app_name}":"${MIGRATION_ABS_PATH}${app_name} \
     -v /etc/timezone:/etc/timezone:ro \
     --link ${db_container}:mysql \
     -it  --rm ${app_image}"


     docker run \
     --name ${app_container} \
     -p ${serverport}:8080 \
     --env-file=docker/config-mysql \
     -v ${LOG_ABS_PATH}${app_name}":"${LOG_ABS_PATH}${app_name} \
     -v ${MIGRATION_ABS_PATH}${app_name}":"${MIGRATION_ABS_PATH}${app_name} \
     -v /etc/timezone:/etc/timezone:ro \
     --link ${db_container}:mysql \
     -it  --rm ${app_image}


echo "&&&&&&&&&&&&&&&&&& Finished linking the app container with the database container &&&&&&&&&&&&&&&&&&"

docker inspect -f "{{ .HostConfig.Links }}" ${app_container}

