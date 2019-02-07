docker history --no-trunc  tutorialpedia:latest
docker inspect <containerid>
docker tag spring-boot-app   baeldung/spring-boot-app:.0.0.1

###----------------- DOCKERIZE DB ------------------
Start MYSQL DB:
######./scripts/db-up.sh  &  ./scripts/db-down.sh

Start MYSQL CLIENT:

``
docker run -it --link tut-mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
``

or

``
docker exec -it tut-mysql bash
``

``
root@d3fd4831d4a0:/# mysql -u tut_user -ptut_pass
``


###----------------- UNDOCKERIZED APP ------------------
##### Run the app UNdockerized with H2-profile (set in application.properties)
``
mvn spring-boot:run
``

or

``
java  -jar target/tutorial_ranking-0.0.1-SNAPSHOT.jar
``

or
##### with Mysql-profile (you must change db url to localhost:6604. Turn off with Ctrl + C ( not Z !!!))

``
mvn spring-boot:run -Dspring.profiles.active=mysql -DskipTests=true
``
or

``
java  -Dspring.profiles.active=mysql -jar target/tutorial_ranking-0.0.1-SNAPSHOT.jar -DskipTests=true
``
``
###----------------- DOCKERIZED APP ------------------
Build app image
``
docker build -t tutorial .
``

##### Run Docker container with Mysql-profile (set in Dockerfile) & migration scripts on host

######./scripts/run.sh

and then

``
docker exec -it tut-app /bin/bash
``

``
curl http://localhost:8080
``

NOTE: make sure container name is used after the --link and in spring.datasource.url=jdbc:mysql://XYZ:3306/tutorial

NOTE: when using "java -jar ....jar" , no changes made to the app will be visible as long as you still use the same image for the app

NOTE: When you start the app, you must have mysql container running

NOTE: It's possible to run undockerized app with H2 when Mysql container is running .

