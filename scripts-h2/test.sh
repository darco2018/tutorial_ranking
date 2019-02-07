#!/usr/bin/env bash



APP_NAME=tut
VOLUME_NAME=tutorialpedia

docker build  -f ./docker/Dockerfile-test-package \
    -t ustrd/"$APP_NAME"-test-pack:latest \
    --cache-from ustrd/tut-test-pack:latest .

docker run -it --rm \
    -v "$VOLUME_NAME"-target:/usr/src/app/target \
    ustrd/tut-test-pack test
