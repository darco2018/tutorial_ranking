#!/usr/bin/env bash

app_name="tutorialpedia"
version="0.0.1"


# jar
app_jar=${app_name}-${version}

# web
serverport=9999

# image & container
app_image=${app_name}
DOCKER_APP_IMAGE_VERSION=03-02-2019
app_container=${app_name}
db_image="mysql"
db_container="tut-mysql"






