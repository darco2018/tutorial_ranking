#!/usr/bin/env bash

source ./scripts/docker-config.sh

docker build  -f ./docker/Dockerfile-test-package \
    -t ustrd/tut-test-pack:latest \
    --cache-from ustrd/tut-test-pack:latest .

docker run -it --rm \
    -v tutorialpedia-target:/usr/src/app/target \
    ustrd/tut-test-pack package
