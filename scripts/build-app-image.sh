#!/usr/bin/env bash

source ./scripts/docker-config.sh

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Building ${app_image} image from  Dockefile... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
# the whole image is always created anew, rather than adding new changes
echo "docker build -f ./docker/Dockerfile -t ${app_image} \
            --build-arg jar_name=${app_jar} ./"

docker build -f ./docker/Dockerfile -t ${app_image} \
            --build-arg jar_name=${app_jar} ./

