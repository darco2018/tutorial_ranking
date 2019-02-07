

pipeline {
    // Blocks must only consist of Sections, Directives, Steps, or assignment statements
    //Sections contain one or more Directives or Steps.
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5')) //numToKeepStr: buildlogs
        skipDefaultCheckout()
    }
    parameters {
        // user=ustrd among Jenkins env vars
        string(name: 'user', defaultValue: 'ustrd', description: 'A user that triggers the pipeline')
    }

    environment {
        /*NOTES:
        lo_var="lowercaseOK" can be called ${LO_VAR}
        black var is interpolated, green is a string
        app_jar='${env.app_name}' - singel quotes dont interpolate variables!!!*/

        app_name = "kroliczek"
        version = "0.0.1"
        app_jar = "${env.app_name}-${env.version}"
        serverport = 9999
        app_image = "${env.app_name}"
        app_container = "${env.app_name}"
        db_image = "mysql"
        db_container = "tut-mysql"

        LOG_ABS_PATH = "/var/log/"
        MIGRATION_ABS_PATH = "/var/migration/"
        // JENKINS_URL=http://185.157.12.162:7777/

    }

    tools {
        maven 'M3'
    }

    stages {
        stage('Install docker') {
            steps {
                echo "Pipeline triggered by ${params.user}" // not working?!
                sh 'java -version' // Jenkins container's Java?
                sh 'which java'
                script {
                    // adding DockerTool plugin
                    def dockerTool = tool name: 'myDocker', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
                    withEnv(["DOCKER=${dockerTool}/bin"]) {
                        //stages
                    }
                }
                //for testing purposes (each sh has its own interpreter - you start afresh each time
                echo 'Careful with variables:'
                //sh '$PWD' // interpolated by Shell :  /var/jenkins_home/workspace/ia_d-06-poll-scm-push-hub_master  Permission denied
                //sh "$PWD" // interpolated in this file  by Groovy:  / Permission denied
               // sh 'docker images' can be used
                sh 'printenv'

            }
        }

        stage('Checkout') {
            steps {
                deleteDir()
                //ALTENATIVE: checkout scm
                git branch: 'master', credentialsId: 'jenkins-webhook-id', url: 'https://github.com/darco2018/tutorial_ranking'

            }
        }

        // if app doesnt even build, then no point of testing later(long process)
        stage('Build JAR') {
            steps {
                echo "All project\'s dependencies are reused. They're loaded from host\'s .m2 as Jenkins has bind mount in my run jenk command"
                echo 'Problem: all dependencies are outside th container, but they could be inside. Container uses volume. '
                sh 'mvn clean package -DskipTests'
                echo 'Problem: Jar is built outside a container. JAR in docker/ dir has been built'
            }
        }

        // if unit tests fail, then no the point of building image later
        stage('Unit tests/Sonar') {
            // A stage directive can have either a parallel or steps directive but not both,  also it cannot have “agent” or “tools”

            steps {
                //sh 'docker build --no-cache -t my-image:1 -f ./Dockerfile-mvn  .' // how will i know that they failed?
                //sh 'docker run --rm  -v "$HOME/.m2":/root/.m2  my-image:1 /bin/sh'
                script {             // docker here is Jenkins global variable, on which we can call methods from the plugin
                    /*sh 'logback.xml'
                    sh 'docker build -t zzz -f ./Dockerfile-unittests  .'
                    sh '(docker run -i --name zzzcont -v "$HOME/.m2:/root/.m2" zzz) >  testfile.txt'  // grep  "FAILURE" | cat '  testfile.txt
                    sh 'cat testfile.txt'
                    sh 'docker wait zzzcont '*/

                    try {
                        sh './unittests_in_docker.sh'
                        currentBuild.result = 'SUCCESS'
                    } catch (Exception err) { // intercepts exit code from the script
                        currentBuild.result = 'FAILURE'
                        //this msg will be pronted at the end of log
                        error 'The build has been aborted due to error or test failure when running unit test in a docker container.'
                    } finally{
                        echo "Unit tests in dockerized container: ${currentBuild.result}"
                        junit 'target/surefire-reports/TEST-*.xml'
                    }






                    //sh 'docker run -i -v `pwd`:/opt/myapp -w /opt/myapp mydockerimage /bin/bash -c "./setup_dev_env.sh && make all"'


                    /*
                    unittests_img = docker.build("ustrd/unittests", " -f ./Dockerfile-unittests  .") //--no-cache -v \"$HOME/.m2\":/root/.m2

                    echo 'Problem: I cant see any output of mvn clean test'
                    //unittests_container = unittests_img.run("--name aaa_tests", "mvn clean test")
                    unittests_container = unittests_img.withRun("--name aaa_tests  -i"){
                        sh "exit ${docker wait ${container.id}}" // should block until the container exits.
                    }

*/

                    //sh 'mvn clean test'
                  /*  mvn_test_container.inside() {
                        sh 'echo "Tests passed"'
                    }

                    // withRun -> like run but stops the container as soon as its body exits,
                    docker.image('mysql:5').withRun('-e "MYSQL_ROOT_PASSWORD=my-secret-pw" -p 3306:3306') { c ->
                        *//* Wait until mysql service is up *//*
                        sh 'while ! mysqladmin ping -h0.0.0.0 --silent; do sleep 1; done'
                        *//* Run some tests which require MySQL *//*
                        sh 'make check'
                    }*/
                }

                //mvn -e -X for debug; cant we run it in container ? mkyoung how to run unit test with maven
                // sonar  NOT working in parallel step with mvn test
                //sh "mvn  sonar:sonar -Dsonar.host.url=${env.SONARQUBE_HOST}"

            }
            post {
                always {
                    echo 'Problem: test report is not generated in Jenkins'

                   // junit 'target/surefire-reports/TEST-*.xml'
                }
            }
        }


        stage('Build app image') {
            steps {
                script {

                    //it also works: myImg = docker.build("ustrd/tutorialpedia:$env.BUILD_NUMBER", "-f ./docker/Dockerfile --build-arg SOMEVAR=dummyvalue . ")
                    sh 'scripts/build-app-image.sh'

                    app_img = docker.build("ustrd/tutorialpedia", "-f docker/Dockerfile .")
                    // docker.inside( Like withRun this starts a container for the duration of the body, but all external commands (sh) launched by the body
                    // run inside the container rather than on the host. These commands run in the same working directory (normally a Jenkins agent workspace)
                    app_img.inside("--entrypoint=''") { // -it ?! by widziec logout
                        sh 'echo "Tests passed"'
                    }
                    /*
                    myImg.inside("--entrypoint=''"){ // turns off Dockerfile entrypoint
                        sh 'ls -al'
                        sh "echo ${app_jar} is app_jar ....." //
                        sh "echo ${APP_JAR} is APP_JAR ....."
                    }
                    */
                }
            }
        }

        stage('Run app') {
            steps {
                script {
                    try {  // database tez powinna byc z custom Dockerfile: ustrd/mysql
                        sh 'scripts/db-up.sh'
                        sh 'scripts/run-app-image.sh'
                        sleep 20
                        echo 'Problem: does it work at all?'
                        retry(10) {
                            sh 'curl -X GET http://172.17.0.1:${serverport}/skills/GE'
                            // curl: (52) Empty reply from server >> when there is no reply from a server, since it is an error for HTTP not to respond anything to a request.
                            //
                            //I suspect the problem you have is that there is some piece of network infrastructure, like a firewall or a proxy, between you and the host in question
                        }

                    } catch (error) {
                        error.printStackTrace()
                    }
                }

                //debugging
                sh 'docker ps -a'
            }
        }

        stage('Integration tests') {
            steps {
                // sh 'mvn clean test'
                echo 'This should run the integration tests against the docker container but is now failing'
            }
            post {
                always {
                    //junit 'target/surefire-reports/TEST-*.xml'
                    echo 'This should generate reports'
                }
                success {
                    echo 'Problem: should archive the image, not the JAR'
                    archiveArtifacts 'docker/*jar'
                }
            }
        }


        stage('Push image') {
            when {
                branch 'master'
            }
            steps {

                // This step should not normally be used in your script. Consult the inline help for details.
                // docker.withRegistry(url[, credentialsId]) {…} >> https://index.docker.io/v1/
                // toolName i url moga byc ominiete
                // Docker Pipeline & Docker Commons Plugin
                /* ALTERNATIVE - not in script step)
                withDockerRegistry(credentialsId: 'dockerhub-credentials', url: 'https://index.docker.io/v1/') {
                    // some block
                }
                */
            script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        myImg = docker.build("ustrd/tutorialpedia:$env.BUILD_NUMBER",
                                "-f ./docker/Dockerfile " +
                                        "--build-arg SOMEVAR=dummyvalue . ")

                        /* Two tags: First, the incremental build number from Jenkins
                         Second, the 'latest' tag.
                          Pushing multiple tags is cheap, as all the layers are reused. */
                        myImg.push("${env.BUILD_NUMBER}")
                        myImg.push("latest")

                    }



                    // There are many features of the Pipeline that are not steps. These are often exposed via global variables,
                    // which are not supported by the snippet generator.

                }

            }

            post {
                always {
                    sh 'scripts/db-down.sh'
                    retry(20) {
                        sh 'curl -X POST http://172.17.0.1:${serverport}/actuator/shutdown'
                    }

                }
            }

        }

    }
}


/*

They dont load the variables into Dockerfile or Jenkins environment:
readFile 'scripts/docker-config.sh'
load './scripts/docker-config.groovy'
sh 'source scripts/docker-config.sh'
*/