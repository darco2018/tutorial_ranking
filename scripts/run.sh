#!/usr/bin/env bash

source ./scripts/docker-config.sh

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Starting the script to compile, skip tests, build the JAR, create an image from Dockerfile and run the app in the Docker container, link to the database"
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Building the project with Maven Wrapper (skipping tests)... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
# always adds compiled changes
./mvnw clean package -DskipTests


echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Building ${app_image} image from  Dockefile... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
# the whole image is always created anew, rather than adding new changes
echo "docker build -f ./docker/Dockerfile -t ${app_image} \
            --build-arg jar_name=${app_jar} ./"

docker build -f ./docker/Dockerfile -t ${app_image} \
            --build-arg jar_name=${app_jar} ./


echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Starting the script link-to-db.sh ... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> (expecting the database container to be up with db-up.sh)... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
./scripts/link-to-db.sh

sleep 15
curl http://localhost:${serverport}/skills/GE

# to verify you're connected to db
#docker exec -it app-container bash
#cat etc/hosts
#you will find 172.17.0.3	db 567ae21e509f
#//------------------------------------
