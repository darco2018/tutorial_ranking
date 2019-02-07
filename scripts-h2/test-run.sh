#!/usr/bin/env bash

source ./scripts/docker-config.sh


# Pull the latest builder image from remote repository
#docker pull ustrd/tut-app:builder || true

# Only build the 'builder' stage, using pulled image as cache
docker build \
  --target builder \
  --cache-from ustrd/test-run:builder \
  -t ustrd/test-run:builder \
  -f ./docker/Dockerfile-test-run .

# Pull the latest runtime image from remote repository
# (This may or may not be worthwhile, depending on your exact image)
# docker pull ustrd/tut-app:latest || true

# Don't specify target (build whole Dockerfile)
# Uses the just-built builder image and the pulled runtime image as cache
docker build \
  --cache-from ustrd/tut-test-run:builder \
  --cache-from ustrd/tut-test-run:latest \
  -t ustrd/tut-test-run:$DOCKER_APP_IMAGE_VERSION \
  -t ustrd/tut-test-run:latest \
   -f ./docker/Dockerfile-test-run .

docker run -it --rm \
  -p 8080:8080 \
  ustrd/tut-test-run:latest

# Push runtime images to remote repository
# docker push ustrd/tut-app:$DOCKER_IMAGE_VERSION
# docker push ustrd/tut-app:latest

# Push builder image to remote repository for next build
# docker push ustrd/tut-app:builder