#!/usr/bin/env bash

APP_NAME="tutorialpedia"
APP_NAME_SHORT="tut"
VERSION="0.0.1"

# web
SERVERPORT=9999

# image & container
APP_CONTAINER=${APP_NAME}
APP_IMAGE=${APP_NAME}

# db
MYSQL_ENV_FILE="scripts-mysql/env_mysql"
DB_IMAGE="mysql"
DB_CONTAINER=${APP_NAME_SHORT}-${DB_IMAGE}

# Dockerfile
DOCKERFILE="scripts-mysql/Dockerfile-new"

# jar
APP_JAR=${APP_NAME}-${VERSION}

echo "build -f ${DOCKERFILE} \
            -t ${APP_IMAGE} \
            --build-arg jar_name=${APP_JAR} \
            ./"

docker build -f ${DOCKERFILE} \
            -t ${APP_IMAGE} \
            --build-arg jar_name=${APP_JAR} \
            ./


echo "&&&&&&&&&&&&&&&&&& Building the app container & linking to the database container with: &&&&&&&&&&&&&&&&&&"
echo "docker run \
     --name ${APP_CONTAINER} \
     -p ${SERVERPORT}:8080 \
     --env-file=${MYSQL_ENV_FILE} \
     -v ${LOG_ABS_PATH}${APP_NAME}":"${LOG_ABS_PATH}${APP_NAME} \
     -v ${MIGRATION_ABS_PATH}${APP_NAME}":"${MIGRATION_ABS_PATH}${APP_NAME} \
     -v /etc/timezone:/etc/timezone:ro \
     --link ${DB_CONTAINER}:mysql \
     -it -d --rm ${APP_IMAGE}"

     docker run \
     --name ${APP_CONTAINER} \
     -p ${SERVERPORT}:8080 \
     --env-file=${MYSQL_ENV_FILE} \
     -v ${LOG_ABS_PATH}${APP_NAME}":"${LOG_ABS_PATH}${APP_NAME} \
     -v ${MIGRATION_ABS_PATH}${APP_NAME}":"${MIGRATION_ABS_PATH}${APP_NAME} \
     -v /etc/timezone:/etc/timezone:ro \
     --link ${DB_CONTAINER}:mysql \
     -it -d --rm ${APP_IMAGE}


echo "&&&&&&&&&&&&&&&&&& Finished linking the app container with the database container &&&&&&&&&&&&&&&&&&"

docker inspect -f "{{ .HostConfig.Links }}" ${APP_CONTAINER}

