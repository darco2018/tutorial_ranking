#!/usr/bin/env bash

#app_image_name="ustrd/tut-app:0.0"
app_image_name="tutorial"
app_container_name="tut-app"

db_image_name="mysql"
db_container_name="tut-mysql"

dbserver=${db_container_name}
dbport="3306"
database="tutorial"
dbroot_pass="password"
dbpass="tut_pass"
dbuser="tut_user"

migration_dirs="/home/ustrd/migration/tutorial:/var/migration"