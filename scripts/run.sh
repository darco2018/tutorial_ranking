#!/bin/bash

source ./scripts/config.sh

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Starting the script to compile, skip tests, build the JAR, create an image from Dockerfile-2 and run the app in the Docker container, link to the database"
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Building the project with Maven Wrapper (skipping tests)... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
# always adds compiled changes
./mvnw clean package -DskipTests


echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Building ${app_image_name} from  Dockefile... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
# the whole image is always created anew, rather than adding new changes
docker build -f Dockerfile-2 -t ${app_image_name} ./


echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Starting the script link-to-db.sh ... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> (expecting the database container to be up with db-up.sh)... >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
./scripts/link-to-db.sh

# to verify you're connected to db
#docker exec -it app-container bash
#cat etc/hosts
#you will find 172.17.0.3	db 567ae21e509f
#//------------------------------------
