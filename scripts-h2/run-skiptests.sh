#!/usr/bin/env bash

source ./scripts/docker-config.sh



docker build -f ./docker/Dockerfile-run-skiptests \
    -t ustrd/tut-run  \
    --cache-from ustrd/tut-run:latest  .

docker run -it --rm \
    -p 8080:8080 \
    ustrd/tut-run