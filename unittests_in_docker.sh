#!/usr/bin/env bash

echo "--------------------------------------000. Building unittests image -----------------------------------------------"
    docker build -t ustrd/tutrialpedia:unittests -f ./Dockerfile-unittests  .

echo "--------------------------------------345.  -----------------------------------------------"
echo "345. PWD: $PWD"
ls
echo "--------------------------------------111. Run unittests container -----------------------------------------------"
    docker run -i \
            --name tut-unittests  \
            -v "$PWD/target:/app/target" \
            -v "$HOME/.m2:/root/.m2"  \
            ustrd/tutrialpedia:unittests

            #-v "$HOME/test-results:/target/surefire-reports/" \

    testsPassed=$?

echo "---------------------------------------444. Displaying tests with printf" and echo
#docker ps -a
#docker cp unittests_cont:target/surefire-reports $HOME/test-results


echo "$(<$target/surefire-reports/pl.ust.tr.AppTests.xml)"
printf "%s" "$(<$target/surefire-reports/pl.ust.tr.AppTests.xml)"

# copying between containers is not supported
# docker cp unittests_cont:target/surefire-reports  jenk:/

 echo "---------------------------------------222. Display tests info with tail -------------------------"
    #docker logs unittests_cont | tail -n 20
    #docker wait zzzcont # Block until one or more containers stop, then print their exit codes
    docker rm -f unittests_cont

echo "---------------------------------------333. Exit code information -------------------------"

    echo "Exit code in script is ${testsPassed}"
    exit ${testsPassed}

